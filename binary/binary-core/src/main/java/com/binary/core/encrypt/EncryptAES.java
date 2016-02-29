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
	
	
	
	
	
}
