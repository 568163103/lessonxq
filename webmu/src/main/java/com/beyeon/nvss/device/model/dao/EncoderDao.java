package com.beyeon.nvss.device.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beyeon.hibernate.fivsdb.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.nvss.common.model.dao.NvsBaseDao;
import com.beyeon.nvss.device.model.dto.EncoderImpDto;
import com.beyeon.nvss.device.model.dto.TreeEncoderImpDto;

@Component("encoderDao")
public class EncoderDao extends NvsBaseDao {

	public void delete(String[] ids) {
		String[] paramNames = { "ids" };
		StringBuilder sb = new StringBuilder("delete s,se,de,g,ugr from encoder s left join server_encoder se on s.id = se.encoder_id ");
		sb.append("left join dmu_equipment de on de.id = s.id ");
		sb.append("left join encoder_groups eg on eg.encoder_id = s.id left join groups g on g.id = eg.group_id ");
		sb.append("left join user_group_right ugr on ugr.group_id = g.id where s.id in (:ids)");
		this.getFivs_w().bulkUpdateSQLByParamName(sb.toString(),paramNames, new Object[]{ids});
	}

	public void find(PageObject pageObject) {
		StringBuilder sb = new StringBuilder("select s,se from Encoder s,ServerEncoder se where s.id = se.encoderId  ");
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
		if (StringUtils.isNotBlank(paramMap.get("mduId"))) {
			params.add(paramMap.get("mduId"));
			sb.append("and se.serverId = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("ip"))) {
			params.add(paramMap.get("ip"));
			sb.append("and s.ip = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("model"))) {
			params.add(paramMap.get("model"));
			sb.append("and s.model = ? ");
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
		sb.append(" order by s.id desc");
		this.getFivs_r().find(pageObject, sb.toString(), params.toArray());
	}

	public List findEncoderModel() {
		String hql = new String("select s from EncoderModel s ");
		return this.getFivs_r().find(hql,null);
	}

	public boolean checkEncoderUnique(String id, String username) {
		StringBuilder sql = new StringBuilder("select t from Encoder t where t.name = ? ");
		List params = new ArrayList();
		params.add(username);
		if (StringUtils.isNotBlank(id)){
			sql.append("and id != ? ");
			params.add(id);
		}
		List list = this.getFivs_r().find(sql.toString(), params.toArray());
		return list.size() == 0;
	}
	public Encoder findEncoderByName(String name){
		StringBuilder sql = new StringBuilder("select t from Encoder t where t.name = ? ");
		List params = new ArrayList();
		params.add(name);
		List<Encoder> list = this.getFivs_r().find(sql.toString(), params.toArray());
		if (list !=null && list.size() >0){
			return list.get(0);
		}
		return null;
	}

	public EncoderGroups findGroupByEncoderId(String id){
		StringBuilder sql = new StringBuilder("select t from EncoderGroups t where t.encoderId = ? ");
		List params = new ArrayList();
		params.add(id);
		List<EncoderGroups> list = this.getFivs_r().find(sql.toString(), params.toArray());
		if (list !=null && list.size() >0){
			return list.get(0);
		}
		return null;
	}
	public ServerEncoder getServerEncoder(String encoderId) {
		return (ServerEncoder) this.getFivs_r().findUnique("select se from ServerEncoder se where se.encoderId = ?", new String[]{encoderId});
	}

	public EncoderExtra getEncoderExtra(String encoderId) {
		return (EncoderExtra) this.getFivs_r().findUnique("select ee from EncoderExtra ee where ee.id = ?", new String[]{encoderId});
	}

	public List findFila(){
		try {
			return this.getTmodb_r().findSQLToBean("select FILA_NO as \"oneNo\",FILA_NAME as \"oneName\" from TM_FILA_INFO order by FILA_NO", null, TreeEncoderImpDto.class);
		} catch (Exception e) {
			return this.getFivs_r().findSQLToBean("select * from tm_fila_info order by FILANO", null, TreeEncoderImpDto.class);
		}
	}

	public void insertFilaInfo(List<EncoderImpDto> busInfos){
		for (EncoderImpDto encoderImpDto : busInfos) {
			TreeEncoderImpDto treeEncoderImpDto = (TreeEncoderImpDto) encoderImpDto;
			this.getFivs_w().bulkUpdateSQL("insert into tm_fila_info values (?,?)",
					new Object[]{treeEncoderImpDto.getOneNo(), treeEncoderImpDto.getOneName()});
		}
	}

	public List<EncoderImpDto> findFilaLineBus(String ver){
		List params = new ArrayList();List<String> paramNames = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		sb.append("select distinct(BRI.BUS_NO) as \"encoderParNo\",BRI.MACH_NO as \"encoderNo\",BRI.LINE_NO as \"twoNo\",TLI.LINE_NAME as \"twoName\",BRI.FILA_NO as \"oneNo\",FI.FILA_NAME as \"oneName\" ");
		sb.append("from TM_BUS_RUN_INFO BRI INNER JOIN TM_MACH_RUN_INFO MRI on BRI.BUS_NO = MRI.BUS_NO and BRI.MACH_NO = MRI.MACH_NO ");
		//sb.append("inner join (select bus_no,max(site_time) as site_time from tm_mach_run_info t group by bus_no) t on mri.bus_no = t.bus_no and mri.site_time = t.site_time ");
		sb.append("left join TM_LINE_INFO TLI on BRI.LINE_NO=TLI.LINE_NO and BRI.FILA_NO=TLI.FILA_NO ");
		sb.append("left join tm_fila_info fi on bri.fila_no = fi.fila_no ");
		sb.append("WHERE MRI.gprs_ver LIKE 'V%' AND MRI.site_time>SYSDATE-180 AND BRI.FILA_NO > 0 ");
		if (null != ver) {
			sb.append("AND floor(MRI.PCB_VER/power(2,8)) in (:ver) ");
			params.add(ver.split(","));
			paramNames.add("ver");
		}
		sb.append("order by BRI.FILA_NO,BRI.LINE_NO,BRI.BUS_NO");
		return this.getTmodb_r().findSQLToBeanByParamName(sb.toString(),paramNames.toArray(new String[0]), params.toArray(), TreeEncoderImpDto.class);
	}

	public void insertBusInfo(List<EncoderImpDto> busInfos){
		Map<String,Integer> map = new HashMap<String,Integer>();
		for (EncoderImpDto encoderImpDto : busInfos) {
			TreeEncoderImpDto treeEncoderImpDto = (TreeEncoderImpDto) encoderImpDto;
			String lineNo = treeEncoderImpDto.getTwoNo();
			Integer i = map.get(lineNo);
			if (null == i){
				i = 0;
			}
			if (i > 3){
				//continue;
			}
			i++;
			map.put(lineNo,new Integer(i));
			this.getFivs_w().bulkUpdateSQL("insert into tm_bus_info values (?,?,?,?,?)",
					new Object[]{treeEncoderImpDto.getEncoderParNo(), treeEncoderImpDto.getEncoderNo(), treeEncoderImpDto.getTwoNo(), treeEncoderImpDto.getTwoName(), treeEncoderImpDto.getOneNo()});
		}
	}

	public List<Object[]> findEncoderNos(){
		//String sql = "SELECT id,address,SUBSTRING_INDEX(name,'-车载设备-',1) FROM encoder where address is not null and address !=''";
		String sql = "SELECT id,SUBSTRING_INDEX(name,'-车载设备-',1),SUBSTRING_INDEX(name,'-车载设备-',-1) FROM encoder ";
		return this.getFivs_r().findSQL(sql,null);
	}

	public void updateServerEncoder(ServerEncoder serverEncoder) {
		String sql = "update server_encoder set server_id = ? where encoder_id = ? ";
		this.getFivs_w().bulkUpdateSQL(sql,new Object[]{serverEncoder.getServerId(),serverEncoder.getEncoderId()});
	}

	public void updateEncoderExtra(EncoderExtra encoderExtra) {
		String sql = "update encoder_extra set corp = ?,type=?,version=?,mac=? where id = ? ";
		this.getFivs_w().bulkUpdateSQL(sql,new Object[]{encoderExtra.getCorp(),encoderExtra.getType(),encoderExtra.getVersion(),encoderExtra.getMac(),encoderExtra.getId()});
	}

	public Long findEncoderNum() {
		StringBuilder sb = new StringBuilder("select count(*) from Channel s ");
		return (Long) this.getFivs_r().findUnique(sb.toString(), null);
	}

	public void deleteInitUserTree(String sql) {
		this.getFivs_w().bulkUpdateSQL(sql,null);
	}

	public List findAllEncoder() {
		String hql = new String("select e from Encoder e ");
		return this.getFivs_r().find(hql, null);
	}

	public Encoder findById(String id) {
		String hql = new String("select e from Encoder e where e.id=?");
		List resList = this.getFivs_r().find(hql, new Object[]{id});
		if (resList.size() > 0)
			return (Encoder)resList.get(0);
		return null;
	}

	public List findPlatformEncoder() {
		String hql = new String("select e from Encoder e where e.model= 'platform' ");
		return this.getFivs_r().find(hql,null);
	}
	public List<Object[]> findByNoPageWithExcel(PageObject pageObject) {
		StringBuilder sb = new StringBuilder("select s,se from Encoder s,ServerEncoder se where s.id = se.encoderId ");
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
		if (StringUtils.isNotBlank(paramMap.get("mduId"))) {
			params.add(paramMap.get("mduId"));
			sb.append("and se.serverId = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("ip"))) {
			params.add(paramMap.get("ip"));
			sb.append("and s.ip = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("model"))) {
			params.add(paramMap.get("model"));
			sb.append("and s.model = ? ");
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
		sb.append(" order by s.id desc");
		List list = this.getFivs_r().findList(sb.toString(), params.toArray());
		return list;
	}

	public void updateEncoderlName(String name,String resId) {
		String sql = new String("update encoder e left join dmu_equipment d on e.id = d.id  set e.name = ? , d.name = ? where e.id  = ?");
		this.getFivs_w().bulkUpdateSQL(sql, new Object[]{name,name,resId});
	}

	public List<ServerEncoder> getMssChanleAll(){
		String sql ="select * from server_encoder";
		return this.getFivs_r().findSQL(sql,null,ServerEncoder.class);
	}
	public List<ServerEncoder> findByServerEncoderId(String id){
		String sql ="select * from server_encoder s where s.encoder_id = ?";
		return this.getFivs_r().findSQL(sql,new Object[]{id},ServerEncoder.class);
	}
}
