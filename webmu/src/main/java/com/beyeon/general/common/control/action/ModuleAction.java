package com.beyeon.general.common.control.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.scheduling.SLSchedulerFactoryBean;
import com.beyeon.common.security.spring.access.authentication.UrlJumpContainer;
import com.beyeon.common.security.spring.detail.UserDetail;
import com.beyeon.common.system.sn.SystemSn;
import com.beyeon.common.util.DateUtil;
import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.common.web.model.dto.TreeNode;
import com.beyeon.general.baseinfo.model.bpo.DictBpo;
import com.beyeon.general.security.model.SecurityFacade;
import com.beyeon.general.security.model.dao.UserDao;
import com.beyeon.general.security.model.dto.MenuTreeNode;
import com.beyeon.hibernate.fivsdb.SystemConfig;
import com.beyeon.hibernate.fivsdb.TDict;
import com.beyeon.hibernate.fivsdb.UserInfo;
import com.beyeon.security.util.SystemSnUtils;
import com.opensymphony.xwork2.ActionContext;

//@Component("moduleAction")
public class ModuleAction extends BaseAction {
	private static final long serialVersionUID = 6190122976807529168L;
	protected Map<String,String> moduleNames = new HashMap<String,String>();
	protected SecurityFacade securityFacade;

	public void setSecurityFacade(SecurityFacade securityFacade) {
		this.securityFacade = securityFacade;
	}

	public String top(){
		if(moduleNames.size()==0){
			List<MenuTreeNode> menuTreeNodes = this.securityFacade.getMenuBpo().findByMid(ResourceUtil.getCoreConf("app.all.module"));
			for (MenuTreeNode menuTreeNode : menuTreeNodes) {
				moduleNames.put(menuTreeNode.getKey().toString(),menuTreeNode.getTitle());
			}
		}
		String moduleName = ResourceUtil.getAppName();
		String version = ResourceUtil.getAppVersion();
		super.setReqAttr("moduleName",moduleName);
		super.setReqAttr("version", "版本号:"+version);		
		
		String username = super.getCurrentUser().getUsername();
		UserInfo user = this.securityFacade.getUserBpo().getUserInfoByUsername(username);
		if (user!=null ){
			if (!"superadmin".equals(username)){
				int flag = SystemConfig.getConfig().getPwdResetFlag();
				int time = SystemConfig.getConfig().getKeepPasswordTime();
				int remand = SystemConfig.getConfig().getPwdRemaindTime();
				if (flag == 1 ){
					if (user.getLastLoginTime()!=null){
						String lastLoginTime = user.getLastLoginTime();						
						Date begin = DateUtil.parse(lastLoginTime, DateUtil.yyyyMMddHHmmssSpt);
						Date after = DateUtil.addMinutes(begin, time);					
						long timedistanct = (after.getTime()- new Date().getTime()) /1000;
						long day = timedistanct / 60;
						
						if (day<remand){
							super.setReqAttr("message", timedistanct);		
						}
						
					}
				}
			}
		}
		return "top";
	}

	public String defaul(){
		UserDetail ud = super.getCurrentUser();
		if(null != ud){
			List<Map> topModules = this.securityFacade.getMenuBpo().findTopMidCurrUser(ResourceUtil.getCoreConf("app.all.module").replace("1,", ""), ud.getId());
			super.setReqAttr("topModules",topModules);
			if (topModules.size() == 1){
				super.setSessAttr("topNodeSize","1");
				super.setReqAttr("parentId", topModules.get(0).get("mid").toString());
				if("3,6".indexOf(topModules.get(0).get("mid").toString()) != -1){
					return "selfManageIndex";
				} else {
					return "index";
				}
			} else if (topModules.size() > 1){
				for (int i = 0 ; i < topModules.size() ; i++){
					if("7".equals(topModules.get(i).get("mid").toString())){
						super.setReqAttr("parentId",topModules.get(i).get("mid").toString());
						return "index";
					}
				}
			}
		}
		return "defaul";
	}

