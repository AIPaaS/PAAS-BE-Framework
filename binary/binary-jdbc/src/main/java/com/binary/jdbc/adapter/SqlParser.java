package com.binary.jdbc.adapter;

import com.binary.jdbc.JdbcType;



/**
 * 组装分页SQL
 */
public interface SqlParser {
	
	
	
	/**
	 * 获取所对应的JdbcType
	 * @return
	 */
	public JdbcType getJdbcType();
	
	
	
	/**
	 * 根据SQL解析select count SQL
	 * @param sql: 查询SQL
	 * @return select count SQL
	 */
	public String parseCountSql(String sql);
	
	
	
	/**
	 * 根据SQL解析select count SQL
	 * @param sql: 不做过多分析, 只在外包一层select count SQL
	 * @return
	 */
	public String parseSimpleCountSql(String sql);
	
	
	
	
	/**
	 * 提取SQL中的字段名
	 * @param sql: Select SQL
	 * @return
	 */
	public String[] parseSqlFields(String sql);
	
	
	
	
	/**
	 * 根据查询SQL解析分页SQL
	 * @param sql: 查询SQL
	 * @param orderByFields: 排序字段, 多个以逗号分隔
	 * @param pageNum: 页码
	 * @param pageSize: 页大小
	 * @return 分页SQL
	 */
	public String parsePagingSql(String sql, String orderByFields, long pageNum, long pageSize);
	
	
	
	
	/**
	 * 获取分页SQL
	 * @param dissolver: SQL容解器
	 * @param orderByFields: 排序字段, 多个以逗号分隔
	 * @param pageNum: 页码
	 * @param pageSize: 页大小
	 * @return
	 */
	public String parsePagingSql(SqlDissolver dissolver, String orderByFields, long pageNum, long pageSize);
	
	
	
	
	
	
}



