package com.beyeon.common.util.security;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

/**
 * AES算法加密解密实用工具类
 */
public class AESUtil extends CipherUtil {
	protected static final String AES ="AES/ECB/ZeroBytePadding";

	/**
	 * 取得密钥
	 * @throws Exception
	 */
	private Key getKey(String key) throws Exception{
		byte[] arr = Hex.decodeHex(key.toCharArray());
		return new SecretKeySpec(arr, AES.substring(0, 3));
	}

	/**
	 * 取得字符串形式的密钥
	 * @throws Exception
	 */
	public static String initKeyHex() throws Exception{
		KeyGenerator kg=KeyGenerator.getInstance(AES.substring(0, 3));
		//kg.init(256);
		SecretKey sk=kg.generateKey();
		byte[] keys = sk.getEncoded();
		return new String(Hex.encodeHex(keys));
	}
    
    /**
     * 取得加密后的字节数组
     * @param originalString
     * @return
     */

	@Override
	public byte[] encrypt(String key, String originalString,String encoding) throws Exception{
		Cipher cp = Cipher.getInstance(AES,"BC");
		IvParameterSpec ivspec = null;
		if("CBC".equals(AES.substring(4, 7))){
			ivspec = new IvParameterSpec(Hex.decodeHex(key.toCharArray()));
		}
		cp.init(Cipher.ENCRYPT_MODE, getKey(key), ivspec);
		return cp.doFinal(originalString.getBytes(encoding));
	}

    /**
     * 取得解密后的字符串
     * @param encryptArr
     * @throws Exception 
     */

	@Override
	public String decrypt(String key, byte[] encryptArr,String encoding) throws Exception {
		Cipher cp = Cipher.getInstance(AES,"BC");
		IvParameterSpec ivspec = null;
		if("CBC".equals(AES.substring(4, 7))){
			ivspec = new IvParameterSpec(Hex.decodeHex(key.toCharArray()));
		}
		cp.init(Cipher.DECRYPT_MODE, getKey(key), ivspec);
		byte[] arr=cp.doFinal(encryptArr);
		return new String(arr,encoding);
	}

	public static void main(String[] args)  throws Exception{
		String str="童杰超";
		String key = initKeyHex();
		System.out.println("生成一个新key方法:"+key);
		key = "6c461a510b6b93fd2fe6b5876dc93a9b";
		str= CipherUtil.AESUTIL.encryptWithBase64UrlEncode(key, str);
		System.out.println("AES 加密后的字符串为:" + str);
		str=CipherUtil.AESUTIL.decryptWithBase64UrlDecode(key, "nn6inoiJv701cHmKLpIjXQ%3D%3D");
		System.out.println("AES 解密后的字符串为:" + str);
    }
}