package com.beyeon.nvss.bussiness.model.bpo;

import java.util.*;

import com.beyeon.hibernate.fivsdb.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.thread.ThreadPoolManager;
import com.beyeon.common.web.model.dto.TreeNode;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.nvss.bussiness.model.dao.GroupsDao;
import com.beyeon.nvss.bussiness.model.dao.UserTreeDao;
import com.beyeon.nvss.device.model.dao.ChannelDao;
import com.beyeon.nvss.device.model.dto.EncoderImpDto;
import com.beyeon.nvss.device.model.dto.TreeEncoderImpDto;

@Component("userTreeBpo")
public class UserTreeBpo {
	private static Logger logger =LoggerFactory.getLogger(UserTreeBpo.class);
	private UserTreeDao userTreeDao;
	private GroupsDao groupsDao;
	private ChannelDao channelDao;

	public void setUserTreeDao(UserTreeDao userTreeDao) {
		this.userTreeDao = userTreeDao;
	}

	public void setGroupsDao(GroupsDao groupsDao) {
		this.groupsDao = groupsDao;
	}

	public void setChannelDao(ChannelDao channelDao) {
		this.channelDao = channelDao;
	}

	public void authGroupsForUser(String uid, String gid) {
		if (StringUtils.isBlank(uid)){
			return;
		}
		String[] uids = uid.split(",");
		this.userTreeDao.deleteUserGroups(uids);
		if (StringUtils.isBlank(gid)){
			return;
		}
		String[] gids = gid.split(",");
		List<UserGroupRight> groupResources = new ArrayList<UserGroupRight>();
		for (String gi : gids) {
			for (String ui : uids) {
				UserGroupRight groupResource = new UserGroupRight(ui,gi.split("---")[0],Integer.valueOf(gi.split("---")[1]));
				groupResources.add(groupResource);
			}
		}
		this.userTreeDao.saveAllFivs(groupResources);
	}

	public void authGroupsForUserGroup(String uid, String gid) {
		if (StringUtils.isBlank(uid)){
			return;
		}
		String[] uids = uid.split(",");
		this.userTreeDao.deleteUserGroupGroups(uids);
		if (StringUtils.isBlank(gid)){
			return;
		}
		String[] gids = gid.split(",");
		List<UserGroupRight> groupResources = new ArrayList<UserGroupRight>();
		List<UserGroupRelation> groupRelations = new ArrayList<UserGroupRelation>();
		for (String gi : gids) {
			for (String ui : uids) {
				List<String> userIds = this.userTreeDao.findUserGroup(ui);
				for (String userid : userIds){
					UserGroupRight groupResource = new UserGroupRight(userid,gi.split("---")[0],Integer.valueOf(gi.split("---")[1]));
					groupResources.add(groupResource);
				}
				UserGroupRelation s = new UserGroupRelation(Integer.parseInt(ui),gi.split("---")[0],Integer.valueOf(gi.split("---")[1]));
				groupRelations.add(s);
			}
		}
		this.userTreeDao.saveAllFivs(groupResources);
		this.userTreeDao.saveAllFivs(groupRelations);


	}

	public void findPage(PageObject pageObject) {
		this.userTreeDao.findPage(pageObject);
	}
	public void findPageForUserAlarmRes(PageObject pageObject) {
		this.userTreeDao.findPageForUserAlarmRes(pageObject);
	}

	public void findPageUserForAuthAlarmRes(PageObject pageObject){
		this.userTreeDao.findPage(pageObject);
	}
	public void authAlarmResForUser(String uid, String gids) {
		if (StringUtils.isBlank(uid)){
			return;
		}
		this.userTreeDao.deleteUserAlarmRes(uid);
		if (StringUtils.isBlank(gids)){
			return;
		}
		String[] gid = gids.split(",");
		List<TbUserAlarmRes> alarmRess = new ArrayList<TbUserAlarmRes>();
		for (String gi : gid) {
			TbUserAlarmRes alarmRes = new TbUserAlarmRes(Integer.parseInt(gi),uid);
			alarmRess.add(alarmRes);
		}
		this.userTreeDao.saveAllFivs(alarmRess);
	}

	public List<Map<String,Object>> getUserGroupIds(String id) {
		return this.userTreeDao.getUserGroupIds(id);
	}
	public List<Map<String,Object>> getUserGroupGroupIds(String id) {
		return this.userTreeDao.getUserGroupGroupIds(id);
	}

	public List<Map<String,Object>> getUserAlarmRes(String id) {
		return this.userTreeDao.getUserAlarmRes(id);
	}

	public void findUser(PageObject pageObject) {
		this.userTreeDao.findUser(pageObject);
	}

	public List<TreeNode> findUserResourceTree(String uid) {
		List<UserTree> userTrees = this.userTreeDao.findUserTreeByUid(uid);
		return findUserResourceTree(uid,userTrees,"1",false,null);
	}

	public List<TreeNode> findUserResourceTree(String uid,String pid) {
		List<UserTree> userTrees = this.userTreeDao.findUserTreeByUid(uid,pid);
		return findUserResourceTree(uid,userTrees,"1",true,null);
	}

	public List<TreeNode> findUserResourceTree(String uid,String pid,String flag,Map<String,String> alarmResIds) {
		List<UserTree> userTrees = this.userTreeDao.findUserTreeByUid(uid,pid);
		return findUserResourceTree(uid,userTrees,flag,false,alarmResIds);
	}

