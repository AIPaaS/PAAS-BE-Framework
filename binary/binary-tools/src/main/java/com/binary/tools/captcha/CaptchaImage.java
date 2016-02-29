package com.binary.tools.captcha;

import java.io.Serializable;



/**
 * 验证码图片
 * @author wanwb
 */
public class CaptchaImage implements Serializable {
	private static final long serialVersionUID = -2253188360126978249L;

	

	/** 验证码代码 **/
	private String code;
	
	
	/** 验证码图片 **/
	private byte[] data;
	
	
	/** 图片类型 **/
	private ImageType type;
	
	
	
	public CaptchaImage() {
	}
	
	public CaptchaImage(String code, byte[] data, ImageType type) {
		this.code = code;
		this.data = data;
		this.type = type;
	}
	
	
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public ImageType getType() {
		return type;
	}

	public void setType(ImageType type) {
		this.type = type;
	}

	
	
	


	
	
	
	
	
	
	
}
