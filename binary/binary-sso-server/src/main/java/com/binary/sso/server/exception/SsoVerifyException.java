package com.binary.sso.server.exception;



public class SsoVerifyException extends SsoException {
	private static final long serialVersionUID = 1L;
	
	
	private String errorCode;
	
	
	public SsoVerifyException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public SsoVerifyException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}
	
	
	

	public String getErrorCode() {
		return errorCode;
	}
	
	
	
	
	
	
}
