package com.beyeon.nvss.ext.control.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;

import com.beyeon.common.util.GeneralUtil;
import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.general.common.model.dto.TransferData;
import com.beyeon.nvss.device.model.DeviceFacade;

@Component("easyPrAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EasyPrAction extends BaseAction {
	private static Logger logger =LoggerFactory.getLogger(EasyPrAction.class);
	private static String FileSeparator = "/";
	private static String EASY_PR = "easypr"+FileSeparator;
	private DeviceFacade deviceFacade;

	public void setDeviceFacade(DeviceFacade deviceFacade) {
		this.deviceFacade = deviceFacade;
	}

	private String importImage(String exePath,boolean noPath){
		TransferData transferData = new TransferData(this.getReqParam("cmd"));
		OutputStream out = null;
		try {
			String[] fileNames = (String[]) ActionContext.getContext().getParameters().get("fileSourceFileName");
			File[] file = (File[]) ActionContext.getContext().getParameters().get("fileSource");
			if (null == file || file.length == 0){
				this.responseHTML("上传文件无效！");
				return null;
			}

			String relativeFilePath = EASY_PR;
			String realPath = baseinfoFacade.getRealStorePath();
			String virtualPath = baseinfoFacade.getVirtualStorePath();
			File childDirectory = new File(realPath+relativeFilePath);
			if (!childDirectory.exists()){
				childDirectory.mkdirs();
			}
			String relativePathFileName = relativeFilePath+fileNames[0];
			File destFile = new File(realPath+relativePathFileName);
			destFile.delete();
			FileUtils.copyFile(file[0], destFile);
			file[0].delete();

			String absolutePathFileName = destFile.getAbsolutePath();
			String result = GeneralUtil.exeCmd(exePath + absolutePathFileName);
			if (!noPath)
				transferData.setData(virtualPath+relativePathFileName+"---"+result);
			else
				transferData.setData(result);
		} catch (Exception e) {
			transferData.setMessage("导入失败");
			logger.error(transferData.getMessage(), e);
		} finally {
			try {
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
			}
		}
		this.responseJSON(transferData);
		return null;
	}

	public String importEasyPrImage(){
		return importImage("c:\\app\\easypr\\pr.exe ",false);
	}

	public String importFaceImage(){
		return importImage("c:\\webapp\\face\\face.exe run  --file ",true);
	}

}