	public List<TreeNode> getUserResourceTree(String channelId) {
		String uid = this.userTreeDao.findAdminUid();
		List<UserTree> userTrees = new ArrayList<UserTree>();
		UserTree userTree = new UserTree();
		userTree.setResId("2100007201000045");
		userTree.setName(Server.getObjectMap(ServerType.CMU.toString()).values().iterator().next());
		userTree.setUserId(uid);
		userTree.setParentId(Server.getObjectMap(ServerType.CMU.toString()).keySet().iterator().next());
		userTrees.add(userTree);
		String parentId = userTree.getResId();
		Channel channel = this.userTreeDao.getFivs(Channel.class, channelId);
		String channelName = "";Boolean channelStatus = false;Integer channelHasPtz = 0;
		if(null == channel){
			PlatformRes platformRes = this.userTreeDao.getFivs(PlatformRes.class,channelId);
			if (null != platformRes){
				channelName = platformRes.getName();channelStatus = (true==platformRes.getStatus());channelHasPtz = platformRes.getHasPtz();
			}
		} else {
			channelName = channel.getName();channelHasPtz = channel.getHasPtz();
		}
		userTree = new UserTree();
		userTree.setResId(channelId);
		userTree.setName(channelName);
		userTree.setUserId(uid);
		userTree.setParentId(parentId);
		userTree.setPreviousId("");
		userTree.setStatus(channelStatus);
		userTree.setHasPtz(channelHasPtz);
		userTrees.add(userTree);
		return findUserResourceTree(uid,userTrees,"1",false,null);
	}

	public List<TreeNode> findUserResourceTree(String uid,List<UserTree> userTrees,String flag,Boolean isLazy,Map<String,String> alarmResIds) {
		List<TreeNode> userResourceTreeNodes = new ArrayList<TreeNode>();
		Map<String, TreeNode> userResourceMap = new HashMap<String, TreeNode>();

		for (UserTree userResource : userTrees) {
			String icon = "tree_folder.png";
			String resId = userResource.getResId();
			String parentId = userResource.getParentId();if (null==parentId){parentId="";}
			StringBuilder treeNodeId = new StringBuilder(uid);
			if (resId.substring(6, 9).equals("120")){
				treeNodeId.append("---").append(parentId);
				icon = "tree_camera_" + (null != userResource.getHasPtz() && userResource.getHasPtz().equals("1")?"ptz_":"")+(null != userResource.getStatus() && userResource.getStatus()?"online":"offline")+".png";
			}
			treeNodeId.append("---").append(resId);
			TreeNode userResourceTreeNode = new TreeNode(treeNodeId.toString(),userResource.getName(),(short)1);
			userResourceTreeNode.setExpand(false);
			userResourceTreeNode.setIcon(icon);
			if (isLazy) {
				userResourceTreeNode.setIsLazy(!resId.substring(6, 9).equals("120"));
			}
			userResourceMap.put(treeNodeId.toString(), userResourceTreeNode);
			if((isLazy || StringUtils.isBlank(parentId) || parentId.substring(6, 10).equals("2007"))&& StringUtils.isBlank((String) userResource.getPreviousId())){
				userResourceTreeNode.setExpand(!isLazy);
				userResourceTreeNodes.add(userResourceTreeNode);
			}
		}

 		for (UserTree userResource : userTrees) {
			String parentId = userResource.getParentId();if (null==parentId){parentId="";}
			String resId = userResource.getResId();
			StringBuilder treeNodeId = new StringBuilder(uid);
			if (resId.substring(6, 9).equals("120")){
				treeNodeId.append("---").append(parentId);
			}
			treeNodeId.append("---").append(resId);
			TreeNode currTreeNode = userResourceMap.get(treeNodeId.toString());
			if("0".equals(flag)){
				List<UserTree> list = this.userTreeDao.findAlarmResUserTreeByResids(resId);
				for(UserTree userTree :list){
					TreeNode treeNode = new TreeNode(userTree.getAlarmId()+"", userTree.getAlarmName(),(short)1);
					if (null != alarmResIds && alarmResIds.containsKey(treeNode.getKey())){
						treeNode.setSelect(true);
					}
					currTreeNode.getChildren().add(treeNode);
				}
			}
			String previousId = userResource.getPreviousId();
			if(StringUtils.isNotBlank(previousId)){
				treeNodeId = new StringBuilder(uid);
				if (previousId.substring(6, 9).equals("120")){
					treeNodeId.append("---").append(parentId);
				}
				treeNodeId.append("---").append(previousId);
				TreeNode previousTreeNode = userResourceMap.get(treeNodeId.toString());
				if (null != previousTreeNode){
					previousTreeNode.setNextNode(currTreeNode);
				}
			}

			if(!isLazy && StringUtils.isNotBlank(parentId) && StringUtils.isBlank(previousId)){
				TreeNode parentTreeNode = userResourceMap.get(uid + "---" + parentId);
				if(null != parentTreeNode){
					parentTreeNode.getChildren().add(currTreeNode);
				}
			}
		}

		return TreeNode.initSort((short) 0, userResourceTreeNodes);
	}


