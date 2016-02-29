package com.binary.framework.critical.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.binary.framework.bean.User;
import com.binary.framework.exception.CriticalException;
import com.binary.framework.web.SessionKey;

public class HttpCriticalObject extends AbstractCriticalObject {
	
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	
	public HttpCriticalObject(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
	
	
	
	
	
	@Override
	public String getContextPath() {
		return this.request.getContextPath();
	}
	
	
	
	

	public HttpServletRequest getRequest() {
		return this.request;
	}
	
	
	public HttpServletResponse getResponse() {
		return this.response;
	}





	@Override
	public User getUser() {
		User user = null;
		HttpSession session = request.getSession(false);
		if(session != null) {
			Object obj = session.getAttribute(SessionKey.SYSTEM_USER);
			if(obj!=null && !(obj instanceof User)) throw new CriticalException(" the session-user-value '"+obj.getClass().getName()+"' is not typeof com.binary.framework.auth.User! ");
			user = (User)obj;
		}
		return user;
	}





	@Override
	public void setUser(User user) {
		HttpSession session = request.getSession(true);
		session.setAttribute(SessionKey.SYSTEM_USER, user);
	}
	
	
		
}