	public String index(){
		String parentId = this.getReqParam("parentId");
		if(StringUtils.isBlank(parentId)){
			String[] parentIds = ResourceUtil.getCoreConf("app.all.module").split(",");
			if (parentIds.length > 0){
				parentId = parentIds[0];
			}
		}
		super.setReqAttr("parentId",parentId);
		String resetflag = "0";
		String available ="0";
		String username = super.getCurrentUser().getUsername();
		UserInfo user = this.securityFacade.getUserBpo().getUserInfoByUsername(username);
		if (user!=null ){
			if (!"superadmin".equals(username)){
				int flag = SystemConfig.getConfig().getPwdResetFlag();
				int time = SystemConfig.getConfig().getKeepPasswordTime();
				if (flag == 1){
					if (StringUtils.isBlank(user.getLastLoginTime())){
						resetflag = "1";  //初次登录修改密码标识
					}else{
						String lastLoginTime = user.getLastLoginTime();
						
						Date begin = DateUtil.parse(lastLoginTime, DateUtil.yyyyMMddHHmmssSpt);
						Date after = DateUtil.addMinutes(begin, time);
						int i = after.compareTo(new Date());
						if (i <0 ){
							resetflag = "2";  //周期内需修改密码
						}
					}
				}
				
				if(!SystemSnUtils.isAvailable()){
					available = "1";
				}
				
			}
			
		}
		super.setReqAttr("flag", resetflag);
		super.setReqAttr("available", available);
		return "index";
	}
	
	public String left(){
		String parentId = getReqParam("parentId");
		if (StringUtils.isBlank(parentId)) {
			parentId = ResourceUtil.getCoreConf("app.all.module");
		}
		if("3,6".indexOf(parentId) != -1){
			UrlJumpContainer.setModuleType(ServletActionContext.getRequest(),ServletActionContext.getResponse(),UrlJumpContainer.MODULE_TYPE_SELF);
		} else {
			UrlJumpContainer.setModuleType(ServletActionContext.getRequest(),ServletActionContext.getResponse(),UrlJumpContainer.MODULE_TYPE_NONE);
		}
		List<TreeNode> list = securityFacade.getMenuBpo().findMenuTreeCurrUser(parentId,super.getCurrentUser().getId());
		super.setReqAttr("menuTreeNodes", createTopMenuHtml(list));
		return "left";
	}

	protected String createTopMenuHtml(List<TreeNode> list){
		String contextPath = ServletActionContext.getRequest().getContextPath();
		if("/".equals(contextPath)){
			contextPath = "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			TreeNode menuTreeNode =  list.get(i);
			if(StringUtils.isBlank(menuTreeNode.getHref())){
				sb.append("<h3>").append(menuTreeNode.getTitle()).append("</h3>");
			} else {
				sb.append("<h3 onclick=\"javascript:openPageInIframe('").append(contextPath);
				sb.append(menuTreeNode.getHref()).append("');\">").append(menuTreeNode.getTitle()).append("</h3>");
			}
			sb.append("<div>").append(createMenuHtml(menuTreeNode.getChildren(),0,contextPath)).append("</div>");
		}
		return sb.toString();
	}

	protected StringBuffer createMenuHtml(List<TreeNode> list,int level,String contextPath){
		if(null == list || list.size() == 0){
			return new StringBuffer("<div></div>");
		}
		StringBuffer sb = new StringBuffer("<ul style='z-index:9999;'");
		if(level == 0){
			sb.append(" class='menu'");
		}
		sb.append(">");
		for (int i = 0; i < list.size(); i++) {
			TreeNode menuTreeNode =  list.get(i);
			if(StringUtils.isBlank(menuTreeNode.getHref())){
				sb.append("<li><a href=\"javascript:void(0);\">").append("<span class='ui-icon' style=\"background-image: url('").append(contextPath).append(menuTreeNode.getIconPath()).append("');\"> </span>").append(menuTreeNode.getTitle()).append("</a>");
			} else {
				sb.append("<li><a href=\"javascript:openPageInIframe('").append(contextPath);
				sb.append(menuTreeNode.getHref()).append("');\">").append("<span class='ui-icon' style=\"background-image: url('").append(contextPath).append(menuTreeNode.getIconPath()).append("');\"> </span>").append(menuTreeNode.getTitle()).append("</a>");
			}
			sb.append(createMenuHtml(menuTreeNode.getChildren(),++level,contextPath));
			sb.append("</li>");
		}
		sb.append("</ul>");
		return sb;
	}

