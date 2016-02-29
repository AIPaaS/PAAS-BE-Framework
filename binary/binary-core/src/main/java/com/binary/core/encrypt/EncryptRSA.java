package com.binary.core.encrypt;

import java.security.Key;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;

import com.binary.core.exception.EncryptException;
import com.binary.core.io.SerializationUtils;
import com.binary.core.util.BinaryUtils;




/**
 * 通过RSA算法实现非对称加密, 操作过程如下：
 * 1. 通过getKeyPair()方法获取一个公/私钥对
 * 2. 跟据上一步生成的公/私钥对，通过encrypt(String key, String data)方法加密数据
 * 3. 跟据上一步生成的公/私钥对，通过decrypt(String key, String code)方法解密数据
 * 4. (使用公钥加密的数据必须由私钥解密, 使用私钥加密的数据必须由公钥解密)
 * @author wanwb
 */
public abstract class EncryptRSA {
	
	
	public static class KeyPair {
		private String publicKey;
		private String privateKey;
		public KeyPair(String publicKey, String privateKey) {
			this.publicKey = publicKey;
			this.privateKey = privateKey;
		}
		public String getPublicKey() {
			return publicKey;
		}
		public String getPrivateKey() {
			return privateKey;
		}
	}
	
	
	
	/**
	 * 获取公钥/私钥健值对
	 * @return
	 */
	public static KeyPair getKeyPair() {
		return getKeyPair(1024);
	}
	

	
	/**
	 * 获取公钥/私钥健值对
	 * @param keysize : 指定密钥长度, 必须是512的倍数
	 * @return
	 */
	public static KeyPair getKeyPair(int keysize) {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			SecureRandom secrand = new SecureRandom();
			secrand.setSeed(BinaryUtils.getUUID().getBytes());
			keyPairGenerator.initialize(keysize, secrand);
			
			java.security.KeyPair keyPair = keyPairGenerator.generateKeyPair();
			
			Key publicKey = keyPair.getPublic();
	        Key privateKey = keyPair.getPrivate();
	        
	        String strPublicKey = Encrypt.byte2String(SerializationUtils.serialize(publicKey));
	        String strPrivateKey = Encrypt.byte2String(SerializationUtils.serialize(privateKey));
	        
	        return new KeyPair(strPublicKey, strPrivateKey);
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
			Cipher cipher = Cipher.getInstance("RSA");
			
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
			Cipher cipher = Cipher.getInstance("RSA");
			
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
