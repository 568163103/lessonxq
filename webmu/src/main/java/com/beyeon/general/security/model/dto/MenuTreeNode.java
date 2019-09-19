package com.beyeon.general.security.model.dto;

import com.beyeon.common.web.model.dto.TreeNode;
import com.beyeon.hibernate.fivsdb.TMenu;

public class MenuTreeNode extends TreeNode {

	public MenuTreeNode(TMenu menu) {
		this.setKey(menu.getMid().toString());
		this.setTitle(menu.getName());
		this.setIconPath(menu.getIconPath());
		this.setHref(menu.getUrl());
		this.setLevel(menu.getLel());
		this.setFullId(menu.getFullMid());
	}

	public MenuTreeNode(TMenu menu, String icon) {
		this(menu);
		this.setIcon(Boolean.valueOf(icon));
	}

	public MenuTreeNode(TMenu menu, String isLazy, String icon) {
		this(menu);
		this.setIcon(Boolean.valueOf(icon));
		this.setIsLazy(Boolean.valueOf(isLazy));
	}

	public MenuTreeNode(Integer key, String title, String href, Short level, Boolean icon) {
		this.setKey(key.toString());
		this.setTitle(title);
		this.setHref(href);
		this.setLevel(level);
		this.setIcon(icon);
	}

	public MenuTreeNode(Integer key, String title, String href, Short level, Boolean icon, String fullId) {
		this.setKey(key.toString());
		this.setTitle(title);
		this.setHref(href);
		this.setLevel(level);
		this.setIcon(icon);
		this.setFullId(fullId);
	}

	public MenuTreeNode(Integer key, String title, String href, Short level, Boolean icon, String iconPath, String fullId) {
		this.setKey(key.toString());
		this.setTitle(title);
		this.setHref(href);
		this.setLevel(level);
		this.setIcon(icon);
		this.setIconPath(iconPath);
		this.setFullId(fullId);
	}

}
