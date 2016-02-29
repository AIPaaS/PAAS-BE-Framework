package com.binary.framework;

import com.binary.framework.critical.CriticalObject;


/**
 * Local环境监听器
 * @author wanwb
 */
public interface LocalListener {
	
	
	
	/**
	 * 环境开启事件
	 * @param criticalObject
	 */
	public void open(CriticalObject criticalObject);
	
	
	
	
	/**
	 * 环境事物提交事件
	 */
	public void commit();
	
	
	
	
	/**
	 * 环境事物回滚事件
	 */
	public void rollback();
	
	
	
	
	/**
	 * 环境事物关闭事件
	 */
	public void close();
	
	
	
	
}
