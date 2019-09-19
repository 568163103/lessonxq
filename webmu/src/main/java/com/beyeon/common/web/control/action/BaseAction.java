package com.beyeon.common.web.control.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Type;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.TokenHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.beyeon.common.security.spring.detail.UserDetail;
import com.beyeon.common.web.control.util.SpringUtil;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.general.baseinfo.model.BaseinfoFacade;
import com.beyeon.nvss.device.model.bpo.ServerBpo;

@SuppressWarnings("rawtypes")
public abstract class BaseAction extends ActionSupport implements ModelDriven {
	private static final long serialVersionUID = 1L;
	public static final String ENCODING_DEFAULT = "UTF-8";
	public static final String BASE_UPDATE_OBJECT = "base_update_object";
	public Logger logger =LoggerFactory.getLogger(this.getClass());

	private PageObject pageObject = new PageObject();

	protected BaseinfoFacade baseinfoFacade;
	private ServerBpo serverBpo;

	public void setBaseinfoFacade(BaseinfoFacade baseinfoFacade) {
		this.baseinfoFacade = baseinfoFacade;
	}
	
	public ServerBpo getServerBpo() {
		return serverBpo;
	}

	public void setServerBpo(ServerBpo serverBpo) {
		this.serverBpo = serverBpo;
	}
	
	public String findServerIp(Integer serverType) {
		return serverBpo.findServerIp(serverType);
	}

	public String[] getReqParams(String key) {
		ActionContext ac = ActionContext.getContext();
		return (String[]) ac.getParameters().get(key);
	}

	public String getReqParam(String key) {
		ActionContext ac = ActionContext.getContext();
		String[] ids = (String[]) ac.getParameters().get(key);
		if (ids != null && ids.length > 0) {
			return ids[0];
		}
		return null;
	}

