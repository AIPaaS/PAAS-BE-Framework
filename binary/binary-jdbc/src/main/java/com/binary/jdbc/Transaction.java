package com.binary.jdbc;

import java.sql.Connection;

public interface Transaction {
	


	/**
	 * 获取当前事物所对应的数据库类型
	 * @return
	 */
	public JdbcType getJdbcType();
	
	

	/**
	 * 判断当前事物是否做了更新操作
	 * @return
	 */
	public boolean isUpdated();

	
	
	
	/**
	 * 设置当前事物更新状态
	 */
	public void updated();
	
	


	/**
	 * 获取当前事物所控制的连接
	 * @return
	 */
	public Connection getConnection();
	
	
	/**
	 * 提交
	 */
	public void commit();
	
	
	
	
	/**
	 * 回滚
	 */
	public void rollback();
	
	
	
//	/**
//	 * 是否完成
//	 * @return
//	 */
//	public boolean isCompleted();
	
	
	
	
	
}
