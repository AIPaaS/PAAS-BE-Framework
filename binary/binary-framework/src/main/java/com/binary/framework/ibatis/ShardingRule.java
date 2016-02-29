package com.binary.framework.ibatis;

public interface ShardingRule {
	
	
	
	
	
	
	/**
	 * 获取分片数据源在本ShardingDataSource所定义的键名
	 * @param statementName IBatis-getSqlMapClientTemplate执行时的statementName
	 * @param parameterObject IBatis-getSqlMapClientTemplate执行时的参数
	 * @return 返回ShardingDataSource所定义的键名, 如果为空则系统采用默认数据源
	 */
	public String getDataSourceKey(String statementName, Object parameterObject);
	
	
	
	
	
	
	/**
	 * 获取分片表名(或表名的后缀)
	 * @param statementName IBatis-getSqlMapClientTemplate执行时的statementName
	 * @param parameterObject IBatis-getSqlMapClientTemplate执行时的参数
	 * @return
	 */
	public String getTableKey(String statementName, Object parameterObject);
	
	
	
	
	
	
}
