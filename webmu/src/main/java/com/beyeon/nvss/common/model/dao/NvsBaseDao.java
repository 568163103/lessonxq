package com.beyeon.nvss.common.model.dao;

import java.util.List;

import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.web.model.dao.BaseDao;
/*
*项目公用数据访问对象
**/
public class NvsBaseDao extends BaseDao {

	public List findServer(Integer[] types) {
		String hql = new String("select s.id,s.name,s.type from server s where s.type in (:types) ");
		return this.getFivs_r().findSQLToMapByParamName(hql, new String[]{"types"}, new Object[]{types});
	}
	
	public List findEquipment(Integer[] types) {
		String hql = new String("select s.id,s.name,s.type from equipment s where s.type in (:types) ");
		return this.getFivs_r().findSQLToMapByParamName(hql, new String[]{"types"}, new Object[]{types});
	}

	public String findAdminUid(){
		String admin = "admin";
		String supperUsers = ResourceUtil.getCoreConf("app.user");
		if (supperUsers.split(",").length > 2) {
			admin = supperUsers.split(",")[1];
		}
		return this.findUid(admin);
	}

	public String findUid(String name) {
		return (String) this.getFivs_r().findUniqueSql("select id from user where name=? " , new Object[]{name});
	}

	public List<String> findUids() {
		return this.getFivs_r().findSQL("select id from user" , null);
	}

	public String findServerIp(Integer serverType) {
		List list = this.getFivs_r().find("select t.ip from Server t where t.type = ?" ,new Object[]{serverType});
		if (null != list && list.size() > 0){
			return list.get(0).toString();
		}
		return null;
	}
	
	public String findServerStatus(Integer serverType) {
		List list = this.getFivs_r().find("select t.status from Server t where t.type = ?" ,new Object[]{serverType});
		if (null != list && list.size() > 0){
			return list.get(0).toString();
		}
		return null;
	}
	
	public String findEquipmentIp(Integer serverType) {
		List list = this.getFivs_r().find("select t.ip from Equipment t where t.type = ?" ,new Object[]{serverType});
		if (null != list && list.size() > 0){
			return list.get(0).toString();
		}
		return null;
	}

	public String findOutServerIp(Integer serverType) {
		List list = this.getFivs_r().find("select t.address from Server t where t.type = ?" ,new Object[]{serverType});
		if (null != list && list.size() > 0){
			return list.get(0).toString();
		}
		return null;
	}
	
	public String findOutEquipmentIp(Integer serverType) {
		List list = this.getFivs_r().find("select t.pos from Equipment t where t.type = ?" ,new Object[]{serverType});
		if (null != list && list.size() > 0){
			return list.get(0).toString();
		}
		return null;
	}
	
	public List findState(String resId) {
		StringBuilder sb = new StringBuilder("select * from (select a.`name`,FROM_UNIXTIME(b.clock) as clock,cast(b.`value` as char) as `value`,a.units from (select b.itemid,b.units,b.`name`,max(clock) as clock from `zabbix`.`hosts` a LEFT JOIN `zabbix`.`items` b on a.hostid = b.hostid LEFT JOIN `zabbix`.`history_uint` c on b.itemid = c.itemid LEFT JOIN `zabbix`.item_discovery d on b.itemid = d.itemid where a.`host` = ? and b.status ='0' and (d.ts_delete=0 or d.ts_delete is null)  group by b.itemid) a,`zabbix`.`history_uint` b where a.itemid= b.itemid and a.clock = b.clock  UNION ");
		sb.append("select a.`name`,FROM_UNIXTIME(b.clock)as clock,cast(b.`value` as char) as `value`,a.units from (select b.itemid,b.units,b.`name`,max(clock) as clock from `zabbix`.`hosts` a LEFT JOIN `zabbix`.`items` b on a.hostid = b.hostid LEFT JOIN `zabbix`.`history` c on b.itemid = c.itemid LEFT JOIN `zabbix`.item_discovery d on b.itemid = d.itemid where a.`host` = ? and b.status ='0' and (d.ts_delete=0 or d.ts_delete is null)  group by b.itemid) a,`zabbix`.`history` b where a.itemid= b.itemid and a.clock = b.clock  UNION ");
		sb.append("select a.`name`,FROM_UNIXTIME(b.clock)as clock,cast(b.`value` as char) as `value`,a.units from (select b.itemid,b.units,b.`name`,max(clock) as clock from `zabbix`.`hosts` a LEFT JOIN `zabbix`.`items` b on a.hostid = b.hostid LEFT JOIN `zabbix`.`history_log` c on b.itemid = c.itemid LEFT JOIN `zabbix`.item_discovery d on b.itemid = d.itemid where a.`host` = ? and b.status ='0' and (d.ts_delete=0 or d.ts_delete is null)  group by b.itemid) a,`zabbix`.`history_log` b where a.itemid= b.itemid and a.clock = b.clock  UNION ");
		sb.append("select a.`name`,FROM_UNIXTIME(b.clock)as clock,cast(b.`value` as char) as `value`,a.units from (select b.itemid,b.units,b.`name`,max(clock) as clock from `zabbix`.`hosts` a LEFT JOIN `zabbix`.`items` b on a.hostid = b.hostid LEFT JOIN `zabbix`.`history_str` c on b.itemid = c.itemid LEFT JOIN `zabbix`.item_discovery d on b.itemid = d.itemid where a.`host` = ? and b.status ='0' and (d.ts_delete=0 or d.ts_delete is null)  group by b.itemid) a,`zabbix`.`history_str` b where a.itemid= b.itemid and a.clock = b.clock   UNION ");
		sb.append("select a.`name`,FROM_UNIXTIME(b.clock)as clock,cast(b.`value` as char) as `value`,a.units from (select b.itemid,b.units,b.`name`,max(clock) as clock from `zabbix`.`hosts` a LEFT JOIN `zabbix`.`items` b on a.hostid = b.hostid LEFT JOIN `zabbix`.`history_text` c on b.itemid = c.itemid LEFT JOIN `zabbix`.item_discovery d on b.itemid = d.itemid where a.`host` = ? and b.status ='0' and (d.ts_delete=0 or d.ts_delete is null)  group by b.itemid) a,`zabbix`.`history_text` b where a.itemid= b.itemid and a.clock = b.clock  ) a order by `clock` desc");
		return this.getFivs_r().findSQLToMap(sb.toString(), new String[]{resId,resId,resId,resId,resId});
	}
	
	public List findPositionList(){
		String hql = new String("select s from PositionCode s ");
		return this.getFivs_r().find(hql,null);
		
	}
}
