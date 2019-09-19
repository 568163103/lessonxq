package com.beyeon.general.baseinfo.control.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.common.web.model.entity.BaseEntity;
import com.beyeon.general.baseinfo.model.dto.DeptTreeNode;
import com.beyeon.hibernate.fivsdb.TDept;

@Component("deptAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DeptAction extends BaseAction {
	private static final long serialVersionUID = -7914762527533273510L;
	private static Logger logger =LoggerFactory.getLogger(DeptAction.class);
	private TDept dept ;
	
	public TDept getDept() {
		return dept;
	}
	public void setDept(TDept dept) {
		this.dept = dept;
	}
	
	public String beforeUpdate(){
		responseJSON(dept);
		return null;
	}
	
	public String update(){
		dept.setDmlflag(BaseEntity.edit);
		dept.setDmltime(new Date());
		baseinfoFacade.getDeptBpo().update(dept);
		List list = new ArrayList();
		list.add(new DeptTreeNode(dept));
		responseJSON(list);
		return null;
	}
	
	public String save(){
		dept.setDmlflag(BaseEntity.insert);
		baseinfoFacade.getDeptBpo().save(dept);
		List list = new ArrayList();
		list.add(new DeptTreeNode(dept,"false"));
		responseJSON(list,true);
		return null;
	}
	
	public String delete(){
		String fullDid = this.getReqParam("dept.fullDid");
		baseinfoFacade.getDeptBpo().deleteDeptByFullDid(fullDid);
		responseHTML("ok");
		return null;
	}
	
	public String findPage(){
		String fullDid = getReqParam("dept.fullDid");
		if (StringUtils.isBlank(fullDid)) {
			fullDid = "101";
		}
		
		List<DeptTreeNode> list = baseinfoFacade.getDeptBpo().findDeptTree(fullDid);
		if(null != list && list.size()>0){
			list.get(0).setExpand(true);
		}
		setReqAttr("json", toJSON(list));
		return "findPage";
	}
	
	public String findDeptTree(){
		String did = getReqParam("dept.did");
		if (StringUtils.isBlank(did)) {
			did = "101";
		}
		
		List<DeptTreeNode> list = baseinfoFacade.getDeptBpo().findDeptTree(did);
		if(null != list && list.size()>0){
			list.get(0).setExpand(true);
		}
		this.responseJSON(list);
		return null;
	}

	public Object getModel() {
		if(null == dept){
			String mid = this.getReqParam("dept.deptId");
			if(StringUtils.isNotBlank(mid)){
				dept = baseinfoFacade.getDeptBpo().get(Integer.valueOf(mid));
			} else {
				dept = new TDept();
			}
		}
		return dept;
	}
}
