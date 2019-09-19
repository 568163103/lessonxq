package com.beyeon.common.security.spring.access.authentication;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.beyeon.common.security.spring.detail.UserDetail;
import com.beyeon.common.web.control.util.SpringUtil;

/**
 * User: lwf
 * Date: 12-5-30
 * Time: 下午5:22
 */
public class UrlJumpContainer {
	public static String DEFAULT_APP_TYPE = "j_apptype";
	public static String J_IDENTITY = "j_identity";
	public static String J_IDENTITY_VALUE = "1_1";//用"_"分割，第一位存APP_TYPE,第二位存MODULE_TYPE

	public static String APP_TYPE_SUPER = "1";
	public static String APP_TYPE_SELF = "2";

	public static String MODULE_TYPE_NONE = "1";
	public static String MODULE_TYPE_SELF = "3";

	public static String appTypeParameter = DEFAULT_APP_TYPE;
	public static boolean isIgnoreAppType = true;
	private String defaultInputUrl;
	private String defaultTargetUrl;
	private Map<String,String> targetUrls;
	private Map<String,String> inputUrls;

	public void setIgnoreAppType(boolean ignoreAppType) {
		isIgnoreAppType = ignoreAppType;
	}

	public void setAppTypeParameter(String appTypeParam) {
		appTypeParameter = appTypeParam;
	}

	public String getAppTypeParameter() {
		return appTypeParameter;
	}

	public void setDefaultInputUrl(String defaultInputUrl) {
		this.defaultInputUrl = defaultInputUrl;
	}

	public void setDefaultTargetUrl(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}

	public void setTargetUrls(Map<String, String> targetUrls) {
		this.targetUrls = targetUrls;
	}

	public void setInputUrls(Map<String, String> InputUrls) {
		this.inputUrls = InputUrls;
	}

	public String getTargetUrl(String key) {
		if(null != targetUrls && StringUtils.isNotBlank(key)){
			String targetUrl = targetUrls.get(key);
			if(StringUtils.isNotBlank(targetUrl)){
				return targetUrl;
			}
		}
		return defaultTargetUrl;
	}

	public String getTargetUrl(HttpServletRequest request) {
		String appType = getAppType(request);
		return this.getTargetUrl(appType);
	}

	public String getInputUrl(String key) {
		if(null != inputUrls && StringUtils.isNotBlank(key)){
			String inputUrl = inputUrls.get(key);
			if(StringUtils.isNotBlank(inputUrl)){
				return inputUrl;
			}
		}
		return defaultInputUrl;
	}

	public String getInputUrl(HttpServletRequest request) {
		String appType = getAppType(request);
		return this.getInputUrl(appType);
	}

	private static String getIDENTITY(HttpServletRequest request,int index){
		Cookie[] cookies = request.getCookies();
		if(null == cookies){
			return null;
		}
		for (int i = 0; i < cookies.length; i++) {
			if(cookies[i].getName().equals(J_IDENTITY)){
				return cookies[i].getValue().split("_")[index];
			}
		}
		return null;
	}

	private static void setIDENTITY(HttpServletRequest request,HttpServletResponse response,String value,int index){
		String j_identity = J_IDENTITY_VALUE;
		Cookie[] cookies = request.getCookies();
		if(null != cookies){
			for (int i = 0; i < cookies.length; i++) {
				if(cookies[i].getName().equals(J_IDENTITY)){
					j_identity = cookies[i].getValue();
					break;
				}
			}
		}
		String [] j_identitys = j_identity.split("_");
		if(StringUtils.isNotBlank(value))
			j_identitys[index]=value;
		Cookie identityCookie=new Cookie(J_IDENTITY,StringUtils.join(j_identitys,"_"));
		identityCookie.setPath("".equals(request.getContextPath())?"/":request.getContextPath());
		identityCookie.setMaxAge(2*24*60*60);
		response.addCookie(identityCookie);
	}

	public static String getAppType(HttpServletRequest request){
		//判断用户类型
		String roleType = UrlJumpContainer.APP_TYPE_SUPER;
		UserDetail userDetail = SpringUtil.getCurrUser();
		if(!isIgnoreAppType && null != userDetail && userDetail.isSelfManager()){
			roleType = UrlJumpContainer.APP_TYPE_SELF;
		}
		return roleType;
	}

	public static void setAppType(HttpServletRequest request,HttpServletResponse response){
		String appType = request.getParameter(appTypeParameter);
		setIDENTITY(request,response,appType,0);
	}

	public static void setModuleType(HttpServletRequest request,HttpServletResponse response,String value){
		setIDENTITY(request,response,value,1);
	}

	public static String getModuleType(HttpServletRequest request){
		return getIDENTITY(request,1);
	}
}
