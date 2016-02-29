package com.binary.framework.bean;

import java.io.Serializable;

public interface User extends Serializable {
	
	
	/**
	 * 获取原对象
	 * @param ownerType
	 * @return
	 */
	public <T extends User> T owner(Class<T> ownerType);
	
	
	
	/**
	 * 获取主键ID
	 * @return
	 */
	public Long getId();
	
	
	
	/**
	 * 获取用户ID
	 * @return
	 */
	public String getUserId();
	
	
	
	/**
	 * 获取用户代码
	 * @return
	 */
	public String getUserCode();
	
	
	
	/**
	 * 获取用户姓名
	 * @return
	 */
	public String getUserName();
	
	
	
	/**
	 * 获取登录代码
	 * @return
	 */
	public String getLoginCode();
	
	
	
	/**
	 * 获取认证识别码
	 * @return
	 */
	public String getAuthCode();
	
	
}
