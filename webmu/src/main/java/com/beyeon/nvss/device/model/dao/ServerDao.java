package com.beyeon.nvss.device.model.dao;

import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.Server;
import com.beyeon.hibernate.fivsdb.ServerExtra;
import com.beyeon.nvss.common.model.dao.NvsBaseDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.stereotype.Component;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("serverDao")
public class ServerDao extends NvsBaseDao {

	public void delete(String[] sids) {
		String[] paramNames = { "sids" };
		this.getFivs_w().bulkUpdateSQLByParamName("delete s,u,se,de from server s  left join server_encoder u on s.id = u.server_id left join server_extra se on s.id = se.id left join dmu_equipment de on s.id = de.id where s.id in (:sids)",
				paramNames, new Object[]{sids});
	}

	public void find(PageObject pageObject) {
		StringBuilder sb = new StringBuilder("select s from Server s where 1=1 ");
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
		if (StringUtils.isNotBlank(paramMap.get("username"))) {
			params.add("%" + paramMap.get("username") + "%");
			sb.append("and s.username like ? ");
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

	public List findServerType() {
		String hql = new String("select s from ServerType s ");
		return this.getFivs_r().find(hql,null);
	}

	public boolean checkServerUnique(String id, String servername) {
		StringBuilder sql = new StringBuilder("select t from Server t where t.name = ? ");
		List params = new ArrayList();
		params.add(servername);
		if (StringUtils.isNotBlank(id)){
			sql.append("and t.id != ? ");
			params.add(id);
		}
		List list = this.getFivs_r().find(sql.toString(), params.toArray());
		return list.size() == 0;
	}
	
	public List<Server> findByNoPageWithExcel(PageObject pageObject) {
		StringBuilder sb = new StringBuilder("select s from Server s where 1=1 ");
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
		if (StringUtils.isNotBlank(paramMap.get("username"))) {
			params.add("%" + paramMap.get("username") + "%");
			sb.append("and s.username like ? ");
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
	
	
	public ServerExtra getEncoderExtra(String serverId) {
		return (ServerExtra) this.getFivs_r().findUnique("select se from ServerExtra se where se.id = ?", new String[]{serverId});
	}

	public void updateServerExtra(ServerExtra serverExtra) {
		String sql = "update server_extra set  corp = ?,version=?,mac=? where id = ? ";
		this.getFivs_w().bulkUpdateSQL(sql,new Object[]{serverExtra.getCorp(),serverExtra.getVersion(),serverExtra.getMac(),serverExtra.getId()});
	}
	public List findAllServer() {
		String sql = "select c from Server c";
		return this.getFivs_r().find(sql, null);
	}
	
	public Server getServerById(String id) {
		return (Server) this.getFivs_r().findUnique("select se from Server se where se.id = ?", new String[]{id});
	}
	
	public List findServerByParamName(){
		StringBuilder sb = new StringBuilder("select s from Server s where 1=1 ");
		return this.getFivs_w().findSQLToMapByParamName(sb.toString(), null, null);
	}
	
	public void updateStautus(String id,String status){
		String sql = "update server s   set  s.status = ?  where s.id = ? ";
		this.getFivs_w().bulkUpdateSQL(sql,new Object[]{status,id});
		
	}

	public void updateProhibitedStatus(String id,int prohibitedStatus){
		String sql = "update server s set s.prohibited_status = ?  where s.id = ?";
        this.getFivs_w().bulkUpdateSQL(sql,new Object[]{prohibitedStatus,id});

	}





	
}
