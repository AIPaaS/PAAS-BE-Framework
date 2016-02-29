package com.binary.sso.client.auth;

import javax.servlet.http.HttpServletRequest;

import com.binary.framework.bean.User;



/**
 * 用户登录事件监听
 * @author wanwb
 */
public interface UserLoginListener {

	
	
	/**
	 * 登录成功事件
	 * @param user
	 */
	public void onLoginSuccess(HttpServletRequest request, User user, String sessionId);
	
	
	
	
	
	
}
