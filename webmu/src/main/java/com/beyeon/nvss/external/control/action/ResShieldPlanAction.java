package com.beyeon.nvss.external.control.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.beyeon.bean.xml.cInterface.SetUserCamManageReqBean;
import com.beyeon.bean.xml.cInterface.SetUserCamManageResBean;
import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.util.DateUtil;
import com.beyeon.common.util.HttpClientUtil;
import com.beyeon.common.util.JaxbUtils;
import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.common.web.model.dto.TreeNode;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.constants.CommandConstant;
import com.beyeon.general.security.model.SecurityFacade;
import com.beyeon.hibernate.fivsdb.ServerType;
import com.beyeon.hibernate.fivsdb.TbResShieldPlan;
import com.beyeon.hibernate.fivsdb.TbShieldRes;
import com.beyeon.hibernate.fivsdb.TbShieldUser;
import com.beyeon.hibernate.fivsdb.UserInfo;
import com.beyeon.nvss.external.model.bpo.ResShieldPlanBpo;
import com.beyeon.nvss.external.model.dto.ResShieldPlanDto;

@Component("resShieldPlanAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ResShieldPlanAction extends BaseAction {
	private static Logger logger =LoggerFactory.getLogger(ResShieldPlanAction.class);
	private ResShieldPlanBpo resShieldPlanBpo;
	private ResShieldPlanDto resShieldPlanDto;
	private SecurityFacade securityFacade;

	public void setSecurityFacade(SecurityFacade securityFacade) {
		this.securityFacade = securityFacade;
	}
	
	public ResShieldPlanBpo getResShieldPlanBpo() {
		return resShieldPlanBpo;
	}

	public void setResShieldPlanBpo(ResShieldPlanBpo resShieldPlanBpo) {
		this.resShieldPlanBpo = resShieldPlanBpo;
	}

	public ResShieldPlanDto getResShieldPlanDto() {
		return resShieldPlanDto;
	}

	public void setResShieldPlanDto(ResShieldPlanDto resShieldPlanDto) {
		this.resShieldPlanDto = resShieldPlanDto;
	}

	public Object getModel() {
		if(null == resShieldPlanDto){
			String id = this.getReqParam("id");
			if(StringUtils.isNotBlank(id)){
				resShieldPlanDto = resShieldPlanBpo.get(id);
			} else {
				resShieldPlanDto = new ResShieldPlanDto();
			}
		}
		return resShieldPlanDto;
	}
	
	public String beforeUpdate(){
		TbResShieldPlan shield = resShieldPlanDto.getTbResShieldPlan();
		List<TbShieldRes> shielduRess = resShieldPlanDto.getShielduRess();
		List<TbShieldUser> shielduUsers = resShieldPlanDto.getShielduUsers();
		String ress = "";
		String users = "";
		for (int i = 0 ; i < shielduRess.size(); i++){
			TbShieldRes res = shielduRess.get(i);
			if (i >0 )
				ress+=",";
			ress+=res.getResId();
		}
		for (int i = 0 ; i < shielduUsers.size(); i++){
			TbShieldUser user = shielduUsers.get(i);
			if (i >0 )
				users+=",";
			users+=user.getUserId();
		}
		String usernames = resShieldPlanBpo.getUsernameByIds(users.split(","));
		String channels = resShieldPlanBpo.getChannelByIds(ress.split(","));
		this.setReqAttr("serverId",shield.getSid());
		this.setReqAttr("shieldId",shield.getId());
		this.setReqAttr("usernames", usernames);
		this.setReqAttr("channels", channels);
		return findPage();
	}

	public String update(){
//		positionBpo.update(position);
		List<TbShieldRes> shielduRess = resShieldPlanDto.getShielduRess();
		List<TbShieldUser> shielduUsers = resShieldPlanDto.getShielduUsers();
		String ress = "";
		String users = "";
		for (int i = 0 ; i < shielduRess.size(); i++){
			TbShieldRes res = shielduRess.get(i);
			if (i >0 )
				ress+=",";
			ress+=res.getResId();
		}
		for (int i = 0 ; i < shielduUsers.size(); i++){
			TbShieldUser user = shielduUsers.get(i);
			if (i >0 )
				users+=",";
			users+=user.getUserId();
		}
		TbResShieldPlan shield = resShieldPlanDto.getTbResShieldPlan();
		int status = shield.getStatus() == 1 ? 0 : 1;
		shield.setStatus(status);
		this.setUserCamManageUpdate(shield, ress.split(","), users.split(","));
		this.setReqAttr("serverId",shield.getSid());
		return findPage();
	}

	public String beforeSave(){
		String serverId = this.getReqParam("serverId");
		if(serverId!=null){
			this.setReqAttr("serverId",serverId);
		}
		//外域设备
	    List<TreeNode>	resourceTree = resShieldPlanBpo.findExtenalChannelTree(serverId);
		//外域用户
	    List<TreeNode>	userTree = resShieldPlanBpo.findExtenalUserTree(serverId);
	    this.setReqAttr("resTreeNode", toJSON(resourceTree));
	    this.setReqAttr("userTreeNode", toJSON(userTree));
		return "beforeSave";
	}
	
	public String save(){
		String resids = this.getReqParam("resids");
		String userids = this.getReqParam("userids");
		this.setUserCamManage(resShieldPlanDto, resids.split(","), userids.split(","));		
		this.setReqAttr("serverId",resShieldPlanDto.getTbResShieldPlan().getSid());
		return findPage();
	}
	
	private void setUserCamManage(ResShieldPlanDto resShieldPlanDto,String[] resids,String[] userids){
		SetUserCamManageResBean resBean = null;
		try {
			UserInfo user = this.securityFacade.getUserBpo().getUserInfoByUsername(this.getCurrentUser().getUsername());
			TbResShieldPlan tbResShieldPlan = resShieldPlanDto.getTbResShieldPlan();
			tbResShieldPlan.setCreateTime(DateUtil.getTime());
			SetUserCamManageReqBean bean = new SetUserCamManageReqBean();
			bean.setCommand(CommandConstant.SETUSERCAMMANAGE);
			
			bean.getParameters().setCuId(user.getId());
			bean.getParameters().setCuLevel(user.getUserLevel()+"");
			bean.getParameters().setAction(tbResShieldPlan.getStatus()+"");
			bean.getParameters().setStartTime(tbResShieldPlan.getBeginTime());
			bean.getParameters().setEndTime(tbResShieldPlan.getEndTime());
			bean.getParameters().setSchduleCreatTime(tbResShieldPlan.getCreateTime());
			
			SetUserCamManageReqBean.Group group = new SetUserCamManageReqBean.Group();
			List<SetUserCamManageReqBean.Url> urls = new ArrayList<SetUserCamManageReqBean.Url>();
			for(String resid :resids){
				if(!resid.equals(tbResShieldPlan.getSid())){
					SetUserCamManageReqBean.Url url = new SetUserCamManageReqBean.Url();
					url.setCamId(resid);
					urls.add(url);
				}
			}
			group.setUrls(urls);
			bean.getParameters().setGroup(group);
			
			SetUserCamManageReqBean.WhiteUser whiteUser = new SetUserCamManageReqBean.WhiteUser();
			List<SetUserCamManageReqBean.UserUrl> userUrls = new ArrayList<SetUserCamManageReqBean.UserUrl>();
			for(String userid :userids){
				if(!userid.equals(tbResShieldPlan.getSid())){
					SetUserCamManageReqBean.UserUrl userUrl = new SetUserCamManageReqBean.UserUrl();
					userUrl.setId(userid);
					userUrls.add(userUrl);
				}
			}
			whiteUser.setUrls(userUrls);
			bean.getParameters().setWhiteUser(whiteUser);
			System.out.println(JaxbUtils.marshaller(bean));
			
			StringBuilder cmuUrl = new StringBuilder();
			cmuUrl.append("http://").append(this.findServerIp(ServerType.CMU)).append(":")
					.append(ResourceUtil.getServerConf("cmu.server.port")).append("/TBMessageReq");
			String result = HttpClientUtil.post(cmuUrl.toString()+"?said="+tbResShieldPlan.getSid(), JaxbUtils.marshaller(bean));
			logger.debug(result);
			resBean = JaxbUtils.unmarshal(result, SetUserCamManageResBean.class);
			if (null == resBean || !resBean.getResult().getResultCode().equals("0")) {
				this.addActionMessage("屏蔽计划制定失败");
			}else{
				resShieldPlanBpo.save(tbResShieldPlan,bean);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			this.addActionMessage("屏蔽计划制定失败");
		}
	}
	
	private void setUserCamManageUpdate(TbResShieldPlan tbResShieldPlan,String[] resids,String[] userids){
		SetUserCamManageResBean resBean = null;
		try {
			UserInfo user = this.securityFacade.getUserBpo().getUserInfoByUsername(this.getCurrentUser().getUsername());
			
			tbResShieldPlan.setCreateTime(DateUtil.getTime());
			SetUserCamManageReqBean bean = new SetUserCamManageReqBean();
			bean.setCommand(CommandConstant.SETUSERCAMMANAGE);
			
			bean.getParameters().setCuId(user.getId());
			bean.getParameters().setCuLevel(user.getUserLevel()+"");
			bean.getParameters().setAction(tbResShieldPlan.getStatus()+"");
			bean.getParameters().setStartTime(tbResShieldPlan.getBeginTime());
			bean.getParameters().setEndTime(tbResShieldPlan.getEndTime());
			bean.getParameters().setSchduleCreatTime(tbResShieldPlan.getCreateTime());
			
			SetUserCamManageReqBean.Group group = new SetUserCamManageReqBean.Group();
			List<SetUserCamManageReqBean.Url> urls = new ArrayList<SetUserCamManageReqBean.Url>();
			for(String resid :resids){
				if(!resid.equals(tbResShieldPlan.getSid())){
					SetUserCamManageReqBean.Url url = new SetUserCamManageReqBean.Url();
					url.setCamId(resid);
					urls.add(url);
				}
			}
			group.setUrls(urls);
			bean.getParameters().setGroup(group);
			
			SetUserCamManageReqBean.WhiteUser whiteUser = new SetUserCamManageReqBean.WhiteUser();
			List<SetUserCamManageReqBean.UserUrl> userUrls = new ArrayList<SetUserCamManageReqBean.UserUrl>();
			for(String userid :userids){
				if(!userid.equals(tbResShieldPlan.getSid())){
					SetUserCamManageReqBean.UserUrl userUrl = new SetUserCamManageReqBean.UserUrl();
					userUrl.setId(userid);
					userUrls.add(userUrl);
				}
			}
			whiteUser.setUrls(userUrls);
			bean.getParameters().setWhiteUser(whiteUser);
			System.out.println(JaxbUtils.marshaller(bean));
			
			StringBuilder cmuUrl = new StringBuilder();
			cmuUrl.append("http://").append(this.findServerIp(ServerType.CMU)).append(":")
					.append(ResourceUtil.getServerConf("cmu.server.port")).append("/TBMessageReq");
			String result = HttpClientUtil.post(cmuUrl.toString()+"?said="+tbResShieldPlan.getSid(), JaxbUtils.marshaller(bean));
			logger.debug(result);
			resBean = JaxbUtils.unmarshal(result, SetUserCamManageResBean.class);
			if (null == resBean || !resBean.getResult().getResultCode().equals("0")) {
				this.addActionMessage("屏蔽计划制定失败");
			}else{
				resShieldPlanBpo.update(tbResShieldPlan);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			this.addActionMessage("屏蔽计划制定失败");
		}
	}
	
	public String delete(){
		String[] ids = this.getReqParams("items");
		String serverid = this.getReqParam("serverId");
		this.setReqAttr("serverId",serverid);
		if(resShieldPlanBpo.checkBeforeDel(ids)){
			resShieldPlanBpo.delete(ids);
		}else{
			this.addActionMessage("只可删除已取消禁止的屏蔽计划");
		}
		
		return findPage();
	}
	
	public String findPage(){
		String serverId = this.getReqParam("items");
		Object serverId1  = this.getReqAttr("serverId");
		if(serverId1!=null){
			serverId = serverId1.toString();
		}
		Object usernames  = this.getReqAttr("usernames");
		Object channels  = this.getReqAttr("channels");
		Object shieldid  = this.getReqAttr("shieldId");
		
		this.setReqAttr("usernames", usernames);
		this.setReqAttr("shieldId", shieldid);
		this.setReqAttr("channels", channels);
		this.setReqAttr("serverId",serverId);
		PageObject pageObject = getPageObject();
		Map<String, String> params = pageObject.getParams();
		params.put("sid", serverId);
		pageObject.setParams(params);
		resShieldPlanBpo.find(pageObject);
		return "findPage";
	}

}
