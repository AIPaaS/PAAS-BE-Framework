package com.binary.framework.ibatis;

import java.util.Map;

import javax.sql.DataSource;



/**
 * 分片数据源
 * @author wanwb
 */
public interface ShardingDataSource extends DataSource, com.binary.jdbc.ds.DataSource {
	

	
	
	
	/**
	 * 设置分片参数
	 * @param statementName IBatis-getSqlMapClientTemplate执行时的statementName
	 * @param parameterObject IBatis-getSqlMapClientTemplate执行时的参数
	 */
	public void setShardingParameter(String statementName, Object parameterObject);
	
	
	
	
	/**
	 * 清除分片参数
	 */
	public void removeShardingParameter();
	
	
	
	
	
	/**
	 * 获取分片数据源名称, 执行此方法之前必须设置@setShardingParameter
	 * @return 可能存在返回空, 返回空表示取系统默认数据源
	 */
	public String getShardingDataSourceName();
	
	
	
	
	
	/**
	 * 获取分片数据源表名称, 执行此方法之前必须设置@setShardingParameter
	 * @return
	 */
	public String getShardingTableName();
	
	
	
	
	
	/**
	 * 获取分片定义存储器
	 * @return
	 */
	public Map<String, String> getShardingStore();
	
	
	
	
	/**
	 * 获取分片规则对象
	 * @return
	 */
	public ShardingRule getShardingRule();
	
	
}
