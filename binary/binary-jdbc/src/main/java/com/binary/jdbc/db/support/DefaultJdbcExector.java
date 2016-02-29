package com.binary.jdbc.db.support;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.binary.core.bean.BMProxy;
import com.binary.core.exception.MultipleException;
import com.binary.core.lang.Conver;
import com.binary.core.util.BinaryUtils;
import com.binary.jdbc.adapter.JdbcAdapter;
import com.binary.jdbc.db.JdbcExectorListener;
import com.binary.jdbc.db.ResultSetSolve;
import com.binary.jdbc.exception.JdbcDBException;
import com.binary.jdbc.print.Printer;

public class DefaultJdbcExector extends AbstractJdbcExector {
	
	
	public DefaultJdbcExector(Printer printer) {
		super(printer);
	}
	
	
	public DefaultJdbcExector(Printer printer, JdbcExectorListener listener) {
		super(printer, listener);
	}
	
		
	
	public List<Map<String,Object>> queryList(JdbcAdapter adapter, Connection conn, String sql, Object[] params, ResultSetSolve[] rsSolves) {
		return queryList(adapter, conn, sql, params, null, rsSolves, false);
	}
	
		
	
	public <T> List<T> queryList(JdbcAdapter adapter, Connection conn, String sql, Object[] params, Class<T> mapping, ResultSetSolve[] rsSolves) {
		return queryList(adapter, conn, sql, params, mapping, rsSolves, false);
	}
	
	
		
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T> List<T> queryList(JdbcAdapter adapter, Connection conn, String sql, Object[] params, Class<T> mapping, ResultSetSolve[] rsSolves, boolean firest) {
		printSql(sql, params, adapter);
		PreparedStatement ps = null;
		ResultSet rs = null;
		List result = new ArrayList();
		MultipleException me = null;
		try {
			ps = prepareQueryStatement(adapter, conn, sql, params);
			rs = executeQuery(adapter, ps);
			if(rsSolves==null || rsSolves.length==0) {
				rsSolves = parseResultSetSolve(rs, adapter);
			}
			
			BMProxy<?> proxy = getRecordBMProxy(mapping);
			Object[] rsparams = new Object[1];
			
			while(rs!=null && rs.next()) {
				Object record = proxy.newInstance();
				for(int i=0; i<rsSolves.length; i++) {
					ResultSetSolve solve = rsSolves[i];
					rsparams[0] = i+1;
					proxy.set(solve.getPropertyName(), solve.getRsMethod().invoke(rs, rsparams));
				}
				result.add(record);
				if(firest) break;
			}
		}catch(Exception e) {
			me = MultipleException.appendException(me, e);
		}finally {
			try {
				adapter.closeResultSet(rs);
			}catch(Exception e) {
				me = MultipleException.appendException(me, e);
			}
			try {
				adapter.closePreparedStatement(ps);
			}catch(Exception e) {
				me = MultipleException.appendException(me, e);
			}
		}
		if(me!=null && me.size()>0) throw new JdbcDBException(me);
		return result;
	}
	
	
	public Map<String,Object> queryFirst(JdbcAdapter adapter, Connection conn, String sql, Object[] params, ResultSetSolve[] rsSolves) {
		return queryFirst(adapter, conn, sql, params, null, rsSolves);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> T queryFirst(JdbcAdapter adapter, Connection conn, String sql, Object[] params, Class<T> mapping, ResultSetSolve[] rsSolves) {
		List list = queryList(adapter, conn, sql, params, mapping, rsSolves, true);
		return (T)(list.size()>0 ? list.get(0) : null);
	}
		
	
	
	@SuppressWarnings("unchecked")
	public <T> T queryObject(JdbcAdapter adapter, Connection conn, String sql, Object[] params, Class<T> mapping) {
		printSql(sql, params, adapter);
		PreparedStatement ps = null;
		ResultSet rs = null;
		T result = null;
		MultipleException me = null;
		try {
			ps = prepareQueryStatement(adapter, conn, sql, params);
			rs = executeQuery(adapter, ps);
			while(rs!=null && rs.next()) {
				Method m = adapter.getJavaRSMethod(mapping);
				Object v = m.invoke(rs, new Object[]{1});
				if(v!=null && !mapping.isAssignableFrom(v.getClass())) {
					v = Conver.to(v, mapping);
				}
				result = (T)v;
				break;
			}
		}catch(Exception e) {
			me = MultipleException.appendException(me, e);
		}finally {
			try {
				adapter.closeResultSet(rs);
			}catch(Exception e) {
				me = MultipleException.appendException(me, e);
			}
			try {
				adapter.closePreparedStatement(ps);
			}catch(Exception e) {
				me = MultipleException.appendException(me, e);
			}
		}
		if(me!=null && me.size()>0) throw new JdbcDBException(me);
		return result;
	}
	
	
	
	
	public int update(JdbcAdapter adapter, Connection conn, String sql, Object[] params) {
		printSql(sql, params, adapter);
		PreparedStatement ps = null;
		int rows = 0;
		try {
			ps = prepareUpdateStatement(adapter, conn, sql, params);
			rows = ps.executeUpdate();
		}catch(Exception e) {
			throw BinaryUtils.transException(e, JdbcDBException.class);
		}finally {
			adapter.closePreparedStatement(ps);
		}
		return rows;
	}
	
	
	
	public int[] updateBatch(JdbcAdapter adapter, Connection conn, String sql, List<Object[]> paramsList) {
		printSql(sql, paramsList, adapter);
		PreparedStatement ps = null;
		int[] rows = null;
		try {
			ps = prepareUpdateStatementBatch(adapter, conn, sql, paramsList);
			rows = ps.executeBatch();
		}catch(Exception e) {
			throw BinaryUtils.transException(e, JdbcDBException.class);
		}finally {
			adapter.closePreparedStatement(ps);
		}
		return rows;
	}
	
	
	
	
}
