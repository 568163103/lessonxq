package com.beyeon.common.web.model.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.beyeon.hibernate.fivsdb.Channel;
import com.beyeon.hibernate.fivsdb.Encoder;

public class EncoderChannelTreeDto {
	public class ECTreeNode {
		private String id;
		private String pid;
		private String title;
		private Boolean enabled;
		private String model;          // encoder
		private String description;
		private Boolean status;
		private Boolean isFolder = false;
		private Boolean isLazy = false;
		private Boolean expand = true;
		private String icon;
		
		private List<ECTreeNode> children = new ArrayList<ECTreeNode>();

		public void addChild(ECTreeNode node) {
			children.add(node);
			this.setIsFolder(true);
		}
		
		public Boolean hasChildren() {
			return children != null && children.size() > 0;
		}
		
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getPid() {
			return pid;
		}

		public void setPid(String pid) {
			this.pid = pid;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public Boolean getEnabled() {
			return enabled;
		}

		public void setEnabled(Boolean enabled) {
			this.enabled = enabled;
		}

		public String getModel() {
			return model;
		}

		public void setModel(String model) {
			this.model = model;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Boolean getStatus() {
			return status;
		}

		public void setStatus(Boolean status) {
			this.status = status;
		}

		public List<ECTreeNode> getChildren() {
			return children;
		}

		public void setChildren(List<ECTreeNode> children) {
			this.children = children;
			this.setIsFolder(children != null && children.size() > 0);
		}

		public Boolean getIsFolder() {
			return isFolder;
		}

		public void setIsFolder(Boolean isFolder) {
			this.isFolder = isFolder;
		}

		public Boolean getIsLazy() {
			return isLazy;
		}

		public void setIsLazy(Boolean isLazy) {
			this.isLazy = isLazy;
		}

		public Boolean getExpand() {
			return expand;
		}

		public void setExpand(Boolean expand) {
			this.expand = expand;
		}

		public String getIcon() {
			return icon;
		}

		public void setIcon(String icon) {
			this.icon = icon;
		}
	} // end public class ECTreeNode
	
	private List<ECTreeNode> ecTreeNodes = new ArrayList<ECTreeNode>();

	public void AddEncodersAndChannels(List encoders, List channels) {
		if (encoders == null) return;
		for (int idx = 0; idx < encoders.size(); ++idx) {
			Encoder encoder = (Encoder)encoders.get(idx);
			if (encoder.getModel().equals("platform"))
				continue;
			ECTreeNode node = AddEncoder(encoder);
			for (int c = 0; c < channels.size(); ++c) {
				AddChannel(node, (Channel)channels.get(c));
			}
		}
	}
	
	public void filter() {
		Iterator<ECTreeNode> itor = ecTreeNodes.iterator();
		while (itor.hasNext()) {
			ECTreeNode node = itor.next();
			if (!node.hasChildren())
				itor.remove();
		}
	}
	
	private ECTreeNode AddEncoder(Encoder e) {
		if (e == null) return null;
		ECTreeNode node = new ECTreeNode();
		node.setId(e.getId());
		node.setPid("0");
		node.setTitle(e.getName());
		node.setModel(e.getModel());
		node.setDescription(e.getDescription());
		node.setIcon("tree_folder.png");
		node.setEnabled(e.getEnabled());
		node.setStatus(e.getStatus());
		node.setIsFolder(true);
		
		ecTreeNodes.add(node);
		return node;
	}
	
	private ECTreeNode AddChannel(ECTreeNode parent, Channel c) {
		if (parent != null && c != null) {
			if (c.getEncoderId().equals(parent.getId())) {
				ECTreeNode node = new ECTreeNode();
				node.setId(c.getId());
				node.setPid(parent.getId());
				node.setTitle(c.getName());
				node.setModel("");
				node.setDescription(c.getDescription());
				node.setIcon("tree_camera_online.png");
				node.setEnabled(c.getEnabled());
				parent.addChild(node);
				return node;
			}
		}
		
		return null;
	}
	
	public List<ECTreeNode> getEcTreeNodes() {
		return ecTreeNodes;
	}

	public void setEcTreeNodes(List<ECTreeNode> ecTreeNodes) {
		this.ecTreeNodes = ecTreeNodes;
	}
}
