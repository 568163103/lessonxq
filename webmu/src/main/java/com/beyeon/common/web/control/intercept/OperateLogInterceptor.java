package com.beyeon.common.web.control.intercept;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.util.UrlUtils;

import com.alibaba.fastjson.JSONObject;
import com.beyeon.common.security.spring.detail.UserDetail;
import com.beyeon.common.util.IpUtil;
import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.common.web.control.util.SpringUtil;
import com.beyeon.general.security.model.bpo.MenuBpo;
import com.beyeon.hibernate.fivsdb.TMenu;
import com.beyeon.hibernate.fivsdb.TUserTrace;
import com.beyeon.nvss.log.model.bpo.UserTraceBpo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class OperateLogInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = -5639935293884137695L;
	private static HashMap<String,String> map = new HashMap<String,String>();
	private Logger logger = LoggerFactory.getLogger(OperateLogInterceptor.class);
	private UserTraceBpo userTraceBpo = null;
	private Map menuMap = null;
	public void init() {
		MenuBpo menuBpo = (MenuBpo) SpringUtil.getBean("menuBpo");
		this.userTraceBpo = (UserTraceBpo) SpringUtil.getBean("userTraceBpo");
		this.menuMap = menuBpo.find();
		super.init();
	}

	public void destroy() {
		this.menuMap.clear();
		super.destroy();
	}

	public String intercept(ActionInvocation invocation) throws Exception {
        String result = null;
		try {
			result = invocation.invoke();
		} finally {
			try {
				ActionProxy ap = invocation.getProxy();
				String url = UrlUtils.buildRequestUrl(ServletActionContext.getRequest());
				TMenu menu = (TMenu) menuMap.get(url);	
				HashMap<String,Object> s4 =(HashMap<String,Object>) invocation.getInvocationContext().getContextMap().get("parameters");
				Iterator<Map.Entry<String, Object>> it = s4.entrySet().iterator();
				JSONObject  json = new JSONObject();
			    while (it.hasNext()) {
			      Map.Entry<String, Object> entry = it.next();
			      String key = entry.getKey();
			      if (!key.startsWith("pageObject") && !key.startsWith("struts") && !key.startsWith("token") 
			    		  && !key.startsWith("isAdmin") && !key.startsWith("randomNum")){
			    	  String[] arg = (String[]) entry.getValue();
			    	  if (StringUtils.isNotBlank(arg[0]) && !"pageObject".equals(arg[0])){
			    		  String name = getType(key)!=null ? getType(key) : key;
			    		  json.put(name, arg[0]); 
			    	  }
			      }
			      // it.remove(); 删除元素
			    }
			    if (menu == null){
			    	menu =  getMethodChineseName(url,json);
			    	if (null != menu)
			    	transfer(menu);
			    }else{
			    	Integer id = menu.getMid();
			    	String modName = menu.getModName();
			    	transfer(menu);
			    	if ( id < 66 && id > 53 && json.size()>0 ){
				    	menu.setModName(modName+",参数："+json.toString());
				    	menu.setName("用户管理");
			    	}
			    }
				if(null != menu){					
					String ip = IpUtil.getClientIpAddr(ServletActionContext.getRequest());
					UserDetail ud = ((BaseAction)ap.getAction()).getCurrentUser();
					TUserTrace userTrace = new TUserTrace();
					if(null != ud){
						userTrace.setAmid(ud.getId());						
						userTrace.setUserName(ud.getUsername());
					}else{
						userTrace.setAmid("0");						
						userTrace.setUserName("anonymous");
					}
					userTrace.setMid(menu.getMid());
					userTrace.setTerminalIp(ip);
					userTrace.setMenuName(menu.getName());
					userTrace.setUserTrace(menu.getModName());
					userTrace.setOperateDate(new Date());
					userTrace.setOperateStatus((short)(null != result?1:2));
					this.userTraceBpo.save(userTrace);
				}
			} catch (Exception e) {
				logger.error("添加日志异常",e);
			}
		}
        return result;
	}
	
	  public  TMenu getMethodChineseName(String methodName,JSONObject json){
		  String name = "";
        if(methodName.indexOf("!save") >-1){
            name ="--新增";
        }else if (methodName.indexOf("!update") >-1){
            name ="--编辑";
        }else if (methodName.indexOf("!delete") >-1){
            name ="--删除";
        }
        if (StringUtils.isNotBlank(name)){
        	 if(methodName.indexOf("/server!")>-1){
                 name = "服务器"+ name;
             }else if (methodName.indexOf("/channel!")>-1){
                 name = "摄像机"+ name;
             }else if (methodName.indexOf("/encoder!")>-1){
                 name = "编码器"+ name;
             }else if (methodName.indexOf("/equipment!")>-1){
                 name = "其他设备"+ name;
             }else if (methodName.indexOf("/terminal!")>-1){
                 name = "终端"+ name;
             }else if (methodName.indexOf("/groups!")>-1){
                 name = "资源组"+ name;
             }else if (methodName.indexOf("/recordplan!")>-1){
                 name = "存储方案"+ name;
             }else if (methodName.indexOf("/prescheme!")>-1){
                 name = "预案"+ name;
             }else if (methodName.indexOf("/alarmprescheme!")>-1){
                 name = "预案关联"+ name;
             }else if (methodName.indexOf("/user!")>-1){
                 name = "用户管理"+ name;
             }else if (methodName.indexOf("/alarmInfo")>-1){
                 name = "告警信息"+ name;
             }else if (methodName.indexOf("/alarmLevel!")>-1){
                 name = "告警级别" + name;
             }else if (methodName.indexOf("/user!")>-1){
                 name = "用户管理" + name;
             }else if (methodName.indexOf("/userGroup!")>-1){
                 name = "用户组管理" + name;
             }else if (methodName.indexOf("/usertree!")>-1){
                 name = "资源管理" + name;
             }
        }
        
        if (StringUtils.isNotBlank(name)){
        	TMenu m = new TMenu();
        	m.setMid(1);
			m.setModName(name+methodName+",参数:"+json.toString());
        	return m;
        }
        return null;
    }
	
	public void transfer(TMenu menu){
		String modname = menu.getModName();
		if (modname.indexOf("服务器")>-1 || modname.indexOf("摄像机")>-1 || modname.indexOf("编码器")>-1
				 || modname.indexOf("其他设备")>-1 || modname.indexOf("终端")>-1 || modname.indexOf("资源组")>-1
				 || modname.indexOf("预案")>-1 || modname.indexOf("资源")>-1 || modname.indexOf("设备")>-1
				 || modname.indexOf("视频")>-1 || modname.indexOf("地图")>-1){
			menu.setName("设备管理");
		}else if (modname.indexOf("用户")>-1 || modname.indexOf("角色")>-1 ){
			menu.setName("用户管理");
		}else if (modname.indexOf("告警")>-1 || modname.indexOf("故障")>-1 || modname.indexOf("网管")>-1 ){
			menu.setName("告警管理");
		}else if (modname.indexOf("日志")>-1 ){
			menu.setName("日志管理");
		}else{
			menu.setName("系统参数设置");
		}
	}
	
	
	private String getType(String key){
		if (map.size() == 0){
			map.put("preScheme.name","预案名称");
			map.put("actionObject.target", "通道ID/用户ID");
			map.put("actionObject.aheadOfTime", "预录时间");
			map.put("actionObject.type", "动作类型");
			map.put("server.type", "服务器类型");
			map.put("server.name", "服务器名称");
			map.put("server.ip", "IP");
			map.put("server.port", "端口");
			map.put("serverExtra.mac", "MAC地址");
			map.put("serverExtra.keepAlivePeriod", "心跳时间");
			map.put("server.username", "用户名");
			map.put("server.password", "密码");
			map.put("server.cmuId", "上级cmuId");
			map.put("server.maxConnection", "最大连接数");
			map.put("serverExtra.version", "设备型号");
			map.put("server.address", "物理位置");
			map.put("serverExtra.corp", "厂家");
			map.put("server.description", "备注");
			map.put("encoder.model", "编码器类型");
			map.put("encoder.name", "编码器名称");
			map.put("encoder.ip", "IP");
			map.put("encoder.port", "端口");
			map.put("encoder.username", "用户名");
			map.put("encoder.password", "密码");
			map.put("serverEncoder.serverId", "接入服务器ID");
			map.put("msuChannel.serverId", "存储服务器ID");
			map.put("encoder.channelCount", "视频通道数");
			map.put("encoder.address", "物理位置");
			map.put("encoder.hasAudio", "是否启用语音");
			map.put("encoder.enabled", "是否启用");
			map.put("encoderExtra.corp", "厂家");
			map.put("encoderExtra.version", "设备型号");
			map.put("encoderExtra.mac", "MAC地址");
			map.put("encoder.description", "备注");
			map.put("encoder.updateChannel", "是否更新通道名");
			map.put("channel.name", "视频通道名称");
			map.put("msuChannel.serverId", "存储服务器ID");
			map.put("channel.hasAudio", "是否启用语音");
			map.put("channel.hasPtz", "是否启用云台");
			map.put("channel.enabled", "是否启用");
			map.put("channel.description", "备注");
			map.put("imsGisInfo.longitude", "经度");
			map.put("imsGisInfo.latitude", "纬度");
			map.put("channel.streamCount", "流媒体数");
			map.put("channelRecordPlan.planName", "存储方案名称");
			map.put("imsGisInfo.curRange", "最大视距");
			map.put("imsGisInfo.angle", "最大视角");
			map.put("imsGisInfo.height", "高度");
			map.put("preset.name", "预置位设置名称");
			map.put("preset.flag", "预置位设置类型");
			map.put("equipment.type", "其他设备类型");
			map.put("equipment.name", "其他设备名称");
			map.put("equipment.ip", "IP");
			map.put("equipment.port", "端口");
			map.put("equipment.pos", "位置");
			map.put("equipment.corp", "厂家");
			map.put("equipment.version", "设备型号");
			map.put("equipment.mac", "MAC地址");
			map.put("equipment.remark", "备注");
			map.put("terminal.name", "终端名称");
			map.put("terminal.ip", "IP");
			map.put("terminal.enabled", "是否启用");
			map.put("terminal.address", "物理位置");
			map.put("terminal.description", "备注");
			map.put("oldAlarmPreScheme.alarmType", "原事件类型");
			map.put("oldAlarmPreScheme.sourceId", "原事件源");
			map.put("oldAlarmPreScheme.schemeId", "原预案ID");
			map.put("alarmType", "事件类型");
			map.put("sourceId", "事件源");
			map.put("schemeId", "预案ID");
			map.put("groups.type", "资源组类型");
			map.put("groups.name", "资源组名称");
			map.put("groups.description", "备注");
			map.put("recordPlan.name", "存储方案名");
			map.put("recordPlan.resolution", "分辨率");
			map.put("recordPlan.frametype", "帧率");
			map.put("recordPlan.cycleDate", "覆盖天数");
			map.put("recordPlan.description", "备注");
			map.put("tbResShieldPlan.beginTime", "屏蔽开始时间");
			map.put("tbResShieldPlan.endTime", "屏蔽结束时间");
			map.put("tbResShieldPlan.status", "取消禁止");
			map.put("tbResShieldPlan.sid", "下级设备ID");
			map.put("userGroup.name", "用户组名称");
			map.put("userGroup.description", "备注");
			map.put("alarmRes.name", "告警资源名称");
			map.put("alarmRes.alarmType", "故障类型");
			map.put("alarmRes.sid", "所属平台ID");
			map.put("alarmType.typeId", "告警类型ID");
			map.put("alarmType.alarmTypeZh", "告警类型名称");
			map.put("alarmType.level", "告警级别");
			map.put("alarmType.description", "备注");
			map.put("tbAlarmsubcriType.alarmType", "告警类型ID");
			map.put("tbAlarmsubcriType.alarmTypeZh", "告警类型名称");
			map.put("tbAlarmsubcriType.beginTime", "布防时间");
			map.put("tbAlarmsubcriType.endTime", "撤防时间");
			map.put("tbAlarmsubcriType.description", "备注");
			map.put("userInfo.name", "账号");
			map.put("userInfo.alias", "姓名");
			map.put("userInfo.password", "密码");
			map.put("userInfo.phone", "电话号码");
			map.put("userInfo.mail", "邮箱");
			map.put("userInfo.ptzLevel", "云台级别");
			map.put("userInfo.userLevel", "用户级别");
			map.put("userInfo.maxCameraNum", "最大视频路数");
			map.put("userInfo.ptzLockTime", "云台锁定时间");
			map.put("userInfo.activeTime", "活动时间");
			map.put("userInfo.avLevel", "音视频级别");
			map.put("userInfo.activeBeginTime", "活跃开始时间");
			map.put("userInfo.activeEndTime", "活跃结束时间");
			map.put("rids", "角色类型");
			map.put("userInfo.userType", "用户类型");
			map.put("corpId", "所属单位ID");
			map.put("userInfo.description", "备注");
			map.put("systemConfig.maxErrorCount", "密码最大错误次数");
			map.put("systemConfig.userLockTime", "错误锁定时间");
			map.put("systemConfig.errorLoginConfig", "是否开启登录失败策略");
			map.put("systemConfig.pwdResetFlag", "是否开启密码定时失效策略");
			map.put("systemConfig.keepPasswordTime", "密码有效期限");
			map.put("systemConfig.pwdRemaindTime", "密码失效提前提醒时间");
			map.put("items", "所选ID");
		}
		return map.get(key);		
	}
}