	public String refreshCache(){
		String jobName = this.getReqParam("jobName");
		if(StringUtils.isNotBlank(jobName)){
			if ("all".equals(jobName)){
				Map<String,String> jobMap = SLSchedulerFactoryBean.listJob();
				for (String key : jobMap.keySet()){
					SLSchedulerFactoryBean.runJob(key);
				}
			}else{
				SLSchedulerFactoryBean.runJob(jobName);
			}
			responseHTML("ok");
			return null;
		}
		this.setReqAttr("listJob",SLSchedulerFactoryBean.listJob());
		return "refreshCache";
	}

	public String findSn(){
		logger.debug(toJSON(SystemSnUtils.getSystemSn()));
		return "findSn";
	}

	public String exportSn(){
		OutputStream out = null;
		try {
			/** 响应头 */
			ServletActionContext.getResponse().setContentType("application/octet-stream;charset=UTF-8");
			ServletActionContext.getResponse().setHeader("Content-Disposition", "attachment;filename=" + new String("系统使用许可.txt".getBytes(), "iso8859-1"));
			out = ServletActionContext.getResponse().getOutputStream();
			IOUtils.write(SystemSnUtils.getSystemSnSrc(), out);
			out.flush();
			ServletActionContext.getResponse().flushBuffer();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
			}
		}
		return null;
	}

	public String importSn(){
		OutputStream out = null;
		try {
			File[] file = (File[]) ActionContext.getContext().getParameters().get("fileSource");
			if (null == file || file.length == 0){
				this.responseHTML("上传文件无效！");
				return null;
			}
			SystemSnUtils.setSystemSn(FileUtils.readFileToString(file[0]));
			file[0].delete();
			this.responseHTML("ok");
		} catch (Exception e) {
			this.responseHTML("导入失败！");
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
			}
		}
		return null;
	}

	public String beforeEditSn(){
		OutputStream out = null;
		try {
			String key = this.getReqParam("k");
			File[] file = (File[]) ActionContext.getContext().getParameters().get("fileSource");
			if (null == file || file.length == 0){
				this.responseHTML("上传文件无效！");
				return null;
			}
			String sn = SystemSnUtils.decoder(key, FileUtils.readFileToString(file[0]));
			SystemSn systemSn = JSON.parseObject(sn,SystemSn.class);
			this.responseJSON(systemSn);
			file[0].delete();
		} catch (Exception e) {
			this.responseHTML("解析失败！");
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
			}
		}
		return null;
	}

	public String cteateSn(){
		OutputStream out = null;
		try {
			String key = DictBpo.getDict(TDict.SYSTEM_PARAM, "1").getDescr();
			String uuid = this.getReqParam("uuid");
			String userNum = this.getReqParam("userNum");
			String deviceNum = this.getReqParam("deviceNum");
			String endTime = this.getReqParam("endTime");
			/** 响应头 */
			ServletActionContext.getResponse().setContentType("application/octet-stream;charset=UTF-8");
			ServletActionContext.getResponse().setHeader("Content-Disposition", "attachment;filename=" + new String("系统使用许可.txt".getBytes(), "iso8859-1"));
			out = ServletActionContext.getResponse().getOutputStream();
			IOUtils.write(SystemSnUtils.createSn(key,uuid,Integer.valueOf(userNum),Integer.valueOf(deviceNum),endTime), out);
			out.flush();
			ServletActionContext.getResponse().flushBuffer();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
			}
		}
		return null;
	}
}
