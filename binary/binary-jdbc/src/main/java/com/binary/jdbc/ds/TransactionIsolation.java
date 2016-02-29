package com.binary.jdbc.ds;

import java.sql.Connection;

import com.binary.jdbc.exception.DataSourceException;

public enum TransactionIsolation {
	
	
	NONE,
	
	READ_COMMITTED,
	
	READ_UNCOMMITTED,
	
	REPEATABLE_READ,
	
	SERIALIZABLE;
	
	
	
	public int getValue() {
		switch(this) {
			case NONE: return Connection.TRANSACTION_NONE;
			case READ_COMMITTED: return Connection.TRANSACTION_READ_COMMITTED;
			case READ_UNCOMMITTED: return Connection.TRANSACTION_READ_UNCOMMITTED;
			case REPEATABLE_READ: return Connection.TRANSACTION_REPEATABLE_READ;
			case SERIALIZABLE: return Connection.TRANSACTION_SERIALIZABLE;
			default: return -1;
		}
	}
	
	
	public static TransactionIsolation valueOf(int v) {
		switch(v) {
			case Connection.TRANSACTION_NONE: return NONE;
			case Connection.TRANSACTION_READ_COMMITTED: return READ_COMMITTED;
			case Connection.TRANSACTION_READ_UNCOMMITTED: return READ_UNCOMMITTED;
			case Connection.TRANSACTION_REPEATABLE_READ: return REPEATABLE_READ;
			case Connection.TRANSACTION_SERIALIZABLE: return SERIALIZABLE;
			default: throw new DataSourceException(" is wrong TransactionIsolation-value:'"+v+"'! ");
		}
	}
	
	
	
}


