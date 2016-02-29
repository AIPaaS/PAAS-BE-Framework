package com.binary.framework.critical.support;

import java.io.File;
import java.io.IOException;

import com.binary.framework.bean.User;
import com.binary.framework.exception.AuthException;
import com.binary.framework.exception.CriticalException;
import com.binary.framework.util.FrameworkProperties;
import com.binary.framework.web.UserCreator;


public class LocalCriticalObject extends AbstractCriticalObject {
	
	
	private String loginCode;
	private User user;
	
	
	public LocalCriticalObject(String loginCode) {
		this.loginCode = loginCode;
	}
	public LocalCriticalObject(User user) {
		this.user = user;
	}
	
	
	
	@Override
	public synchronized User getUser() {
		if(this.user == null) {
			user = buildUser();
		}
		return this.user;
	}
	
	
	@Override
	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
	protected User buildUser() {
		Object ucobj = FrameworkProperties.getInstance().get("UserCreator");
		if(ucobj != null) {
			if(!(ucobj instanceof UserCreator)) {
				throw new AuthException(" the user-creator '"+ucobj.getClass()+"' is not typeof 'com.binary.framework.web.UserCreator'! ");
			}
			
			UserCreator uc = (UserCreator)ucobj;
			return uc.createUser(this.loginCode);
		}
		return null;
	}
	
	
	
	
	@Override
	public String getContextPath() {
		try {
			return "/"+new File("./").getCanonicalFile().getName();
		} catch (IOException e) {
			throw new CriticalException(e);
		}
	}
	
	
	
	
}



