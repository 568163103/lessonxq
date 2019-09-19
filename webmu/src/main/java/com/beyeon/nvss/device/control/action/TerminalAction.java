package com.beyeon.nvss.device.control.action;

import com.beyeon.common.util.DateUtil;
import com.beyeon.common.util.ExcelFileGenerator;
import com.beyeon.nvss.device.model.dto.TerminalDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.hibernate.fivsdb.PositionCode;
import com.beyeon.hibernate.fivsdb.Terminal;
import com.beyeon.nvss.device.model.DeviceFacade;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

@Component("terminalAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TerminalAction extends BaseAction {
	private static Logger logger =LoggerFactory.getLogger(TerminalAction.class);
	private DeviceFacade deviceFacade;
	private TerminalDto terminalDto;

	public void setDeviceFacade(DeviceFacade deviceFacade) {
		this.deviceFacade = deviceFacade;
	}

	public TerminalDto getTerminalDto() {
		return terminalDto;
	}

	public void setTerminalDto(TerminalDto terminalDto) {
		this.terminalDto = terminalDto;
	}

	public Object getModel() {
		if(null == terminalDto){
			String id = this.getReqParam("id");
			if(StringUtils.isNotBlank(id)){
				terminalDto = deviceFacade.getTerminalBpo().get(id);
			} else {
				terminalDto = new TerminalDto();
			}
		}
		return terminalDto;
	}
	
	public String beforeUpdate(){
		return "beforeUpdate";
	}

	public String update(){
		deviceFacade.getTerminalBpo().update(terminalDto);
		return findPage();
	}

	public String beforeSave(){
		this.setReqAttr("positions",PositionCode.getTypeMap());
		return "beforeSave";
	}
	
	public String save(){
		deviceFacade.getTerminalBpo().save(terminalDto);
		return findPage();
	}
	
	public String delete(){
		String[] ids = this.getReqParams("items");
		deviceFacade.getTerminalBpo().delete(ids);
		return findPage();
	}
	
	public String findPage(){
		this.setReqAttr("positions",PositionCode.getTypeMap());
		deviceFacade.getTerminalBpo().find(getPageObject());
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
		ArrayList<ArrayList<String>> fieldData = deviceFacade.getTerminalBpo().findExcelFieldData(getPageObject());
		ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator(fieldName, fieldData);
		try {
			/** 获取字节输出流*/
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			/** 设置excel的响应头部信息，（内联和附件）*/
			String filename = "终端设备列表导出("+ DateUtil.format("yyyyMMddHHmmss")+ ")";
			/** 处理中文 */
			filename = new String(filename.getBytes("gbk"), "iso-8859-1");
			ServletActionContext.getRequest().setAttribute("name", filename);
			excelFileGenerator.expordExcel(os);
			/**从缓存区中读出字节数组 */
			byte[] bytes = os.toByteArray();
			InputStream inputStream = new ByteArrayInputStream(bytes);
			/** 将inputStream放置到模型驱动的对象中 */
			terminalDto.setInputStream(inputStream);
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
		fieldName.add("终端ID");
		fieldName.add("终端名称");
		fieldName.add("位置");
		fieldName.add("ip");
		fieldName.add("物理位置");
		fieldName.add("是否启用");
		fieldName.add("描述");
		return fieldName;
	}


}
