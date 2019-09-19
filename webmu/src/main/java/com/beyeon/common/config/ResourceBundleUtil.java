package com.beyeon.common.config;

import java.io.File;
import java.util.List;
import java.util.Set;

import com.beyeon.common.config.ResourceBundle.PropertiesHolder;

public class ResourceBundleUtil {
	static ResourceBundleManager resourceBundleManager = new ResourceBundleManager();
	
	/**
	 * 刷新所有的配制文件
	 * @throws java.io.IOException
	 */
	public static boolean refreshAll(){
		return resourceBundleManager.refreshAll();
	}
	
	/**
	 * 刷新指定的文件名对应的属性
	 * @param fileName	要刷新的文件名
	 * @return			返回指定的文件名对应的属性句柄
	 */
	public static PropertiesHolder refresh(String fileName){
		return resourceBundleManager.refresh(fileName);
	}	

	/**
	 * 刷新指定的文件名对应的属性
	 * @param fileName	要刷新的文件名
	 * @return			如果有变化反回TRUE，无变化返回FALSE
	 */
	public static boolean isModified(String fileName){
		return resourceBundleManager.isModified(fileName);
	}
	
	/**
	 * 从所有的不刷新配制文件中获取属性值
	 * @param key		属性名
	 * @return			属性对应的值
	 */
	public static String getConstantProperty(String key){
		return resourceBundleManager.getConstantProperty(key);
	}
	
	/**
	 * 从所有的自动刷新配制文件中获取属性值
	 * @param key		属性名
	 * @return			属性对应的值
	 */
	public static String getAutoProperty(String key){
		return resourceBundleManager.getAutoProperty(key);
	}
	
	/**
	 * 从所有的手动刷新配制文件中获取属性值
	 * @param key		属性名
	 * @return			属性对应的值
	 */
	public static String getManualProperty(String key){
		return resourceBundleManager.getManualProperty(key);
	}
	
	public static int getManualPropertyInt(String key){
		return Integer.parseInt(resourceBundleManager.getManualProperty(key));
	}
	
	public static boolean getManualPropertyBoolean(String key){
		return Boolean.parseBoolean(resourceBundleManager.getManualProperty(key));
	}
	
	/**
	 * 从指定的手动刷新配制文件中获取属性值
	 * @param fileName	key所在的配制文件名
	 * @param key		属性名
	 * @return			属性对应的值
	 */
	public static String getManualProperty(String fileName,String key){
		return resourceBundleManager.getManualProperty(fileName,key);
	}
	
	public static int getManualPropertyInt(String fileName,String key){
		return Integer.parseInt(resourceBundleManager.getManualProperty(fileName,key));
	}
	
	public static boolean getManualPropertyBoolean(String fileName,String key){
		return Boolean.parseBoolean(resourceBundleManager.getManualProperty(fileName,key));
	}
	
	/**
	 * 从指定的手动刷新配制文件中获取属性值
	 * @param fileName	key所在的配制文件名
	 * @param key		属性名
	 * @param args		消息中参数
	 * @return			属性对应的值
	 */
	public static String getManualProperty(String fileName,String key,Object[]args){
		return resourceBundleManager.getManualProperty(fileName,key,args);
	}
	
	/**
	 * 从指定的手动刷新配制文件中获取属性值
	 * @param fileName	key所在的配制文件名
	 * @param key		属性名
	 * @return			属性对应的值
	 */
	public static List getMoreManualProperty(String fileName,String key){
		return resourceBundleManager.getMoreManualProperty(fileName,key);
	}
	
	/**
	 * 获取要手动刷新的动态加载配 制文件的文件名
	 * @return	以逗号分割的字符串
	 */
	public static String getManualConfig(){
		return resourceBundleManager.getManualConfig();
	}
	
	/**
	 * 根据指定的文件名获取文件对象
	 * @return	file
	 */
	public static File getManualFile(String fileName){
		return resourceBundleManager.getManualFile(fileName);
	}
	
	public static String[] getFileList(String fileName) throws Exception{
		return resourceBundleManager.getFileList(fileName);
	}

	public static Set getManuaKeys(String fileName) {
		return resourceBundleManager.getManualKeys(fileName);
	}
	
	public static void main(String[] args) throws Exception {
		System.err.println(Thread.currentThread().getContextClassLoader().getResource(""));
		System.err.println(ResourceBundleUtil.getManualProperty("coreConfig", "system.error"));
	}
}
