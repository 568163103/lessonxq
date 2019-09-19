package com.beyeon.nvss.bussiness.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.Action;
import com.beyeon.nvss.bussiness.model.dto.PreSchemeDto;
import com.beyeon.nvss.common.model.dao.NvsBaseDao;

@Component("preSchemeDao")
public class PreSchemeDao extends NvsBaseDao {

	public void find(PageObject pageObject) {
		StringBuilder sb = new StringBuilder();
		sb.append("select t.id, t.name,GROUP_CONCAT(v.type) as actionTypes ");
		sb.append("from pre_scheme t left join pre_scheme_action u on t.id = u.scheme_id left join action v on u.action_id = v.id where 1=1 ");
		List params = new ArrayList();
		Map<String,String> paramMap = pageObject.getParams();
		if (StringUtils.isNotBlank(paramMap.get("name"))) {
			params.add("%"+paramMap.get("name")+"%");
			sb.append("and t.name like ? ");
		}
		sb.append(" group by t.id");
		this.getFivs_r().findSQLToBean(pageObject, sb.toString(), params.toArray(), true, PreSchemeDto.class);
	}

	public void deletePreSchemeById(String[] ids) {
		List id = new ArrayList();
		for (int i = 0; i < ids.length; i++) {
			id.add(Integer.valueOf(ids[i]));
		}
		String[] paramNames = {"ids"};
		this.getFivs_w().bulkUpdateSQLByParamName("delete p,pa from pre_scheme p left join pre_scheme_action pa on p.id = pa.scheme_id where p.id in (:ids)", paramNames, new Object[]{id});
	}

	public boolean checkPreSchemeUnique(String id, String username) {
		StringBuilder sql = new StringBuilder("select t from PreScheme t where t.name = ? ");
		List params = new ArrayList();
		params.add(username);
		if (StringUtils.isNotBlank(id)){
			sql.append("and id != ? ");
			params.add(Integer.valueOf(id));
		}
		List list = this.getFivs_r().find(sql.toString(), params.toArray());
		return list.size() == 0;
	}

	public List getPresetByChannelId(String channelId) {
		return this.getFivs_r().findSQLToMap("select t.num,t.name from preset t where t.channel_id = ?", new String[]{channelId});
	}

	public List<Action> getAction(Integer id) {
		return this.getFivs_r().find("select t from Action t,PreSchemeAction u where t.id = u.actionId and u.schemeId = ? ", new Integer[]{id});
	}

	public void deleteActionById(String[] aids) {
		List id = new ArrayList();
		for (int i = 0; i < aids.length; i++) {
			id.add(Integer.valueOf(aids[i]));
		}
		String[] paramNames = {"ids"};
		this.getFivs_w().bulkUpdateSQLByParamName("delete a,pa from action a,pre_scheme_action pa where a.id = pa.action_id and a.id in (:ids)", paramNames, new Object[]{id});
	}
}
