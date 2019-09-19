package com.beyeon.general.security.control.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.beyeon.common.web.control.util.SpringUtil;
import com.beyeon.hibernate.fivsdb.*;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.beyeon.common.cache.CacheSupport;
import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.exception.AppExceptionImpl;
import com.beyeon.common.security.spring.detail.UserDetail;
import com.beyeon.common.util.GeneralUtil;
import com.beyeon.common.util.SendMail;
import com.beyeon.common.util.security.CipherUtil;
import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.general.security.model.SecurityFacade;
import com.beyeon.general.security.model.dto.UserDto;

import javax.servlet.http.HttpServletRequest;

@Component("passwordAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PasswordAction extends BaseAction {
	private static Logger logger =LoggerFactory.getLogger(PasswordAction.class);
	private static String verifyMailTempl = SendMail.getEmailTemplate("forgetPassVerify");
	private static String restSuccMailTempl = SendMail.getEmailTemplate("forgetPassRestSuccy");
	protected CacheSupport cacheSupport;
	private SecurityFacade securityFacade;
	protected UserDto userDto;

	public Object getModel() {
		if (null == this.userDto) {
			String amid = this.getReqParam("amid");
			if(StringUtils.isNotBlank(amid)){
				this.userDto = securityFacade.getUserBpo().get(amid);
			} else {
				this.userDto = new UserDto();
			}
		}
		return this.userDto;
	}

	public void setCacheSupport(CacheSupport cacheSupport) {
		this.cacheSupport = cacheSupport;
	}

	public void setSecurityFacade(SecurityFacade securityFacade) {
		this.securityFacade = securityFacade;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public String modifyPasswd(){
		String passwd = this.getReqParam("passwd");
		String newPasswd = this.getReqParam("newPasswd");
		UserDetail user = this.getCurrentUser();
		User userInfo = this.securityFacade.getUserBpo().getUserByUsername(this.getCurrentUser().getUsername());
		if (!userInfo.getPasswd().equals(passwd)) {
			this.addActionMessage("输入原始密码不正确！");
			return "modifyPasswd";
		}
		this.securityFacade.getUserBpo().modifyPasswd(user.getUsername(),newPasswd);
		UserDetail userDetail = new UserDetail(user.getUsername(), newPasswd, user.isEnabled(), 
				user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(), user.getAuthorities());
		userDetail.setName(user.getName());
		userDetail.setId(user.getId());
		userDetail.setPrivDatas(user.getPrivDatas());
		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(userDetail,
				newPasswd, user.getAuthorities());
        result.setDetails(SecurityContextHolder.getContext().getAuthentication().getDetails());
        SecurityContextHolder.getContext().setAuthentication(result);
		return "modifyPasswdSuccess";
	}

	public String modifyClientPasswd(){
		String passwd = this.getReqParam("passwd");
		String newPasswd = this.getReqParam("newPasswd");
		String username = this.getReqParam("username");;
		if (StringUtils.isBlank(passwd)||StringUtils.isBlank(newPasswd)||StringUtils.isBlank(username)){
			return null;
		}
		User userInfo = this.securityFacade.getUserBpo().getUserByUsername(username);
		if (null == userInfo){
			this.responseHTML("输入用户不存");
			return null;
		}
		if (!userInfo.getPasswd().equals(passwd)) {
			this.responseHTML("输入原始密码不正确");
			return null;
		}
		this.securityFacade.getUserBpo().modifyPasswd(username,newPasswd);
		this.responseHTML("success");
		return null;
	}

	public String forgetPasswd() throws Exception{
		String username = this.getReqParam("username");
		String verifyType = this.getReqParam("verifyType");
		String email = this.getReqParam("email");
		String mobile = this.getReqParam("mobile");String target = null;
		this.userDto.setUserInfo(securityFacade.getUserBpo().getUserInfoByUsername(username));
		if (null == this.userDto.getUserInfo()) {
			this.addActionMessage("输入用户名不存在！");
			return "forgetPasswd";
		}
		if ("0".equals(verifyType)) {
			if (!this.userDto.getUserInfo().getMail().equals(email)) {
				this.addActionMessage("输入email不正确！");
				return "forgetPasswd";
			}
			target = email;
			String verifyMessage = sendVerifyMail(email, this.userDto.getUserInfo().getAlias());
			cacheSupport.setex(username+"forgetPasswd", ResourceUtil.getCoreConfInt("digest.mail.outtime"), verifyMessage);
		} else {
			if (!this.userDto.getUserInfo().getPhone().equals(mobile)) {
				this.addActionMessage("输入手机号不正确！");
				return "forgetPasswd";
			}
			target = mobile;
			String verifyMessage = sendVerifyShortMsg(username,mobile);
			cacheSupport.setex(username+"forgetPasswd", ResourceUtil.getCoreConfInt("verify.message.outtime"), verifyMessage);
		}
		this.setSessAttr("verifyType", verifyType);
		this.setSessAttr("target", target);
		this.setSessAttr("username", username);
		return "forgetPasswdVerify";
	}

	public String reForgetPasswd() throws Exception{
		try {
			String username = (String) this.getSessAttr("username");
			String verifyType = (String) this.getSessAttr("verifyType");
			String target = (String) this.getSessAttr("target");
			if(StringUtils.isBlank(username) || StringUtils.isBlank(verifyType) || StringUtils.isBlank(verifyType)){
				responseHTML("用户认证信息已失效，请返回上一页重新填写");
				return null;
			}
			if ("0".equals(verifyType)) {
				String verifyMessage = sendVerifyMail(target, username);
				cacheSupport.setex(username+"forgetPasswd", ResourceUtil.getCoreConfInt("digest.mail.outtime"), verifyMessage);
			} else {
				String verifyMessage = sendVerifyShortMsg(username,target);
				cacheSupport.setex(username+"forgetPasswd", ResourceUtil.getCoreConfInt("verify.message.outtime"), verifyMessage);
			}
			responseHTML("验证码已发送请查收");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			responseHTML("验证码发送失败，请稍再试");
		}
		return null;
	}

	@Deprecated
	private String forgetPasswdOld() throws Exception{
		String username = this.getReqParam("username");
		String verifyType = this.getReqParam("verifyType");
		String email = this.getReqParam("email");
		String mobile = this.getReqParam("mobile");		
		this.userDto.setUserInfo(securityFacade.getUserBpo().getUserInfoByUsername(username));
		if (null == this.userDto.getUserInfo()) {
			this.addActionMessage("输入用户名不存在！");
			return "forgetPasswd";
		}		
		if ("0".equals(verifyType)) {
			if (!this.userDto.getUserInfo().getMail().equals(email)) {
				this.addActionMessage("输入email不正确！");
				return "forgetPasswd";
			}			
			String digest = sendVerifyMail(email, this.userDto.getUserInfo().getName());
			cacheSupport.setex(digest, ResourceUtil.getCoreConfInt("digest.mail.outtime"), "");
			return "forgetPasswdSuccess";
		} else {
			if (!this.userDto.getUserInfo().getPhone().equals(mobile)) {
				this.addActionMessage("输入手机号不正确！");
				return "forgetPasswd";
			}
			String verifyMessage = sendVerifyShortMsg(username,mobile);
			this.setSessAttr("username", username);
			cacheSupport.setex(username+verifyMessage, ResourceUtil.getCoreConfInt("verify.message.outtime"), "");
			return "forgetPasswdMobileVerify";
		}
	}

	@Deprecated
	private String emailVerifyDigest() throws Exception{
		String digest = this.getReqParam("digest");
		digest = URLEncoder.encode(digest,"UTF-8");
		if(StringUtils.isNotBlank(digest) && null != cacheSupport.get(digest)){
			cacheSupport.del(digest);
			digest = CipherUtil.AESUTIL.decryptWithBase64UrlDecode(digest);
			this.setSessAttr("username", digest.substring(0, digest.lastIndexOf("@")));
			this.setSessAttr("verifyState", "pass");
			return "restPassword";
		}
		this.addActionMessage("链接已失效！");
		return "forgetPasswd";
	}

	public String forgetPasswdVerify(){
		String verifyMessage = this.getReqParam("verifyMessage");
		String username = (String) this.getSessAttr("username");
		if(StringUtils.isBlank(username)){
			this.addActionMessage("用户认证信息已失效，请返回上一页重新填写");
			return "forgetPasswd";
		}
		if(StringUtils.isNotBlank(verifyMessage)){
			String jedMes = cacheSupport.get(username+"forgetPasswd");
			if(null != jedMes){
				if(jedMes.equals(verifyMessage)){
					cacheSupport.del(username+"forgetPasswd");
					this.setSessAttr("verifyState", "pass");
					return "restPassword";
				}
			} else {
				this.addActionMessage("验证码已失效请重新获取！");
				return "forgetPasswdVerify";
			}
		}
		this.addActionMessage("您输入的验证码不正确，请重新输入！");
		return "forgetPasswdVerify";
	}

	@Deprecated
	private String forgetPasswdMobileVerify(){
		String verifyMessage = this.getReqParam("verifyMessage");
		if(StringUtils.isNotBlank(verifyMessage) && null != cacheSupport.get(this.getSessAttr("username").toString()+verifyMessage)){
			cacheSupport.del(this.getSessAttr("username").toString()+verifyMessage);
			this.setSessAttr("verifyState", "pass");
			return "restPassword";
		}
		this.addActionMessage("您输入的手机验证码不正确，请重新输入！");
		return "forgetPasswdMobileVerify";
	}
	
	public String restPasswd() throws Exception {
		if(this.getSessAttr("verifyState")!="pass" || null == this.getSessAttr("username")){
			this.addActionMessage("用户认证信息已失效，请重新填写找回信息");
			return "forgetPasswd";
		}
		String username = this.getSessAttr("username").toString();
		String passwd = this.getReqParam("passwd");
		String target = (String) this.getSessAttr("target");
		this.securityFacade.getUserBpo().modifyPasswd(username,passwd);
		String verifyType = (String) this.getSessAttr("verifyType");
		if("0".equals(verifyType))
			sendRestSuccMail(target, username, passwd);
		else
			sendRestSuccShortMsg(username, target, passwd);
		this.removeSessAttr("verifyState");
		this.removeSessAttr("verifyType");
		this.removeSessAttr("target");
		this.securityFacade.getUserTraceBpo().resetUserSecurity(username);
		return "restPasswordSuccess";
	}

	protected String sendVerifyMail(String email, String username) throws Exception{
		String digest = null;
		try {
			digest = GeneralUtil.randomNum(4);
			String subject = ResourceUtil.getGlobalPublicConf("app.forgetpwd.email.title");
			//logger.debug(verifyMailTempl);
			String content = verifyMailTempl.replaceAll("\\{username\\}", username);
			content = content.replaceAll("\\{url\\}",digest);
			content = content.replaceAll("\\{global_app_name\\}",ResourceUtil.getAppName());
			content = content.replaceAll("\\{app_copyright_name\\}",ResourceUtil.getCopyrightName());
			logger.debug(content);
			new SendMail().send(new String[]{email},subject,content);
		} catch (Exception e) {
			throw new AppExceptionImpl("验证码发送失败，请稍再试",e);
		}
		return digest;
	}

	protected void sendRestSuccMail(String email, String username, String password) throws Exception{
		try {
			String subject = ResourceUtil.getGlobalPublicConf("app.forgetpwd.email.title");
			String content = restSuccMailTempl.replaceAll("\\{username\\}", username);
			content = content.replaceAll("\\{password\\}",password);
			content = content.replaceAll("\\{global_app_name\\}",ResourceUtil.getAppName());
			content = content.replaceAll("\\{app_copyright_name\\}",ResourceUtil.getCopyrightName());
			new SendMail().send(new String[]{email},subject,content);
		} catch (Exception e) {
			throw new AppExceptionImpl("密码修改成功，确认邮件发送失败，不影响您使用新密码登录系统！",e);
		}
	}

	@Deprecated
	private String sendDigestMailOld(String email,String username) throws Exception{
		String verifyUrl = ResourceUtil.getGlobalPublicConf("app.forgetpwd.email.verifyUrl");
		String digest = CipherUtil.AESUTIL.encryptWithBase64UrlEncode(username+"@"+new Date().getTime());
		String subject = ResourceUtil.getGlobalPublicConf("app.forgetpwd.email.title");
		String content = verifyMailTempl.replaceAll("\\{username\\}", username);
		content = content.replaceAll("\\{url\\}",verifyUrl+digest);
		content = content.replaceAll("\\{global_app_name\\}",ResourceUtil.getAppName());
		content = content.replaceAll("\\{app_copyright_name\\}",ResourceUtil.getCopyrightName());
		new SendMail().send(new String[]{email}, subject, content);
		return digest;
	}

	protected String sendVerifyShortMsg(String username, String mobileCode) throws Exception{
		String digest = GeneralUtil.randomNum(4);
		String content = ResourceUtil.getGlobalPublicConf("restPwd.verifyShortMsg", new String[]{username, digest});
		GeneralUtil.sendShortMsg(mobileCode, content);
		return digest;
	}

	protected void sendRestSuccShortMsg(String username, String mobileCode, String passwd) throws Exception{
		String content = ResourceUtil.getGlobalPublicConf("restPwd.succShortMsg",new String[]{username,passwd});
		GeneralUtil.sendShortMsg(mobileCode,content);
	}

	public String restForgetPasswd(){
		String type = this.getReqParam("type");
		String start_time = this.getReqParam("start_time");
		String end_time = this.getReqParam("end_time");
		String ip = this.getReqParam("ip");
		String state = this.getReqParam("state");
		String description = this.getReqParam("description");
		System.out.println("type="+type+",start="+start_time+",end="+end_time+",ip="+ip+",state="+state+".description="+description);
		Equipment equipment = this.baseinfoFacade.getCorpBpo().findIdByIp(ip);
		if (equipment!=null){
			DmuAlarmInfo dmuAlarmInfo =  new DmuAlarmInfo();
			dmuAlarmInfo.setAlarmType(type);
			dmuAlarmInfo.setBeginTime(start_time);
			dmuAlarmInfo.setEndTime(end_time);
			dmuAlarmInfo.setSourceId(equipment.getId());
			dmuAlarmInfo.setName(equipment.getName());
			Map<String,String> map = TbAlarmType.getObjectMap();
			String typeId = "";
			String level = "1";
			String levelName = "重要告警";
			for (String key : map.keySet()){
				String value = map.get(key);
				if (value.equals(type)){
					typeId = key;
					level = TbAlarmType.getLevel(typeId);
					levelName = TbAlarmType.getLevelName(typeId);
				}
			}
			dmuAlarmInfo.setState(Integer.parseInt(state));
			dmuAlarmInfo.setDescription(description);
			dmuAlarmInfo.setStatus("0");
			dmuAlarmInfo.setLevel(Integer.parseInt(level));
			dmuAlarmInfo.setTargetInfo(levelName);
			dmuAlarmInfo.setDealState(0);
			this.baseinfoFacade.getCorpBpo().saveAlarmInfo(dmuAlarmInfo);
		}
		JSONObject json = new JSONObject();
		json.put("code","200");
		json.put("msg","success");
		this.responseJSON(json);
		return null;
	}
}
