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
import com.beyeon.common.web.model.dto.TreeNode;
import com.beyeon.common.web.model.entity.BaseEntity;
import com.beyeon.general.baseinfo.model.bpo.DictBpo;
import com.beyeon.hibernate.fivsdb.TDict;

@Component("dictAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DictAction extends BaseAction {
	private static final long serialVersionUID = -7914762527533273510L;
	private static Logger logger =LoggerFactory.getLogger(DictAction.class);
	private TDict dict ;
	
	public TDict getDict() {
		return dict;
	}
	public void setDict(TDict dict) {
		this.dict = dict;
	}
	
	public String beforeUpdate(){
		responseJSON(dict);
		return null;
	}
	
	public String update(){
		dict.setDmlflag(BaseEntity.edit);
		dict.setDmltime(new Date());
		baseinfoFacade.getDictBpo().update(dict);
		List list = new ArrayList();
		list.add(new TreeNode(dict.getDid().toString(), dict.getName(),(short)2));
		responseJSON(list);
		return null;
	}
	
	public String save(){
		dict.setDmlflag(BaseEntity.insert);
		baseinfoFacade.getDictBpo().save(dict);
		List list = new ArrayList();
		list.add(new TreeNode(dict.getDid().toString(), dict.getName(),(short)2));
		responseJSON(list,true);
		return null;
	}
	
	public String delete(){
		String id = this.getReqParam("dict.id");
		baseinfoFacade.getDictBpo().deleteByDid(id);
		responseHTML("ok");
		return null;
	}
	
	public String findDict(){
		TreeNode rootNode = null;
		String did = getReqParam("dict.did");
		if (StringUtils.isBlank(did)) {
			did = "0";
			rootNode = new TreeNode("0", "系统字典",(short)0);
		} else {
			rootNode = new TreeNode(did, DictBpo.getDictName(did),(short)1);
		}
		
		List<TreeNode> list = baseinfoFacade.getDictBpo().findDictTree(did);
		if(null != list && list.size()>0){
			list.get(0).setExpand(true);
		}

		rootNode.setExpand(true);
		rootNode.setChildren(list);
		this.setReqAttr("rootNodeName", rootNode.getTitle());
		this.setReqAttr("treeNodes", toJSON(rootNode));
		return "findDict";
	}

	public Object getModel() {
		if(null == dict){
			String mid = this.getReqParam("dict.did");
			if(StringUtils.isNotBlank(mid)){
				dict = baseinfoFacade.getDictBpo().get(Integer.valueOf(mid));
			} else {
				dict = new TDict();
			}
		}
		return dict;
	}
}
