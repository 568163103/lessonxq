package com.beyeon.nvss.external.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.TbShieldRes;
import com.beyeon.hibernate.fivsdb.TbShieldUser;
import com.beyeon.nvss.common.model.dao.NvsBaseDao;

@Component("resShieldPlanDao")
public class ResShieldPlanDao extends NvsBaseDao {

	public void find(PageObject pageObject) {
		StringBuilder sb = new StringBuilder("select s from TbResShieldPlan s where 1=1 ");
		List params = new ArrayList();
		Map<String, String> paramMap = pageObject.getParams();
		if (StringUtils.isNotBlank(paramMap.get("sid"))) {
			params.add(paramMap.get("sid"));
			sb.append("and s.sid = ? ");
		}
		sb.append(" order by s.createTime desc");
		this.getFivs_r().find(pageObject, sb.toString(), params.toArray());
	}

	public void delete(String[] ids) {
        StringBuilder sql = new StringBuilder("delete c from tb_res_shield_plan c ");
		sql.append("where c.id in (:ids)");
        this.getFivs_w().bulkUpdateSQLByParamName(sql.toString(), new String[]{"ids"}, new Object[]{ids});
    }
	
	public List<TbShieldUser> getShielduUsers (Integer id ){
		String sql = "select c from TbShieldUser c where c.shieldId = ? ";
		return this.getFivs_r().find(sql, new Object[]{id});
	}
	
	public List<TbShieldRes> getShielduRess (Integer id ){
		String sql = "select c from TbShieldRes c where c.shieldId = ? ";
		return this.getFivs_r().find(sql, new Object[]{id});
	}
	
	public boolean checkBeforeDel(String[] ids){
		StringBuilder sb = new StringBuilder("select c.id from tb_res_shield_plan c where c.id in (:ids) and c.status = '0' ");
		List list = this.getFivs_r().findSQLToMapByParamName(sb.toString(), new String[]{"ids"}, new Object[]{ids});
		if (list !=null && list.size()>0){
			return false;
		}
		return true;
	}
}
