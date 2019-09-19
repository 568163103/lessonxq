package com.beyeon.nvss.dmu.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.DmuEquipment;
import com.beyeon.hibernate.fivsdb.SwitchPortInfo;
import com.beyeon.hibernate.fivsdb.TbShieldUser;
import com.beyeon.nvss.common.model.dao.NvsBaseDao;

@Component("dmuSwitchPortDao")
public class DmuSwitchPortDao extends NvsBaseDao {
	public void delete(String[] ids) {
        StringBuilder sql = new StringBuilder("delete c from switch_port_info c ");
		sql.append("where c.id in (:ids)");
        this.getFivs_w().bulkUpdateSQLByParamName(sql.toString(), new String[]{"ids"}, new Object[]{ids});
    }
	
	public void find(PageObject pageObject) {
		StringBuilder sb = new StringBuilder("select s from SwitchPortInfo s where 1=1 ");
		List params = new ArrayList();
		Map<String, String> paramMap = pageObject.getParams();
		if (StringUtils.isNotBlank(paramMap.get("ip"))) {
			params.add(paramMap.get("ip"));
			sb.append("and s.ip = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("port"))) {
			params.add(Integer.parseInt(paramMap.get("port")));
			sb.append("and s.port = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("resId"))) {
			params.add(paramMap.get("resId"));
			sb.append("and s.switchid = ? ");
		}		
		if (StringUtils.isNotBlank(paramMap.get("deviceid"))) {
			params.add(paramMap.get("deviceid"));
			sb.append("and s.deviceid = ? ");
		}
		sb.append(" order by s.id desc");
		this.getFivs_r().find(pageObject, sb.toString(), params.toArray());
	}
	public List<SwitchPortInfo> getSwitchPortInfo (String id ){
		String sql = "select c from SwitchPortInfo c where c.switchid = ? ";
		return this.getFivs_r().find(sql, new Object[]{id});
	}
	public List<HashMap<String,Object>> findList(){
		 StringBuilder sb = new StringBuilder("select a.switchid,a.`port`,a.mac,a.ip,b.id as deviceId from switch_port_info a left join dmu_equipment b on a.ip = b.ip where 1=1  "); 
	     List list = this.getFivs_r().findSQLToMap(sb.toString(), null); 
	     return list;
	}
	
	public String findIpByPort(String switchid,Integer port){
		StringBuilder sb = new StringBuilder("select s from SwitchPortInfo s where switchid= ? and port = ? ");
	     List list = this.getFivs_r().find(sb.toString(), new Object[]{switchid,port}); 
	     if (list!=null && list.size()>0){
	    	 SwitchPortInfo info = (SwitchPortInfo) list.get(0);
	    	 return info.getIp();
	     }
	     return "";
	}
	
	
	public List<HashMap<String,Object>> findListByStateRecord(){
		StringBuilder sb = new StringBuilder("SELECT  a.id as switchid,d.`port` as `port` ,d.ip,de.id as deviceId FROM dmu_equipment a join ( select a.switchid ,max(a.recordTime) as recordTime from switch_state_record a group by a.switchid) b on a.id = b.switchid "); 
		sb.append("join switch_state_record c on b.switchid = c.switchid and b.recordTime = c.recordTime join switch_state_record_port d on c.id = d.recordid join dmu_equipment de on d.ip = de.ip where 1=1 "); 
		List list = this.getFivs_r().findSQLToMap(sb.toString(), null); 
	    return list;
	}
	
	public SwitchPortInfo findBySwitchIdAndPort(String switchid,Integer port) {
		List list = this.getFivs_r().find("select t from SwitchPortInfo t where t.switchid = ? and port = ? " ,new Object[]{switchid,port});
		if (null != list && list.size() > 0){
			SwitchPortInfo result = (SwitchPortInfo)list.get(0);
			return result;
		}
		return null;
	}
	
	public boolean checkSelf(String id) {
		DmuEquipment dmu = new DmuEquipment();
		String hql = new String("select e from DmuEquipment e where e.id=?");
		List resList = this.getFivs_r().find(hql, new Object[]{id});
		if (resList.size() > 0){
			dmu = (DmuEquipment)resList.get(0);
		}
		return StringUtils.isBlank(dmu.getMaster());
	}
}
