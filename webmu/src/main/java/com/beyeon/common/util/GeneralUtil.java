package com.beyeon.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beyeon.common.config.ResourceUtil;

/**
 * User: lwf Date: 12-6-7 Time: 下午4:15
 */
public class GeneralUtil {
	private static Logger logger =LoggerFactory.getLogger(GeneralUtil.class);
	private static final String encoding = "utf-8";

	/** 数字的正则表达式 */
	public static final String REGEX_DIGIT = "[0-9]+";
	/** 英文字母正在表达式 */
	public static final String REGEX_EN = "^[A-Za-z]+$";
	/** 英文字母或数字组合 正则表达式 */
	public static final String REGEN_EON = "^\\w+$";
	/** 英文字母和数字组合 正则表达式 */
	public static final String REGEN_EAN = "^(([A-Za-z]+\\d+)|(\\d+[A-Za-z]+))*$";

	/**
	 * 生成随机数
	 * 
	 * @param length
	 *            随机数的长度
	 * @return
	 */
	public static String randomNum(int length) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}

	/**
	 * 数字字符串判断
	 * 
	 * @param s
	 * @return
	 */
	public static boolean digit(String s) {
		if (StringUtils.isBlank(s)) {
			return false;
		}

		if (s.matches(REGEX_DIGIT)) {
			return true;
		}
		logger.info(s);
		return false;
	}

	/**
	 * 英文字母判断
	 * 
	 * @param s
	 * @return
	 */
	public static boolean en(String s) {
		if (StringUtils.isBlank(s)) {
			return false;
		}
		if (s.matches(REGEX_EN)) {
			return true;
		}
		logger.info(s);
		return false;
	}

	public static String encryptWithBase64(String originalString) throws Exception {
		return encryptWithBase64(originalString, encoding);
	}

	public static String decryptWithBase64(String originalString) throws Exception {
		return decryptWithBase64(originalString, encoding);
	}

	public static String encryptWithBase64(String originalString, String encoding) throws Exception {
		return new String(Base64.encodeBase64(originalString.getBytes(encoding)), encoding);
	}

	public static String decryptWithBase64(String originalString, String encoding) throws Exception {
		return new String(Base64.decodeBase64(originalString.getBytes(encoding)), encoding);
	}

	public static String encryptWithBase64URL(String originalString) throws Exception {
		return encryptWithBase64URL(originalString, encoding);
	}

	public static String decryptWithBase64URL(String originalString) throws Exception {
		return decryptWithBase64URL(originalString, encoding);
	}

	public static String encryptWithBase64URL(String originalString, String encoding) throws Exception {
		return URLEncoder.encode(encryptWithBase64(originalString, encoding), encoding);
	}

	public static String decryptWithBase64URL(String originalString, String encoding) throws Exception {
		return decryptWithBase64(URLDecoder.decode(originalString, encoding), encoding);
	}

	public static String[] getPinYin(String str) {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);
		StringBuffer py = new StringBuffer();
		StringBuffer pinyin = new StringBuffer();
		if (str != null) {
			try {
				char[] pyary = str.replaceAll("\\p{P}|\\p{Space}", "").toCharArray();
				if (pyary != null) {
					for (int i = 0; i < pyary.length; i++) {
						String[] ary = PinyinHelper.toHanyuPinyinStringArray(pyary[i], format);
						if (ary != null) {
							py.append(ary[0].charAt(0));
							pinyin.append(ary[0]);
						} else {
							py.append(pyary[i]);
							pinyin.append(pyary[i]);
						}
					}
				}
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				logger.info(e.getMessage(), e);
			}
		}
		return new String[] { py.toString(), pinyin.toString() };
	}

	public static void sendShortMsg(String mobile, String content) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("mobile", mobile);
		map.put("content", content);
		String shortMsgServer = ResourceUtil.getShortMsgServer();
		HttpClientUtil.post(shortMsgServer, map);
	}

	public static String exeCmd(String commandStr) {
		BufferedReader br = null;
		try {
			Process p = Runtime.getRuntime().exec(commandStr);
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			return sb.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} finally {
			if (br != null)	{
				try {
					br.close();
				} catch (Exception e) {
					logger.debug(e.getMessage(),e);
				}
			}
		}
		return "";
	}
}
