package com.beyeon.nvss.external.model.bpo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.beyeon.bean.xml.cInterface.SetUserCamManageReqBean;
import com.beyeon.common.web.model.dto.TreeNode;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.general.security.model.dao.UserDao;
import com.beyeon.hibernate.fivsdb.Channel;
import com.beyeon.hibernate.fivsdb.Encoder;
import com.beyeon.hibernate.fivsdb.TbResShieldPlan;
import com.beyeon.hibernate.fivsdb.TbShieldRes;
import com.beyeon.hibernate.fivsdb.TbShieldUser;
import com.beyeon.hibernate.fivsdb.TbUser;
import com.beyeon.nvss.device.model.dao.ChannelDao;
import com.beyeon.nvss.device.model.dao.EncoderDao;
import com.beyeon.nvss.external.model.dao.ResShieldPlanDao;
import com.beyeon.nvss.external.model.dto.ResShieldPlanDto;

@Component("resShieldPlanBpo")
public class ResShieldPlanBpo{

	private ResShieldPlanDao resShieldPlanDao;
	
	private ChannelDao channelDao;
	
	private EncoderDao encoderDao;
	
	private UserDao userDao;
	
	public void setChannelDao(ChannelDao channelDao) {
		this.channelDao = channelDao;
	}

	public void setEncoderDao(EncoderDao encoderDao) {
		this.encoderDao = encoderDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public ResShieldPlanDao getResShieldPlanDao() {
		return resShieldPlanDao;
	}

	public void setResShieldPlanDao(ResShieldPlanDao resShieldPlanDao) {
		this.resShieldPlanDao = resShieldPlanDao;
	}
	
	public ResShieldPlanDto get(String id) {
		ResShieldPlanDto resShieldPlanDto = new ResShieldPlanDto();
		resShieldPlanDto.setTbResShieldPlan(this.resShieldPlanDao.getFivs(TbResShieldPlan.class, Integer.parseInt(id)));
		resShieldPlanDto.setShielduUsers(this.resShieldPlanDao.getShielduUsers(Integer.parseInt(id)));
		resShieldPlanDto.setShielduRess(this.resShieldPlanDao.getShielduRess(Integer.parseInt(id)));
//		encoderDto.setEncoder(this.encoderDao.getFivs(Encoder.class, id));
//		encoderDto.setServerEncoder(this.encoderDao.getServerEncoder(id));
//		encoderDto.setGroups(this.groupsDao.findGroupsByEncoderId(encoderDto.getEncoder().getId()));
//		encoderDto.setChannels(this.channelDao.findByEncoderId(id));
//		List<Channel> platformReses = this.channelDao.findPlatformResByEncoderId(id);
//		if (platformReses != null && platformReses.size() > 0){
//			encoderDto.getChannels().addAll(platformReses);
//		}
//		if(encoderDto.getChannels().size() > 0 ) {
//			encoderDto.setMsuChannel(this.channelDao.findMsuChannel(encoderDto.getChannels().get(0).getId()));
//		}
		return resShieldPlanDto;
	}
	
	public List<TreeNode> findExtenalChannelTree(String sid) {
		List<TreeNode> userResourceTreeNodes = new ArrayList<TreeNode>();
		Encoder encoder = this.encoderDao.findById(sid);
		List<Channel> channellist = this.channelDao.findPlatformResByEncoderId(sid);
		TreeNode userResourceTreeNode = new TreeNode(encoder.getId(),encoder.getName(),(short)1);
		for (Channel channel : channellist) {
			String icon = "tree_camera_" + (null != channel.getHasPtz() && channel.getHasPtz().equals("1")?"ptz_":"")+".png";
			TreeNode treeNode = new TreeNode(channel.getId(), channel.getName(),(short)1);
			userResourceTreeNode.getChildren().add(treeNode);
			userResourceTreeNode.setIcon(icon);
		}
		userResourceTreeNodes.add(userResourceTreeNode);
		return TreeNode.initSort((short) 0, userResourceTreeNodes);
	}
	
	public List<TreeNode> findExtenalUserTree(String sid) {
		List<TreeNode> userResourceTreeNodes = new ArrayList<TreeNode>();
		Encoder encoder = this.encoderDao.findById(sid);
		List<TbUser> userlist = this.userDao.findExternalUserBySid(sid);
		TreeNode userResourceTreeNode = new TreeNode(encoder.getId(),encoder.getName(),(short)1);
		for (TbUser tbUser : userlist) {
			TreeNode treeNode = new TreeNode(tbUser.getId(), tbUser.getName(),(short)1);
			userResourceTreeNode.getChildren().add(treeNode);
		}
		userResourceTreeNodes.add(userResourceTreeNode);
		return TreeNode.initSort((short) 0, userResourceTreeNodes);
	}

	public void save(TbResShieldPlan tbResShieldPlan,SetUserCamManageReqBean bean) {
		tbResShieldPlan.setUserId(bean.getParameters().getCuId());
		tbResShieldPlan.setUserLevel(Integer.parseInt(bean.getParameters().getCuLevel()));
		this.resShieldPlanDao.saveFivs(tbResShieldPlan);
		int shieldId = tbResShieldPlan.getId();
		List<SetUserCamManageReqBean.Url> resUrls = bean.getParameters().getGroup().getUrls();
		for(SetUserCamManageReqBean.Url resUrl :resUrls){
			TbShieldRes tbShieldRes = new TbShieldRes(resUrl.getCamId(), shieldId);
			this.resShieldPlanDao.saveFivs(tbShieldRes);
			
		}
		List<SetUserCamManageReqBean.UserUrl> userUrls = bean.getParameters().getWhiteUser().getUrls();
		for(SetUserCamManageReqBean.UserUrl userUrl :userUrls){
				TbShieldUser tbShieldUser = new TbShieldUser(userUrl.getId(), shieldId);
				this.resShieldPlanDao.saveFivs(tbShieldUser);
		}
	}
	public void update (TbResShieldPlan tbResShieldPlan){
		this.resShieldPlanDao.updateFivs(tbResShieldPlan);
	}
	public void find(PageObject pageObject) {
		this.resShieldPlanDao.find(pageObject);
	}
	
	public void delete(String[] ids) {
		this.resShieldPlanDao.delete(ids);
	}
	
	public String getUsernameByIds(String[] userids){
		List<HashMap<String,String>> list =this.userDao.findUserByIds(userids);
		String usernames ="";
		for (int i = 0 ; i < list.size(); i++){
			HashMap<String,String> user = list.get(i);
			if (i >0 )
				usernames+=",";
			usernames+=user.get("name");
		}
		return usernames;
	}
	
	public String getChannelByIds(String[] channels){
		List<HashMap<String,String>> list =this.channelDao.findChannelByIds(channels);
		String names ="";
		for (int i = 0 ; i < list.size(); i++){
			HashMap<String,String> user = list.get(i);
			if (i >0 )
				names+=",";
			names+=user.get("name");
		}
		return names;
	}
	
	public boolean checkBeforeDel (String[] ids){
		return this.resShieldPlanDao.checkBeforeDel(ids);
	}
	
}
