package com.beyeon.common.web.model.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class TreeNode {
	String key;
	String title;
	Boolean isFolder;
	Boolean isLazy = false;
	String href;
	Object icon = false;
	String iconPath;
	Boolean select;
	Boolean expand;
	Short level;
	String fullId;
	String topParentId;
	String parentId;
	String previosId;
	String nextId;
	TreeNode previosNode;
	TreeNode nextNode;
	List<TreeNode> children = new ArrayList<TreeNode>();

	public TreeNode() {
	}

	public TreeNode(String key, String title, Short level) {
		this.setKey(key);
		this.setTitle(title);
		this.setLevel(level);
		this.setExpand(false);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Boolean getIsLazy() {
		return isLazy;
	}

	public void setIsLazy(Boolean isLazy) {
		this.isLazy = isLazy;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getIsFolder() {
		return isFolder;
	}

	public void setIsFolder(Boolean isFolder) {
		this.isFolder = isFolder;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public Object getIcon() {
		return icon;
	}

	public void setIcon(Object icon) {
		this.icon = icon;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public Boolean getSelect() {
		return select;
	}

	public void setSelect(Boolean select) {
		this.select = select;
	}

	public Boolean getExpand() {
		return expand;
	}

	public void setExpand(Boolean expand) {
		this.expand = expand;
	}

	public Short getLevel() {
		return level;
	}

	public void setLevel(Short level) {
		this.level = level;
	}

	public String getFullId() {
		return fullId;
	}

	public void setFullId(String fullId) {
		this.fullId = fullId;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
		this.setIsFolder(null != children && children.size()>0);
	}

	public String getTopParentId() {
		return topParentId;
	}

	public void setTopParentId(String topParentId) {
		this.topParentId = topParentId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getPreviosId() {
		return previosId;
	}

	public void setPreviosId(String previosId) {
		this.previosId = previosId;
	}

	public String getNextId() {
		return nextId;
	}

	public void setNextId(String nextId) {
		this.nextId = nextId;
	}

	public TreeNode getPreviosNode() {
		return previosNode;
	}

	public void setPreviosNode(TreeNode previosNode) {
		this.previosNode = previosNode;
	}

	public TreeNode getNextNode() {
		return nextNode;
	}

	public void setNextNode(TreeNode nextNode) {
		this.nextNode = nextNode;
	}

	public static List<TreeNode> init(String topParentId,List<String> selectKeyes, List<TreeNode> treeNodes){
		List<TreeNode> result = new ArrayList<TreeNode>();
		Map<String,TreeNode> treeNodeMap = new HashMap<String,TreeNode>();
		for (TreeNode treeNode : treeNodes) {
			if (null != selectKeyes && selectKeyes.contains(treeNode.getKey())){
				treeNode.setSelect(true);
			}
			treeNodeMap.put(treeNode.getKey(),treeNode);
			if (StringUtils.isBlank(treeNode.getParentId()) || treeNode.getParentId().equals(topParentId)){
				//treeNode.setExpand(true);
				result.add(treeNode);
			}
			treeNode.setExpand(true);
			treeNode.setLevel((short)5);
		}
		for (TreeNode treeNode : treeNodes) {
			TreeNode parentNode = treeNodeMap.get(treeNode.getParentId());
			if (null != parentNode){
				parentNode.getChildren().add(treeNode);
			}
		}
		return result;
	}

	public static List<TreeNode> initSort(short level, List<TreeNode> treeNodes){
		List<TreeNode> result = new ArrayList<TreeNode>();
		for (TreeNode treeNode : treeNodes) {
			while (null != treeNode){
				treeNode.setLevel(level);
				treeNode.setChildren(initSort((short) (level + 1), treeNode.getChildren()));
				result.add(treeNode);
				TreeNode oldTreeNode = treeNode;
				treeNode = treeNode.getNextNode();
				oldTreeNode.setNextNode(null);
			}
		}
		return result;
	}
}
