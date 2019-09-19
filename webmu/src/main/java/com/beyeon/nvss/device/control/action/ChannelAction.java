package com.beyeon.nvss.device.control.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beyeon.hibernate.fivsdb.*;
import com.beyeon.nvss.device.model.bpo.EncoderBpo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.beyeon.bean.xml.cInterface.ReqCamResStateReqBean;
import com.beyeon.bean.xml.cInterface.ReqCamResStateResBean;
import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.util.DateUtil;
import com.beyeon.common.util.ExcelFileGenerator;
import com.beyeon.common.util.HttpClientUtil;
import com.beyeon.common.util.JaxbUtils;
import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.constants.CommandConstant;
import com.beyeon.general.baseinfo.model.bpo.DictBpo;
import com.beyeon.nvss.bussiness.model.BussinessFacade;
import com.beyeon.nvss.common.model.dto.NvsResultDto;
import com.beyeon.nvss.device.model.DeviceFacade;
import com.beyeon.nvss.device.model.dto.ChannelDto;
import com.beyeon.nvss.device.model.dto.ChannelImpDto;
import com.beyeon.nvss.device.model.dto.EncoderImpDto;
import com.opensymphony.xwork2.ActionContext;

@Component("channelAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ChannelAction extends BaseAction {
	private static Logger logger =LoggerFactory.getLogger(ChannelAction.class);
	private static String FileSeparator = "/";
	private static String CHANNEL_SNAPSHOT_PARENT_PATH = "channelsnapshot"+FileSeparator;
	private static String CHANNEL_SNAPSHOT_DEFAULT_IMAGE = "/images/default_channel.jpg";
	private DeviceFacade deviceFacade;
	private BussinessFacade bussinessFacade;

	public void setDeviceFacade(DeviceFacade deviceFacade) {
		this.deviceFacade = deviceFacade;
	}

	public void setBussinessFacade(BussinessFacade bussinessFacade) {
		this.bussinessFacade = bussinessFacade;
	}

	private ChannelDto channelDto;
	
	public void setChannelDto(ChannelDto channelDto) {
		this.channelDto = channelDto;
	}

	public Object getModel() {
		if(null == channelDto){
			String id = this.getReqParam("id");
			if(StringUtils.isNotBlank(id)){
				channelDto = deviceFacade.getChannelBpo().get(id);
			} else {
				channelDto = new ChannelDto();
			}
		}
		return channelDto;
	}

	public String beforeUpdate(){
		String channelId = this.getReqParam("id");
		Channel channel = deviceFacade.getChannelBpo().findById(channelId);

		String encoderId = channel.getEncoderId();
		Encoder encoder =deviceFacade.getEncoderBpo().findById(encoderId);

		EncoderExtra encoderExtra  = encoder.getEncoderExtra();
		this.setReqAttr("presetFlags", DictBpo.find(TDict.PRESET_FLAG));
		this.setReqAttr("cameraTypes", DictBpo.find(TDict.CAMERA_TYPE));
		this.setReqAttr("msus", Server.getObjectMap(ServerType.MSU.toString()));
		this.setReqAttr("recordPlanNames",this.bussinessFacade.getRecordPlanBpo().findRecordPlanName());
		this.setReqAttr("encoder",encoder);
		this.setReqAttr("encoderExtra",encoderExtra);
		return "beforeUpdate";
	}

	public String update(){
		deviceFacade.getChannelBpo().update(channelDto);
		if(StringUtils.isNotBlank(this.getReqParam("encoderId"))){
			return "updateEncoder";
		}
		return findPage();
	}

	public String beforeSave(){
		this.setReqAttr("positions",PositionCode.getTypeMap());
		this.setReqAttr("presetFlags", DictBpo.find(TDict.PRESET_FLAG));
		this.setReqAttr("msus", Server.getObjectMap(ServerType.MSU.toString()));
		return "beforeSave";
	}

	public String save(){
		deviceFacade.getChannelBpo().save(channelDto);
		return findPage();
	}
	
	public String beforeUpdatePlan(){
		String[] ids = this.getReqParams("items");
		String str = "";
		for(String s:ids){
			str+=s+",";
		}
		this.setReqAttr("ids", str);
		this.setReqAttr("presetFlags", DictBpo.find(TDict.PRESET_FLAG));
		this.setReqAttr("cameraTypes", DictBpo.find(TDict.CAMERA_TYPE));
		this.setReqAttr("msus", Server.getObjectMap(ServerType.MSU.toString()));
		this.setReqAttr("recordPlanNames",this.bussinessFacade.getRecordPlanBpo().findRecordPlanName());
		return "beforeUpdatePlan";
	}

	public String updatePlan(){
		deviceFacade.getChannelBpo().updatePlan(channelDto);
		return findPage();
	}

	public String delete(){
		String[] ids = this.getReqParams("items");
		this.bussinessFacade.getUserTreeBpo().deleteUserTreeByChannelId(ids);
		this.deviceFacade.getChannelBpo().delete(ids);
		return findPage();
	}

	public String findPage(){
		this.setReqAttr("positions",PositionCode.getTypeMap());
		this.setReqAttr("msus",Server.getObjectMap(ServerType.MSU.toString()));
		this.setReqAttr("mdus",Server.getObjectMap(ServerType.MDU.toString()));
		PageObject pageObject = getPageObject();
		deviceFacade.getChannelBpo().find(pageObject);
		return "findPage";
	}

	public String updatePreset(){
		deviceFacade.getChannelBpo().updatePreset(channelDto.getPreset());
		return "updatePreset";
	}

	public String uploadSnapshot(){
		try {
			String fileName = this.getReqParam("channelSnapshotFileName");
			String[] fileNames = fileName.split("_");
			File[] file = (File[]) ActionContext.getContext().getParameters().get("channelSnapshot");
			if (StringUtils.isBlank(fileName) || fileNames.length < 5 || null == file || file.length == 0){
				this.responseHTML("not find file");
				return null;
			}
			String realPath = baseinfoFacade.getRealStorePath();
			String virtualPath = baseinfoFacade.getVirtualStorePath();
			String childPath = CHANNEL_SNAPSHOT_PARENT_PATH + fileNames[0] + FileSeparator + fileNames[1] + FileSeparator +fileNames[2] + FileSeparator;
			File childDirectory = new File(realPath+childPath);
			if (!childDirectory.exists()){
				childDirectory.mkdirs();
			}
			File destFile = new File(realPath+childPath+fileNames[3]+".jpg");
			destFile.delete();
			FileUtils.copyFile(file[0],destFile);
			ChannelSnapshot channelSnapshot = new ChannelSnapshot();
			channelSnapshot.setOtId(fileNames[0]);
			channelSnapshot.setResId(fileNames[1]);
			channelSnapshot.setEventType(fileNames[2]);
			channelSnapshot.setCreateTime(fileNames[3]);
			channelSnapshot.setRemark(fileNames[4].substring(0, fileNames[4].lastIndexOf(".") < 0 ? fileNames[4].length() : fileNames[4].lastIndexOf(".")));
			channelSnapshot.setUploadTime(new Date());
			channelSnapshot.setUrl(virtualPath+childPath+fileNames[3]+".jpg");
			deviceFacade.getChannelBpo().save(channelSnapshot);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			this.responseHTML(e.getMessage());
			return null;
		}
		this.responseHTML("success");
		return null;
	}

	public String findPageSnapshot(){
		this.getPageObject().setPageSize(16);
		deviceFacade.getChannelBpo().findSnapshot(this.getPageObject());
		return "findSnapshot";
	}

	public String findSnapshot(){
		NvsResultDto<ChannelSnapshot> resultDto = new NvsResultDto<ChannelSnapshot>();
		try {
			Map<String,String> params = getReqParamObject(HashMap.class);
			this.getPageObject().setParams(params);
			if ( null == params){
				params = new HashMap<String, String>();
				String resId = this.getReqParam("resId");
				if (StringUtils.isNotBlank(resId)){
					params.put("resId",resId);
				}
				String isNew = this.getReqParam("isNew");
				if (StringUtils.isNotBlank(isNew)){
					params.put("isNew",isNew);
				}
				String beginTime = this.getReqParam("beginTime");
				if (StringUtils.isNotBlank(beginTime)){
					params.put("beginTime",beginTime);
				}
				String endTime = this.getReqParam("endTime");
				if (StringUtils.isNotBlank(endTime)){
					params.put("endTime",endTime);
				}
				String remark = this.getReqParam("remark");
				if (StringUtils.isNotBlank(remark)){
					params.put("remark",remark);
				}
			}
			this.getPageObject().setParams(params);
			this.getPageObject().setPageSize(Integer.MAX_VALUE);
			this.deviceFacade.getChannelBpo().findSnapshot(this.getPageObject());
			resultDto.setResult(0);
			resultDto.setItemList(this.getPageObject().getResultList());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultDto.setMessage(e.getMessage());
		}
		this.responseJSON(resultDto);
		return null;
	}

	private String getUserId(){
		String uid = null;
		if (null != this.getCurrentUser()){
			uid = this.getCurrentUser().getId();
		}
		if (StringUtils.isBlank(uid)){
			String userName = this.getReqParam("u");
			if (StringUtils.isNotBlank(userName)) {
				uid = bussinessFacade.getUserTreeBpo().findUid(userName);
			}
		}
		if (StringUtils.isBlank(uid)){
			uid = bussinessFacade.getUserTreeBpo().findUid("everybody");
		}
		return uid;
	}

	public String findUserSnapshot(){
		this.getPageObject().setPageSize(3);
		List<ChannelSnapshot> channelSnapshots = deviceFacade.getChannelBpo().findUserSnapshot(this.getPageObject(),getUserId());
		this.setReqAttr("channelSnapshots",channelSnapshots);
		return "findUserSnapshot";
	}

	public String getSnapshot(){
		String channelId = this.getReqParam("channelId");
		String imageUrl = this.deviceFacade.getChannelBpo().getSnapshot(channelId);
		outputImageFile(imageUrl,CHANNEL_SNAPSHOT_DEFAULT_IMAGE);
		return null;
	}

	public String viewSnapshot(){
		String imageUrl = this.getReqParam("url");
		outputImageFile(imageUrl,CHANNEL_SNAPSHOT_DEFAULT_IMAGE);
		return null;
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
		ArrayList<ArrayList<String>> fieldData = deviceFacade.getChannelBpo().findExcelFieldData(getPageObject());
		ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator(fieldName, fieldData);
		try {
			/** 获取字节输出流*/
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			/** 设置excel的响应头部信息，（内联和附件）*/
			String filename = "摄像机列表导出("+ DateUtil.format("yyyyMMddHHmmss")+ ")";
			/** 处理中文 */
			filename = new String(filename.getBytes("gbk"), "iso-8859-1");
			ServletActionContext.getRequest().setAttribute("name", filename);
			excelFileGenerator.expordExcel(os);
			/**从缓存区中读出字节数组 */
			byte[] bytes = os.toByteArray();
			InputStream inputStream = new ByteArrayInputStream(bytes);
			/** 将inputStream放置到模型驱动的对象中 */
			channelDto.setInputStream(inputStream);
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
		fieldName.add("摄像机ID");
		fieldName.add("摄像机名称");
		fieldName.add("位置");
		fieldName.add("通道");
		fieldName.add("存储服务器");
		fieldName.add("存储方案");
		fieldName.add("是否启用");
		return fieldName;
	}
	
	/**
	 * 
	 *外域设备begin
	 */
	public String findExternalPage(){
		String[] items = this.getReqParams("items");
		String serverId = this.getReqParam("serverId");
		if(serverId!=null){
			this.setReqAttr("serverId",serverId);
		}else if(items!=null&&items.length==1){
			this.setReqAttr("serverId",items[0]);
		}
		this.setReqAttr("platforms",Encoder.getTypeMap());
		this.setReqAttr("positions",PositionCode.getTypeMap());
		this.setReqAttr("msus",Server.getObjectMap(ServerType.MSU.toString()));
		this.setReqAttr("mdus",Server.getObjectMap(ServerType.MDU.toString()));
		PageObject pageObject = getPageObject();
		deviceFacade.getChannelBpo().findExternal(pageObject);
		return "findExternalPage";
	}
	
	public String deleteExternalChannel(){
		String[] ids = this.getReqParams("items");
		this.bussinessFacade.getUserTreeBpo().deleteUserTreeByChannelId(ids);
		this.deviceFacade.getChannelBpo().delete(ids);
		return findExternalPage();
	}
	
	/**
	 * 报表导出
	 * @author
	 * @return
	 */
	public String exportListExternal() {
		/** 构造excel的标题数据 */
		ArrayList<String> fieldName = findExternalExcelFieldName();
		/** 构造excel的数据内容 */
		ArrayList<ArrayList<String>> fieldData = deviceFacade.getChannelBpo().findExternalExcelFieldData(getPageObject());
		ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator(fieldName, fieldData);
		try {
			/** 获取字节输出流*/
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			/** 设置excel的响应头部信息，（内联和附件）*/
			String filename = "下级摄像机列表导出("+ DateUtil.format("yyyyMMddHHmmss")+ ")";
			/** 处理中文 */
			filename = new String(filename.getBytes("gbk"), "iso-8859-1");
			ServletActionContext.getRequest().setAttribute("name", filename);
			excelFileGenerator.expordExcel(os);
			/**从缓存区中读出字节数组 */
			byte[] bytes = os.toByteArray();
			InputStream inputStream = new ByteArrayInputStream(bytes);
			/** 将inputStream放置到模型驱动的对象中 */
			channelDto.setInputStream(inputStream);
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
	public ArrayList<String> findExternalExcelFieldName() {
		ArrayList<String> fieldName = new ArrayList<String>();
		fieldName.add("序号");
		fieldName.add("摄像机ID");
		fieldName.add("摄像机名称");
		fieldName.add("位置");
		fieldName.add("用途");
		fieldName.add("所属下级ID");
		fieldName.add("所属下级名称");
		fieldName.add("是否在线");
		return fieldName;
	}
	
	public String updateCamResState(){
		ReqCamResStateResBean resBean = null;
		try {
			String serverId = this.getReqParam("serverId");
			String[] ids = this.getReqParams("items");
			
			ReqCamResStateReqBean bean = new ReqCamResStateReqBean();
			bean.setCommand(CommandConstant.REQCAMRESSTATE);
			ReqCamResStateReqBean.Group group = new ReqCamResStateReqBean.Group();
			
			List<ReqCamResStateReqBean.Url> urllist = new ArrayList<ReqCamResStateReqBean.Url>();
			for(String s:ids){
				ReqCamResStateReqBean.Url url = new ReqCamResStateReqBean.Url();
				url.setResId(s);
				urllist.add(url);
			}
			group.setUrls(urllist);
			bean.getParameters().setGroup(group);
			bean.getParameters().setSaId(serverId);
			
			System.out.println(JaxbUtils.marshaller(bean));
			
			StringBuilder cmuUrl = new StringBuilder();
			cmuUrl.append("http://").append(deviceFacade.findServerIp(ServerType.CMU)).append(":")
					.append(ResourceUtil.getServerConf("cmu.server.port")).append("/TBMessageReq");
			String result = HttpClientUtil.post(cmuUrl.toString()+"?said="+serverId, JaxbUtils.marshaller(bean));
			logger.debug(result);
			System.out.println(result);
			resBean = JaxbUtils.unmarshal(result, ReqCamResStateResBean.class);
			deviceFacade.getChannelBpo().updateExternalChannel(resBean.getParameters().getGroup().getUrls());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (null == resBean || !resBean.getResult().getResultCode().equals("0")) {
			this.addActionMessage("同步摄像头状态失败！");
		}else if(resBean.getResult().getResultCode().equals("0")){
			this.addActionMessage("同步摄像头状态成功！");
		}
		return findExternalPage();
	}
	
	/**
	 * 
	 *外域设备end
	 */
	
	public String impFromExcel(){		
		File[] file = (File[]) ActionContext.getContext().getParameters().get("fileSource");
		if (null == file || file.length == 0){
			this.responseHTML("上传文件无效！");
			return null;
		}
		int i = 1;
		try {
			List<ChannelImpDto> channelImpDtos = new ArrayList<ChannelImpDto>();
			Workbook wb = WorkbookFactory.create(file[0]);
			Sheet sheet = wb.getSheetAt(0);
			int rowNum = sheet.getLastRowNum();
			Row row = null;
			for (; i <= rowNum; i++) {
				int j = 1;
				row = sheet.getRow(i);
				ChannelImpDto channelImpDto = new ChannelImpDto();
				channelImpDto.setChannelId(getCellValue(row.getCell(j)));j++;
				channelImpDto.setChannelName(getCellValue(row.getCell(j)));j++;
				channelImpDtos.add(channelImpDto);
			}
			this.deviceFacade.getChannelBpo().updateChannelName(channelImpDtos);
			this.responseHTML("ok");
		} catch (Exception e) {
			logger.debug("导入数据失败，第"+i+"行:",e);
			this.responseHTML("导入数据失败，请查看第"+i+"行数据有误");
		}
		return null;
	}
	
	public String getCellValue(Cell c) {
		String string = "";
		if(c == null){
			return string;
		}
		if (c != null) {
			if (c.getCellType() == Cell.CELL_TYPE_BLANK) {
				string = "";
			} else if (c.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
				string = Boolean.toString(c.getBooleanCellValue());
			} else if (c.getCellType() == Cell.CELL_TYPE_ERROR) {
				string = "";
			} else if (c.getCellType() == Cell.CELL_TYPE_FORMULA) {
				double d = c.getNumericCellValue();
				string = Double.toString(d);
				if(string.endsWith(".0")){
					string = Integer.toString(((Double)d).intValue());
				}
			} else if (c.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				double d = c.getNumericCellValue();
				BigDecimal bd = new BigDecimal(d);
				string = bd.toPlainString();
			} else {
				string = c.getStringCellValue();
			}
		}
		return string;
	}

}
