package com.binary.sso.client;

import java.io.Serializable;

import com.binary.core.http.URLResolver;
import com.binary.core.util.BinaryUtils;


/**
 * 客户端所需配置参数
 * @author wanwb
 */
public class SsoClientConfiguration implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	
	/** SSO服务端URL根目录, 格式：http://domain:port/contextpath **/
	private String ssoServerRoot;
	
	
	/** SSO客户端(当前集成应用)URL根目录, 格式：http://domain:port/contextpath **/
	private String ssoClientRoot;

	
	/** 指定登录用户User对象映射的实体类 **/
	private String userClass;
	
	
	/** 如果登录之后没有回调页面, 则指定跳转连接 **/
	private String noBeforeUrl;
	
	
	/** 当登录成功之后, 从SSO取出的User对象处理对象, 必须实现com.binary.sso.client.auth.SessionUserHandler, 如果没有指定, 则User对象自动存入当前服务器Session当中 **/
	private String sessionUserHandler;
	
	
	/** 用户登录成功事件 **/
	private String userLoginListener;
	
	
	

	public String getSsoServerRoot() {
		return ssoServerRoot;
	}


	public void setSsoServerRoot(String ssoServerRoot) {
		this.ssoServerRoot = ssoServerRoot;
	}


	public String getSsoClientRoot() {
		return ssoClientRoot;
	}


	public void setSsoClientRoot(String ssoClientRoot) {
		this.ssoClientRoot = ssoClientRoot;
	}


	public String getUserClass() {
		return userClass;
	}


	public void setUserClass(String userClass) {
		this.userClass = userClass;
	}


	public String getNoBeforeUrl() {
		return noBeforeUrl;
	}


	public void setNoBeforeUrl(String noBeforeUrl) {
		this.noBeforeUrl = noBeforeUrl;
	}


	public String getSessionUserHandler() {
		return sessionUserHandler;
	}


	public void setSessionUserHandler(String sessionUserHandler) {
		this.sessionUserHandler = sessionUserHandler;
	}
	
		
	public String getUserLoginListener() {
		return userLoginListener;
	}


	public void setUserLoginListener(String userLoginListener) {
		this.userLoginListener = userLoginListener;
	}


	/**
	 * 获取SSO注销SessionURL
	 * @param callbackUrl : 客户端回调地址 (只需相对根目录地址)
	 * @return
	 */
	public String getInvalidateSessionUrl(String callbackUrl) {
		BinaryUtils.checkEmpty(callbackUrl, "callbackUrl");
		
		callbackUrl = this.ssoClientRoot + callbackUrl;
		if(callbackUrl.indexOf('/') >= 0) callbackUrl = URLResolver.encode(callbackUrl);
		return this.ssoServerRoot+"/user/auth/invalidateSession?callbackUrl="+callbackUrl;
	}
	
	
	
	
	
}
