package com.binary.framework.web;




/**
 * 存放在Session中的Key, 统一管理对象
 */
public abstract class SessionKey {
	
	
	/**
	 * 登录用户key, 值类型为com.binary.framework.bean.User
	 */
	public static final String SYSTEM_USER = "SK_SYSTEM_USER";
	
	
	
	/**
	 * 登录验证码, 值类型为com.binary.tools.captcha.CaptchaImage
	 */
	public static final String LOGIN_CAPTCHA = "SK_LOGIN_CAPTCHA";
	
	
	
	
}




