package com.beyeon.nvss.device.control.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.scheduling.SLSchedulerFactoryBean;
import com.beyeon.common.util.HttpClientUtil;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.beyeon.common.util.DateUtil;
import com.beyeon.common.util.ExcelFileGenerator;
import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.hibernate.fivsdb.PositionCode;
import com.beyeon.hibernate.fivsdb.Server;
import com.beyeon.hibernate.fivsdb.ServerType;
import com.beyeon.nvss.device.model.DeviceFacade;
import com.beyeon.nvss.device.model.dto.ServerDto;

import javax.persistence.Id;

@Component("serverAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ServerAction extends BaseAction {
	private static Logger logger =LoggerFactory.getLogger(ServerAction.class);
	private DeviceFacade deviceFacade;
	private ServerDto serverDto;

	public void setDeviceFacade(DeviceFacade deviceFacade) {
		this.deviceFacade = deviceFacade;
	}

	public ServerDto getServer() {
		return serverDto;
	}
	public void setServer(ServerDto serverDto) {
		this.serverDto = serverDto;
	}

	public Object getModel() {
		if(null == serverDto){
			String id = this.getReqParam("id");
			if(StringUtils.isNotBlank(id)){
				serverDto = deviceFacade.getServerBpo().get(id);
			} else {
				serverDto = new ServerDto();
			}
		}
		return serverDto;
	}
	
	public String beforeUpdate(){
		this.setReqAttr("types",Server.getTypeMap());
		this.setReqAttr("cmus",Server.getObjectMap(ServerType.CMU.toString()));
		return "beforeUpdate";
	}

	public String update(){
		deviceFacade.getServerBpo().update(serverDto);
		return findPage();
	}

	public String beforeSave(){
		this.setReqAttr("types",Server.getTypeMap());
		this.setReqAttr("positions",PositionCode.getTypeMap());
		this.setReqAttr("cmus",Server.getObjectMap(ServerType.CMU.toString()));
		this.setReqAttr("keepAlivePeriod", 60);
		return "beforeSave";
	}
	
	public String save(){
		Map<String,String> map = Server.getObjectMap(ServerType.CMU.toString());
		deviceFacade.getServerBpo().save(serverDto);
		return findPage();
	}
	
	public String delete(){
		String[] ids = this.getReqParams("items");
		deviceFacade.getServerBpo().delete(ids);
		return findPage();
	}
	public String updateProStatus(){
		String serverId = this.getReqParam("serverId");
		int prohibited_status = Integer.parseInt(this.getReqParam("server.prohibited_status"));
		StringBuilder cmuUrl = new StringBuilder();
		cmuUrl.append("http://").append(deviceFacade.getServerBpo().findServerIp(ServerType.CMU)).append(":")
				.append(ResourceUtil.getServerConf("cmu.server.port")).append("/UsbManageAction");
		String result = HttpClientUtil.post(cmuUrl.toString(), "{\"serverId\":\""+serverId+"\",\"prohibited_status\":\""+prohibited_status+"\"}");
		logger.debug(result);
		deviceFacade.getServerBpo().updateProhibitedStatus(serverId,prohibited_status);
		return findPage();
	}
	public String getStatus(){
		JSONObject statusJson = new JSONObject();
		String serverid = this.getReqParam("serverId");
		int prohibited_status = Integer.parseInt(this.getReqParam("status"));
		statusJson.put("serverid",serverid);
		statusJson.put("prohibited_status",prohibited_status);
        this.responseJSON(statusJson);
		return null;
	}

	public String findPage(){
		SLSchedulerFactoryBean.runJob("ServerBpo.findServer");
		this.setReqAttr("types",Server.getTypeMap());
		this.setReqAttr("positions",PositionCode.getTypeMap());
		deviceFacade.getServerBpo().find(getPageObject());
		return "findPage";
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
		ArrayList<ArrayList<String>> fieldData = deviceFacade.getServerBpo().findExcelFieldData(getPageObject());
		ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator(fieldName, fieldData);
		try {
			/** 获取字节输出流*/
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			/** 设置excel的响应头部信息，（内联和附件）*/
			String filename = "服务器列表导出("+ DateUtil.format("yyyyMMddHHmmss")+ ")";
			/** 处理中文 */
			filename = new String(filename.getBytes("gbk"), "iso-8859-1");
			ServletActionContext.getRequest().setAttribute("name", filename);
			excelFileGenerator.expordExcel(os);
			/**从缓存区中读出字节数组 */
			byte[] bytes = os.toByteArray();
			InputStream inputStream = new ByteArrayInputStream(bytes);
			/** 将inputStream放置到模型驱动的对象中 */
			serverDto.setInputStream(inputStream);
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
		fieldName.add("位置");
		fieldName.add("设备类型");
		fieldName.add("服务器IP");
		fieldName.add("是否启用");
		return fieldName;
	}

}
