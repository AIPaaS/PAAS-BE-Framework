package com.binary.jdbc.ds.support;

import java.sql.Connection;

import com.binary.jdbc.JdbcType;
import com.binary.jdbc.ds.DataSource;
import com.binary.jdbc.ds.TransactionIsolation;
import com.binary.jdbc.exception.DataSourceException;
import com.binary.jdbc.print.Printer;
import com.binary.jdbc.print.PrinterFactory;
import com.binary.jdbc.print.PrinterType;
import com.binary.jdbc.util.DataSourceUtils;

public abstract class AbstractDataSource implements DataSource {
	private static final long serialVersionUID = 1522859427069775969L;
	
	
	/**
	 * 数据源名称
	 */
	private String name;
	
	/**
	 * 是否只读
	 */
	private Boolean readOnly;
	
	/**
	 * 事物级别
	 */
	private TransactionIsolation transactionIsolation;
	
	
	/**
	 * 数据库类型
	 */
	private JdbcType jdbcType;
	
	
	
	protected AbstractDataSource(String name, JdbcType jdbcType) {
		if(name==null || (name=name.trim()).length()==0) throw new DataSourceException(" the datasource-name is empty argument! ");
		if(jdbcType==null) throw new DataSourceException(" the datasource:'"+name+"'.jdbcType is empty argument! ");
		
		this.name = name;
		this.jdbcType = jdbcType;
	}
	
	
	
	@Override
	public Connection getConnection() {
		return getConnection(null, null);
	}
	

	
	@Override
	public Connection getConnection(Boolean readOnly, TransactionIsolation transactionIsolation) {
		if(readOnly == null) readOnly = this.readOnly;
		if(transactionIsolation == null) transactionIsolation = this.transactionIsolation;
		Connection conn = newConnection();
		DataSourceUtils.setConnectionIsolation(getName(), conn, readOnly, transactionIsolation);
		return conn;
	}
	
	
	protected abstract Connection newConnection();
	
	

	
	@Override
	public void validate(Printer printer) {
		if(printer==null) printer = PrinterFactory.getPrinter(PrinterType.NONE, null);
		try {
			Connection conn = null;
			try {
				conn = newConnection();
				if(conn != null) {
					printer.printInfo("DataSource:'"+getName()+"' initialized is successful!!! ");
				}else {
					printer.printInfo("DataSource:'"+getName()+"' initialized is failed!!! create connection is NULL! ");
				}
			}finally {
				if(conn != null) conn.close();
			}
		}catch(Exception e) {
			printer.printInfo("DataSource:'"+getName()+"' initialized is failed!!!\n"+e.toString());
		}
	}
	
	

	
	@Override
	public String getName() {
		return name;
	}


	
	@Override
	public Boolean getReadOnly() {
		return readOnly;
	}


	
	@Override
	public TransactionIsolation getTransactionIsolation() {
		return transactionIsolation;
	}


	
	@Override
	public JdbcType getJdbcType() {
		return jdbcType;
	}



	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}



	public void setTransactionIsolation(TransactionIsolation transactionIsolation) {
		this.transactionIsolation = transactionIsolation;
	}
	
	
	
	
	
	
	
	
	
	
	
}


