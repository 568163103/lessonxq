package com.beyeon.nvss.dmu.control.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.beyeon.common.util.DateUtil;
import com.beyeon.common.util.ExcelFileGenerator;
import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.hibernate.fivsdb.AlarmType;
import com.beyeon.hibernate.fivsdb.DmuAlarmInfo;
import com.beyeon.hibernate.fivsdb.DmuAlarmType;
import com.beyeon.hibernate.fivsdb.TbAlarmType;
import com.beyeon.nvss.device.model.DeviceFacade;
import com.beyeon.nvss.dmu.model.dto.DmuAlarmInfoDto;
import com.beyeon.nvss.dmu.model.dto.DmuEquipmentDto;
import com.socket.sip.SIPSocketUtil;

@Component("dmuAlarmInfoAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DmuAlarmInfoAction extends BaseAction {
	private DmuAlarmInfoDto dmuAlarmInfoDto;
	private DeviceFacade deviceFacade;
	
	public void setDeviceFacade(DeviceFacade deviceFacade) {
		this.deviceFacade = deviceFacade;
	}
	public void setDmuAlarmInfoDto(DmuAlarmInfoDto dmuAlarmInfoDto) {
		this.dmuAlarmInfoDto = dmuAlarmInfoDto;
	}
	
	public Object getModel() {
		if(null == dmuAlarmInfoDto){
			String id = this.getReqParam("id");
			if(StringUtils.isNotBlank(id)){
				dmuAlarmInfoDto = deviceFacade.getDmuAlarmInfoBpo().get(id);				
			} else {
				dmuAlarmInfoDto = new DmuAlarmInfoDto();
			}
		}
		return dmuAlarmInfoDto;
	}
	public String findPage(){
		deviceFacade.getDmuAlarmInfoBpo().findAlarmInfo(this.getPageObject());
		this.setReqAttr("alarmTypes", AlarmType.getTypes());
		this.setReqAttr("levels", AlarmType.getLevels());
		this.setReqAttr("alarmLevels", TbAlarmType.getLevelMap());
		return "findPage";
	}
	
	public String alarmInfoStatistics(){
		this.setReqAttr("levels", AlarmType.getLevels());
		this.setReqAttr("alarmTypes2", DmuAlarmType.getTypeList());
		String statisticsTime = this.getReqParam("statisticsTime");
		String sourceId = this.getReqParam("sourceId");
		String alarmLevel = this.getReqParam("alarmLevel");
		String status = this.getReqParam("status");
		String alarmType = this.getReqParam("alarmType");
		String beginTime = this.getReqParam("beginTime");
		String endTime = this.getReqParam("endTime");	
		int i = 1;
		if(StringUtils.isNotBlank(statisticsTime)){
			i = Integer.valueOf(statisticsTime);
		}
		switch (i){
			case 2 : {
				statisticsTime = DateUtil.format(DateUtil.addWeeks(new Date(), -1), "yyyy-MM-dd HH:mm:ss");
				break;
			}
			case 3 : {
				statisticsTime = DateUtil.format(DateUtil.addMonths(new Date(), -1), "yyyy-MM-dd HH:mm:ss");
				break;
			}
			default:
				statisticsTime = DateUtil.format(DateUtil.addDays(new Date(), -1), "yyyy-MM-dd HH:mm:ss");
		}

		Map result = new HashMap();
		
//		for (AutoCompleteDto o : AlarmType.getDmuTypes()) {
//			result.put(o.getLabel(),"");
//		}
		for (Map<String, String> type : DmuAlarmType.getTypeList()){
			result.put(type.get("name"), "");
		}
		Map<String,String> params = new HashMap<String,String>();
//		params.put("statisticsTime", statisticsTime);
		params.put("sourceId", sourceId);
		params.put("alarmLevel", alarmLevel);
		params.put("status", status);
		params.put("alarmType", alarmType);
		params.put("beginTime", beginTime);
		params.put("endTime", endTime);
		List<Object[]> alarmInfoStatistics = deviceFacade.getDmuAlarmInfoBpo().findAlarmInfoStatistics(params);
		for (Object[] alarmInfoStatistic : alarmInfoStatistics) {
			result.put(DmuAlarmType.getTypeName(alarmInfoStatistic[0].toString()),alarmInfoStatistic[1].toString());
		}
		this.setReqAttr("alarmTypes", StringUtils.join(result.keySet(),"','"));
		this.setReqAttr("alarmDatas", StringUtils.join(result.values(),","));
		this.setReqAttr("statisticsTime",i);
		this.setReqAttr("sourceId",sourceId);
		this.setReqAttr("alarmLevel",alarmLevel);
		this.setReqAttr("status",status);
		this.setReqAttr("alarmType",alarmType);
		this.setReqAttr("beginTime",params.get("beginTime"));
		this.setReqAttr("endTime",params.get("endTime"));	
		return "alarmInfoStatistics";
	}
	
	
	/**
	 * 报表导出
	 * @author
	 * @return
	 */
	public String exportList() {
		/** 构造excel的标题数据 */
		ArrayList<String> fieldName = findExcelFieldName();
		/** 构造excel的数据内容 */
		ArrayList<ArrayList<String>> fieldData = deviceFacade.getDmuAlarmInfoBpo().findExcelFieldData(getPageObject());
		ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator(fieldName, fieldData);
		try {
			/** 获取字节输出流*/
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			/** 设置excel的响应头部信息，（内联和附件）*/
			String filename = "告警信息导出("+ DateUtil.format("yyyyMMddHHmmss")+ ")";
			/** 处理中文 */
			filename = new String(filename.getBytes("gbk"), "iso-8859-1");
			ServletActionContext.getRequest().setAttribute("name", filename);
			excelFileGenerator.expordExcel(os);
			/**从缓存区中读出字节数组 */
			byte[] bytes = os.toByteArray();
			InputStream inputStream = new ByteArrayInputStream(bytes);
			/** 将inputStream放置到模型驱动的对象中 */
			dmuAlarmInfoDto.setInputStream(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "exportList";
	}
	
	public String exportList2() {
		/** 构造excel的标题数据 */
		ArrayList<String> fieldName = findExcelFieldName2();
		/** 构造excel的数据内容 */
		String statisticsTime = this.getReqParam("statisticsTime");
		String sourceId = this.getReqParam("sourceId");
		String alarmLevel = this.getReqParam("alarmLevel");
		String status = this.getReqParam("status");
		String alarmType = this.getReqParam("alarmType");
		String beginTime = this.getReqParam("beginTime");
		String endTime = this.getReqParam("endTime");	
		Map<String,String> params = new HashMap<String,String>();
//		params.put("statisticsTime", statisticsTime);
		params.put("sourceId", sourceId);
		params.put("alarmLevel", alarmLevel);
		params.put("status", status);
		params.put("alarmType", alarmType);
		params.put("beginTime", beginTime);
		params.put("endTime", endTime);
		ArrayList<ArrayList<String>> fieldData = deviceFacade.getDmuAlarmInfoBpo().findExcelFieldData2(params);
		ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator(fieldName, fieldData);
		try {
			/** 获取字节输出流*/
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			/** 设置excel的响应头部信息，（内联和附件）*/
			String filename = "告警统计("+ DateUtil.format("yyyyMMddHHmmss")+ ")";
			/** 处理中文 */
			filename = new String(filename.getBytes("gbk"), "iso-8859-1");
			ServletActionContext.getRequest().setAttribute("name", filename);
			excelFileGenerator.expordExcel(os);
			/**从缓存区中读出字节数组 */
			byte[] bytes = os.toByteArray();
			InputStream inputStream = new ByteArrayInputStream(bytes);
			/** 将inputStream放置到模型驱动的对象中 */
			dmuAlarmInfoDto.setInputStream(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "exportList";
	}
	/**
	 * EXCEL表头名称
	 * @author 
	 * @return
	 */
	public ArrayList<String> findExcelFieldName() {
		ArrayList<String> fieldName = new ArrayList<String>();
		fieldName.add("序号");
		fieldName.add("设备ID");
		fieldName.add("设备名称");
		fieldName.add("告警类型");
		fieldName.add("开始时间");
		fieldName.add("结束时间");
		fieldName.add("告警状态");
		fieldName.add("备注");
		return fieldName;
	}
	
	public ArrayList<String> findExcelFieldName2() {
		ArrayList<String> fieldName = new ArrayList<String>();
		fieldName.add("序号");
		fieldName.add("告警类型");
		fieldName.add("数量");
		return fieldName;
	}
	
	
	public String handleAlarm(){
		String status = this.getReqParam("status");
		String id = this.getReqParam("sid");
		String memo = this.getReqParam("memo");
		String uid = this.getCurrentUser().getId();
		deviceFacade.getDmuAlarmInfoBpo().handleAlarm(status,id);
		
		return findPage();
	}

	public String reportAlarmInfo(){
		String type = this.getReqParam("type");
		String start_time = this.getReqParam("start_time");
		String end_time = this.getReqParam("end_time");
		String ip = this.getReqParam("ip");
		String state = this.getReqParam("state");
		String description = this.getReqParam("description");
		Map<String,String> map = new HashMap<String,String>();
		map.put("code", "200");
		this.responseJSON(map);
		return null;
	}
}
