package com.beyeon.common.web.control.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Comparator;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;

import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.exception.AppExceptionImpl;

public class FileOperateUtil {
	protected static Logger logger =LoggerFactory.getLogger(FileOperateUtil.class);
	public static String root_path = ResourceUtil.getParamConf("root.file.path");
	static {
		if(!root_path.endsWith(File.separator)){
			root_path = root_path + File.separator;
		}
	}
	public static String getRootPath(){
		return root_path;
	}
	public static String getFullPath(String fileName){
		return root_path + fileName;
	}

	public static String[] getFileName(String inputName){
		String fileNameName = inputName + "FileName";

		ActionContext ac = ActionContext.getContext();
		return (String[]) ac.getParameters().get(fileNameName);
		
	}

	/**
	 * @param inputName 页面上传文件的input名字
	 * @param fileName 文件存放的相对路径
	 * @throws Exception
	 */
	public static void uploadFile(String inputName, String fileName) throws Exception {
		fileName = FilenameUtils.separatorsToSystem(fileName);
		if(fileName.startsWith(File.separator)){
			fileName = fileName.substring(1);
		}
		String fullFileName = root_path + fileName;
		logger.debug(fullFileName);
		File filePath = new File(FilenameUtils.getFullPath(fullFileName));
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		ActionContext ac = ActionContext.getContext();
		File[] files = (File[]) ac.getParameters().get(inputName);
		for (int i = 0; i < files.length; i++) {
			File srcFile = files[i];
			File destFile = new File(fullFileName);
			if(destFile.exists()){
				destFile.delete();
			}
			FileUtils.copyFile(srcFile, destFile);
		}
	}

	/**
	 * @param fileName 下载文件的相对路径
	 * @throws Exception
	 */
	public static void downloadFile(String fileName) throws Exception {
		fileName = FilenameUtils.separatorsToSystem(fileName);
		StringBuilder fullFileNameBuilder = new StringBuilder();
		if(fileName.startsWith(File.separator)){
			fileName = fileName.substring(1);
		}
		fullFileNameBuilder.append(root_path).append(fileName);
		File downloadFile = null;
		String fullFileName = fullFileNameBuilder.toString();
		String resultFileName = FilenameUtils.getName(fullFileName);
		if("".equals(FilenameUtils.getExtension(fullFileName))){
			String fullPath = FilenameUtils.getFullPath(fullFileName);
			File fileDirectory = new File(fullPath);
			if(fileDirectory.exists() && fileDirectory.isDirectory()){
				File[] files = fileDirectory.listFiles();
				Arrays.sort(files,new Comparator<File>(){
					public int compare(File f1, File f2) {
						if(f1.lastModified() > f2.lastModified()){
							return -1;
						} else if(f1.lastModified() < f2.lastModified()){
							return 1;
						}
						return 0;
					}
				});
				for (File file : files) {
					if(file.getName().contains(resultFileName)){
						downloadFile = file;
						resultFileName = file.getName();
						break;
					}
				}
			}
		} else {
			downloadFile = new File(fullFileName);
		}
		if (null == downloadFile || !downloadFile.exists()) {
			throw new AppExceptionImpl("要下载的文件不存在");
		}
		HttpServletResponse resp = ServletActionContext.getResponse();

		long start = 0;
		long length = downloadFile.length();

		/** 响应头 */
		resp.setContentType("application/octet-stream;charset=UTF-8");
		resultFileName = handleFileDownloadName(fullFileName, resultFileName);
		String fn = new String(resultFileName.getBytes(), "iso8859-1");
		resp.setHeader("Content-Disposition", "attachment;filename=" + fn);
		resp.setHeader("Content-Length", String.valueOf(length));

		/** 输出流 */
		FileInputStream input = new FileInputStream(downloadFile);
		input.skip(start);//定位到请求位置
		copy(input, resp.getOutputStream(), start, length);
		resp.flushBuffer();
		return ;
	}

	public static void copy(InputStream input, OutputStream out,long start, long end) throws IOException{
		try {
			long remain = input.available() - end;
			input.skip(start);
			byte[] buffer = new byte[2 * 1024];
			int len = 0;
			while (input.available() > remain) {
				len = input.read(buffer);
				if (input.available() < remain) {
					len -= (remain - input.available());
				}
				out.write(buffer, 0, len);
			}
		} finally {
			/** 连接断开 */
			if(null != out){
				try {
					out.flush();
					out.close();
				} catch (Exception e) {
				}
			}
			if (null != input){
				try {
					input.close();
				} catch (Exception e) {
				}
			}
		}
	}
	
	public static String handleFileDownloadName(String fullFileName,
			String resultFileName) {
		return resultFileName;
	}
}
