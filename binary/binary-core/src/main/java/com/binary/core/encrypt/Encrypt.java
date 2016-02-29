package com.binary.core.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import com.binary.core.exception.EncryptException;
import com.binary.core.util.BinaryUtils;

public abstract class Encrypt {
	
	
	private static final Object syncobj = new Object();
	private static final Map<EncryptType, MessageDigest> digests = new HashMap<EncryptType, MessageDigest>();
	
	
	
	private static MessageDigest getMessageDigest(EncryptType type) {
		MessageDigest digest = digests.get(type);
		if(digest == null) {
			synchronized (syncobj) {
				digest = digests.get(type);
				if(digest == null) {
					try {
						digest = MessageDigest.getInstance(type.name());
					} catch (NoSuchAlgorithmException e) {
						throw new EncryptException(e);
					}
					digests.put(type, digest);
				}
			}
		}
		return digest;
	}
	
	
	
	
	/**
	 * MD5加密
	 * @param data : 被加密明文
	 * @return 密文二进制
	 */
	public static byte[] encryptBinary(String data) {
		return encryptBinary(data, EncryptType.MD5);
	}
	
	
	/**
	 * 加密
	 * @param data : 被加密明文
	 * @param encryptType : 加密类型
	 * @return 密文二进制
	 */
	public static byte[] encryptBinary(String data, EncryptType encryptType) {
		BinaryUtils.checkNull(encryptType, "encryptType");
		MessageDigest digest = getMessageDigest(encryptType);
		return digest.digest(data.getBytes());
	}
	
	
	
	
	/**
	 * 将byte[]数组转换成16进制数字符串
	 * @param array
	 * @return
	 */
	public static String byte2String(byte[] array) {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<array.length; i++) {
			int a = array[i];
			if(a < 0) a += 256;
			
			String s = Integer.toHexString(a);
			if(s.length() < 2) sb.append("0");
			sb.append(s);
		}
		return sb.toString();
	}
	
	
	
	/**
	 * 将16进制数字符串转换成byte[]数组
	 * @param s
	 * @return
	 */
	public static byte[] string2Byte(String hexString) {
		char[] cs = hexString.toCharArray();
		byte[] array = new byte[cs.length/2];
		for(int i=0,j=0; i<cs.length; i+=2,j++) {
			String s = cs[i]+""+cs[i+1];
			int a = Integer.valueOf(s, 16);
			array[j] = (byte)(a - 256);
		}
		return array;
	}
	
	
	
	/**
	 * MD5加密
	 * @param data : 被加密明文
	 * @return 密文
	 */
	public static String encrypt(String data) {
		return encrypt(data, EncryptType.MD5);
	}
	
	
	/**
	 * 加密
	 * @param data : 被加密明文
	 * @param encryptType : 加密类型
	 * @return 密文
	 */
	public static String encrypt(String data, EncryptType encryptType) {
		byte[] bys = encryptBinary(data, encryptType);
		return byte2String(bys);
	}
	
	
	
	
	
	/**
	 * MD5加密
	 * @param data : 被加密明文
	 * @param encryptCount : 连续加密次数
	 * @return 密文
	 */
	public static String encrypt(String data, int encryptCount) {
		if(encryptCount < 1) throw new EncryptException(" the encryptCount is error '"+encryptCount+"'! ");
		String code = encrypt(data);
		return encryptCount==1 ? code : encrypt(code, encryptCount-1);
	}
	
	
	
	/**
	 * SHA加密
	 * @param data : 被加密明文
	 * @param encryptType : 加密类型
	 * @param encryptCount : 连续加密次数
	 * @return 密文二进制
	 */
	public static String encrypt(String data, EncryptType encryptType, int encryptCount) {
		if(encryptCount < 1) throw new EncryptException(" the encryptCount is error '"+encryptCount+"'! ");
		String code = encrypt(data, encryptType);
		return encryptCount==1 ? code : encrypt(code, encryptType, encryptCount-1);
	}
	
	
	
	
	
	
}