	public List<TreeNode> findUserDmuResourceTree(String uid,String pid,String flag,Map<String,String> alarmResIds) {
		List<DmuEquipment> equipments = this.userTreeDao.findEquipmentList();
		return findUserDmuResourceTree(uid,equipments,flag,false,alarmResIds);
	}


	public List<TreeNode> findUserDmuResourceTree(String uid,List<DmuEquipment> userTrees,String flag,Boolean isLazy,Map<String,String> alarmResIds) {
		List<TreeNode> userResourceTreeNodes = new ArrayList<TreeNode>();
		Map<String, TreeNode> userResourceMap = new HashMap<String, TreeNode>();
		String cmuId = Server.getObjectMap(ServerType.CMU.toString()).keySet().iterator().next();
		Map<String,String> masterMap  = new HashMap<>();
		for (DmuEquipment dmuEquipment : userTrees){
			String master = dmuEquipment.getMaster();
			if (StringUtils.isBlank(master))
				master  =  cmuId;
			masterMap.put(master,null);
		}

		for (String key : masterMap.keySet()){
			String icon = "tree_folder.png";
			StringBuilder treeNodeId = new StringBuilder(uid);
			treeNodeId.append("---").append(key);
			String name = key;
			if (key.equals(cmuId)){
				name = "本级网管设备";
			}
			TreeNode userResourceTreeNode = new TreeNode(treeNodeId.toString(),name,(short)1);
			userResourceTreeNode.setExpand(false);
			userResourceTreeNode.setIcon(icon);
			userResourceMap.put(treeNodeId.toString(), userResourceTreeNode);
			userResourceTreeNode.setExpand(!isLazy);
			userResourceTreeNodes.add(userResourceTreeNode);
		}

		for (DmuEquipment dmuEquipment : userTrees) {
			String icon = "tree_camera_online.png";
			String resId = dmuEquipment.getId();
			String parentId = dmuEquipment.getMaster();
			if (StringUtils.isBlank(parentId)){
				parentId =  cmuId;
			}
			StringBuilder treeNodeId = new StringBuilder(uid);
			treeNodeId.append("---").append(parentId);
			TreeNode currTreeNode = userResourceMap.get(treeNodeId.toString());
			treeNodeId.append("---").append(resId);
			TreeNode userResourceTreeNode = new TreeNode(treeNodeId.toString(),dmuEquipment.getName(),(short)1);
			userResourceTreeNode.setExpand(false);
			userResourceTreeNode.setIcon(icon);
			if (isLazy) {
				userResourceTreeNode.setIsLazy(!resId.substring(6, 9).equals("120"));
			}
			userResourceMap.put(treeNodeId.toString(), userResourceTreeNode);
			currTreeNode.getChildren().add(userResourceTreeNode);
//			userResourceTreeNode.setExpand(!isLazy);
//			userResourceTreeNodes.add(userResourceTreeNode);
		}

		for (DmuEquipment dmuEquipment  : userTrees) {
			String parentId = dmuEquipment.getMaster();
			if (StringUtils.isBlank(parentId)){
				parentId =  cmuId;
			}
			String resId = dmuEquipment.getId();
			StringBuilder treeNodeId = new StringBuilder(uid);
			treeNodeId.append("---").append(parentId);
			treeNodeId.append("---").append(resId);
			TreeNode currTreeNode = userResourceMap.get(treeNodeId.toString());
			if("0".equals(flag)){
				List<UserTree> list = this.userTreeDao.findAlarmResUserTreeByResids(resId);
				for(UserTree userTree :list){
					TreeNode treeNode = new TreeNode(userTree.getAlarmId()+"", userTree.getAlarmName(),(short)1);
					if (null != alarmResIds && alarmResIds.containsKey(treeNode.getKey())){
						treeNode.setSelect(true);
					}
					currTreeNode.getChildren().add(treeNode);
				}
			}
		}

		return TreeNode.initSort((short) 0, userResourceTreeNodes);
	}

	public List<TreeNode> findExtenalChannelTree(String sid) {
		List<TreeNode> userResourceTreeNodes = new ArrayList<TreeNode>();
		List<Channel> channellist = this.channelDao.findPlatformResByEncoderId(sid);
		for (Channel channel : channellist) {
			TreeNode userResourceTreeNode = new TreeNode(channel.getId(),channel.getName(),(short)1);
			String icon = "tree_camera_" + (null != channel.getHasPtz() && channel.getHasPtz().equals("1")?"ptz_":"")+".png";
			List<UserTree> list = this.userTreeDao.findAlarmResUserTreeByResids(channel.getId());
			for(UserTree userTree :list){
				TreeNode treeNode = new TreeNode(userTree.getAlarmType()+"", userTree.getAlarmName(),(short)1);
				userResourceTreeNode.getChildren().add(treeNode);
			}
			userResourceTreeNode.setIcon(icon);
			userResourceTreeNodes.add(userResourceTreeNode);
		}

		return TreeNode.initSort((short) 0, userResourceTreeNodes);
	}

