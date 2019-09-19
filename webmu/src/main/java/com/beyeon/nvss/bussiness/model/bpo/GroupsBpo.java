package com.beyeon.nvss.bussiness.model.bpo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beyeon.common.aop.annotation.Cache;
import com.beyeon.common.web.model.bpo.BaseBpo;
import com.beyeon.common.web.model.dto.AutoCompleteDto;
import com.beyeon.common.web.model.dto.TreeNode;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.general.baseinfo.model.bpo.DictBpo;
import com.beyeon.hibernate.fivsdb.GroupResource;
import com.beyeon.hibernate.fivsdb.Groups;
import com.beyeon.hibernate.fivsdb.Server;
import com.beyeon.hibernate.fivsdb.ServerType;
import com.beyeon.hibernate.fivsdb.TDict;
import com.beyeon.nvss.bussiness.model.dao.GroupsDao;

@Cache(cacheName = "资源组")
@Component("groupsBpo")
public class GroupsBpo extends BaseBpo{
	private static Logger logger =LoggerFactory.getLogger(GroupsBpo.class);
	private GroupsDao groupsDao;
	
	public void setGroupsDao(GroupsDao groupsDao) {
		this.groupsDao = groupsDao;
	}

	public Groups get(String id) {
		return this.groupsDao.getFivs(Groups.class, id);
	}
	
	public void save(Groups groups) {
		groups.setId(this.groupsDao.createId("group","","0",groups.getPosition()));
        this.groupsDao.saveFivs(groups);
	}
	
	public void update(Groups groups) {
        this.groupsDao.updateFivs(groups);
	}
	
	public void delete(String[] groupss) {
		this.groupsDao.deleteGroupsById(groupss);
	}

	public void find(PageObject pageObject) {
		this.groupsDao.find(pageObject);
	}

	public List<String> getGroupResourceIds(String id) {
		List<String> channelIds = this.groupsDao.getGroupResourceIds(id);
		channelIds.addAll(this.groupsDao.getGroupPlatformIds(id));
		return channelIds;
	}

	public boolean checkUnique(String attrName,String value,String id) {
		return this.groupsDao.checkGroupsUnique(id, value);
	}

	public List<TreeNode> findResourceTreeAndSelect(List<String> resourceIds){
		List<TreeNode> resourceTreeNodes = new ArrayList<TreeNode>();
		Map<String, TreeNode> resourceMap = new HashMap<String, TreeNode>();

		Map<String,String> topResources = Server.getObjectMap(ServerType.CMU.toString());
		if(null != topResources){
			for (Map.Entry<String,String> topResource : topResources.entrySet()) {
				TreeNode groupsTreeNode = new TreeNode(topResource.getKey(),topResource.getValue(),(short)0);
				groupsTreeNode.setExpand(true);
				resourceMap.put(groupsTreeNode.getKey(), groupsTreeNode);
				resourceTreeNodes.add(groupsTreeNode);
			}
			List<Map> servers = groupsDao.findServer();
			for (Map server : servers) {
				TreeNode serverTreeNode = new TreeNode((String)server.get("id"),(String)server.get("name"),(short)1);
				serverTreeNode.setExpand(true);
				resourceMap.get((String) server.get("parentId")).getChildren().add(serverTreeNode);
				resourceMap.put(serverTreeNode.getKey(), serverTreeNode);
			}
			List<Map> encoders = groupsDao.findEncoder();
			for (Map encoder : encoders) {
				TreeNode encoderTreeNode = new TreeNode((String)encoder.get("id"),(String)encoder.get("name"),(short)2);
				encoderTreeNode.setExpand(false);
				resourceMap.get((String) encoder.get("serverId")).getChildren().add(encoderTreeNode);
				resourceMap.put(encoderTreeNode.getKey(), encoderTreeNode);
			}
			List<Map> channels = groupsDao.findChannel();
			//channels.addAll(groupsDao.findPlatform());
			for (Map channel : channels) {
				TreeNode channelTreeNode = new TreeNode((String)channel.get("id"),(String)channel.get("name"),(short)3);
				resourceMap.get((String) channel.get("encoderId")).getChildren().add(channelTreeNode);
				if (null != resourceIds && resourceIds.contains(channelTreeNode.getKey())){
					channelTreeNode.setSelect(true);
				}
			}
			List<TreeNode> platformTreeNodes = TreeNode.init(null,resourceIds,groupsDao.findPlatform());
			for (TreeNode platformTreeNode : platformTreeNodes) {
				TreeNode topParentTree = resourceMap.get(platformTreeNode.getTopParentId());
				if (null != platformTreeNode.getSelect() && platformTreeNode.getSelect()) {
					topParentTree.setExpand(true);
					topParentTree.setSelect(true);
				}
				topParentTree.getChildren().add(platformTreeNode);
			}
		}
		resourceMap.clear();

		return resourceTreeNodes;
	}

	public TreeNode findGroupsTreeAndSelect(Map<String,Integer> groupsIds){
		List<TreeNode> groupsTreeNodes = new ArrayList<TreeNode>();
		Map<String, TreeNode> groupsTypeMap = new HashMap<String, TreeNode>();
		Collection<AutoCompleteDto> groupsTypes = DictBpo.find(TDict.GROUPS_TYPE);
		for (AutoCompleteDto groupsType : groupsTypes) {
			TreeNode groupsTreeNode = new TreeNode(groupsType.getValue(),groupsType.getLabel(),(short)1);
			groupsTreeNode.setExpand(true);
			groupsTypeMap.put(groupsTreeNode.getKey(), groupsTreeNode);
			groupsTreeNodes.add(groupsTreeNode);
		}
		List<Groups> groupses = groupsDao.findGroupsById(null);
		for (Groups groups : groupses) {
			TreeNode groupsTreeNode = new TreeNode(groups.getId(),groups.getName(),(short)2);
			groupsTreeNode.setExpand(true);
			groupsTypeMap.get(groups.getType().toString()).getChildren().add(groupsTreeNode);
			if (null != groupsIds && groupsIds.containsKey(groupsTreeNode.getKey())){
				groupsTreeNode.setSelect(true);
			}
			for (AutoCompleteDto userGroupRightType : DictBpo.find(TDict.USER_GROUPS_RIGHT_TYPE)) {
				TreeNode userGroupRightTypeTreeNode = new TreeNode(groupsTreeNode.getKey()+"---"+userGroupRightType.getValue(),userGroupRightType.getLabel(),(short)3);
				groupsTreeNode.getChildren().add(userGroupRightTypeTreeNode);
				if (null != groupsIds && null != groupsIds.get(groupsTreeNode.getKey()) && 0!=(groupsIds.get(groupsTreeNode.getKey())&Integer.valueOf(userGroupRightType.getValue()))){
					userGroupRightTypeTreeNode.setSelect(true);
				}
			}
		}
		groupsTypeMap.clear();
		TreeNode groupsTreeNode = new TreeNode("0","资源组", (short)0);
		groupsTreeNode.setExpand(true);
		groupsTreeNode.setChildren(groupsTreeNodes);
		return groupsTreeNode;
	}

	public void authForGroups(String id, String[] rids) {
		this.groupsDao.deleteResourceIds(id);
		List<GroupResource> groupResources = new ArrayList<GroupResource>();
		for (int i = 0; i < rids.length; i++) {
			GroupResource groupResource = new GroupResource(id,rids[i]);
			groupResources.add(groupResource);
		}
		this.groupsDao.saveAllFivs(groupResources);
	}

}
