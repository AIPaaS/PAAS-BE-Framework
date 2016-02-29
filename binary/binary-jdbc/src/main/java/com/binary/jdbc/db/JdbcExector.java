package com.binary.jdbc.db;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.binary.jdbc.adapter.JdbcAdapter;


/**
 * Jdbc操作
 */
public interface JdbcExector {
	
	
	
	/**
	 * 查询记录数
	 * @param adapter: jdbc适配器
	 * @param conn: 数据库连接
	 * @param sql: select count sql
	 * @return 记录数
	 */
	public long queryCount(JdbcAdapter adapter, Connection conn, String sql);
	
	
	
	
	/**
	 * 查询记录数
	 * @param adapter: jdbc适配器
	 * @param conn: 数据库连接
	 * @param sql: select count sql
	 * @param params: 对应SQL中?参数
	 * @return 记录数
	 */
	public long queryCount(JdbcAdapter adapter, Connection conn, String sql, Object[] params);
	
	
	
	
	/**
	 * 基本查询
	 * @param adapter: jdbc适配器
	 * @param conn: 数据库连接
	 * @param sql: 查询SQL
	 * @return
	 */
	public List<Map<String,Object>> queryList(JdbcAdapter adapter, Connection conn, String sql);
	
	
	
	/**
	 * 基本查询
	 * @param adapter: jdbc适配器
	 * @param conn: 数据库连接
	 * @param sql: 查询SQL
	 * @param params: 对应SQL中?参数
	 * @return
	 */
	public List<Map<String,Object>> queryList(JdbcAdapter adapter, Connection conn, String sql, Object[] params);
	
	
	
	/**
	 * 基本查询
	 * @param adapter: jdbc适配器
	 * @param conn: 数据库连接
	 * @param sql: 查询SQL
	 * @param params: 对应SQL中?参数
	 * @param rsSolves: RS说明
	 * @return
	 */
	public List<Map<String,Object>> queryList(JdbcAdapter adapter, Connection conn, String sql, Object[] params, ResultSetSolve[] rsSolves);
	
	
	
	/**
	 * 映射基本查询
	 * @param adapter: jdbc适配器
	 * @param conn: 数据库连接
	 * @param sql: 查询SQL
	 * @param mapping: 映射对象
	 * @return
	 */
	public <T> List<T> queryList(JdbcAdapter adapter, Connection conn, String sql, Class<T> mapping);
	
	
	
	/**
	 * 映射基本查询
	 * @param adapter: jdbc适配器
	 * @param conn: 数据库连接
	 * @param sql: 查询SQL
	 * @param params: 对应SQL中?参数
	 * @param mapping: 映射对象
	 * @return
	 */
	public <T> List<T> queryList(JdbcAdapter adapter, Connection conn, String sql, Object[] params, Class<T> mapping);
	
	
	
	
	/**
	 * 映射基本查询
	 * @param adapter: jdbc适配器
	 * @param conn: 数据库连接
	 * @param sql: 查询SQL
	 * @param params: 对应SQL中?参数
	 * @param mapping: 映射对象
	 * @param rsSolves: RS说明
	 * @return
	 */
	public <T> List<T> queryList(JdbcAdapter adapter, Connection conn, String sql, Object[] params, Class<T> mapping, ResultSetSolve[] rsSolves);
	
	
	
	
	/**
	 * 基本查询第一条记录
	 * @param adapter: jdbc适配器
	 * @param conn: 数据库连接
	 * @param sql: 查询SQL
	 * @return
	 */
	public Map<String,Object> queryFirst(JdbcAdapter adapter, Connection conn, String sql);
	
	
	
	/**
	 * 基本查询第一条记录
	 * @param adapter: jdbc适配器
	 * @param conn: 数据库连接
	 * @param sql: 查询SQL
	 * @param params: 对应SQL中?参数
	 * @return
	 */
	public Map<String,Object> queryFirst(JdbcAdapter adapter, Connection conn, String sql, Object[] params);
	
	
	
	/**
	 * 基本查询第一条记录
	 * @param adapter: jdbc适配器
	 * @param conn: 数据库连接
	 * @param sql: 查询SQL
	 * @param params: 对应SQL中?参数
	 * @param rsSolves: RS说明
	 * @return
	 */
	public Map<String,Object> queryFirst(JdbcAdapter adapter, Connection conn, String sql, Object[] params, ResultSetSolve[] rsSolves);
	
	
	/**
	 * 映射基本查询第一条记录
	 * @param adapter: jdbc适配器
	 * @param conn: 数据库连接
	 * @param sql: 查询SQL
	 * @param mapping: 映射对象
	 * @return
	 */
	public <T> T queryFirst(JdbcAdapter adapter, Connection conn, String sql, Class<T> mapping);
	
	
	
	/**
	 * 映射基本查询第一条记录
	 * @param adapter: jdbc适配器
	 * @param conn: 数据库连接
	 * @param sql: 查询SQL
	 * @param params: 对应SQL中?参数
	 * @param mapping: 映射对象
	 * @return
	 */
	public <T> T queryFirst(JdbcAdapter adapter, Connection conn, String sql, Object[] params, Class<T> mapping);
	
	
	
	
	/**
	 * 映射基本查询第一条记录
	 * @param adapter: jdbc适配器
	 * @param conn: 数据库连接
	 * @param sql: 查询SQL
	 * @param params: 对应SQL中?参数
	 * @param mapping: 映射对象
	 * @param rsSolves: RS说明
	 * @return
	 */
	public <T> T queryFirst(JdbcAdapter adapter, Connection conn, String sql, Object[] params, Class<T> mapping, ResultSetSolve[] rsSolves);
	
	
	
	/**
	 * 查询第一条记录第一例
	 * @param adapter: jdbc适配器
	 * @param conn: 数据库连接
	 * @param sql: 查询SQL
	 * @param mapping: 映射对象
	 * @return
	 */
	public <T> T queryObject(JdbcAdapter adapter, Connection conn, String sql, Class<T> mapping);
	
	
	
	/**
	 * 查询第一条记录第一例
	 * @param adapter: jdbc适配器
	 * @param conn: 数据库连接
	 * @param sql: 查询SQL
	 * @param params: 对应SQL中?参数
	 * @param mapping: 映射对象
	 * @return
	 */
	public <T> T queryObject(JdbcAdapter adapter, Connection conn, String sql, Object[] params, Class<T> mapping);
	
	
	
	
	/**
	 * 增、删、改操作
	 * @param adapter: jdbc适配器
	 * @param conn: 数据库连接
	 * @param sql: 查询SQL
	 * @return 相应行 数
	 */
	public int update(JdbcAdapter adapter, Connection conn, String sql);
	
	
	
	
	/**
	 * 增、删、改操作
	 * @param adapter: jdbc适配器
	 * @param conn: 数据库连接
	 * @param sql: 查询SQL
	 * @param params: 对应SQL中?参数
	 * @return 相应行 数
	 */
	public int update(JdbcAdapter adapter, Connection conn, String sql, Object[] params);
	
	
	
	
	
	/**
	 * 批量增、删、改操作
	 * @param adapter: jdbc适配器
	 * @param conn: 数据库连接
	 * @param sql: 查询SQL
	 * @param paramsList: 对应SQL中?参数
	 * @return 相应行 数
	 */
	public int[] updateBatch(JdbcAdapter adapter, Connection conn, String sql, List<Object[]> paramsList);
	
	
	
	
	
	
}






