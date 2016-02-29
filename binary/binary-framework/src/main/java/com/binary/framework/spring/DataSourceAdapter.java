package com.binary.framework.spring;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.binary.core.util.BinaryUtils;
import com.binary.framework.exception.FrameworkException;
import com.binary.jdbc.JdbcOperatorFactory;
import com.binary.jdbc.JdbcType;
import com.binary.jdbc.ds.TransactionIsolation;
import com.binary.jdbc.print.Printer;


public class DataSourceAdapter implements DataSource, com.binary.jdbc.ds.DataSource {
	private static final long serialVersionUID = 1L;
	
	private PrintWriter writer;
	private int loginTimeout;
	
	private JdbcOperatorFactory factory;
	private String dataSourceName;
	
	
	public DataSourceAdapter(JdbcOperatorFactory jdbcOperatorFactory) {
		this(null, jdbcOperatorFactory);
	}
	public DataSourceAdapter(String dataSourceName, JdbcOperatorFactory jdbcOperatorFactory) {
		BinaryUtils.checkEmpty(jdbcOperatorFactory, "jdbcOperatorFactory");
		this.factory = jdbcOperatorFactory;
		this.dataSourceName = dataSourceName;
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
		throw new FrameworkException(" the datasource-adapter is not support method 'getParentLogger()'! ");
	}

	
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new FrameworkException(" the datasource-adapter is not support method 'unwrap(Class<T> iface)'! ");
	}

	
	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new FrameworkException(" the datasource-adapter is not support method 'isWrapperFor(Class<?> iface)'! ");
	}

	
	
	@Override
	public Connection getConnection() {
		return this.factory.getJdbcOperator(this.dataSourceName).getTransaction().getConnection();
	}
	
	

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		throw new FrameworkException(" the datasource-adapter is not support method 'getConnection(String username, String password)'! ");
	}
	
	
	private com.binary.jdbc.ds.DataSource getBinaryDataSource() {
		com.binary.jdbc.ds.DataSource ds = this.factory.getDataSourceManager().getDataSource(this.dataSourceName);
		if(ds == null) throw new FrameworkException(" is not found dataSource '"+this.dataSourceName+"'! ");
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
		getBinaryDataSource().validate(printer);
	}
	
	
	@Override
	public String getName() {
		return getBinaryDataSource().getName();
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

	
	
	
	
}
