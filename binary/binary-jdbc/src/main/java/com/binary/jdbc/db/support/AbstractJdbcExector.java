package com.binary.jdbc.db.support;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.binary.core.bean.BMProxy;
import com.binary.core.lang.Types;
import com.binary.jdbc.adapter.JdbcAdapter;
import com.binary.jdbc.db.JdbcExector;
import com.binary.jdbc.db.JdbcExectorListener;
import com.binary.jdbc.db.ResultSetSolve;
import com.binary.jdbc.exception.JdbcDBException;
import com.binary.jdbc.print.Printer;

public abstract class AbstractJdbcExector implements JdbcExector {
	
	
	private Printer printer;
	private JdbcExectorListener listener;
	
	
	
	public AbstractJdbcExector(Printer printer) {
		this(printer, null);
	}
	
	public AbstractJdbcExector(Printer printer, JdbcExectorListener listener) {
		this.printer = printer;
		this.listener = listener;
	}
	
	
		
	protected void printSql(String sql, Object[] params, JdbcAdapter adapter) {
		if(this.printer != null) {
			this.printer.printSql(sql, params, adapter);
		}
	}
	
	
	protected void printSql(String sql, List<Object[]> paramsList, JdbcAdapter adapter) {
		if(this.printer != null) {
			this.printer.printSql(sql, paramsList, adapter);
		}
	}
	
	
	
	protected PreparedStatement prepareQueryStatement(JdbcAdapter adapter, Connection conn, String sql, Object[] params) {
		if(this.listener != null) {
			params = this.listener.beforePrepareQueryStatement(adapter, conn, sql, params);
		}
		PreparedStatement ps = adapter.prepareQueryStatement(conn, sql);
		if(params!=null && params.length>0) adapter.setPreparedStatementParams(ps, params);
		return ps;
	}
	
	
	protected PreparedStatement prepareUpdateStatement(JdbcAdapter adapter, Connection conn, String sql, Object[] params) {
		if(this.listener != null) {
			params = this.listener.beforePrepareUpdateStatement(adapter, conn, sql, params);
		}
		PreparedStatement ps = adapter.prepareUpdateStatement(conn, sql);
		if(params!=null && params.length>0) adapter.setPreparedStatementParams(ps, params);
		return ps;
	}
	
	
	protected PreparedStatement prepareUpdateStatementBatch(JdbcAdapter adapter, Connection conn, String sql, List<Object[]> paramsList) {
		if(this.listener != null) {
			paramsList = this.listener.beforePrepareUpdateStatementBatch(adapter, conn, sql, paramsList);
		}
		PreparedStatement ps = adapter.prepareUpdateStatement(conn, sql);
		if(paramsList!=null && paramsList.size()>0) {
			for(int i=0; i<paramsList.size(); i++) {
				Object params = paramsList.get(i);
				if(!(params instanceof Object[])) {
					throw new JdbcDBException(" DB executeUpdateBatch paramsList's Elements is not Object[]:'"+(params==null?"null":params.getClass().getCanonicalName())+"'! ");
				}
				adapter.setPreparedStatementParams(ps, (Object[])params);
				try {
					ps.addBatch();
				} catch (SQLException e) {
					throw new JdbcDBException(" DB executeUpdateBatch addBatch failured! ", e);
				}
			}
		}
		return ps;
	}
	
	
	protected ResultSetSolve[] parseResultSetSolve(ResultSet rs, JdbcAdapter adapter) {
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			ResultSetSolve[] solves = new ResultSetSolve[count];
			for(int i=0; i<count; i++) {
				String columnName = rsmd.getColumnName(i+1).toUpperCase();
				String typeName = rsmd.getColumnTypeName(i+1);
				Method rsMethod = adapter.getRSMethod(typeName);
				solves[i] = new ResultSetSolve(columnName, columnName, rsMethod);
			}
			return solves;
		}catch(SQLException e) {
			throw new JdbcDBException(e);
		}
	}
	
	protected BMProxy<?> getRecordBMProxy(Class<?> mapping) {
		BMProxy<?> proxy = null;
		if(mapping != null) {
			if(!Types.isBean(mapping)) throw new JdbcDBException(" the mapping:'"+mapping.getName()+"' is not bean! ");
			proxy = BMProxy.getInstance(mapping);
		}else {
			proxy = BMProxy.getInstance(Map.class);
		}
		return proxy;
	}
	
	
	
	protected ResultSet executeQuery(JdbcAdapter adapter, PreparedStatement ps) {
		return adapter.executeQuery(ps);
	}
	
	
	
	
	public long queryCount(JdbcAdapter adapter, Connection conn, String sql) {
		return queryCount(adapter, conn, sql, null);
	}
	
	
	public long queryCount(JdbcAdapter adapter, Connection conn, String sql, Object[] params) {
		return queryObject(adapter, conn, sql, params, Long.class);
	}
	
	
	public List<Map<String,Object>> queryList(JdbcAdapter adapter, Connection conn, String sql)  {
		return queryList(adapter, conn, sql, (Object[])null, (ResultSetSolve[])null);
	}
	
	public List<Map<String,Object>> queryList(JdbcAdapter adapter, Connection conn, String sql, Object[] params) {
		return queryList(adapter, conn, sql, params, (ResultSetSolve[])null);
	}
	
	
	public <T> List<T> queryList(JdbcAdapter adapter, Connection conn, String sql, Class<T> mapping) {
		return queryList(adapter, conn, sql, null, mapping, null);
	}
	
	
	public <T> List<T> queryList(JdbcAdapter adapter, Connection conn, String sql, Object[] params, Class<T> mapping) {
		return queryList(adapter, conn, sql, params, mapping, null);
	}
	
	
	public Map<String,Object> queryFirst(JdbcAdapter adapter, Connection conn, String sql) {
		return queryFirst(adapter, conn, sql, (Object[])null, (ResultSetSolve[])null);
	}
	
	
	public Map<String,Object> queryFirst(JdbcAdapter adapter, Connection conn, String sql, Object[] params) {
		return queryFirst(adapter, conn, sql, params, (ResultSetSolve[])null);
	}
	
	
	public <T> T queryFirst(JdbcAdapter adapter, Connection conn, String sql, Class<T> mapping) {
		return queryFirst(adapter, conn, sql, null, mapping, null);
	}
	
	public <T> T queryFirst(JdbcAdapter adapter, Connection conn, String sql, Object[] params, Class<T> mapping) {
		return queryFirst(adapter, conn, sql, params, mapping, null);
	}
	
	
	public <T> T queryObject(JdbcAdapter adapter, Connection conn, String sql, Class<T> mapping) {
		return queryObject(adapter, conn, sql, null, mapping);
	}
	
	
	
	public int update(JdbcAdapter adapter, Connection conn, String sql) {
		return update(adapter, conn, sql, null);
	}
	
	
	
	
	
}
