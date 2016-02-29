package com.binary.framework.web;

import com.binary.framework.bean.User;

public interface UserCreator {
	
	
	
	
	/**
	 * 跟据登录代码查询用户对象
	 * @param loginCode
	 * @return
	 */
	public User createUser(String loginCode);
	
	
	
	
}
