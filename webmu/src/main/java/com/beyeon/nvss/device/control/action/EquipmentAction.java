package com.beyeon.nvss.device.control.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.beyeon.hibernate.fivsdb.Equipment;
import com.beyeon.hibernate.fivsdb.EquipmentCorp;
import com.beyeon.hibernate.fivsdb.PositionCode;
import com.beyeon.nvss.device.model.DeviceFacade;
import com.beyeon.nvss.device.model.dto.EquipmentDto;

@Component("equipmentAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EquipmentAction extends BaseAction {
	private static Logger logger =LoggerFactory.getLogger(EquipmentAction.class);
	private DeviceFacade deviceFacade;
	private EquipmentDto equipmentDto;

	public void setDeviceFacade(DeviceFacade deviceFacade) {
		this.deviceFacade = deviceFacade;
	}

	public EquipmentDto getEquipmentDto() {
		return equipmentDto;
	}
	public void setEquipmentDto(EquipmentDto equipmentDto) {
		this.equipmentDto = equipmentDto;
	}

	public Object getModel() {
		if(null == equipmentDto){
			String id = this.getReqParam("id");
			if(StringUtils.isNotBlank(id)){
				equipmentDto = deviceFacade.getEquipmentBpo().get(id);
			} else {
				equipmentDto = new EquipmentDto();
			}
		}
		return equipmentDto;
	}

	public String beforeUpdate(){
		Integer type = equipmentDto.getEquipment().getType();
		List<EquipmentCorp> corpList = deviceFacade.getEquipmentBpo().findCorpByType(String.valueOf(type));
		 Map<String,String> corpMap = new HashMap<String,String>();
		for (EquipmentCorp corp : corpList){
			corpMap.put(corp.getId(), corp.getName());
		}
		this.setReqAttr("equipmentTypes",Equipment.getTypeMap());
		this.setReqAttr("equipmentCorps",corpMap);
		return "beforeUpdate";
	}

	public String update(){
		deviceFacade.getEquipmentBpo().update(equipmentDto);
		return findPage();
	}

	public String beforeSave(){
		this.setReqAttr("positions",PositionCode.getTypeMap());
		this.setReqAttr("equipmentTypes",Equipment.getTypeMap());
		this.setReqAttr("equipmentCorps",Equipment.getCorpMap());
		return "beforeSave";
	}

	public String save(){
		deviceFacade.getEquipmentBpo().save(equipmentDto);

		return findPage();
	}

	public String delete(){
		String[] ids = this.getReqParams("items");
		deviceFacade.getEquipmentBpo().delete(ids);
		return findPage();
	}

	public String findPage(){
		this.setReqAttr("positions",PositionCode.getTypeMap());
		this.setReqAttr("types",Equipment.getTypeMap());
		deviceFacade.getEquipmentBpo().find(getPageObject());
		return "findPage";
	}

	/**
	 * 报表导出
	 *
	 * @return
	 * @author
	 */
	public String exportList() {
		/** 构造excel的标题数据 */
		ArrayList<String> fieldName = findExcelFieldName();
		/** 构造excel的数据内容 */
		ArrayList<ArrayList<String>> fieldData = deviceFacade.getEquipmentBpo().findExcelFieldData(getPageObject());
		ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator(fieldName, fieldData);
		try {
			/** 获取字节输出流*/
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			/** 设置excel的响应头部信息，（内联和附件）*/
			String filename = "其他列表导出(" + DateUtil.format("yyyyMMddHHmmss") + ")";
			/** 处理中文 */
			filename = new String(filename.getBytes("gbk"), "iso-8859-1");
			ServletActionContext.getRequest().setAttribute("name", filename);
			excelFileGenerator.expordExcel(os);
			/**从缓存区中读出字节数组 */
			byte[] bytes = os.toByteArray();
			InputStream inputStream = new ByteArrayInputStream(bytes);
			/** 将inputStream放置到模型驱动的对象中 */
			equipmentDto.setInputStream(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "exportList";
	}

	/**
	 * EXCEL表头名称
	 *
	 * @return
	 * @author
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


	public String findEquipmentCorp(){
		String type = this.getReqParam("type");
		List<EquipmentCorp> corpList = deviceFacade.getEquipmentBpo().findCorpByType(type);
		this.responseJSON(corpList);
		return null;
	}

}
