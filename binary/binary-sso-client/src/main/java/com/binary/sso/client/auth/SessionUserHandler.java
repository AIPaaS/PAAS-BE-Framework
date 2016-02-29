package com.binary.sso.client.auth;

import javax.servlet.http.HttpSession;

import com.binary.framework.bean.User;



/**
 * 当登录成功之后, 从SSO取出的User对象处理对象
 * @author wanwb
 */
public interface SessionUserHandler {
	
	
	
	
	
	/**
	 * 登录用户对象处理
	 * @param session : 当前Session
	 * @param user : 用户对象
	 * @return true=User存入当前服务器当中, false=User不存入当前服务器当中
	 */
	public boolean handle(HttpSession session, User user);
	
	
	

}