	public List<TreeNode> getUserGroupResourceTrees(String uid) {
		return getUserGroupResourceTrees(uid,null);
	}
	public List<TreeNode> getUserGroupResourceTrees(String uid,String param) {
		List<TreeNode> resourceTreeNodes = new ArrayList<TreeNode>();
		Map<String, TreeNode> groupMap = new HashMap<String, TreeNode>();

		List<Map> userGroupResources = this.userTreeDao.getUserGroupResources(uid,true,param);
		userGroupResources.addAll(this.userTreeDao.getUserGroupResources(uid,false,param));
		if(null != userGroupResources){
			for (Map channel : userGroupResources) {
				TreeNode groupTreeNode = groupMap.get((String) channel.get("groupId"));
				if (null == groupTreeNode){
					groupTreeNode = new TreeNode((String) channel.get("groupId"),(String) channel.get("groupName"),(short)0);
					groupTreeNode.setExpand(true);
					groupMap.put((String) channel.get("groupId"),groupTreeNode);
					resourceTreeNodes.add(groupTreeNode);
				}
				if (StringUtils.isNotBlank((String)channel.get("channelId"))) {
					TreeNode channelTreeNode = new TreeNode((String) channel.get("channelId"), (String) channel.get("channelName"), (short) 1);
					groupTreeNode.getChildren().add(channelTreeNode);
				}
			}
		}
		List<TreeNode> platformTreeNodes = new ArrayList<TreeNode>();
		userGroupResources = this.userTreeDao.getUserGroupPlatformRess(uid);
		if(null != userGroupResources){
			for (Map channel : userGroupResources) {
				TreeNode groupTreeNode = groupMap.get((String) channel.get("groupId"));
				if (null == groupTreeNode){
					groupTreeNode = new TreeNode((String) channel.get("groupId"),(String) channel.get("groupName"),(short)0);
					groupTreeNode.setExpand(false);
					groupMap.put((String) channel.get("groupId"),groupTreeNode);
					resourceTreeNodes.add(groupTreeNode);
				}
				if (StringUtils.isNotBlank((String)channel.get("channelId"))) {
					TreeNode channelTreeNode = new TreeNode((String) channel.get("channelId"), (String) channel.get("channelName"), (short) 1);
					channelTreeNode.setParentId((String) channel.get("parentId"));
					channelTreeNode.setTopParentId((String) channel.get("groupId"));
					platformTreeNodes.add(channelTreeNode);
				}
			}
			platformTreeNodes = TreeNode.init(null,null,platformTreeNodes);
			for (TreeNode platformTreeNode : platformTreeNodes) {
				groupMap.get(platformTreeNode.getTopParentId()).getChildren().addAll(platformTreeNode.getChildren());
			}
		}

		groupMap.clear();

		return resourceTreeNodes;
	}

	public void saveUserTree(UserTree userTree) {
		if (StringUtils.isBlank(userTree.getResId())){
			userTree.setResId(this.userTreeDao.createId("usertree","","0",userTree.getPosition()));
		}
		this.userTreeDao.saveFivs(userTree);
	}

	public void updateUserTree(UserTree userTree) {
		this.userTreeDao.updateFivs(userTree);
	}

	private List<String> fingChildUserTree(String userId, List<String> parentIds){
		List<String> result = new ArrayList<String>();
		List<String> resIds = this.userTreeDao.fingChildUserTree(userId,parentIds);
		if(resIds.size() > 0) {
			result.addAll(resIds);
			result.addAll(fingChildUserTree(userId, resIds));
		}
		return result;
	}

	public void deleteUserTree(UserTree userTree) {
		this.userTreeDao.deleteFivs(userTree);
		List<String> parentIds = new ArrayList<String>();
		parentIds.add(userTree.getResId());
		parentIds.addAll(this.fingChildUserTree(userTree.getUserId(), parentIds));
		this.userTreeDao.deleteChildUserTree(userTree.getUserId(), parentIds);
		this.userTreeDao.updateUserTreePreviousId(userTree.getUserId(), userTree.getParentId(), userTree.getResId(), userTree.getPreviousId());
	}

	public void moveUserTree(String model,String[] keys) {
		UserTree userTree = this.userTreeDao.findUserTreeByTreeKey(keys[0]);
		UserTree userTree1 = this.userTreeDao.findUserTreeByTreeKey(keys[1]);
		if(null != userTree && null != userTree1){
			UserTree userTreeAfter = this.userTreeDao.fingNextUserTree(userTree.getUserId(),userTree.getParentId(),userTree.getResId());
			if (null != userTreeAfter) {
				userTreeAfter.setPreviousId(userTree.getPreviousId());
				this.userTreeDao.updateFivs(userTreeAfter);
			}
			if("after".equals(model)){
				userTree.setPreviousId(userTree1.getResId());
				UserTree userTree1After = this.userTreeDao.fingNextUserTree(userTree1.getUserId(),userTree1.getParentId(),userTree1.getResId());
				if (null != userTree1After) {
					userTree1After.setPreviousId(userTree.getResId());
					this.userTreeDao.updateFivs(userTree1After);
				}
			} else {
				userTree.setPreviousId(userTree1.getPreviousId());
				userTree1.setPreviousId(userTree.getResId());
				this.userTreeDao.updateFivs(userTree1);
			}
			if (!userTree.getParentId().equals(userTree1.getParentId())) {
				UserTree newUserTree = new UserTree();
				BeanUtils.copyProperties(userTree, newUserTree);
				newUserTree.setParentId(userTree1.getParentId());
				this.userTreeDao.saveFivs(newUserTree);
				this.userTreeDao.deleteFivs(userTree);
			} else {
				this.userTreeDao.updateFivs(userTree);
			}
		}
	}

