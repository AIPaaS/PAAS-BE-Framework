package com.binary.framework.web;

import java.io.Serializable;

public class RemoteResult implements Serializable {
	private static final long serialVersionUID = 2588163462392220979L;
	
	/**
	 * 无异常时error-code
	 */
	public static final int NO_ERROR_CODE = -1;
	
	
	private boolean success;
	private Object data;
	private ErrorCode errorCode;
	private String errorMsg;
	
	
	public RemoteResult(Object data) {
		this.success = true;
		this.data = data;
	}
	
	
	public RemoteResult(Throwable t) {
		this(ErrorCode.SERVER_ERROR, t.getMessage());
	}
	
	
	public RemoteResult(ErrorCode errorCode, String errorMsg) {
		this.success = false;
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	

	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}


	public Object getData() {
		return data;
	}


	public void setData(Object data) {
		this.data = data;
	}


	public int getErrorCode() {
		return errorCode!=null ? errorCode.getCode() : NO_ERROR_CODE;
	}


	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode==NO_ERROR_CODE ? null : ErrorCode.valueOf(errorCode);
	}


	public String getErrorMsg() {
		return errorMsg;
	}


	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	
	
}
