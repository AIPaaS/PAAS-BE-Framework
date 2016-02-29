package com.binary.jdbc.db;

import java.sql.Connection;
import java.util.List;

import com.binary.jdbc.adapter.JdbcAdapter;



/**
 * SQL执行时监听器
 */
public interface JdbcExectorListener {
	
		
	
	/**
	 * 获取查询PrepareStatement时事件
	 * @param adapter: 数据库适配器
	 * @param conn: 数据库连接
	 * @param sql: 查询SQL
	 * @param params: 查询参数
	 * @return params
	 */
	public Object[] beforePrepareQueryStatement(JdbcAdapter adapter, Connection conn, String sql, Object[] params);
	
	
	
	
	/**
	 * 获取更新PrepareStatement时事件
	 * @param adapter: 数据库适配器
	 * @param conn: 数据库连接
	 * @param sql: 查询SQL
	 * @param params: 查询参数
	 * @return params
	 */
	public Object[] beforePrepareUpdateStatement(JdbcAdapter adapter, Connection conn, String sql, Object[] params);
	
	
	
	
	/**
	 * 获取批量更新PrepareStatement时事件
	 * @param adapter: 数据库适配器
	 * @param conn: 数据库连接
	 * @param sql: 查询SQL
	 * @param params: 查询参数
	 * @return paramsList
	 */
	public List<Object[]> beforePrepareUpdateStatementBatch(JdbcAdapter adapter, Connection conn, String sql, List<Object[]> paramsList);
	
	
	
	
}


