package com.binary.sso.server.session;

import java.util.Enumeration;

public interface Session  {

	
	/**
	 * 获取SessionID
	 * @return
	 */
	public String getId();
	
	
	
	/**
	 * 获取参数
	 * @param name
	 * @return
	 */
	public Object getAttribute(String name);
	
	
	
	/**
	 * 设置键值参数
	 * @param name
	 * @param value
	 */
	public void setAttribute(String name, Object value);

	
	
	/**
	 * 获取所有参数名称
	 * @return
	 */
	public Enumeration<String> getAttributeNames();

	
	
	/**
	 * 删除参数
	 * @param name
	 */
	public void removeAttribute(String name);

	
	
	
	/**
	 * 注销Session
	 */
	public void invalidate();

	
	
	
	
	/**
	 * 判断当前Session是否是新Session
	 * @return
	 */
	public boolean isNew();
	
	
	
	
	
}
