package com.beyeon.common.config;

import java.util.HashMap;
import java.util.Map;

public class ResourceUtil extends ResourceBundleUtil {
	static ThreadLocal<Map<String,Object>> requestContexts = new ThreadLocal<Map<String,Object>>();
	private static Map<String,Object> getRequestContext() {
		Map<String,Object> requestContext = requestContexts.get();
		if(null == requestContext){
			requestContext = new HashMap<String, Object>();
			requestContexts.set(requestContext);
		}
		return requestContext;
	}

	public static Object getRequestContext(String key){
		return getRequestContext().get(key);
	}

	public static void setRequestContext(String key, Object value){
		getRequestContext().put(key,value);
	}

	public static void cleanRequestContext(){
		getRequestContext().clear();
		requestContexts.set(null);
	}

	/*对应资源文件名获取key值方法区*/
	public static String getCoreConf(String key){
		return getManualProperty("coreConf", key);
	}
	public static int getCoreConfInt(String key){
		return getManualPropertyInt("coreConf", key);
	}

	public static String getGlobalPublicConf(String key){
		return getManualProperty("globalPublicConf", key);
	}
	public static String getGlobalPublicConf(String key,String[] args){
		return getManualProperty("globalPublicConf", key, args);
	}
	public static int getGlobalPublicConfInt(String key){
		return getManualPropertyInt("globalPublicConf", key);
	}

	public static String getPrivDataConfig(String key){
		return getManualProperty("privDataConfig", key);
	}

	public static String getPublicConf(String key){
		return getManualProperty("publicConf", key);
	}
	public static String getPublicConf(String key,String[] args){
		return getManualProperty("publicConf", key, args);
	}

	public static String getParamConf(String key){
		return getManualProperty("paramConf", key);
	}
	public static String getParamConf(String key,String[] args){
		return getManualProperty("paramConf", key, args);
	}

	public static String getServerConf(String key){
		return getManualProperty("server", key);
	}
	public static String getServerConf(String key,String[] args){
		return getManualProperty("server", key, args);
	}

	public static String getAppRscConf(String key){
		return getAppRscConf(key, null);
	}
	public static String getAppRscConf(String key,String[] args){
		return getManualProperty("appRscConf" + getIndustry(), key,args);
	}

	public static String getIndustry(){
		Object industry = getRequestContext("app_industry");
		if(null != industry){
			return (String) industry;
		}
		return "";
	}

	public static String getHgCode(){
		Object hgCode = getRequestContext("app_hg_code");
		if(null != hgCode){
			return (String) hgCode;
		}
		return "lskj";
	}

	public static String getIdentityCode(){
		Object identityCode = getRequestContext("app_identity_code");
		if(null != identityCode){
			return (String) identityCode;
		}
		return "_" + getHgCode();
	}

	/*获取常用KEY对应值方法区*/
	public static String getAppId(){
		String appCode = getCoreConf("app.code");
		if(appCode.startsWith("xxx")){
			return getHgCode()+appCode.substring(3);
		}
		return appCode;
	}

	public static String getAppCode(){
		return getAppId();
	}

	public static String getAppName(){
		return getCoreConf("app.name");
	}
	
	public static String getAppVersion(){
		return getCoreConf("app.version");
	}
	
//	public static Integer getAppPasswordResetTime(){
//		String hgCode = getCoreConf("app.pwd.resettime");
//		if(null != hgCode){
//			return Integer.parseInt(hgCode);
//		}
//		return 90;
//	}
//	
//	public static Integer getAppPasswordRemandTime(){
//		String hgCode = getCoreConf("app.pwd.remandtime");
//		if(null != hgCode){
//			return Integer.parseInt(hgCode);
//		}
//		return 7;
//	}
	
	public static String getZabbixStatus(){
		return getCoreConf("zabbix.status");
	}
	public static Integer getZabbixListenTime(){
		String alive  = getCoreConf("zabbix.listen.time");
		if (alive !=null){
			return Integer.parseInt(alive);
		}
		return 300;
	}
	
	public static Integer getZabbixCleanTime(){
		String alive  = getCoreConf("zabbix.clean.time");
		if (alive !=null){
			return Integer.parseInt(alive);
		}
		return 30;
	}
	
	public static Integer getEncoderListenTime(){
		String alive  = getCoreConf("encoder.listen.time");
		if (alive !=null){
			return Integer.parseInt(alive);
		}
		return 300;
	}
	
	public static Integer getAlarmReportTime(){
		String alive  = getCoreConf("alarm.report.time");
		if (alive !=null){
			return Integer.parseInt(alive);
		}
		return 300;
	}
	public static Integer getSocketPort(){
		String port = getCoreConf("socket.port");
		if (port !=null){
			return Integer.parseInt(port);
		}
		return 8888;
	}
	public static Integer getSocketKeepAlive(){
		String alive  = getCoreConf("socket.keepAlive");
		if (alive !=null){
			return Integer.parseInt(alive);
		}
		return 60;
	}
	
	public static Integer getPacketNum(){
		String packet  = getCoreConf("packet.num");
		if (packet!=null)
			return Integer.parseInt(packet);
		return 100;
	}
	
	public static String getDiskBrand(){
		return getCoreConf("disk.brand");
	}
	
	public static String getPassenger(){
		return getCoreConf("passenger.flow");		
	}
	public static String getCorporationName(){
		return getAppRscConf("app.corporation.name");
	}

	public static String getShortMsgServer(){
		return getPublicConf("short.msg.server");
	}

	public static String getSmpTypeName(){
		return getAppRscConf("app_smp_type_name");
	}

	public static String getGrpTypeName(){
		return getAppRscConf("app_grp_type_name");
	}

	public static String getCopyrightName(){
		return getAppRscConf("app.copyright.name",new String[]{getCorporationName()});
	}

}
