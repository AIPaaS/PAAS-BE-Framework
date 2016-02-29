package com.binary.jdbc.ds;

import java.util.Iterator;

public interface DataSourceManager {
	
	
	/**
	 * 设置缺省数据源
	 * @param defaultDataSourceName
	 */
	public void setDefaultDataSource(String defaultDataSourceName);
	
	
	/**
	 * 获取缺省数据源
	 * @return
	 */
	public DataSource getDefaultDataSource();
	
	
	
	/**
	 * 判断数据源是否是缺省数据源
	 * @param ds
	 * @return
	 */
	public boolean isDefaultDataSource(DataSource ds);
	
	
	
	/**
	 * 判断数据源名称是否是缺省数据源
	 * @param dsname
	 * @return
	 */
	public boolean isDefaultDataSource(String dsname);
	
	
	
	/**
	 * 添加数据源
	 * @param ds
	 */
	public void addDataSource(DataSource ds);
	
	
	
	
	/**
	 * 删除数据源
	 * @param dsname
	 * @return
	 */
	public DataSource removeDataSource(String dsname);
	
	
	
	
	/**
	 * 根据数据源名称判断数据源是否存在
	 * @param dsname
	 * @return
	 */
	public boolean containsName(String dsname);
	
	
	
	
	/**
	 * 根据数据源名称获取数据源
	 * @param dsname
	 * @return
	 */
	public DataSource getDataSource(String dsname);
	
	
	
	
	/**
	 * 迭代数据源
	 * @return
	 */
	public Iterator<DataSource> getDataSourceIterator();
	
	
	
	
	
}


