package com.binary.jdbc.adapter;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.binary.jdbc.JdbcType;



/**
 * Jdbc各数据库适配器
 */
public interface JdbcAdapter {
	
	
	/**
	 * 获取所对应的JdbcType
	 * @return
	 */
	public JdbcType getJdbcType();
	
	
	
	/**
	 * 获取SQL函数对象
	 * @return
	 */
	public SqlFunction getSqlFunction();
	
	
	
	/**
	 * 获取SQL解析器
	 * @return
	 */
	public SqlParser getSqlParser();
	
	
	
	/** ResultSet-Java Mapping *************************************************************************************************/
	
	/**
	 * 根据数据库字段类型获取ResultSet读方法
	 * @param dbFieldType: 数据库字段类型
	 * @return
	 */
	public Method getRSMethod(String dbFieldType);
	
	
	
	
	/**
	 * 获取java读取ResultSet方法
	 * @param javaFieldType: java字段类型
	 * @return
	 */
	public Method getJavaRSMethod(Class<?> javaFieldType);
	
	
	
	
	
	
	
	
	/** PreparedStatement ***********************************************************************************/
	/**
	 * 获取查询操作PreparedStatement
	 * @param conn: 数据库连接
	 * @param sql: 执行的SQL
	 * @return
	 */
	public PreparedStatement prepareQueryStatement(Connection conn, String sql);
	
	
	/**
	 * 获取更新操作PreparedStatement
	 * @param conn: 数据库连接
	 * @param sql: 执行的SQL
	 * @return
	 */
	public PreparedStatement prepareUpdateStatement(Connection conn, String sql) ;
	
	
	/**
	 * 对PreparedStatement添加参数
	 * @param ps
	 * @param params
	 */
	public void setPreparedStatementParams(PreparedStatement ps, Object[] params);
	
	
	/**
	 * PreparedStatement查询
	 * @param ps
	 * @return
	 */
	public ResultSet executeQuery(PreparedStatement ps);
	
	
	/**
	 * 执行更新
	 * @param ps
	 * @return
	 */
	public int executeUpdate(PreparedStatement ps);
	
	
	/**
	 * 批量操作
	 * @param ps
	 * @return
	 */
	public int[] executeBatch(PreparedStatement ps);
	
	
	
	/**
	 * 关闭PreparedStatement
	 * @param ps
	 */
	public void closePreparedStatement(PreparedStatement ps);
	
	
	
	/**
	 * 半闭ResultSet
	 * @param rs
	 */
	public void closeResultSet(ResultSet rs);
		
	
	
	
	
	
}








