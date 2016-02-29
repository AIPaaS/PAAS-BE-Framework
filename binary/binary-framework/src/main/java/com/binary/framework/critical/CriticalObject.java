package com.binary.framework.critical;

import com.binary.framework.bean.User;


public interface CriticalObject {
	
	
	/**
	 * 获取当前登录用户对象
	 * @return
	 */
	public User getUser();	
	
	
	
	/**
	 * 设置登录用户对象
	 */
	public void setUser(User user);
	
	
	
	/**
	 * 获取上下文环境根路径
	 * @return
	 */
	public String getContextPath();
	
	
	
	
	
}



