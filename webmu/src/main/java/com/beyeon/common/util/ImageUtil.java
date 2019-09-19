package com.beyeon.common.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageUtil {
	private static final Integer SIZE_WIDTH = 150;
	private static final Integer SIZE_HEIGHT = 100;
	private static final String[] FILE_TYPE = {"jpg", "png", "ico", "gif"};
	public  static final String WIDTH = "_width_";
	public  static final String HEIGHT = "_height_";
	public static Integer getGeometricWidth(Integer iwidth, Integer iheight){
		Integer result = 0;
		Integer width = iwidth.intValue();
		Integer height = iheight.intValue();
		if(width > ImageUtil.SIZE_WIDTH || height > ImageUtil.SIZE_HEIGHT){
			double wPre = width <= ImageUtil.SIZE_WIDTH ? 
					width : (ImageUtil.SIZE_WIDTH * 1.0) / width;
			double hPre = height <= ImageUtil.SIZE_HEIGHT ? 
					height : (ImageUtil.SIZE_HEIGHT * 1.0) / height;
			if(wPre < hPre)
				result = new Double(width * wPre).intValue();
			else
				result = new Double(width * hPre).intValue();
		}
		if(result <= 0)
			return new Double(SIZE_WIDTH * 0.1).intValue();
		return result;
	}
	public static Integer getGeometricHeight(Integer iwidth, Integer iheight){
		Integer result = 0;
		Integer width = iwidth.intValue();
		Integer height = iheight.intValue();
		if(width > ImageUtil.SIZE_WIDTH || height > ImageUtil.SIZE_HEIGHT){
			double wPre = width <= ImageUtil.SIZE_WIDTH ? 
					width : (ImageUtil.SIZE_WIDTH * 1.0) / width;
			double hPre = height <= ImageUtil.SIZE_HEIGHT ? 
					height : (ImageUtil.SIZE_HEIGHT * 1.0) / height;
			if(wPre < hPre)
				result = new Double(height * wPre).intValue();
			else
				result = new Double(height * hPre).intValue();
		}
		if(result <= 0)
			return new Double(SIZE_HEIGHT * 0.1).intValue();
		return result;
	}
	public static Integer getImageWidth(String urlStr){
		Logger logger =LoggerFactory.getLogger(ImageUtil.class);
		logger.info("url:" + urlStr);
		if(isNext(urlStr))
			return -1;
		BufferedImage sourceImg = null;
		try{
			sourceImg = ImageUtil.getImageBuffer(urlStr);
		}catch (Exception e) {
			logger.info("获取文件属性失败,文件类型不匹配或其他错误!");
			sourceImg = null;
		}
		if(null == sourceImg)
			return -1;
		Integer result = sourceImg.getWidth();
        return result;
	}
	public static Integer getImageHeight(String urlStr){
		Logger logger =LoggerFactory.getLogger(ImageUtil.class);
		logger.info("url:" + urlStr);
		if(isNext(urlStr))
			return -1;
		BufferedImage sourceImg = null;
		try{
			sourceImg = ImageUtil.getImageBuffer(urlStr);
		}catch (Exception e) {
			logger.info("获取文件属性失败,文件类型不匹配或其他错误!");
			sourceImg = null;
		}
		if(null == sourceImg)
			return -1;
		Integer result = sourceImg.getHeight();
        return result;
	}
	
	/**
	 * 获取图片的宽和高
	 * @param urlStr 
	 * 				图片资源路径
	 * @return
	 */
	public static Map<String, Object> getImageWH(String urlStr){
		Map<String, Object> info = new HashMap<String, Object>();
		Logger logger =LoggerFactory.getLogger(ImageUtil.class);
		logger.info("url:" + urlStr);
		if(isNext(urlStr))
			return info;
		BufferedImage sourceImg = null;
		try{
			sourceImg = ImageUtil.getImageBuffer(urlStr);
		}catch (Exception e) {
			logger.info("获取文件属性失败,文件类型不匹配或其他错误!");
			sourceImg = null;
		}
		if(null == sourceImg)
			return info;
		
		Integer height = sourceImg.getHeight();
		Integer  width = sourceImg.getWidth();
		
		info.put(WIDTH,width);
		info.put(HEIGHT,height);
		
        return info;
	}
	private static BufferedImage getImageBuffer(String urlStr){
		Logger logger =LoggerFactory.getLogger(ImageUtil.class);
		URL url = null;
		HttpURLConnection conn = null;
		InputStream is = null;
		BufferedImage sourceImg = null;
		try {
			url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			is = conn.getInputStream();
			sourceImg = javax.imageio.ImageIO.read(is);
		} catch (MalformedURLException e) {
			logger.info(urlStr + ",路径错误!");
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			logger.info("建立输入流异常!");
			e.printStackTrace();
			return null;
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				logger.info("关闭输入流异常!");
				return null;
			}
		}
		return sourceImg;
	}
	private static boolean isNext(String urlStr) {
		String vUrlStr = urlStr.toLowerCase();
		for(String type : FILE_TYPE){
			if(vUrlStr.endsWith(type)){
				return false;
			}
		}
		return true;
	}
}
