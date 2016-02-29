package com.binary.jdbc.ds;

import java.util.Map;

import javax.sql.DataSource;

public interface DataSourceFactory {
	
	
	
	/**
	 * 跟据配置信息创建数据源
	 * @param dataSourceClass
	 * @param properties
	 * @return
	 */
	public DataSource getDataSource(Class<?> dataSourceClass, Map<String,String> properties);
	
	
}