	public String getReqParamJson(){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(ServletActionContext.getRequest().getInputStream(),"utf-8"));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while((line = br.readLine())!=null){
				sb.append(line);
			}
			logger.debug(sb.toString());
			return sb.toString();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return "";
	}

	public <T> T getReqParamObject(Type classes){
		return JSON.parseObject(getReqParamJson(),classes);
	}

	public void setReqAttr(String key, Object value) {
		ServletActionContext.getRequest().setAttribute(key, value);
	}

	public Object getReqAttr(String key) {
		return ServletActionContext.getRequest().getAttribute(key);
	}

	public void setSessAttr(String key, Serializable value) {
		ActionContext ac = ActionContext.getContext();
		ac.getSession().remove(key);
		ac.getSession().put(key, value);
	}

	public Serializable getSessAttr(String key) {
		ActionContext ac = ActionContext.getContext();
		return (Serializable) ac.getSession().get(key);
	}

	public Serializable removeSessAttr(String key) {
		ActionContext ac = ActionContext.getContext();
		return (Serializable) ac.getSession().remove(key);
	}

	public String getSessionId(){
		return ServletActionContext.getRequest().getSession().getId();
	}

	public void addActionMessage(String aMessage) {
		this.setReqAttr("ACTIONMESSAGE",aMessage);
	}

	public void setSessPageObject() {
		this.setSessAttr("pageObject", this.getPageObject());
	}

	public PageObject getSessPageObject() {
		return (PageObject) this.getSessAttr("pageObject");
	}

	public void responseHTML(String content) {
		response(content, "text/html", ENCODING_DEFAULT, true);
	}

	public void responseJSON(Object object) {
		responseJSON(object, false);
	}

	public void responseJSON(Object object,boolean isAddToken) {
		response(toJSON(object,isAddToken,TokenHelper.DEFAULT_TOKEN_NAME), "application/json", ENCODING_DEFAULT, true);
	}

	public void responseJSON(Object object,boolean isAddToken,String tokenName) {
		response(toJSON(object,isAddToken,tokenName), "application/json", ENCODING_DEFAULT, true);
	}

	public void response(String content, String contentType, String encoding, boolean isNocache) {
		ServletActionContext.getResponse().setContentType(contentType + ";charset=" + encoding);
		if (isNocache) {
			ServletActionContext.getResponse().setHeader("Pragma", "No-cache");
			ServletActionContext.getResponse().setHeader("Cache-Control", "no-cache");
			ServletActionContext.getResponse().setDateHeader("Expires", 0);
		}
		PrintWriter printWriter = null;
		try {
			printWriter = ServletActionContext.getResponse().getWriter();
			printWriter.write(content);
			printWriter.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 得到服务全路径，例如：http://127.0.0.1:8080/portal
	 * 
	 * @return 服务全路径
	 */
	public String getBasePath() {
		StringBuffer basePath = new StringBuffer();
		basePath.append(ServletActionContext.getRequest().getScheme());
		basePath.append("://");
		basePath.append(ServletActionContext.getRequest().getServerName());
		basePath.append(":");
		basePath.append(ServletActionContext.getRequest().getServerPort());
		basePath.append(ServletActionContext.getRequest().getContextPath());
		return basePath.toString();
	}

	public String getRequestURI(){
		return ServletActionContext.getRequest().getRequestURI();
	}
	public boolean isRequestMethod(String method){
		return this.getRequestURI().indexOf(method) >= 0;
	}
	/**
	 * 获取真是的客户端的IP 一般情况直接用request.getRemoteAddr()方法
	 * 获取客户端的IP地址，但是如果客户端使用了代理服务器，该方法得到的是 代理服务器的地址，不是客户端的地址。
	 * 代理服务器在转发客户端的请求时，会在HTTP的头消息中增加"x-forwarded-for"
	 * 信息，该信息中就保存有原客户端的地址。通过request的getHeader方法得
	 * HTTP头中的"x-forwarded-for"信息，便可以获得客户端的真实IP。
	 * 当客户端通过多级代理访问时，"x-forwarded-for"信息中的第一个非unknown字符串 即为客户端的真实IP
	 * 
	 * @return 返回真实的IP地址
	 */
	public String getRemoteAddr() {
		String remoteAddr = null;
		remoteAddr = ServletActionContext.getRequest().getHeader("x-forwarded-for");
		if (remoteAddr != null && remoteAddr.length() != 0) {
			if (remoteAddr.equals("unknown")) {
				remoteAddr = null;
			}
		}
		if (remoteAddr == null) {
			remoteAddr = ServletActionContext.getRequest().getHeader("Proxy-Client-IP");
		}
		if (remoteAddr == null) {
			remoteAddr = ServletActionContext.getRequest().getHeader("WL-Proxy-Client-IP");
		}
		if (remoteAddr == null) {
			remoteAddr = ServletActionContext.getRequest().getRemoteAddr();
		}
		return remoteAddr;
	}

	public PageObject getPageObject() {
		return pageObject;
	}

	public void setPageObject(PageObject pageObject) {
		this.pageObject = pageObject;
	}

	public UserDetail getCurrentUser() {
		return SpringUtil.getCurrUser();
	}

	public String toJSON(Object object) {
		return toJSON(object,false);
	}

	public String toJSON(Object object,boolean isAddToken) {
		return toJSON(object,isAddToken,TokenHelper.DEFAULT_TOKEN_NAME);
	}

	public String toJSON(Object object,boolean isAddToken,String tokenName) {
		if(!isAddToken)
			return JSON.toJSONString(object);
		else{
			String jst = JSON.toJSONString(object);
			Object o = JSON.parse(jst);
			if(o instanceof JSONObject){
				JSONObject jo = (JSONObject) o;
				jo.put(TokenHelper.TOKEN_NAME_FIELD,tokenName);
				jo.put(tokenName,TokenHelper.setToken(tokenName));
				return jo.toString();
			}

			JSONObject jo = new JSONObject();
			jo.put(TokenHelper.TOKEN_NAME_FIELD,tokenName);
			jo.put(tokenName,TokenHelper.setToken(tokenName));
			JSONArray ja = null;
			if(o instanceof JSONArray) {
				ja = (JSONArray) o;
				ja.add(jo);
			} else {
				ja = new JSONArray();
				ja.add(o);
				ja.add(jo);
			}
			return ja.toString();
		}
	}

	public Object getModel() {
		return null;
	}

	protected String getPath(String path){
		String absolutePath = "";
		if (path.startsWith("SD__")){
			absolutePath = baseinfoFacade.getAbsolutePath(path);
		} else if (path.indexOf(":")==1 || path.startsWith("//")) {
			absolutePath = path;
		} else {
			absolutePath = baseinfoFacade.getRealStorePath()+path;
		}
		return absolutePath;
	}

	protected void outputFile(String contentType, String reqFile, String defaultFile){
		InputStream inputStream = null;OutputStream output = null;
		ServletActionContext.getResponse().setContentType(contentType);
		try {
			if (StringUtils.isNotBlank(reqFile)){
				String absolutePath = getPath(reqFile);
				File imageFile = new File(absolutePath);
				if (imageFile.exists()){
					inputStream = new FileInputStream(imageFile);
				}
			}
			if (null == inputStream && StringUtils.isNotBlank(defaultFile)) {
				inputStream = ServletActionContext.getServletContext().getResourceAsStream(defaultFile);
			}
			if (null != inputStream) {
				output = ServletActionContext.getResponse().getOutputStream();//得到输出流
				IOUtils.copy(inputStream, output);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (null != inputStream){
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}
			if (null != output){
				try {
					output.close();
				} catch (IOException e) {
				}
			}
		}
	}

	protected void outputStreamFile(String reqFile){
		outputFile("application/octet-stream;charset=utf-8",reqFile,null);
	}

	protected void outputImageFile(String reqImage,String defaultImage){
		outputFile("image/jpeg;charset=utf-8",reqImage,defaultImage);
	}

}
