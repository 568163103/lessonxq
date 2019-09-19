package com.beyeon.common.util.security;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RSAUtil extends CipherUtil{
	protected static Logger logger =LoggerFactory.getLogger(RSAUtil.class);
	protected static final String RSA = "RSA";
	protected static KeyPairGenerator keyPairGen;
	static {
		try {
			keyPairGen = KeyPairGenerator.getInstance(RSA);
			// 密钥位数
			keyPairGen.initialize(1024);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 得到密钥字符串（经过base64编码）
	 *
	 * @return
	 */
	public static String getKeyString(Key key) throws Exception {
		return new String(Base64.encodeBase64(key.getEncoded()), ENCODING);
	}

	public static HdKeyPair createKeyPair() throws Exception{
		// 密钥对
		KeyPair keyPair = keyPairGen.generateKeyPair();
		// 公钥
		PublicKey publicKey = keyPair.getPublic();
		// 私钥
		PrivateKey privateKey = keyPair.getPrivate();
		HdKeyPair keyPairs = new HdKeyPair();
		keyPairs.setPublicKey(getKeyString(publicKey));
		keyPairs.setPrivateKey(getKeyString(privateKey));
		return keyPairs;
	}

	/**
	 * 得到公钥
	 *
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public PublicKey getPublicKey(String key) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(key.getBytes(ENCODING));
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA);
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	/**
	 * 得到私钥
	 *
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public PrivateKey getPrivateKey(String key) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(key.getBytes(ENCODING));
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA);
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	/**
	 * 取得加密后的字节数组
	 * @param originalString
	 * @return
	 */
	public byte[] encrypt(String publicKey,String originalString,String encoding) throws Exception{
		Cipher cp=Cipher.getInstance(RSA);
		cp.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
		return cp.doFinal(originalString.getBytes(encoding));
	}

	/**
	 * 取得解密后的字符串
	 * @param encryptArr
	 * @throws Exception
	 */
	public String decrypt(String privateKey,byte[] encryptArr,String encoding) throws Exception {
		Cipher cp=Cipher.getInstance(RSA);
		cp.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey));
		byte[] arr=cp.doFinal(encryptArr);
		return new String(arr,encoding);
	}

	public static void main(String[] args) throws Exception {
		HdKeyPair keyPairs = createKeyPair();
		System.err.println(keyPairs.getPublicKey());
		System.err.println("");
		System.err.println(keyPairs.getPrivateKey());
		System.err.println("");
		// 明文
		String plainText = "我们都很好！邮件：@sina.com";
		// 加密
		plainText = CipherUtil.RSAUTIL.encryptWithBase64(keyPairs.getPublicKey(), plainText,ENCODING);
		System.err.println(plainText);
		plainText = CipherUtil.RSAUTIL.decryptWithBase64(keyPairs.getPrivateKey(), plainText, ENCODING);
		System.err.println(plainText);

	}

}