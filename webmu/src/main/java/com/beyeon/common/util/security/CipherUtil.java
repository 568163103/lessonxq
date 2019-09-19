package com.beyeon.common.util.security;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Security;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.beyeon.common.config.ResourceUtil;

/**
 * User: liwf
 * Date: 14-10-13
 * Time: 上午10:05
 */
public abstract class CipherUtil {
	protected static final String ENCODING = "utf-8";
	static {
		Security.addProvider(new BouncyCastleProvider());
	}
	// 加密方法
	public static final AESUtil AESUTIL = new AESUtil();
	public static final RSAUtil RSAUTIL = new RSAUtil();
	
	public abstract byte[] encrypt(String key, String originalString, String encoding) throws Exception;

	public abstract String decrypt(String key, byte[] encryptArr, String encoding) throws Exception;

	public String encryptWithBase64(String key, String originalString,String encoding) throws Exception{
		byte[] encryptDests = encrypt(key, originalString, encoding);
		return new String(Base64.encodeBase64(encryptDests),encoding);
	}

	public String decryptWithBase64(String key, String decryptOriginal,String encoding) throws Exception{
		byte[] decryptDests = Base64.decodeBase64(decryptOriginal.getBytes(encoding));
		return decrypt(key, decryptDests, encoding);
	}

	public String encryptWithBase64(String originalString) throws Exception{
		return encryptWithBase64(ResourceUtil.getPublicConf("app.aes.key"), originalString);
	}

	public String encryptWithBase64(String key, String originalString) throws Exception{
		return encryptWithBase64(key, originalString, ENCODING);
	}

	public String decryptWithBase64(String decryptOriginal) throws Exception{
		return decryptWithBase64(ResourceUtil.getPublicConf("app.aes.key"), decryptOriginal);
	}

	public String decryptWithBase64(String key, String decryptOriginal) throws Exception{
		return decryptWithBase64(key, decryptOriginal, ENCODING);
	}

	public String encryptWithBase64UrlEncode(String originalString) throws Exception{
		return encryptWithBase64UrlEncode(ResourceUtil.getPublicConf("app.aes.key"), originalString);
	}

	public String encryptWithBase64UrlEncode(String key, String originalString) throws Exception{
		return encryptWithBase64UrlEncode(key, originalString, ENCODING);
	}

	public String encryptWithBase64UrlEncode(String key, String originalString,String encoding) throws Exception{
		return URLEncoder.encode(encryptWithBase64(key, originalString, encoding), encoding);
	}

	public String decryptWithBase64UrlDecode(String decryptOriginal) throws Exception{
		return decryptWithBase64UrlDecode(ResourceUtil.getPublicConf("app.aes.key"), decryptOriginal);
	}

	public String decryptWithBase64UrlDecode(String key, String decryptOriginal) throws Exception{
		return decryptWithBase64UrlDecode(key,decryptOriginal, ENCODING);
	}

	public String decryptWithBase64UrlDecode(String key, String decryptOriginal,String encoding) throws Exception{
		return decryptWithBase64(key, URLDecoder.decode(decryptOriginal, encoding),encoding);
	}

}
