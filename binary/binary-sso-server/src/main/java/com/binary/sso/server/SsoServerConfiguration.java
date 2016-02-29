package com.binary.sso.server;

import java.io.Serializable;

import com.binary.sso.server.cache.SsoCache;

public class SsoServerConfiguration implements Serializable {
	private static final long serialVersionUID = 1L;

	
	/** token有效时间, 单位: 秒 **/
	private int tokenValidTime = 10;
	
	
	/** 扫描token间隔时间, 单位: 分钟 **/
	private int scanTokenIntervalTime = 30;

	
	/** 缓存服务 **/
	private transient SsoCache ssoCache;
	
	
	
	/** 登录时是否需要验证码 **/
	private boolean loginCaptcha = false;

	
	/** 验证登录的服务对象 (框架向SpringContext中找, 如果没找到则抛异常) **/
	private String userLoginListenerRef;
	
	
	/** 指定登录页面URL **/
	private String loginUrl;
	
	
	/** 如果没有客户端的回调地址时, 登录之后所指向的地址 **/
	private String noClientUrl;
	
	
	
	
	public int getTokenValidTime() {
		return tokenValidTime;
	}


	public void setTokenValidTime(int tokenValidTime) {
		this.tokenValidTime = tokenValidTime;
	}


	public int getScanTokenIntervalTime() {
		return scanTokenIntervalTime;
	}


	public void setScanTokenIntervalTime(int scanTokenIntervalTime) {
		this.scanTokenIntervalTime = scanTokenIntervalTime;
	}


	public SsoCache getSsoCache() {
		return ssoCache;
	}


	public void setSsoCache(SsoCache ssoCache) {
		this.ssoCache = ssoCache;
	}


	public boolean isLoginCaptcha() {
		return loginCaptcha;
	}


	public void setLoginCaptcha(boolean loginCaptcha) {
		this.loginCaptcha = loginCaptcha;
	}



	public String getUserLoginListenerRef() {
		return userLoginListenerRef;
	}


	public void setUserLoginListenerRef(String userLoginListenerRef) {
		this.userLoginListenerRef = userLoginListenerRef;
	}


	public String getLoginUrl() {
		return loginUrl;
	}


	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}


	public String getNoClientUrl() {
		return noClientUrl;
	}


	public void setNoClientUrl(String noClientUrl) {
		this.noClientUrl = noClientUrl;
	}
	
	
	
	
	
}
