package com.beyeon.security;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

import com.beyeon.common.system.sn.SystemSn;
import com.beyeon.common.util.security.CipherUtil;
import com.beyeon.general.baseinfo.model.bpo.DictBpo;
import com.beyeon.hibernate.fivsdb.TDict;
import com.beyeon.security.util.HardWareUtils;

/**
 * User: Administrator
 * Date: 2016/1/15
 * Time: 14:38
 */
public class SystemSnSecurity {
	private static SystemSn defaultSystemSn = init();

	public static String encoder(String key) {
		return encoder(key, JSON.toJSONString(new SystemSn()));
	}

	public static String encoder(String key,String text) {
		String result = text;
		try {
			result = CipherUtil.AESUTIL.encryptWithBase64UrlEncode(key,text);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.substring(result.length()/2)+result.substring(0,result.length()/2);
	}

	public static String decoder(String key,String text) {
		String result = text.substring(text.length()-text.length()/2)+text.substring(0,text.length()-text.length()/2);
		try {
			result = CipherUtil.AESUTIL.decryptWithBase64UrlDecode(key, result);
		} catch (Exception e) {
		}
		return result;
	}

	public static SystemSn init(){
		try {
			String key = DictBpo.getDict(TDict.SYSTEM_PARAM, "1").getDescr();
			String systemSnStr = DictBpo.getDict(TDict.SYSTEM_PARAM, "2").getDescr();
			if (StringUtils.isNotBlank(systemSnStr)) {
				systemSnStr = decoder(key, systemSnStr);
				SystemSn systemSn = JSON.parseObject(systemSnStr, SystemSn.class);
				if (StringUtils.isBlank(systemSn.getUuid()) || systemSn.getUuid().equals(HardWareUtils.getUUID())) {
					defaultSystemSn = systemSn;
				}
			}
		} catch (Exception e) {
		}
		if (null == defaultSystemSn){
			defaultSystemSn = new SystemSn(HardWareUtils.getUUID());
		} else {
			if (StringUtils.isBlank(defaultSystemSn.getUuid())){
				defaultSystemSn.setUuid(HardWareUtils.getUUID());
			}
		}
		return defaultSystemSn;
	}

	public static Boolean isAvailable(){
		return defaultSystemSn.isAvailable();
	}

	public static TDict refresh(int i){
		defaultSystemSn.setUseTime(defaultSystemSn.getUseTime()+i);
		TDict dict = DictBpo.getDict(TDict.SYSTEM_PARAM, "2");
		dict.setDescr(encoder(DictBpo.getDict(TDict.SYSTEM_PARAM, "1").getDescr(), JSON.toJSONString(defaultSystemSn)));
		return dict;
	}

	public static void setSystemSn(String sn){
		SystemSn systemSn = JSON.parseObject(decoder(DictBpo.getDict(TDict.SYSTEM_PARAM, "1").getDescr(), sn), SystemSn.class);
		if (StringUtils.isNotBlank(systemSn.getUuid()) && systemSn.getUuid().equals(HardWareUtils.getUUID())) {
			defaultSystemSn = systemSn;
		}
	}

	public static String getSystemSnSrc(){
		if (null == defaultSystemSn.getUuid() || !HardWareUtils.getUUID().equals(defaultSystemSn.getUuid())){
			defaultSystemSn = new SystemSn(HardWareUtils.getUUID());
		}
		return encoder(DictBpo.getDict(TDict.SYSTEM_PARAM, "1").getDescr(), JSON.toJSONString(defaultSystemSn));
	}

	public static SystemSn getSystemSn(){
		return defaultSystemSn;
	}

}
