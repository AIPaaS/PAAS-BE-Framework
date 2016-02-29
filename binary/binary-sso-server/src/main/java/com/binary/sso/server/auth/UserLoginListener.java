package com.binary.sso.server.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.binary.framework.bean.User;
import com.binary.sso.server.exception.SsoVerifyException;



/**
 * 用户登录事件监听
 * @author wanwb
 */
public interface UserLoginListener {

	
	
	
	/**
	 * 验证用户登录
	 * @param loginCode : 登录代码
	 * @param password : 登录密码
	 * @return 返回登录后的用户信息
	 * @throws SsoVerifyException : 如果验证不通过抛出SsoVerifyException, SSO框架会自动将exception.message返回至前端, 如果是其他异常, SSO框架以正常异常来对待
	 */
	public User verify(String loginCode, String password) throws SsoVerifyException;
	
	
	
	
	
	/**
	 * 登录成功事件
	 * @param user
	 */
	public void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, User user, String sessionId);
	
	
	
	
	
	/**
	 * 登录失败事件
	 * @param e
	 */
	public void onLoginFailed(HttpServletRequest request, HttpServletResponse response, String loginCode, String password, Exception e);
	
	
	
	
	
	/**
	 * 登出事件
	 * @param user
	 * @param sessionId
	 */
	public void onLogout(HttpServletRequest request, HttpServletResponse response, User user, String sessionId);
	
	
	
	
	
	
}
