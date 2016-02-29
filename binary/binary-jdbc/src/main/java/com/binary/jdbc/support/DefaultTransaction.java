package com.binary.jdbc.support;

import java.sql.Connection;
import java.sql.SQLException;

import com.binary.jdbc.JdbcType;
import com.binary.jdbc.Transaction;
import com.binary.jdbc.exception.TransactionException;

public class DefaultTransaction implements Transaction {
	
	
	private JdbcType jdbcType;
	private Connection connection;
	
	private boolean uped;
	//private boolean completed = false;
	
	
	
	public DefaultTransaction(JdbcType jdbcType, Connection connection) {
		this.jdbcType = jdbcType;
		this.connection = connection;
	}



	@Override
	public JdbcType getJdbcType() {
		return jdbcType;
	}



	@Override
	public Connection getConnection() {
		return connection;
	}



	@Override
	public boolean isUpdated() {
		return this.uped;
	}



	@Override
	public void updated() {
		this.uped = true;
	}
	

	@Override
	public void commit() {
		try {
			//if(!this.completed) {
				this.connection.commit();
				//this.completed = true;
			//}
		} catch (SQLException e) {
			throw new TransactionException("Connection:'"+this.connection+"' commit error! ", e);
		}
	}
	
	

	@Override
	public void rollback() {
		try {
			//if(!this.completed) {
				this.connection.rollback();
				//this.completed = true;
			//}
		} catch (SQLException e) {
			throw new TransactionException("Connection:'"+this.connection+"' rollback error! ", e);
		}
	}
	
	

//	@Override
//	public boolean isCompleted() {
//		return this.completed;
//	}
	
	
	
	
	
	
	
	
}
