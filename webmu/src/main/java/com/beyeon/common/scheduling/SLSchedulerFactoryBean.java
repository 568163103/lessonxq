package com.beyeon.common.scheduling;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.scheduling.SchedulingException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.util.MethodInvoker;
import org.springframework.util.ReflectionUtils;

import com.beyeon.common.aop.annotation.Cache;
import com.beyeon.common.cache.CacheSupport;
import com.beyeon.common.util.IpUtil;

/**
 * User: lwf
 * Date: 12-6-15
 * Time: 下午9:05
 */
public class SLSchedulerFactoryBean extends SchedulerFactoryBean implements InstantiationAwareBeanPostProcessor,Ordered {
	private static Logger logger = LoggerFactory.getLogger(SLSchedulerFactoryBean.class);
	private static Map<String,List<MethodInvoker>> jobCaches = new HashMap<String,List<MethodInvoker>>();
	private static Map<String,String> jobZhNames = new HashMap<String,String>();
	private static boolean isDameonServer = false;
	private static Ehcache cache;
	private int order = Ordered.HIGHEST_PRECEDENCE;
	private Map<String,Object> refreshBeans = new HashMap<String, Object>();
	private List<Trigger> triggers;
	private Map<String,String> cronExpressions;
	private CacheSupport cacheSupport;

	public void setTriggers(List<Trigger> triggers) {
		this.triggers = triggers;
	}

	public void setCache(Ehcache cache) {
		SLSchedulerFactoryBean.cache = cache;
	}

	public void setCronExpressions(Map<String, String> cronExpressions) {
		this.cronExpressions = cronExpressions;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public void setCacheSupport(CacheSupport cacheSupport) {
		this.cacheSupport = cacheSupport;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
	}

	private void initCronTrigger() throws Exception {
		Map<String,List<MethodInvoker>> refreshMap = new HashMap<String,List<MethodInvoker>>();
		Iterator<Map.Entry<String,Object>> it = refreshBeans.entrySet().iterator();
		while (it.hasNext()){
			Map.Entry<String,Object> entry = it.next();
			Object beanObject = entry.getValue();Class objectClass = beanObject.getClass();
			Method[] methods = ReflectionUtils.getAllDeclaredMethods(objectClass);
			for (int i = 0; i < methods.length; i++) {
				Cache cacheAnn = methods[i].getAnnotation(Cache.class);
				if(null != cacheAnn && cacheAnn.type() != Cache.GET){
					String refreshExpre = cacheAnn.refreshExpre();
					List<MethodInvoker> invokerList = refreshMap.get(refreshExpre);
					if(null == invokerList){
						invokerList = new ArrayList<MethodInvoker>();
						refreshMap.put(refreshExpre,invokerList);
					}
					MethodInvoker invoker = new MethodInvoker();
					invoker.setTargetObject(beanObject);
					invoker.setTargetMethod(methods[i].getName());
					invoker.prepare();
					invokerList.add(invoker);
					String jobName = objectClass.getSimpleName()+"."+methods[i].getName();
					List oneInvoker = new ArrayList<MethodInvoker>();oneInvoker.add(invoker);
					jobCaches.put(jobName, oneInvoker);
					String jobZhName = cacheAnn.cacheName();
					if(StringUtils.isBlank(jobZhName)){
						jobZhName = jobName;
					}
					jobZhNames.put(jobName, jobZhName);
				}
			}
		}
		createCronTrigger(refreshMap);
	}

	private void createCronTrigger(Map<String,List<MethodInvoker>> refreshMap) throws ClassNotFoundException, NoSuchMethodException, ParseException {
		Iterator<Map.Entry<String,List<MethodInvoker>>> it = refreshMap.entrySet().iterator();
		while (it.hasNext()){
			Map.Entry<String,List<MethodInvoker>> entry = it.next();
			JobDetailImpl jobDetail = new JobDetailImpl();
			jobDetail.setName(entry.getKey());
			jobDetail.setGroup("autoCronTrigger");
			jobDetail.setJobClass(MethodInvokingJob.class);
			jobDetail.setDurability(true);
			jobDetail.getJobDataMap().put("methodInvokers", entry.getValue());
			jobDetail.getJobDataMap().put("jobDetail", jobDetail);
			CronTriggerImpl cronTrigger = new CronTriggerImpl();
			cronTrigger.setName(jobDetail.getName());
			cronTrigger.setGroup(jobDetail.getGroup());
			cronTrigger.setJobName(jobDetail.getName());
			cronTrigger.setJobGroup(jobDetail.getGroup());
			cronTrigger.setCronExpression(this.cronExpressions.get(entry.getKey()));
			cronTrigger.setStartTime(new Date());
			cronTrigger.setJobDataMap(jobDetail.getJobDataMap());
			if(null == this.triggers){
				triggers = new LinkedList<Trigger>();
			}
			this.triggers.add(cronTrigger);
			MethodInvokingJob.runJob(entry.getValue(),true);
		}
		super.setTriggers(this.triggers.toArray(new Trigger[this.triggers.size()]));
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
		return null;
	}

	@Override
	public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
		if(null != AnnotationUtils.findAnnotation(bean.getClass(),Cache.class)){
			refreshBeans.put(beanName, bean);
		}
		return true;
	}

