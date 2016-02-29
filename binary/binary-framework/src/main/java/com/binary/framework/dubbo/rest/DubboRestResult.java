package com.binary.framework.dubbo.rest;

import java.io.Serializable;

public class DubboRestResult implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private boolean success;
	private String data;
	private String errorMsg;
	
	
	public DubboRestResult() {
	}
	
	
	public DubboRestResult(String data) {
		this.success = true;
		this.data = data;
	}
	
	
	public DubboRestResult(Throwable t) {
		this.success = false;
		this.errorMsg = t.getMessage();
	}


	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}


	public String getData() {
		return data;
	}


	public void setData(String data) {
		this.data = data;
	}


	public String getErrorMsg() {
		return errorMsg;
	}


	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	

	
	
	
	
}
