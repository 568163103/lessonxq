package com.beyeon.general.baseinfo.model.bpo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.beyeon.common.aop.annotation.Cache;
import com.beyeon.general.baseinfo.model.dao.DeptDao;
import com.beyeon.general.baseinfo.model.dto.DeptTreeNode;
import com.beyeon.hibernate.fivsdb.TDept;

//@Cache(cacheName = "组织机构管理")
@Component("deptBpo")
public class DeptBpo {
	public static Map<Integer,TDept> deptMap = new HashMap<Integer, TDept>();

	public static TDept getDept(Integer deptId){
		return deptMap.get(deptId);
	}
	private DeptDao deptDao;
	
	public void setdeptDao(DeptDao deptDao) {
		this.deptDao = deptDao;
	}

	public TDept get(Integer mid) {
		return this.deptDao.getFivs(TDept.class, mid);
	}
	
	public void save(TDept dept) {
        this.deptDao.saveFivs(dept);
        this.deptDao.freshFivs(dept);
        dept.setFullDid(dept.getFullDid()+"-"+dept.getDeptId().toString());
	}
	
	public void update(TDept dept) {
        this.deptDao.updateFivs(dept);
	}
	
	public void deleteDeptByFullDid(String fullDid) {
		this.deptDao.deleteDeptByDeptId(fullDid.substring(fullDid.lastIndexOf("-")+1));
		this.deptDao.deleteDeptByFullDid(fullDid);
	}

	public List<DeptTreeNode> findDeptTree(String did) {
		Map<Integer,DeptTreeNode> deptTreeNodeMap = new HashMap<Integer, DeptTreeNode>();
		List<DeptTreeNode> deptTreeNodes = new ArrayList<DeptTreeNode>();
		List<TDept> depts = new ArrayList<TDept>();
		List<TDept> parentDepts = this.deptDao.findByDid(did);
		for (TDept dept : parentDepts) {
			DeptTreeNode deptTreeNode = new DeptTreeNode(dept);
			deptTreeNode.setIcon(false);
			deptTreeNodes.add(deptTreeNode);
			deptTreeNodeMap.put(dept.getDeptId(), deptTreeNode);
		}
		
		for (int i = 0; i < deptTreeNodes.size(); i++) {
			List<TDept> tempDepts = this.deptDao.findDeptByFullDid(deptTreeNodes.get(i).getFullId());
			for (TDept dept : tempDepts) {
				DeptTreeNode deptTreeNode = new DeptTreeNode(dept);
				deptTreeNode.setIcon(false);
				depts.add(dept);
				deptTreeNodeMap.put(dept.getDeptId(), deptTreeNode);
			}
		}
		for (TDept dept : depts) {
			DeptTreeNode parentDeptTreeNode = deptTreeNodeMap.get(dept.getPreid());
			DeptTreeNode currDeptTreeNode = deptTreeNodeMap.get(dept.getDeptId());
			if(null == parentDeptTreeNode){
				deptTreeNodes.add(currDeptTreeNode);
			} else {
				parentDeptTreeNode.getChildren().add(currDeptTreeNode);
			}
		}
		return  deptTreeNodes;
	}

	@Cache(type = Cache.REFRESH,cacheName = "组织机构")
	public void initDeptTrees() {
		Map<Integer,TDept> tempDeptMap = new HashMap<Integer, TDept>();
		List<TDept> depts = deptDao.findDeptByFullDid(null);
		for (Iterator iterator = depts.iterator(); iterator.hasNext();) {
			TDept dept = (TDept) iterator.next();
			tempDeptMap.put(dept.getDeptId(), dept);
		}
		Map<Integer,TDept> trDeptMap = deptMap;
		deptMap = tempDeptMap;
		trDeptMap.clear();
	}
}
