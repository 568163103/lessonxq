package com.beyeon.nvss.device.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.Equipment;
import com.beyeon.hibernate.fivsdb.EquipmentCorp;
import com.beyeon.nvss.common.model.dao.NvsBaseDao;

@Component("equipmentDao")
public class EquipmentDao extends NvsBaseDao {

	public void delete(String[] sids) {
		String[] paramNames = { "sids" };
		this.getFivs_w().bulkUpdateSQLByParamName("delete s,de from equipment s left join dmu_equipment de on s.id = de.id where s.id in (:sids)",
				paramNames, new Object[]{sids});
	}

	public void find(PageObject pageObject) {
		StringBuilder sb = new StringBuilder("select s from Equipment s where 1=1 ");
		List params = new ArrayList();
		Map<String, String> paramMap = pageObject.getParams();
		if (StringUtils.isNotBlank(paramMap.get("id"))) {
			params.add(paramMap.get("id"));
			sb.append("and s.id = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("name"))) {
			params.add("%" + paramMap.get("name") + "%");
			sb.append("and s.name like ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("ip"))) {
			params.add(paramMap.get("ip"));
			sb.append("and s.ip = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("type"))) {
			params.add(Integer.valueOf(paramMap.get("type")));
			sb.append("and s.type = ? ");
		}		
		if (StringUtils.isNotBlank(paramMap.get("status"))) {
			params.add(Boolean.valueOf(paramMap.get("status")));
			sb.append("and s.status = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("position"))) {
			params.add(paramMap.get("position") + "%");
			sb.append("and s.id like ? ");
		}
		sb.append(" order by s.status desc");
		this.getFivs_r().find(pageObject, sb.toString(), params.toArray());
	}

	public List findEquipmentType() {
		String hql = new String("select s from EquipmentType s ");
		return this.getFivs_r().find(hql,null);
	}
	
	public List findEquipmentCorp() {
		String hql = new String("select s from EquipmentCorp s ");
		return this.getFivs_r().find(hql,null);
	}

	public boolean checkEquipmentUnique(String id, String equipmentname) {
		StringBuilder sql = new StringBuilder("select t from Equipment t where t.name = ? ");
		List params = new ArrayList();
		params.add(equipmentname);
		if (StringUtils.isNotBlank(id)){
			sql.append("and t.id != ? ");
			params.add(id);
		}
		List list = this.getFivs_r().find(sql.toString(), params.toArray());
		return list.size() == 0;
	}
	
	public List<Equipment> findByNoPageWithExcel(PageObject pageObject) {
		StringBuilder sb = new StringBuilder("select s from Equipment s where 1=1 ");
		List params = new ArrayList();
		Map<String, String> paramMap = pageObject.getParams();
		if (StringUtils.isNotBlank(paramMap.get("id"))) {
			params.add(paramMap.get("id"));
			sb.append("and s.id = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("name"))) {
			params.add("%" + paramMap.get("name") + "%");
			sb.append("and s.name like ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("ip"))) {
			params.add(paramMap.get("ip"));
			sb.append("and s.ip = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("type"))) {
			params.add(Integer.valueOf(paramMap.get("type")));
			sb.append("and s.type = ? ");
		}		
		if (StringUtils.isNotBlank(paramMap.get("status"))) {
			params.add(Boolean.valueOf(paramMap.get("status")));
			sb.append("and s.status = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("position"))) {
			params.add(paramMap.get("position") + "%");
			sb.append("and s.id like ? ");
		}
		sb.append(" order by s.status desc");
		List list = this.getFivs_r().findList(sb.toString(), params.toArray());
		return list;
	}
	
	public List<Equipment> findByParam(Map<String, String> paramMap) {
		StringBuilder sb = new StringBuilder("select s from Equipment s where 1=1 ");
		List params = new ArrayList();
		if (StringUtils.isNotBlank(paramMap.get("id"))) {
			params.add(paramMap.get("id"));
			sb.append("and s.id = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("name"))) {
			params.add("%" + paramMap.get("name") + "%");
			sb.append("and s.name like ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("ip"))) {
			params.add(paramMap.get("ip"));
			sb.append("and s.ip = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("type"))) {
			params.add(Integer.valueOf(paramMap.get("type")));
			sb.append("and s.type = ? ");
		}		
		if (StringUtils.isNotBlank(paramMap.get("status"))) {
			params.add(Boolean.valueOf(paramMap.get("status")));
			sb.append("and s.status = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("position"))) {
			params.add(paramMap.get("position") + "%");
			sb.append("and s.id like ? ");
		}
		sb.append(" order by s.status desc");
		List list = this.getFivs_r().findList(sb.toString(), params.toArray());
		return list;
	}
	
	public List<EquipmentCorp> findCorpByType(String type) {
		StringBuilder sb = new StringBuilder("select s from EquipmentCorp s where 1=1 ");
		List params = new ArrayList();
		if (StringUtils.isNotBlank(type)) {
			params.add( type + "%");
			sb.append("and s.id like ? ");
		}				
		List list = this.getFivs_r().findList(sb.toString(), params.toArray());
		return list;
	}
	
	public List findAllEquipment() {
		String sql = "select c from Equipment c";
		return this.getFivs_r().find(sql, null);
	}
	
}
