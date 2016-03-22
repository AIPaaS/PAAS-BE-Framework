package com.binary.core.encrypt;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.binary.core.exception.EncryptException;
import com.binary.core.io.SerializationUtils;
import com.binary.core.util.BinaryUtils;



/**
 * 通过AES算法实现对称加密, 操作过程如下：
 * 1. 通过getKey()方法获取一个密钥
 * 2. 跟据上一步生成的密钥，通过encrypt(String key, String data)方法加密数据
 * 3. 跟据上一步生成的密钥，通过decrypt(String key, String code)方法解密数据
 * @author wanwb
 */
public abstract class EncryptAES {
	
	public static String EFAULT_KEY = "aced00057372001f6a617661782e63727970746f2e737065632e5365637265744b6579537065635b470b66e230614d0200024c0009616c676f726974686d7400124c6a6176612f6c616e672f537472696e673b5b00036b65797400025b427870740003414553757200025b42acf317f8060854e00200007870000000103ff07184a7fe26945d88aafc1b9ffe61";

	
	
	/**
	 * 获取密钥
	 * @return
	 */
	public static String getKey() {
		return getKey(128);
	}
	
	
	
	/**
	 * 获取密钥
	 * @param keysize : 指定密钥长度, 必须是64的倍数, 最小为128
	 * @return
	 */
	public static String getKey(int keysize) {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			SecureRandom secrand = new SecureRandom();
			secrand.setSeed(BinaryUtils.getUUID().getBytes());
			keyGenerator.init(keysize, secrand);
			
			SecretKey key = keyGenerator.generateKey();
			String strKey = Encrypt.byte2String(SerializationUtils.serialize(key));
			
			return strKey;
		} catch (NoSuchAlgorithmException e) {
			throw new EncryptException(e);
		}
	}
	
	
	
	
	/**
	 * 加密
	 * @param key : 公钥/私钥
	 * @param data : 被加密数据
	 * @return 加密之后的密文
	 */
	public static String encrypt(String key, String data) {
		BinaryUtils.checkEmpty(key, "key");
		BinaryUtils.checkEmpty(data, false, "data");
		
		try {
			Cipher cipher = Cipher.getInstance("AES");
			
			byte[] array = Encrypt.string2Byte(key);
			Key keyObj = SerializationUtils.deserialize(array, Key.class);
			cipher.init(Cipher.ENCRYPT_MODE, keyObj);
			
	        byte[] code = cipher.doFinal(data.getBytes());
	        return Encrypt.byte2String(code);
		} catch (Exception e) {
			throw new EncryptException(e);
		}
	}
	
	
	
	
	
	/**
	 * 解密
	 * @param key : 公钥/私钥
	 * @param code : 密文
	 * @return 解密之后数据
	 */
	public static String decrypt(String key, String code) {
		BinaryUtils.checkEmpty(key, "key");
		BinaryUtils.checkEmpty(code, false, "code");
		
		try {
			Cipher cipher = Cipher.getInstance("AES");
			
			byte[] array = Encrypt.string2Byte(key);
			Key keyObj = SerializationUtils.deserialize(array, Key.class);
			cipher.init(Cipher.DECRYPT_MODE, keyObj);
			
	        byte[] data = cipher.doFinal(Encrypt.string2Byte(code));
	        return new String(data);
		} catch (Exception e) {
			throw new EncryptException(e);
		}
	}
	
	
	/**
	 * 加密
	 * @param key : 公钥/私钥
	 * @param data : 被加密数据
	 * @return 加密之后的密文
	 */
	public static String encrypt( String data) {
		BinaryUtils.checkEmpty(data, false, "data");		
		return encrypt(EFAULT_KEY,data);
	}
	
	
	
	
	
	/**
	 * 解密
	 * @param key : 公钥/私钥
	 * @param code : 密文
	 * @return 解密之后数据
	 */
	public static String decrypt(String code) {
		BinaryUtils.checkEmpty(code, false, "code");		
		return decrypt(EFAULT_KEY,code);
	}
	
	
	public static void main(String[] args ){
		
		//String key = EncryptAES.getKey();
		//System.out.println(key);
		String pwd = "123456";
		String resutl = EncryptAES.encrypt(pwd);
		System.out.println(resutl);
		System.out.println(EncryptAES.decrypt(resutl));
	}
	
}
