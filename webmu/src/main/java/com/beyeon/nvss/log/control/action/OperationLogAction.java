package com.beyeon.nvss.log.control.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.beyeon.common.util.DateUtil;
import com.beyeon.common.util.ExcelFileGenerator;
import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.general.baseinfo.model.bpo.DictBpo;
import com.beyeon.hibernate.fivsdb.OperationLog;
import com.beyeon.hibernate.fivsdb.TDict;
import com.beyeon.hibernate.fivsdb.TUserTrace;
import com.beyeon.nvss.log.model.bpo.OperationLogBpo;

@Component("operationLogAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OperationLogAction extends BaseAction {
	private OperationLogBpo operationLogBpo;
	private String downFileName;  
    public String getDownFileName() {  
    return downFileName;  
    }  
    public void setDownFileName(String downFileName) {  
        this.downFileName = downFileName;  
    }   
	public void setOperationLogBpo(OperationLogBpo operationLogBpo) {
		this.operationLogBpo = operationLogBpo;
	}

	public String findPage(){
		this.setReqAttr("types", DictBpo.find(TDict.OPERATION_TYPE));
		operationLogBpo.findOperationLog(this.getPageObject());
		return "findPage";
	}
	
	public String exportList() {
		/** 构造excel的标题数据 */
		String filename = "客户端操作日志导出("+ DateUtil.format("yyyyMMddHHmmss")+ ")";
		/** 处理中文 */
		try {
			downFileName = new String(filename.getBytes("gbk"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "exportList";
	}
	
	public InputStream getApendListInputStream() {  
        InputStream inputStream = null;  
        ArrayList<String> fieldName = findExcelFieldName();
		/** 构造excel的数据内容 */
		ArrayList<ArrayList<String>> fieldData = operationLogBpo.findExcelFieldData(getPageObject());
		ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator(fieldName, fieldData);
		try {
			/** 获取字节输出流*/
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			/** 设置excel的响应头部信息，（内联和附件）*/
			
			excelFileGenerator.expordExcel(os);
			/**从缓存区中读出字节数组 */
			byte[] bytes = os.toByteArray();
			inputStream = new ByteArrayInputStream(bytes);
			
//			userTraceBpo.
//			serverDto.getServer().setInputStream(inputStream);
        }catch (Exception e) {  
            e.printStackTrace();
        }  
        return inputStream;  
    }   
	/**
	 * EXCEL表头名称
	 * @author 
	 * @return
	 */
	public ArrayList<String> findExcelFieldName() {
		ArrayList<String> fieldName = new ArrayList<String>();
		fieldName.add("序号");
		fieldName.add("用户编码");
		fieldName.add("用户名");
		fieldName.add("登录IP");
		fieldName.add("操作类型");
		fieldName.add("操作详细信息");
		fieldName.add("操作时间");
		fieldName.add("起止时间");
		return fieldName;
	}
}
