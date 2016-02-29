package com.binary.framework.web;

import com.binary.framework.exception.WebException;


public enum ErrorCode {
	
	
	URL_WRONG(401),
	
	
	SERVER_ERROR(501),
	
	
	SESSION_INVALID(601),
	
	
	NOT_LOGIN(701);
	
	
	
	private int value;
	
	
	private ErrorCode(int value) {
		this.value = value;
	}
	
	
	public int getCode() {
		return this.value;
	}
	
	
	
	public static ErrorCode valueOf(int code) {
		switch(code) {
			case 401: return URL_WRONG;
			case 501: return SERVER_ERROR;
			case 601: return SESSION_INVALID;
			case 701: return NOT_LOGIN;
			default: throw new WebException(" is wrong error-code:'"+code+"'! ");
		}
	}
	
		
}
