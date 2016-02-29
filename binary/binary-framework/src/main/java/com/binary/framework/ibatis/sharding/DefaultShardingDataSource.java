package com.binary.framework.ibatis.sharding;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.binary.core.util.BinaryUtils;
import com.binary.core.util.SecurityMap;
import com.binary.framework.exception.FrameworkException;
import com.binary.framework.ibatis.ShardingDataSource;
import com.binary.framework.ibatis.ShardingRule;
import com.binary.jdbc.JdbcOperatorFactory;
import com.binary.jdbc.JdbcType;
import com.binary.jdbc.ds.DataSourceManager;
import com.binary.jdbc.ds.TransactionIsolation;
import com.binary.jdbc.print.Printer;


public class DefaultShardingDataSource implements ShardingDataSource {
	private static final long serialVersionUID = 1L;
	
	
	private static class ShardingParameter {
		String statementName;
		Object parameterObject;
		ShardingParameter(String statementName, Object parameterObject) {
			this.statementName = statementName;
			this.parameterObject = parameterObject;
		}
	}
	
	
	private String name;
	
	private PrintWriter writer;
	private int loginTimeout;
	
	private JdbcOperatorFactory factory;
	private ShardingRule shardingRule;
	
	//key=自定义key	value=数据源名称
	private Map<String, String> shardingStore;
	
	private final ThreadLocal<ShardingParameter> shardingLocalParameter = new ThreadLocal<ShardingParameter>();
	
	
	
	public DefaultShardingDataSource(JdbcOperatorFactory jdbcOperatorFactory, ShardingRule shardingRule, Map<String, String> shardingStore) {
		BinaryUtils.checkEmpty(jdbcOperatorFactory, "jdbcOperatorFactory");
		BinaryUtils.checkEmpty(shardingRule, "shardingRule");
		BinaryUtils.checkEmpty(shardingStore, "shardingStore");
		
		this.factory = jdbcOperatorFactory;
		this.shardingRule = shardingRule;
		
		DataSourceManager dsmgr = jdbcOperatorFactory.getDataSourceManager();
		this.shardingStore = new HashMap<String, String>();
		
		Iterator<Entry<String, String>> itor = shardingStore.entrySet().iterator();
		while(itor.hasNext()) {
			Entry<String, String> e = itor.next();
			String k = e.getKey();
			String v = e.getValue();
			
			if(k==null || (k=k.trim().toUpperCase()).length()==0) {
				throw new FrameworkException(" has empty sharding key! ");
			}
			if(v==null || (v=v.trim()).length()==0) {
				throw new FrameworkException(" has empty sharding dataSource! ");
			}
			
			if(this.shardingStore.containsKey(k)) {
				throw new FrameworkException(" repeated sharding key '"+k+"'! ");
			}
			if(!dsmgr.containsName(v)) {
				throw new FrameworkException(" not found sharding dataSource '"+v+"'! ");
			}
			
			this.shardingStore.put(k, v);
		}
	}
	
	
	
	@Override
	public void setShardingParameter(String statementName, Object parameterObject) {
		BinaryUtils.checkEmpty(statementName, "statementName");
		BinaryUtils.checkEmpty(parameterObject, "parameterObject");
		this.shardingLocalParameter.set(new ShardingParameter(statementName, parameterObject));
	}
	
	
	
	@Override
	public void removeShardingParameter() {
		this.shardingLocalParameter.remove();
	}
	
	
	
	
	
	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return this.writer;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		this.writer = out;
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		this.loginTimeout = seconds;
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return this.loginTimeout;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new FrameworkException(" not support method 'getParentLogger()'! ");
	}

	
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new FrameworkException(" not support method 'unwrap(Class<T> iface)'! ");
	}

	
	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new FrameworkException(" not support method 'isWrapperFor(Class<?> iface)'! ");
	}

	
	
	@Override
	public Connection getConnection() {
		String dsName = getShardingDataSourceName();
		return this.factory.getJdbcOperator(dsName).getTransaction().getConnection();
	}
	
	

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		throw new FrameworkException(" is not support method 'getConnection(String username, String password)'! ");
	}
	
	
	
	private ShardingParameter getShardingParameter() {
		ShardingParameter sp = shardingLocalParameter.get();
		if(sp == null) throw new FrameworkException(" not setting shardingParameter! ");
		return sp;
	}
	
	
	
	@Override
	public String getShardingDataSourceName() {
		ShardingParameter sp = getShardingParameter();
		String dsKey = this.shardingRule.getDataSourceKey(sp.statementName, sp.parameterObject);
		//if(BinaryUtils.isEmpty(dsKey)) throw new FrameworkException(" the shardingRule '"+this.shardingRule.getClass().getName()+"' return dataSourceKey is empty! ");
		if(!BinaryUtils.isEmpty(dsKey)) {
			String dsName = this.shardingStore.get(dsKey.trim().toUpperCase());
			if(dsName == null) throw new FrameworkException(" the shardingRule '"+this.shardingRule.getClass().getName()+"' return dataSourceKey '"+dsKey+"' not found! ");
			return dsName;
		}
		return null;
	}
	
	
	@Override
	public String getShardingTableName() {
		ShardingParameter sp = getShardingParameter();
		String tableName = this.shardingRule.getTableKey(sp.statementName, sp.parameterObject);
		if(tableName == null) throw new FrameworkException(" the shardingRule '"+this.shardingRule.getClass().getName()+"' return tableKey is null! ");
		return tableName;
	}
	
	
	private com.binary.jdbc.ds.DataSource getBinaryDataSource() {
		String dsName = getShardingDataSourceName();
		if(BinaryUtils.isEmpty(dsName)) {
			return this.factory.getDataSourceManager().getDefaultDataSource();
		}
		
		com.binary.jdbc.ds.DataSource ds = this.factory.getDataSourceManager().getDataSource(dsName);
		if(ds == null) throw new FrameworkException(" is not found dataSource '"+dsName+"'! ");
		return ds;
	}
	
	
	@Override
	public Connection getConnection(Boolean readOnly, TransactionIsolation transactionIsolation) {
		Connection conn = getConnection();
		try {
			if(readOnly!=null) conn.setReadOnly(readOnly);
			if(transactionIsolation!=null) conn.setTransactionIsolation(transactionIsolation.getValue());
		} catch (SQLException e) {
			throw new FrameworkException(e);
		}
		return conn;
	}
	
	
	@Override
	public void validate(Printer printer) {
	}
	
	
	@Override
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Override
	public Boolean getReadOnly() {
		return getBinaryDataSource().getReadOnly();
	}
	
	
	@Override
	public TransactionIsolation getTransactionIsolation() {
		return getBinaryDataSource().getTransactionIsolation();
	}
	
	
	@Override
	public JdbcType getJdbcType() {
		return getBinaryDataSource().getJdbcType();
	}
	
	
	
	@Override
	public Map<String, String> getShardingStore() {
		return new SecurityMap<String, String>(this.shardingStore);
	}



	@Override
	public ShardingRule getShardingRule() {
		return shardingRule;
	}
	
	

	
	
	

}
