package com.binary.sso.server.auth.bean;

import java.io.Serializable;

public class LoginResult implements Serializable {
	private static final long serialVersionUID = 1L;

	
	/** 是否登录成功 **/
	private boolean success;
	
	
	/** 登录代码标识 **/
	private String code;
	
	
	/** 登录备注信息 **/
	private String message;
	
	
	/** 当前登录token **/
	private String token;
	
	
	public LoginResult() {
	}
	
	public LoginResult(boolean success, String message, String token) {
		this.success = success;
		this.message = message;
		this.token = token;
	}





	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	
	
	
}
