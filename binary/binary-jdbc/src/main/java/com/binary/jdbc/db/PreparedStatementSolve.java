package com.binary.jdbc.db;

import java.lang.reflect.Method;

import com.binary.jdbc.adapter.DBType;
import com.binary.jdbc.exception.JdbcException;

public class PreparedStatementSolve {	
	
	private Object parameter;
	private DBType dbType;
	private Method psMethod;
	
	
	public PreparedStatementSolve(Object parameter, DBType dbType, Method psMethod) {
		if(dbType == null) throw new JdbcException(" the dbType is NULL argument! ");
		if(psMethod == null) throw new JdbcException(" the psMethod is NULL argument! ");
		
		this.parameter = parameter;
		this.dbType = dbType;
		this.psMethod = psMethod;
	}


	public Object getParameter() {
		return parameter;
	}


	public DBType getDbType() {
		return dbType;
	}


	public Method getPsMethod() {
		return psMethod;
	}
	
	
	
	
}
