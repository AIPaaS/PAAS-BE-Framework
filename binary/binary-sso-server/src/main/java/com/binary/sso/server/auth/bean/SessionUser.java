package com.binary.sso.server.auth.bean;

import java.io.Serializable;

import com.binary.framework.bean.User;

public class SessionUser implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	/** SessionID **/
	private String sid;
	
	/** Session中的登录用户信息 **/
	private User user;

	
	public SessionUser() {
	}
	
	
	public SessionUser(String sid, User user) {
		this.sid = sid;
		this.user = user;
	}
	
	
	
	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
