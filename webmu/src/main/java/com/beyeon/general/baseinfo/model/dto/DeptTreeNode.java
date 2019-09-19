package com.beyeon.general.baseinfo.model.dto;

import com.beyeon.common.web.model.dto.TreeNode;
import com.beyeon.hibernate.fivsdb.TDept;

public class DeptTreeNode extends TreeNode {
	public DeptTreeNode(TDept dept) {
		this.setKey(dept.getDeptId().toString());
		this.setTitle(dept.getDeptName());
		this.setLevel(dept.getLel());
		this.setFullId(dept.getFullDid());
	}
	public DeptTreeNode(TDept dept,String icon) {
		this(dept);
		this.setIcon(Boolean.valueOf(icon));
	}
}
