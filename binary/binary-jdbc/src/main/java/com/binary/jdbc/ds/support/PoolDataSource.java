package com.binary.jdbc.ds.support;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import com.binary.core.lang.ClassUtils;
import com.binary.jdbc.JdbcType;
import com.binary.jdbc.ds.DataSourceFactory;
import com.binary.jdbc.exception.DataSourceException;

public class PoolDataSource extends AbstractDataSource {
	private static final long serialVersionUID = -3247230948402344564L;
	
	
	private DataSource ds;
	
	
	
	public PoolDataSource(String name, JdbcType jdbcType, String poolClass, Map<String,String> properties) {
		this(name, jdbcType, poolClass, properties, null);
	}
	public PoolDataSource(String name, JdbcType jdbcType, String poolClass, Map<String,String> properties, DataSourceFactory factory) {
		super(name, jdbcType);
		if(factory == null) factory = new DefaultDataSourceFactory();
		this.ds = factory.getDataSource(ClassUtils.forName(poolClass), properties);
	}
	
	
	
	
	
	protected Connection newConnection() {
		try {
			if(ds == null) throw new DataSourceException("DataSource:'"+getName()+"' initialized is failed!!!");
			return ds.getConnection();
		} catch (SQLException e) {
			throw new DataSourceException(e);
		}
	}
	
	
	
}
