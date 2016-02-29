package com.binary.jdbc.db;

import java.lang.reflect.Method;

import com.binary.jdbc.exception.JdbcException;

public class ResultSetSolve {
	
	private String propertyName;
	private String columnName;
	private Method rsMethod;
	
	
	public ResultSetSolve(String propertyName, String columnName, Method rsMethod) {
		if(propertyName==null || propertyName.length()==0) throw new JdbcException(" the propertyName is NULL argument! ");
		if(columnName==null || columnName.length()==0) throw new JdbcException(" the columnName is NULL argument! ");
		if(rsMethod == null) throw new JdbcException(" the rsMethod is NULL argument! ");
		
		this.propertyName = propertyName;
		this.columnName = columnName;
		this.rsMethod = rsMethod;
	}

	
	
	
	public String getColumnName() {
		return columnName;
	}


	
	public Method getRsMethod() {
		return rsMethod;
	}




	public String getPropertyName() {
		return propertyName;
	}
	
	
	
	
	
	
	
}


