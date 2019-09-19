package com.beyeon.common.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;

public class ResourceBundleManager extends ResourceBundle {
	public static Locale root = new Locale("","","");
	private ResourceBundle manual = new ResourceBundle();													//手动刷新
	private ResourceBundleMessageSource constant = new ResourceBundleMessageSource(); 						//不刷新
	private ReloadableResourceBundleMessageSource auto = new ReloadableResourceBundleMessageSource();		//自动刷新，每隔20秒
	private String basePath = "config/";
	private String manualPath = "manual/";
	private String constantPath = "constant/";
	private String autoPath = "auto/";
	
	public ResourceBundleManager() {
		this("config");
	}
	
	public ResourceBundleManager(String baseName) {
		if (StringUtils.isNotBlank(baseName)) {
			setBasename(baseName);
		}
		
		try {
			afterPropertiesSet();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private String[] loadFileList(String listPath,boolean isAllPath) throws Exception{
		String[] fileList = this.getResourceLoader().getResource(this.basePath + listPath).getFile().list();
		if (null != fileList) {
			List<String> tempFileList = new ArrayList<String>();
			for (int i = 0; i < fileList.length; i++) {
				if(fileList[i].endsWith(PROPERTIES_SUFFIX) && !fileList[i].contains("_")){
					if(isAllPath)
						tempFileList.add(this.basePath + listPath + fileList[i].split(PROPERTIES_SUFFIX)[0]);
					else
						tempFileList.add(fileList[i].split(PROPERTIES_SUFFIX)[0]);
				}
			}
			String[] lastFileList = new String[tempFileList.size()];
			for (int i = 0; i < tempFileList.size(); i++) {
				lastFileList[i]=tempFileList.get(i);
			}
			return lastFileList;
		}
		return new String[0];
	}
	
	public boolean refreshAll() {
		try {
			super.init(root);
			basePath = this.getMessage("base.path", null, root);
			constantPath = this.getMessage("constant.path", null, root);
			autoPath = this.getMessage("auto.path", null, root);
			manualPath = this.getMessage("manual.path", null, root);

			if (StringUtils.isNotBlank(constantPath)) {
				constant.setBasenames(loadFileList(constantPath,true));
			}
			if (StringUtils.isNotBlank(manualPath)) {
				manual.setBasenames(loadFileList(manualPath,true));
				manual.init(root);
			}
			if (StringUtils.isNotBlank(autoPath)) {
				auto.setBasenames(loadFileList(autoPath,true));
			}
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return false;
		}
	}
	
	public PropertiesHolder refresh(String fileName){
		return manual.refreshProperties(this.basePath+manualPath+fileName,root);
	}
	
	public boolean isModified(String fileName){
		return manual.isModified(this.basePath+manualPath+fileName,root);
	}
	
	public String getConstantProperty(String key){
		try {
			return constant.getMessage(key, null, root);
		} catch (NoSuchMessageException e) {
			logger.error("不存在的--->"+key);
		}
		return null;
	}
	
	public String getAutoProperty(String key){
		try {
			return auto.getMessage(key, null, root);
		} catch (NoSuchMessageException e) {
			logger.error("不存在的--->"+key);
		}
		return null;
	}
	
	public String getManualProperty(String key){
		try {
			return manual.getMessage(key, null, root);
		} catch (NoSuchMessageException e) {
			logger.error("不存在的--->"+key);
		}
		return null;
	}
	
	public String getManualProperty(String fileName,String key){
		try {
			return manual.getProperty(this.basePath+manualPath+fileName, key, null, root);
		} catch (Exception e) {
			logger.error("不存在的--->"+key);
		}
		return null;
	}
	
	public String getManualProperty(String fileName,String key,Object[] args){
		try {
			return manual.getProperty(this.basePath+manualPath+fileName, key, args, root);
		} catch (Exception e) {
			logger.error("不存在的--->"+key);
		}
		return null;
	}
	
	public List getMoreManualProperty(String fileName,String key){
		try {
			return manual.getMoreProperty(this.basePath+manualPath+fileName, key, null, root);
		} catch (Exception e) {
			logger.error("不存在的--->"+key);
		}
		return null;
	}
	
	public String getManualConfig(){
		return manualPath;
	}
	
	public File getManualFile(String fileName){
		return getFile(manualPath, fileName);
	}
	
	public File getFile(String modPath,String fileName){
		if(!fileName.startsWith("/"))
			fileName = this.basePath+modPath+fileName+ PROPERTIES_SUFFIX;
		Resource resource = this.getResourceLoader().getResource(fileName);
		try {
			return resource.getFile();
		} catch (Exception e) {
			logger.error(fileName+"文件不存在",e);
		}
		return null;
	}
	
	public String[] getFileList(String listPath) throws Exception{
		return loadFileList(listPath,false);
	}

	public Set getManualKeys(String fileName) {
		return manual.getKey(this.basePath+manualPath+fileName, root);
	}
	
	public void afterPropertiesSet() throws IOException {
		manual.setResourceLoader(this.getResourceLoader());
		auto.setResourceLoader(this.getResourceLoader());
		auto.setFallbackToSystemLocale(false);
		auto.setCacheSeconds(20);
		refreshAll();
	}
}
