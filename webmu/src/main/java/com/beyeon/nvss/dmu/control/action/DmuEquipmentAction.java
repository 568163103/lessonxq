package com.beyeon.nvss.dmu.control.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.beyeon.common.util.DateUtil;
import com.beyeon.common.util.ExcelFileFourPage;
import com.beyeon.common.util.ExcelFileGenerator;
import com.beyeon.common.util.ExcelFileGeneratorPage;
import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.common.web.filter.FlyLoggerThread;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.Equipment;
import com.beyeon.hibernate.fivsdb.PositionCode;
import com.beyeon.nvss.device.model.DeviceFacade;
import com.beyeon.nvss.dmu.model.dto.DmuEquipmentDto;
import com.socket.sip.SIPSocketUtil;

@Component("dmuEquipmentAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DmuEquipmentAction extends BaseAction {
	private static Logger logger =LoggerFactory.getLogger(DmuEquipmentAction.class);
	private DeviceFacade deviceFacade;
	private DmuEquipmentDto dmuEquipmentDto;
	private static String code = null;

	public void setDeviceFacade(DeviceFacade deviceFacade) {
		this.deviceFacade = deviceFacade;
	}

	public DmuEquipmentDto getDmuEquipment() {
		return dmuEquipmentDto;
	}
	public void setDmuEquipment(DmuEquipmentDto dmuEquipmentDto) {
		this.dmuEquipmentDto = dmuEquipmentDto;
	}

	public Object getModel() {
		if(null == dmuEquipmentDto){
			String id = this.getReqParam("id");
			String type = this.getReqParam("type");
			String master = this.getReqParam("master");
			if(StringUtils.isNotBlank(id)){
				dmuEquipmentDto = deviceFacade.getDmuEquipmentBpo().get(id);
				if (StringUtils.isNotBlank(type)){
					HashMap<String,String> result = SIPSocketUtil.queryState(id,type,master);
					code = result.get("code");
					dmuEquipmentDto = deviceFacade.getDmuEquipmentBpo().get(id);
				}
			} else {
				dmuEquipmentDto = new DmuEquipmentDto();
			}
		}
		return dmuEquipmentDto;
	}
	
	public String beforeUpdate(){
		return "beforeUpdate";
	}

	public String update(){
		deviceFacade.getDmuEquipmentBpo().update(dmuEquipmentDto);
		return findPage();
	}

	public String beforeSave(){
		return "beforeSave";
	}
	
	public String save(){
		deviceFacade.getDmuEquipmentBpo().save(dmuEquipmentDto);
		
		return findPage();
	}
	
	public String delete(){
		String[] ids = this.getReqParams("items");
		deviceFacade.getDmuEquipmentBpo().delete(ids);
		return findPage();
	}
	public String queryState(){
		String type = dmuEquipmentDto.getDmuEquipment().getType();
		if ("-1".equals(code)){
			this.addActionMessage("该类型设备无法获取实时状态！");
			return findPage();
		}else if ("48".equals(code)){
			this.addActionMessage("查询超时，获取实时状态失败！");
		}else if ("49".equals(code)){
			this.addActionMessage("对方不在连接通道中，无法获取实时状态！");
		}else if (!"0".equals(code)){
			this.addActionMessage("获取状态失败！");
		}
		 if ("服务器".equals(type)){
			 return "serverState";    
		 }else if ("交换机".equals(type)){
			 return "switchState";
		 }else if ("磁盘阵列".equals(type)){
			 return "diskState";
		 }else if ("编码器".equals(type)){
			 return "encoderState";
		 }else if ("IP摄像机".equals(type)){
			 return "IPCamState";
		 }else if ("终端".equals(type)){
			 return "PCState";
		 }else if ("解码器".equals(type)){
			 return "decoderState";
		 }else if ("光端机".equals(type)){
			 return "flashState";
		 }
		return findPage();
	}
	
	public String findPage(){
		this.setReqAttr("positions",PositionCode.getTypeMap());
		deviceFacade.getDmuEquipmentBpo().find(getPageObject());
		return "findPage";
	}
	
public String findAllRecordPage(){

		PageObject pageObject  = getPageObject();
		HashMap<String, String> map =(HashMap<String, String>) pageObject.getParams();
		String status = this.getReqParam("status");
		if (StringUtils.isNotBlank(status)){
			map.put("status",status);
			pageObject = getPageObject();			
			boolean flag = "0".equals(status) ? true : false;
			String time  = map.get("time") ;
			if (StringUtils.isBlank(time))
				time = "5";
			FlyLoggerThread.setTime(Integer.parseInt(time) * 60 * 1000);
			if ("1".equals(status)){
				HashMap<String, String> map2 = new HashMap<>();
				map2.put("status",status);
				FlyLoggerThread.setMap(map2);
			}else{
				FlyLoggerThread.setMap(map);
			}

			FlyLoggerThread.setStatus(flag);			
		}else{
			HashMap<String, String> map2 = FlyLoggerThread.getMap();
			map.putAll(map2);
			pageObject.setParams(map);
		}
		ArrayList<HashMap<String, String>> typeList = new ArrayList<HashMap<String,String>>();
		Map<String, String> types = Equipment.getTypeMap();
		Set<Entry<String, String>> entrySet =  types.entrySet();
		HashMap<String,String> map3 = new HashMap<String,String>();
		map3.put("id", "7");
		map3.put("name", "服务器");
		typeList.add(map3);
		HashMap<String,String> map4 = new HashMap<String,String>();
		map4.put("id", "8");
		map4.put("name", "编码器");
		typeList.add(map4);
		HashMap<String,String> map5 = new HashMap<String,String>();
		map5.put("id", "9");
		map5.put("name", "IP摄像机");
		typeList.add(map5);
		for (Entry<String, String> entry :entrySet){
			HashMap<String,String> map2 = new HashMap<String,String>();
			map2.put("id", entry.getKey());
			map2.put("name", entry.getValue());
			typeList.add(map2);
		}
		
		this.setReqAttr("types",typeList);
		deviceFacade.getDmuEquipmentBpo().findAllRecord(pageObject);
		return "findAllRecordPage";
	}
	
	/**
	 * 报表导出
	 * @author
	 * @return
	 */
	public String exportList() {
		/** 构造excel的标题数据 */
		String type = this.getReqParam("type");
		String id = this.getReqParam("id");
		ExcelFileGenerator excelFileGenerator = null;
		ExcelFileFourPage excelFileFourPage  = null;
		ExcelFileGeneratorPage excelFileGeneratorPage = null;
		PageObject pageObject  = getPageObject();
		HashMap<String, String> map =(HashMap<String, String>) pageObject.getParams();
		String filename = "";
		if (StringUtils.isBlank(type)){
			filename = "网管设备列表导出("+ DateUtil.format("yyyyMMddHHmmss")+ ")";
			ArrayList<String> fieldName = findExcelFieldName();
			/** 构造excel的数据内容 */
			ArrayList<ArrayList<String>> fieldData = deviceFacade.getDmuEquipmentBpo().findExcelFieldData(getPageObject());
			excelFileGenerator = new ExcelFileGenerator(fieldName, fieldData);
		}else if ("1".equals(type)){ //服务器
			filename = "服务器状态导出("+ DateUtil.format("yyyyMMddHHmmss")+ ")";
			ArrayList<String> fieldName1 = findExcelFieldNameServer();
			ArrayList<String> fieldName2 = findExcelFieldNameServerDisk();
			ArrayList<String> fieldName3 = findExcelFieldNameServerCpu();
			ArrayList<String> fieldName4 = findExcelFieldNameServerNetcard();
			ArrayList<ArrayList<ArrayList<String>>> fieldDataList = deviceFacade.getDmuEquipmentBpo().findExcelFieldDataServer(id,map);
			ArrayList<ArrayList<String>> fieldData1  = fieldDataList.get(0);
			ArrayList<ArrayList<String>> fieldData2  = fieldDataList.get(1);
			ArrayList<ArrayList<String>> fieldData3  = fieldDataList.get(2);
			ArrayList<ArrayList<String>> fieldData4  = fieldDataList.get(3);
			excelFileFourPage = new ExcelFileFourPage(fieldName1,fieldName2,fieldName3,fieldName4,
					fieldData1,fieldData2,fieldData3,fieldData4);
		}else if ("2".equals(type)){ //IPCam
			filename = "编码器/摄像机状态导出("+ DateUtil.format("yyyyMMddHHmmss")+ ")";
			ArrayList<String> fieldName = findExcelFieldNameEncoder();
			/** 构造excel的数据内容 */
			ArrayList<ArrayList<String>> fieldData = deviceFacade.getDmuEquipmentBpo().findExcelFieldDataEncord(id,map);
			excelFileGenerator = new ExcelFileGenerator(fieldName, fieldData);
		}else if ("3".equals(type)){ //磁盘阵列
			filename = "磁盘阵列状态导出("+ DateUtil.format("yyyyMMddHHmmss")+ ")";
			ArrayList<String> fieldName = findExcelFieldNameDisk();
			/** 构造excel的数据内容 */
			ArrayList<ArrayList<String>> fieldData = deviceFacade.getDmuEquipmentBpo().findExcelFieldDataDisk(id,map);
			excelFileGenerator = new ExcelFileGenerator(fieldName, fieldData);
		}else if ("4".equals(type)){ //交换机
			filename = "交换机状态导出("+ DateUtil.format("yyyyMMddHHmmss")+ ")";
			ArrayList<String> fieldName1 = findExcelFieldNameSwitch();
			ArrayList<String> fieldName2 = findExcelFieldNameSwitchPort();
			/** 构造excel的数据内容 */
			ArrayList<ArrayList<ArrayList<String>>> fieldDataList = deviceFacade.getDmuEquipmentBpo().findExcelFieldDataSwitch(id,map);
			ArrayList<ArrayList<String>> fieldData1  = fieldDataList.get(0);
			ArrayList<ArrayList<String>> fieldData2  = fieldDataList.get(1);
			excelFileGeneratorPage = new ExcelFileGeneratorPage(fieldName1, fieldName2,fieldData1,fieldData2);
		}
			
		try {
			/** 获取字节输出流*/
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			/** 设置excel的响应头部信息，（内联和附件）*/
			/** 处理中文 */
			filename = new String(filename.getBytes("gbk"), "iso-8859-1");
			ServletActionContext.getRequest().setAttribute("name", filename);
			if (excelFileGenerator !=null){
				excelFileGenerator.expordExcel(os);
			}else if ( excelFileFourPage !=null){
				 excelFileFourPage.expordExcel(os);
			}else if (excelFileGeneratorPage  !=null){
				excelFileGeneratorPage.expordExcel(os);
			}
			
			/**从缓存区中读出字节数组 */
			byte[] bytes = os.toByteArray();
			InputStream inputStream = new ByteArrayInputStream(bytes);
			/** 将inputStream放置到模型驱动的对象中 */
			dmuEquipmentDto.setInputStream(inputStream);
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
		fieldName.add("设备类型");
		fieldName.add("设备型号");
		fieldName.add("IP");
		fieldName.add("端口");
		fieldName.add("MAC地址");
		fieldName.add("设备厂家");
		fieldName.add("设备物理位置");
		fieldName.add("描述");
		fieldName.add("是否在线");
		return fieldName;
	}
	
	
	public ArrayList<String> findExcelFieldNameServer() {
		ArrayList<String> fieldName = new ArrayList<String>();
		fieldName.add("记录ID");
		fieldName.add("设备ID");
		fieldName.add("操作系统信息");
		fieldName.add("硬盘分区数");
		fieldName.add("内存总容量");
		fieldName.add("内存已使用容量");
		fieldName.add("内存剩余容量");
		fieldName.add("CPU数量");
		fieldName.add("网卡端口数量");
		fieldName.add("状态记录时间");
		return fieldName;
	}
	
	public ArrayList<String> findExcelFieldNameServerDisk() {
		ArrayList<String> fieldName = new ArrayList<String>();
		fieldName.add("记录ID");
		fieldName.add("硬盘分区盘符");
		fieldName.add("硬盘分区总容量");
		fieldName.add("硬盘分区已使用容量");
		fieldName.add("硬盘分区剩余容量");
		return fieldName;
	}
	
	public ArrayList<String> findExcelFieldNameServerCpu() {
		ArrayList<String> fieldName = new ArrayList<String>();
		fieldName.add("记录ID");
		fieldName.add("CPU使用百分比");
		fieldName.add("CPU温度");
		return fieldName;
	}
	public ArrayList<String> findExcelFieldNameServerNetcard() {
		ArrayList<String> fieldName = new ArrayList<String>();
		fieldName.add("记录ID");
		fieldName.add("网卡端口");
		fieldName.add("网卡端口连接状态");
		fieldName.add("网卡端口带宽");
		fieldName.add("网卡端口流量");
		fieldName.add("IP地址");
		fieldName.add("MAC地址");
		return fieldName;
	}
	
	public ArrayList<String> findExcelFieldNameDisk() {
		ArrayList<String> fieldName = new ArrayList<String>();
		fieldName.add("记录ID");
		fieldName.add("设备ID");
		fieldName.add("总体容量");
		fieldName.add("未分配容量");
		fieldName.add("存储端口数量");
		fieldName.add("CPU温度");
		fieldName.add("风扇工作状态");
		fieldName.add("磁盘坏块数量");
		fieldName.add("磁盘阵列状态");
		fieldName.add("状态记录时间");
		return fieldName;
	}
	
	public ArrayList<String> findExcelFieldNameSwitch() {
		ArrayList<String> fieldName = new ArrayList<String>();
		fieldName.add("记录ID");
		fieldName.add("设备ID");
		fieldName.add("内存使用百分比");
		fieldName.add("CPU占用率");
		fieldName.add("端口个数");
		fieldName.add("状态记录时间");
		return fieldName;
	}
	
	public ArrayList<String> findExcelFieldNameSwitchPort() {
		ArrayList<String> fieldName = new ArrayList<String>();
		fieldName.add("记录ID");
		fieldName.add("端口");
		fieldName.add("端口类型");
		fieldName.add("端口速率");
		fieldName.add("端口状态");
		fieldName.add("端口远程IP");
		fieldName.add("端口丢包数");
		return fieldName;
	}
	
	public ArrayList<String> findExcelFieldNameEncoder() {
		ArrayList<String> fieldName = new ArrayList<String>();
		fieldName.add("记录ID");
		fieldName.add("设备ID");
		fieldName.add("视频编码方式");
		fieldName.add("当前码流类型");
		fieldName.add("主码流码率类型");
		fieldName.add("主码流分辨率");
		fieldName.add("主码流帧率");
		fieldName.add("主码流GOP");
		fieldName.add("子码流分辨率");
		fieldName.add("状态记录时间");
		return fieldName;
	}
	public String findSwitchPage(){
		PageObject object =getPageObject();
		Map<String, String> params = new HashMap<String, String>();
		params.put("type", "交换机");
		object.setParams(params);
		deviceFacade.getDmuEquipmentBpo().find(object);
		return "findSwitchPage";
	}
	
}
