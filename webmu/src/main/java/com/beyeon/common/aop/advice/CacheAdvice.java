package com.beyeon.common.aop.advice;

import java.lang.reflect.Method;
import java.util.Map;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.beyeon.common.aop.annotation.Cache;

/**
 * User: lwf
 * Date: 12-6-14
 * Time: 下午12:17
 */
public class CacheAdvice implements MethodInterceptor {
	private Ehcache cache;

	public void setCache(Ehcache cache) {
		this.cache = cache;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		if(method.getAnnotation(Cache.class).type()!=Cache.REFRESH){
			StringBuilder key = new StringBuilder();
			key.append(method.getDeclaringClass().getSimpleName()+"."+method.getName());
			Element element = cache.get(key.toString());
			if(null != element){
				Object[] params = invocation.getArguments();
				if(null != params && params.length > 0){
					StringBuilder paramKey = new StringBuilder();
					paramKey.append(params[0].toString());
					for (int i = 1; i < params.length; i++) {
						paramKey.append(".");
						paramKey.append(params[i].toString());
					}
					return ((Map)element.getValue()).get(paramKey.toString());
				}
				return element.getValue();
			}
		}
		return invocation.proceed();
	}
}
