package com.beyeon.common.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import com.beyeon.common.config.ResourceUtil;

public class BaiduMapUtil {
	
	private static Logger logger =LoggerFactory.getLogger(BaiduMapUtil.class);
	private static interface MapCallback {
		Map callback(Map result);
	}
	
	public static Map getBaiduMapData(String baiduMapUrl,Map params,MapCallback mapCallback) {
		try {
			String responseBody = HttpClientUtil.get(baiduMapUrl, params);
			logger.debug(responseBody);
			Map baiduMapData = JSON.parseObject(responseBody, HashMap.class);
			if(null == baiduMapData){
				return null;
			}
			return mapCallback.callback(baiduMapData);
		} catch (Exception e) {
			logger.error("获取百度地图数据失败");
			return null;
		}
	}
	
	public static Map getBaiduMapByLocation(String addr, String cityName) {
		Map<String,String> params = new HashMap<String,String>();
		params.put("address", addr);params.put("city", cityName);
		String baiduMapUrl = ResourceUtil.getGlobalPublicConf("baidu_map_url_by_adrs");
		return getBaiduMapData(baiduMapUrl, params, new MapCallback() {
			@Override
			public Map callback(Map baiduMapData) {
				Object result = baiduMapData.get("result");
				if(result instanceof Map){
					Object location = ((Map) result).get("location");
					if(location instanceof Map){
						return (Map) location;
					}
				}
				return null;
			}
		});
	}

	public static Map getBaiduMapByIp(String ip) {
		Map<String,String> params = new HashMap<String,String>();
		params.put("ip", ip);
		String baiduMapUrl = ResourceUtil.getGlobalPublicConf("baidu_map_url_by_ip");
		return getBaiduMapData(baiduMapUrl, params, new MapCallback() {
			@Override
			public Map callback(Map baiduMapData) {
				Object result = baiduMapData.get("content");
				if(result instanceof Map){
					logger.debug((String)((Map) result).get("address"));
					Object point = ((Map) result).get("point");
					if(point instanceof Map){
						return (Map) point;
					}
				}
				return null;
			}
		});
	}
	
	public static void main(String[] arg0) {
		try {
			Map location = getBaiduMapByLocation("郑州蓝视科技", "蓝视科技");
			if(null != location){
				System.err.println(location.get("lng"));
				System.err.println(location.get("lat"));
			}
			location = getBaiduMapByIp(null);
			if(null != location){
				System.err.println(location.get("x"));
				System.err.println(location.get("y"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