	public List findUser(String uid) {
		return userTreeDao.findUser(uid);
	}

	public void copyUserTreeToUser(String resIds, String[] uids) {
//		List<String> channelIds = new ArrayList<String>();
		String[] userTrees = resIds.split(";");
//		for (String userTree : userTrees) {
//			String[] tmpUserTrees = userTree.split("---");
//			if(tmpUserTrees[1].substring(6,9).equals("120")){
//				channelIds.add(tmpUserTrees[1]);
//			}
//		}
//		List<String> groupIds = this.groupsDao.findGroupsByChannelId(channelIds.toArray());
//		if (null != groupIds){
//			groupIds.addAll(this.groupsDao.findGroupsByPlatformId(channelIds.toArray()));
//		} else {
//			groupIds = this.groupsDao.findGroupsByPlatformId(channelIds.toArray());
//		}
//		this.userTreeDao.deleteUserGroups(uids);
		this.userTreeDao.deleteUserTrees(uids);
		for (String id : uids) {
			List<UserTree> newUserTrees = new ArrayList<UserTree>();
			Map<String,String> parentIdMap = new HashMap<String, String>();
			for (String userTree : userTrees) {
				String[] tmpUserTrees = userTree.split("---");
				UserTree newUserTree = new UserTree();
				newUserTree.setUserId(id);
				newUserTree.setResId(tmpUserTrees[1]);
				newUserTree.setName(tmpUserTrees[2]);
				newUserTree.setParentId(tmpUserTrees[0]);
				String previousId = parentIdMap.get(newUserTree.getParentId());
				if(null == previousId){
					previousId = "";
				}
				newUserTree.setPreviousId(previousId);
				newUserTrees.add(newUserTree);
				parentIdMap.put(newUserTree.getParentId(), newUserTree.getResId());
			}
			this.userTreeDao.saveAllFivs(newUserTrees);
//			for (String groupId : groupIds) {
//				UserGroupRight newUserGroupRight = new UserGroupRight();
//				newUserGroupRight.setUserId(id);
//				newUserGroupRight.setGroupId(groupId);
//				newUserGroupRight.setGroupRight(23);
//				this.userTreeDao.saveFivs(newUserGroupRight);
//			}
		}
	}

	public String findUid(String name){
		return this.userTreeDao.findUid(name);
	}

	public String findAdminUid(){
		return this.userTreeDao.findAdminUid();
	}

	public String findTopUserTree(String topParentId) {
		return this.userTreeDao.findTopUserTree(topParentId);
	}