	@Override
	public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
		return pvs;
	}

	@Override
	public void start() throws SchedulingException {
		try {
			String dameonIp = cacheSupport.get("is.dameon.server");
			String localIp = "127.0.0.1=";
			List localIps = IpUtil.getLocalIps();
			if (localIps.size()>0){
				localIp = localIps.get(0)+"=";
			}
			if (StringUtils.isBlank(dameonIp)){
				isDameonServer = true;
			} else {
				if(dameonIp.startsWith(localIp)){
					long exsitTime = Long.valueOf(dameonIp.replace(localIp,""));
					if(System.currentTimeMillis()-exsitTime > 60 * 1000){
						isDameonServer = true;
					}
				}
			}
			if(isDameonServer){
				cacheSupport.setex("is.dameon.server", 60 * 24 * 60 * 60, localIp+System.currentTimeMillis());
				logger.error("当前服务作为主后台服务！");
			}
			this.initCronTrigger();
			super.afterPropertiesSet();
		} catch (Exception e) {
			throw new SchedulingException("初始化定时器失败", e);
		}
		super.start();
	}

	public static void runJob(String methodName){
		List<MethodInvoker> methodInvoker = jobCaches.get(methodName);
		if(null == methodInvoker || methodInvoker.size() == 0){
			logger.debug("未找到该方法的缓存："+methodName);
			return;
		}
		MethodInvokingJob.runJob(methodInvoker,false);
	}

	public static Map<String,String> listJob(){
		return jobZhNames;
	}

	public static class MethodInvokingJob extends QuartzJobBean {
		private List<MethodInvoker> methodInvokers;

		public void setMethodInvokers(List<MethodInvoker> methodInvokers) {
			this.methodInvokers = methodInvokers;
		}

		@Override
		protected void executeInternal(JobExecutionContext context) {
			context.setResult(runJob(this.methodInvokers,false));
		}

		public static String runJob(List<MethodInvoker> methodInvokers,boolean isStart){
			StringBuilder ok = new StringBuilder("ok:");
			StringBuffer notOk = new StringBuffer(" notOk:");
			for (int i = 0; i < methodInvokers.size(); i++) {
				MethodInvoker methodInvoker = methodInvokers.get(i);
				Cache cacheAnn = AnnotationUtils.findAnnotation(methodInvoker.getPreparedMethod(),Cache.class);
				if(null == cacheAnn){
					continue;
				}
				if(isStart){
					if(!cacheAnn.startRun()){
						continue;
					}
				}
				String jobName = "";long startTime = System.currentTimeMillis();
				try {
					jobName = methodInvoker.getTargetClass().getSimpleName()+"."+methodInvoker.getTargetMethod();
					if(cacheAnn.exclusive() && !isDameonServer){
						logger.error(jobName+":非主后台服务忽略！");
						continue;
					}
					Object object = methodInvoker.invoke();
					if(null != object){
						Element element = new Element(jobName,object);
						cache.put(element);
					}
					ok.append(jobName);
					logger.debug("init data:"+jobName+" "+(System.currentTimeMillis() - startTime));
				} catch (Exception e) {
					notOk.append(jobName);
					logger.error(jobName+":失败！",e);
				}
			}
			return ok.append(notOk).toString();
		}
	}
}
