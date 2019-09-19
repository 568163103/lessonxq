package com.beyeon.nvss.ext.control.action;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.general.common.model.dto.TransferData;
import com.beyeon.hibernate.fivsdb.CaseInfo;
import com.beyeon.hibernate.fivsdb.CaseVideoFile;
import com.beyeon.hibernate.fivsdb.CaseVideoFilePK;
import com.beyeon.nvss.device.model.DeviceFacade;
import com.beyeon.nvss.ext.model.bpo.CaseInfoBpo;
import com.beyeon.nvss.ext.model.dto.CaseInfoDto;

@Component("caseInfoAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CaseInfoAction extends BaseAction {
	private static Logger logger =LoggerFactory.getLogger(CaseInfoAction.class);
	private static String FileSeparator = "/";
	private static String CASE_INFO = "caseinfo"+FileSeparator;
	private DeviceFacade deviceFacade;
	private CaseInfoBpo caseInfoBpo;
	private CaseInfoDto caseInfoDto;

	private String filesourceFileName;
	private File filesource;

	public void setDeviceFacade(DeviceFacade deviceFacade) {
		this.deviceFacade = deviceFacade;
	}

	public void setCaseInfoBpo(CaseInfoBpo caseInfoBpo) {
		this.caseInfoBpo = caseInfoBpo;
	}

	public CaseInfoDto getCaseInfoDto() {
		return caseInfoDto;
	}

	public void setCaseInfoDto(CaseInfoDto caseInfoDto) {
		this.caseInfoDto = caseInfoDto;
	}

	public File getFilesource() {
		return filesource;
	}

	public void setFilesource(File filesource) {
		this.filesource = filesource;
	}

	public String getFilesourceFileName() {
		return filesourceFileName;
	}

	public void setFilesourceFileName(String filesourceFileName) {
		this.filesourceFileName = filesourceFileName;
	}

	public Object getModel() {
		if(null == caseInfoDto){
			String id = this.getReqParam("id");
			if(StringUtils.isNotBlank(id)){
				caseInfoDto = caseInfoBpo.get(id);
			} else {
				caseInfoDto = new CaseInfoDto();
			}
		}
		return caseInfoDto;
	}

	public String uploadCaseInfo(){
		TransferData transferData = (TransferData) this.getReqAttr("transferData");
		try {
			this.caseInfoBpo.saveOrUpdate(transferData.getObject(CaseInfo.class));
		} catch (Exception e) {
			transferData.setMessage("上传失败");
			logger.error(transferData.getMessage(), e);
		}
		this.responseJSON(transferData);
		return null;
	}

	public String uploadCaseVideoInfo(){
		TransferData transferData = (TransferData) this.getReqAttr("transferData");
		try {
			this.caseInfoBpo.saveOrUpdate(transferData.getObject(CaseVideoFile.class));
		} catch (Exception e) {
			transferData.setMessage("上传失败");
			logger.error(transferData.getMessage(), e);
		}
		this.responseJSON(transferData);
		return null;
	}

	public String uploadCaseVideoFile(){
		TransferData transferData = new TransferData(this.getReqParam("cmd"));
		try {
			String caseid = this.getReqParam("caseid");
			String casefileseq = this.getReqParam("casefileseq");
			String relativeFilePath = CASE_INFO + caseid + FileSeparator;
			String realPath = baseinfoFacade.getRealStorePath();
			String virtualPath = baseinfoFacade.getVirtualStorePath();
			File childDirectory = new File(realPath+relativeFilePath);
			if (!childDirectory.exists()){
				childDirectory.mkdirs();
			}
			String fileName = casefileseq+"_"+filesourceFileName;
			File destFile = new File(realPath+relativeFilePath+fileName);
			destFile.delete();
			FileUtils.copyFile(filesource, destFile);
			CaseVideoFilePK caseVideoFilePK = new CaseVideoFilePK();
			caseVideoFilePK.setCaseid(caseid);
			caseVideoFilePK.setCasefileseq(casefileseq);
			CaseVideoFile caseVideoFile = this.caseInfoBpo.get(CaseVideoFile.class,caseVideoFilePK);
			caseVideoFile.setFilepath(virtualPath+relativeFilePath+fileName);
			this.caseInfoBpo.saveOrUpdate(caseVideoFile);
			filesource.delete();
		} catch (Exception e) {
			transferData.setMessage("上传失败");
			logger.error(transferData.getMessage(), e);
		}
		this.responseJSON(transferData);
		return null;
	}

	public String findPage(){
		this.caseInfoBpo.findPage(this.getPageObject());
		return "findPage";
	}

	public String view(){
		return "view";
	}

	public String findPageVideo(){
		this.caseInfoBpo.findPageVideo(this.getPageObject());
		return "findPageVideo";
	}

}