	public void initUserResourceTree(String uid, String rootName, List<EncoderImpDto> encoderImpDtos) {
		logger.debug("start-------------------------");
		List<Map> groupChannels = this.userTreeDao.findGroupsChannel();
		groupChannels.addAll(this.userTreeDao.findGroupsPlatform());
		Map<String, List<Map>> groupChannelMap = new HashMap<String, List<Map>>();
		for (Map groupChannel : groupChannels) {
			List<Map> childChannels = groupChannelMap.get((String)groupChannel.get("name"));
			if (null == childChannels){
				childChannels = new ArrayList<Map>();
				groupChannelMap.put((String)groupChannel.get("name"),childChannels);
			}
			childChannels.add(groupChannel);
		}
		groupChannels.clear();

		HashMap<String, UserTree> oneTreeMap = new HashMap<String, UserTree>();
		HashMap<String, UserTree> twoTreeMap = new HashMap<String, UserTree>();
		HashMap<String, UserTree> encoderParTreeMap = new HashMap<String, UserTree>();
		HashMap<String, UserTree> channelTreeMap = new HashMap<String, UserTree>();

		List<String> lastUserTreeEds = new ArrayList<String>();
		Map<String,UserTree> lastUserTreeMap = new HashMap<String, UserTree>();
		List<UserTree> lastUserTrees = this.userTreeDao.findLastUserTrees(uid,null);
		lastUserTrees.addAll(this.userTreeDao.findLastUserTreesIsNull(uid,null));
		for (UserTree lastUserTree : lastUserTrees) {
			if(StringUtils.isNotBlank(lastUserTree.getName())) {
				lastUserTreeMap.put(lastUserTree.getName(), lastUserTree);
			}
		}

		String onePreviousId = "";
		UserTree rootTree = null;
		String topParentId = Server.getObjectMap(ServerType.CMU.toString()).keySet().iterator().next();
		if(lastUserTreeMap.size() == 0 && StringUtils.isNotBlank(rootName)) {
			rootTree = new UserTree();
			rootTree.setUserId(uid);
			rootTree.setParentId(topParentId);
			rootTree.setResId(this.userTreeDao.createId("usertree","","0",rootTree.getPosition()));
			rootTree.setName(rootName);
			rootTree.setPreviousId("");
			this.userTreeDao.saveFivs(rootTree);
		} else if (StringUtils.isNotBlank(rootName)){
			rootTree = lastUserTreeMap.get(rootName);
			if (null != rootTree){
				onePreviousId = rootTree.getPreviousId();
			}
		}
		Map<String,UserTree> encoderParNoMap = new HashMap<String, UserTree>();
		List<UserTree> encoderParNos = this.userTreeDao.findBusNos(uid);
		for (UserTree encoderParNo : encoderParNos) {
			encoderParNoMap.put(encoderParNo.getName(),encoderParNo);
		}
		List<UserTree> noExitUserTreeeds = new ArrayList<UserTree>();
		String twoPreviousId = "",encoderParPreviousId = "",channelPreviousId = "";
		for (EncoderImpDto encoderImpDto : encoderImpDtos) {
			String parentId = topParentId;
			if (null != rootTree){
				parentId = rootTree.getResId();
			}
			TreeEncoderImpDto treeEncoderImpDto = (TreeEncoderImpDto)encoderImpDto;
			UserTree oneTree = oneTreeMap.get(treeEncoderImpDto.getOneNo());
			if (null == oneTree && StringUtils.isNotBlank(treeEncoderImpDto.getOneName())) {
				oneTree = lastUserTreeMap.get(treeEncoderImpDto.getOneName());
				if (null != oneTree){
					if (!lastUserTreeEds.contains(oneTree.getResId())) {
						twoPreviousId = oneTree.getPreviousId();
						lastUserTreeEds.add(oneTree.getResId());
					}
				}
			}
			if(null == oneTree && StringUtils.isNotBlank(treeEncoderImpDto.getOneName())) {
				oneTree = new UserTree();
				oneTree.setUserId(uid);
				oneTree.setParentId(parentId);
				oneTree.setResId(this.userTreeDao.createId("usertree","","0",oneTree.getPosition()));
				oneTree.setName(treeEncoderImpDto.getOneName());
				oneTree.setPreviousId(onePreviousId);
				oneTreeMap.put(treeEncoderImpDto.getOneNo(),oneTree);
				onePreviousId = oneTree.getResId();
				twoPreviousId = "";
			}
			if (null == oneTree){
				oneTree = rootTree;
				twoPreviousId = onePreviousId;
			} else {
				parentId = oneTree.getResId();
			}
			UserTree twoTree = twoTreeMap.get(treeEncoderImpDto.getTwoNo());
			if (null == twoTree && StringUtils.isNotBlank(treeEncoderImpDto.getTwoName())){
				twoTree = lastUserTreeMap.get(treeEncoderImpDto.getTwoName());
				if (null != twoTree){
					if (!lastUserTreeEds.contains(twoTree.getResId())) {
						encoderParPreviousId = twoTree.getPreviousId();
						lastUserTreeEds.add(twoTree.getResId());
					}
				}
			}
			if(null == twoTree && StringUtils.isNotBlank(treeEncoderImpDto.getTwoName())) {
				twoTree = new UserTree();
				twoTree.setUserId(uid);
				twoTree.setParentId(parentId);
				twoTree.setResId(this.userTreeDao.createId("usertree","","0",twoTree.getPosition()));
				twoTree.setName(treeEncoderImpDto.getTwoName());
				twoTree.setPreviousId(twoPreviousId);
				twoTreeMap.put(treeEncoderImpDto.getTwoNo(),twoTree);
				twoPreviousId = twoTree.getResId();
				encoderParPreviousId = "";
			}
			if (null == twoTree){
				twoTree = oneTree;
				encoderParPreviousId = twoPreviousId;
			} else {
				parentId = twoTree.getResId();
			}
			UserTree encoderParTree = encoderParTreeMap.get(treeEncoderImpDto.getEncoderParNo());
			if (null == encoderParTree){
				encoderParTree = encoderParNoMap.get(treeEncoderImpDto.getEncoderParNo());
				if (null != encoderParTree && !encoderParTree.getParentId().equals(parentId)){
					noExitUserTreeeds.add(encoderParTree);
					encoderParTree = null;
				}
			}
			if (null == encoderParTree) {
				encoderParTree = new UserTree();
				encoderParTree.setUserId(uid);
				encoderParTree.setParentId(parentId);
				encoderParTree.setResId(this.userTreeDao.createId("usertree","","0",encoderParTree.getPosition()));
				encoderParTree.setName(treeEncoderImpDto.getEncoderParNo());
				encoderParTree.setPreviousId(encoderParPreviousId);
				encoderParTreeMap.put(treeEncoderImpDto.getEncoderParNo(), encoderParTree);
				encoderParPreviousId = encoderParTree.getResId();
			}
			List<Map> childChannels = groupChannelMap.get(treeEncoderImpDto.getEncoderName());
			if (null != childChannels){
				channelPreviousId = "";
				for (Map childChannel : childChannels) {
					UserTree channelTree = new UserTree();
					channelTree.setUserId(uid);
					channelTree.setParentId(encoderParTree.getResId());
					channelTree.setResId((String)childChannel.get("channelId"));
					channelTree.setName((String)childChannel.get("channelName"));
					channelTree.setPreviousId(channelPreviousId);
					channelTreeMap.put(channelTree.getResId(),channelTree);
					channelPreviousId = channelTree.getResId();
				}
			}
		}
		this.userTreeDao.saveAllFivs(oneTreeMap.values());
		this.userTreeDao.saveAllFivs(twoTreeMap.values());
		this.userTreeDao.saveAllFivs(encoderParTreeMap.values());
		this.userTreeDao.saveAllFivs(channelTreeMap.values());
		for (UserTree noExitUserTreeed : noExitUserTreeeds) {
			deleteUserTree(noExitUserTreeed);
		}
		refreshIndex(encoderParTreeMap.size(), userTreeDao);
		oneTreeMap.clear();
		twoTreeMap.clear();
		encoderParTreeMap.clear();
		channelTreeMap.clear();
		encoderImpDtos.clear();
		logger.debug("handle-------------------------end");
	}

