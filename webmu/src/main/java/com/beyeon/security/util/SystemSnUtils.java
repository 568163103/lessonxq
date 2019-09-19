package com.beyeon.security.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import com.beyeon.common.system.sn.SystemSn;
import com.beyeon.common.util.DateUtil;
import com.beyeon.common.util.security.CipherUtil;
import com.beyeon.general.baseinfo.model.bpo.DictBpo;
import com.beyeon.hibernate.fivsdb.TDict;
import com.beyeon.security.SystemSnSecurity;

/**
 * User: liwf
 * Date: 16-1-17
 * Time: 下午4:18
 */
public class SystemSnUtils {
	public static Logger logger =LoggerFactory.getLogger(SystemSnUtils.class);
	private static long RESETTIME = 0L;

	public static long getResetTime(){
    	if (RESETTIME ==0){
    		String key = DictBpo.getDict(TDict.SYSTEM_PARAM, "3").getDescr();
    		RESETTIME = key!=null ?Long.parseLong(key) : 7776000L;
    	}
		return RESETTIME;
    	
    }
	
	public static String encoder(String key,String text) {
		return SystemSnSecurity.encoder(key, text);
	}

	public static String decoder(String key,String text) {
		return SystemSnSecurity.decoder(key,text);
	}

	public static String encoder(String key) {
		return SystemSnSecurity.encoder(key);
	}

	public static Boolean isAvailable(){
		return SystemSnSecurity.isAvailable();
	}

	public static TDict refresh(int i){
		return SystemSnSecurity.refresh(i);
	}

	public static void setSystemSn(String sn){
		SystemSnSecurity.setSystemSn(sn);
	}

	public static String getSystemSnSrc() {
		return SystemSnSecurity.getSystemSnSrc();
	}

	public static SystemSn getSystemSn() {
		return SystemSnSecurity.getSystemSn();
	}

	protected static String createSn(String key,Integer un,Integer dn,Integer mn){
		String result = "";
		try {
			SystemSn systemSn = new SystemSn();
			systemSn.setDeviceNum(dn);
			systemSn.setUserNum(un);
			systemSn.setEndTime(DateUtil.addMonths(systemSn.getStartTime(),mn));
			result = JSON.toJSONString(systemSn);
			logger.info(result);
			result = encoder(key, result);
		} catch (Exception e) {
		}
		return result;
	}

	public static String createSn(String key,String uuid,Integer un,Integer dn,String endTime){
		String result = "";
		try {
			SystemSn systemSn = new SystemSn();
			systemSn.setUuid(uuid);
			systemSn.setDeviceNum(dn);
			systemSn.setUserNum(un);
			systemSn.setEndTime(DateUtil.parse(endTime, DateUtil.yyyyMMddHHmmssSpt));
			result = JSON.toJSONString(systemSn);
			logger.info(result);
			result = encoder(key, result);
		} catch (Exception e) {
		}
		return result;
	}

	protected static String updateSn(String key,String sn,Integer un,Integer dn,Integer mn){
		String result = sn;
		try {
			result = decoder(key, sn);
			logger.info(result);
			SystemSn systemSn = JSON.parseObject(result, SystemSn.class);
			systemSn.setDeviceNum(dn);
			systemSn.setUserNum(un);
			systemSn.setEndTime(DateUtil.addMonths(systemSn.getStartTime(),mn));
			result = JSON.toJSONString(systemSn);
			logger.info(result);
			result = encoder(key, result);
		} catch (Exception e) {
		}
		return result;
	}

	public static void main(String[] args){
		try {
			String key = CipherUtil.AESUTIL.initKeyHex();
			key = "654d9fe1dd2a9ae6ba7315604e4a80f6";
			String sn = "Fxeu4kXlr0J4KRO0NIE%2B82edsXxUiVtwFj46glcIcy3IxP%2FLRddR23zjQpadbjRPj0JbQwO2KEK1Ohsp8WBa3B78%2FKGbY7bmmQMN4srSmfg9uCN%2FXjn5GX8U7YrA5VA%2BZlOEAnFR8Rrs%2BP9mQIZlBD2vdedquO%2FfFLt6VCm4SufsbqnV7g%2FBO1TJhpZVnFMS%2";
			//String sn = "hkHdh65NTSotGrwntthFiKA8j2OOdOcy8Vpj8RBsjcRE8Tl26OJiG83uXHnW1j9k8jzJUPi%2BazfesYta4amw80x0TS6IdS5asA5llwTEZXufg%3D%3DN4srSmfg9uCN%2FXjn5GX8U8z1xsGbqc2hVitZ5pw8dJF4SvL4nL9PfGiKUgLy0ytolx4OSc%2B4NJiwm%2F%2BluoHtOkhI%2Fufb2tpgIxybYQJ%2FJ";
			logger.info(createSn(key,10,2,120));
			//log.info(createSn(key,10000,10000,120));
			logger.info(updateSn(key,sn,1000,1000,120));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
