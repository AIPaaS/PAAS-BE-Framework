package com.binary.tools.captcha;

import java.io.Serializable;

public class ImageType implements Serializable {
	private static final long serialVersionUID = 101606466221283628L;

	
	/** 包含非正式格式名称（例如 "jpeg" 或 "tiff"）的 String **/
	private String formatName;
	
	
	/** 文件在网页中输入类型 **/
	private String contentType;


	
	public ImageType() {
	}	
	public ImageType(String formatName, String contentType) {
		this.formatName = formatName;
		this.contentType = contentType;
	}


	
	public String getFormatName() {
		return formatName;
	}


	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}


	public String getContentType() {
		return contentType;
	}


	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	
	
	
	
	
	
}