	private void refreshIndex(final int createSize,final UserTreeDao userTreeDao ){
		ThreadPoolManager.excuteDefault(new Runnable() {
			@Override
			public void run() {
				try {
					logger.debug("新增节点："+createSize);
					if (createSize > 50) {
						userTreeDao.refreshIndex();
						logger.debug("refresh-------------------------end");
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		});
	}

	public void deleteUserTreeByEncoderId(String[] encoderIds) {
		for (String encoderId : encoderIds) {
			List<UserTree> noExitUserTrees = this.userTreeDao.findUserTreeByEncoderId(encoderId);
			for (UserTree noExitUserTree : noExitUserTrees) {
				this.deleteUserTree(noExitUserTree);
			}
		}
	}
	public void deleteUserTreeByChannelId(String[] channelIds) {
		for (String channelId : channelIds) {
			List<UserTree> noExitUserTrees = this.userTreeDao.findUserTreeByChannelId(channelId);
			for (UserTree noExitUserTree : noExitUserTrees ) {
				this.deleteUserTree(noExitUserTree);
			}
		}
	}

	public ArrayList<ArrayList<String>> findExcelFieldData1(String uid) {
		ArrayList<ArrayList<String>> fieldData = new ArrayList<ArrayList<String>>();
		// 存放excel的内容的所有数据
		List<UserTree> list = this.userTreeDao.findUserTreeByUidAndFlag(uid,"0");
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				// 存放每一行的数据
				UserTree usertree = (UserTree) list.get(i);
				// 将每一行的数据，放置到ArrayList<WmuOperationLog>
				if (usertree != null) {
					ArrayList<String> data = new ArrayList<String>();
					data.add(String.valueOf(i + 1));
					data.add(uid);
					data.add(usertree.getUsername());
					data.add(usertree.getResId());
					data.add(usertree.getName());
					data.add(usertree.getParentId());
					fieldData.add(data);
				}
			}
		}
		return fieldData;
	}

	public ArrayList<ArrayList<String>> findExcelFieldData2(String uid) {
		ArrayList<ArrayList<String>> fieldData = new ArrayList<ArrayList<String>>();
		// 存放excel的内容的所有数据
		List<UserTree> list = this.userTreeDao.findUserTreeByUidAndFlag(uid,"1");
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				// 存放每一行的数据
				UserTree usertree = (UserTree) list.get(i);
				// 将每一行的数据，放置到ArrayList<WmuOperationLog>
				if (usertree != null) {
					ArrayList<String> data = new ArrayList<String>();
					data.add(String.valueOf(i + 1));
					data.add(uid);
					data.add(usertree.getParentId());
					data.add(usertree.getResId());
					data.add(usertree.getName());
					fieldData.add(data);
				}
			}
		}
		return fieldData;
	}

	public Map<String,String> findUserList() {
		List<User> list = this.userTreeDao.findUserList();
		Map<String,String> map = new HashMap<String,String>();
		for (User user : list){
			map.put(user.getId(), user.getName());
		}
		return map;
	}

	public List<UserTree> findMenuByUserid(String userid) {
		return this.userTreeDao.findMenuByUserid(userid);
	}

	public void updateMenu(String[] ids,String userid,String parentid){
		String previousId = "";
		for (int i = 0 ; i < ids.length ; i ++){
			String[] idname = ids[i].split("\\|");
			String id = idname[0];
			String name = idname[1];
			this.userTreeDao.deleteUserTree(userid,id);
			this.userTreeDao.updatePreviousId(userid, id);
			if (StringUtils.isNotBlank(parentid)){
				UserTree usertree = new UserTree();
				usertree.setUserId(userid);
				usertree.setResId(id);
				usertree.setParentId(parentid);
				usertree.setName(name);
				usertree.setPreviousId(previousId);
				this.userTreeDao.saveFivs(usertree);
				previousId = id;
			}
		}

	}

	public  List<UserGroup> findUserGroupResource() {
		return this.userTreeDao.findUserGroupResource();
	}

	public  String findUserGroupResourceByUserId(String userId) {
		return this.userTreeDao.findUserGroupResourceByUserId(userId);
	}


	public void authGroupsForUserGroup2(String uid, String newId,String oldId) {
		if (StringUtils.isBlank(oldId) && StringUtils.isBlank(newId)){
			return;
		}else if (StringUtils.isNotBlank(newId) && oldId.equals(newId)){
			return;
		}

		if (StringUtils.isBlank(uid)){
			return;
		}
		String[] uids = uid.split(",");
		this.userTreeDao.deleteUserGroupById(uids);
		if (StringUtils.isBlank(newId)){
			return;
		}
		List<UserGroupRight> groupResources = new ArrayList<UserGroupRight>();
		List<UserGroupRelation> list = this.userTreeDao.findUserGroupRelation(newId);
		for (UserGroupRelation r : list ){
			UserGroupRight groupResource = new UserGroupRight(uid,r.getGroupId(),r.getGroupRight());
			groupResources.add(groupResource);
		}

		UserGroupResource re = new UserGroupResource(Integer.parseInt(newId),uid);

		this.userTreeDao.saveFivs(re);
		this.userTreeDao.saveAllFivs(groupResources);


	}

	/**
	 * 处理
	 * @param menuList
	 * @param resourceList
	 * @return
	 */
	public List<UserTree> handleWithExcelBeforeSave(List<UserTree> menuList,List<UserTree> resourceList){
		List<UserTree> templist = new ArrayList<UserTree>();
		templist.addAll(menuList);
		HashMap <String,String> userMap = new HashMap<String,String>();
		HashMap<String,List<Map>> resourceRight = new HashMap<String,List<Map>>();
		HashMap<String,String> parentMap = new HashMap<String,String>();
		for (UserTree menu : menuList){
			userMap.put(menu.getUserId(),"");
			parentMap.put(menu.getUserId()+"\\|"+menu.getResId(), "");
		}
		for (String userid : userMap.keySet()){
			List<Map> rightResources = this.userTreeDao.getUserGroupResources(userid);
			resourceRight.put(userid, rightResources);
		}
		for (UserTree resource: resourceList){
			String userid = resource.getUserId();
			String resId = resource.getResId();
			String parentId = resource.getParentId();
			List<Map> rightResources = resourceRight.get(userid);
			for (Map right : rightResources){
				if (resId.equals(right.get("id")) && (StringUtils.isBlank(parentId) || parentMap.containsKey(userid+"\\|"+parentId))){
					templist.add(resource);
					break;
				}
			}
		}


		return  templist;
	}

	public void handleWithExcel(List<UserTree> userTreeList){
		HashMap <String,String> userMap = new HashMap<String,String>();
		HashMap <String,String> parentMap = new HashMap<String,String>();
		for (UserTree usertree : userTreeList){
			userMap.put(usertree.getUserId(),"");
		}
		for (String userid : userMap.keySet()){
			this.userTreeDao.deleteUserTreeByUserid(userid);
		}
		for (UserTree usertree : userTreeList){
			String previousId = parentMap.get(usertree.getParentId());
			if (StringUtils.isBlank(previousId)){
				previousId = "";
			}
			usertree.setPreviousId(previousId);
			this.userTreeDao.saveFivs(usertree);
			parentMap.put(usertree.getParentId(), usertree.getResId());
		}
	}


	public List<UserTree> findUserTreeByUid (String uid,boolean flag){
		return this.userTreeDao.findBusNosByUid(uid,flag);
	}

	public List<UserTree> sysUserTree(String uid){
		List<UserTree> allList = this.userTreeDao.findBusByUid(uid,true);
		List<UserTree> menuList = this.userTreeDao.findBusNosByUid(uid,false);

		List<UserTree> needReplace = sort(menuList);
		for (UserTree userTree : needReplace){
			this.userTreeDao.deleteFivs(userTree);
		}
		List<UserTree>  result = new ArrayList<>();
		for (UserTree userTree : needReplace){
			String oldResId = userTree.getResId();
			String newResId = this.userTreeDao.createId("usertree","","0",userTree.getPosition());
			replace(allList,result,oldResId,newResId);
			userTree.setResId(newResId);
			this.userTreeDao.saveFivs(userTree);
		}
		for(UserTree userTree : result){
			this.userTreeDao.updateFivs(userTree);
		}
		return allList;
	}



	public static List<UserTree>  sort(List<UserTree> list){
		List<UserTree> result = new ArrayList<>();
		for (UserTree userTree : list){
			if (StringUtils.isBlank(userTree.getPreviousId())){
				result.add(userTree);
			}
		}
		list.removeAll(result);
		int m = 0 ;
		while(list.size()>0 || m > 100){
			for (int k = 0 ; k < list.size(); k ++){
				m ++;
				boolean flag = false;
				UserTree userTree = list.get(k);
				String previousId = userTree.getPreviousId();
				for (int i = 0 ; i < result.size(); i ++){
					UserTree user = result.get(i);
					String userid = user.getResId();
					if (previousId.equals(user.getResId()) || previousId.startsWith("120",6)){
						result.add(i+1,userTree);
						list.remove(k);
						flag = true;
						break;
					}
				}
				if (flag){
					break;
				}

			}
		}
		Iterator<UserTree> iterator = result.iterator();
		while(iterator.hasNext()){
			UserTree userTree = iterator.next();
			if (StringUtils.isBlank(userTree.getPreviousId())){
				iterator.remove();
			}
		}


		return result;
	}

	public static void  replace(List<UserTree> list , List<UserTree> result ,String oldResId,String newResId){
		for (UserTree userTree : list){
			boolean flag = false;
			if (oldResId.equals(userTree.getResId())){
				userTree.setResId(newResId);
				flag = true;
			}
			if (oldResId.equals(userTree.getParentId())){
				userTree.setParentId(newResId);
				flag = true;
			}
			if (oldResId.equals(userTree.getPreviousId())){
				userTree.setPreviousId(newResId);
				flag = true;
			}
			if (flag){
				result.add(userTree);
			}
		}
	}

	private void saveUserTreeList(List<UserTree> userTrees ,UserTreeDao userTreeDao ){
		ThreadPoolManager.excuteDefault(new Runnable() {
			@Override
			public void run() {
				try {
					userTreeDao.saveAllFivs(userTrees);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		});
	}
}
