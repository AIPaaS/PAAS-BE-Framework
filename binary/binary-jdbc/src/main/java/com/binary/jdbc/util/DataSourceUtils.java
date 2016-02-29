package com.binary.jdbc.util;

import java.sql.Connection;
import java.sql.SQLException;

import com.binary.jdbc.ds.TransactionIsolation;
import com.binary.jdbc.exception.DataSourceException;

public class DataSourceUtils {
	
	
	
	
	public static void setConnectionIsolation(String dsName, Connection conn, Boolean readOnly, TransactionIsolation transactionIsolation) {
		if(readOnly != null) {
			try {
				conn.setReadOnly(readOnly);
			}catch(SQLException e) {
				throw new DataSourceException("datasource:'"+dsName+"' set Connection:'"+conn+"'-ReadOnly:'"+readOnly+"' error! ", e);
			}
		}else {
			setConnectionAutoCommit(dsName, conn, false);
			
		}
		
		if(transactionIsolation != null) {
			try {
				conn.setTransactionIsolation(transactionIsolation.getValue());
			}catch(SQLException e) {
				throw new DataSourceException("datasource:'"+dsName+"' set Connection:'"+conn+"'-TransactionIsolation:'"+transactionIsolation+"' error! ", e);
			}
		}
	}
	
	
	
	
	
	public static void setConnectionAutoCommit(String dsName, Connection conn, boolean autoCommit) {
		try {
			if(conn.getAutoCommit() != autoCommit) {
				conn.setAutoCommit(autoCommit);
			}
		} catch (SQLException e) {
			throw new DataSourceException("datasource:'"+dsName+"' set Connection:'"+conn+"'-AutoCommit:'"+autoCommit+"' error! ", e);
		}
	}
	
	
	
	
	
	
	public static void closeConnection(String dsName, Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			}catch (SQLException e) {
				throw new DataSourceException(" Connection:'"+conn+"' close error! ", e);
			}
		}
	}
	
	
	
	
	
	
}
