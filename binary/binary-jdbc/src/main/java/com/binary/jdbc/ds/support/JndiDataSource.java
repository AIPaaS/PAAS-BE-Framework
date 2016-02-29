package com.binary.jdbc.ds.support;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.binary.core.util.BinaryUtils;
import com.binary.jdbc.JdbcType;
import com.binary.jdbc.exception.DataSourceException;

public class JndiDataSource extends AbstractDataSource {
	private static final long serialVersionUID = -6444534172315108672L;
	
	
	private String jndiName;
	private DataSource ds;
	
	public JndiDataSource(String name, JdbcType jdbcType) {
		super(name, jdbcType);
	}
	
	
	
	
	
	protected Connection newConnection() {
		if(this.ds == null) throw new DataSourceException(" datasource-jndi:'"+this.jndiName+"' init error! ");
		try {
			if(ds == null) throw new DataSourceException("DataSource:'"+getName()+"' initialized is failed!!!");
			return this.ds.getConnection();
		} catch (SQLException e) {
			throw new DataSourceException(e);
		}
	}





	public String getJndiName() {
		return jndiName;
	}





	public void setJndiName(String jndiName) {
		if(jndiName==null || (jndiName=jndiName.trim()).length()==0) throw new DataSourceException(" the jndiname is empty argument! ");
		try {
			Context initCtx = new InitialContext();
			try {
				ds = (DataSource) initCtx.lookup(jndiName);
			}catch(NamingException e1) {
				try {
					ds = (DataSource) initCtx.lookup("java:"+jndiName);
				}catch(NamingException e2) {
					Context env = (Context) initCtx.lookup("java:comp/env");
					ds = (DataSource) env.lookup(jndiName);
				}
			}
		}catch(Exception e) {
			throw BinaryUtils.transException(e, DataSourceException.class, " datasource-jndi:'"+jndiName+"' lookup error! ");
		}
		this.jndiName = jndiName;
	}





	public DataSource getDs() {
		return ds;
	}





	public void setDs(DataSource ds) {
		this.ds = ds;
	}
	
	
	
	
	
	
	
}
