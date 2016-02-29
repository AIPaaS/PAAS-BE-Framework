package com.binary.framework.ibatis;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.binary.core.util.SecurityIterator;
import com.binary.jdbc.JdbcOperator;
import com.binary.jdbc.JdbcOperatorFactory;
import com.binary.jdbc.adapter.JdbcAdapter;
import com.binary.jdbc.print.Printer;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback;
import com.ibatis.sqlmap.engine.scope.StatementScope;



/**
 *<p>Title: 扩展的SqlExecutor</p>
 *@author wanwb
 */
public class IBatisSqlExecutor extends SqlExecutor {
	
	private SqlExecutor sqlExecutor;
	
	private final Object syncobj = new Object();
	
	/** SqlHandler容器 **/
	private List<SqlHandler> sqlHandlers = new ArrayList<SqlHandler>();
	
	
	

	public SqlExecutor getSqlExecutor() {
		return sqlExecutor;
	}


	public void setSqlExecutor(SqlExecutor sqlExecutor) {
		this.sqlExecutor = sqlExecutor;
	}
	
	
	/**
	 * 添加SqlHandler
	 * @param handler
	 */
	public void addSqlHandler(SqlHandler handler) {
		sqlHandlers.add(handler);
	}
	
	
	/**
	 * 删除指定SqlHandler
	 * @param handler
	 */
	public void removeSqlHandler(SqlHandler handler) {
		synchronized(syncobj) {
			sqlHandlers.remove(handler);
		}
	}
	
	
	/**
	 * 清除所有SqlHandler
	 */
	public void clearSqlHandler() {
		synchronized(syncobj) {
			sqlHandlers.clear();
		}
	}
	
	
	
	/**
	 * 重新设置SqlHandler
	 * @param handlers
	 */
	public void setSqlHandlers(List<SqlHandler> handlers) {
		synchronized (syncobj) {
			clearSqlHandler();
			this.sqlHandlers.addAll(handlers);
		}
	}
	
	
	
	/**
	 * 获取SqlHandler容器中所有SqlHandler迭代器
	 * @return
	 */
	public Iterator<SqlHandler> getSqlHandlerIterator() {
		return new SecurityIterator<SqlHandler>(this.sqlHandlers.iterator());
	}
	
	
	
	private void printSql(String sql, Object[] params, Connection conn) {
		JdbcOperator operator = JdbcOperatorFactory.getMomentFactory().appraisalJdbcOperator(conn);
		if(operator == null) return ;
		
		Printer printer = operator.getPrinter();
		JdbcAdapter adapter = operator.getJdbcAdapter();
		printer.printSql(sql, params, adapter);
	}
	
	
	
	@Override
	public void executeQuery(StatementScope statementScope, Connection conn, String sql, Object[] parameters, int skipResults, int maxResults, RowHandlerCallback callback) throws SQLException {
		if(sqlHandlers.size() > 0) {
			Iterator<SqlHandler> iteraotr = this.sqlHandlers.iterator();
			while(iteraotr.hasNext()) {
				SqlHandler handler = iteraotr.next();
				sql = handler.parseQuerySql(statementScope, conn, sql, parameters, skipResults, maxResults, callback, this);
				if(sql == null) break;
			}
		}
		
		/** 如果SQL为null终于执行 **/
		if(sql != null) {
			if (sqlExecutor==null) {
				printSql(sql, parameters, conn);
				super.executeQuery(statementScope, conn, sql, parameters, skipResults, maxResults, callback);
			} else {
				sqlExecutor.executeQuery(statementScope, conn, sql, parameters, skipResults, maxResults, callback);
			}
		}
	}


	
	@Override
	public int executeUpdate(StatementScope statementScope, Connection conn,
			String sql, Object[] parameters) throws SQLException {
		if(sqlHandlers.size() > 0) {
			Iterator<SqlHandler> iteraotr = this.sqlHandlers.iterator();
			while(iteraotr.hasNext()) {
				SqlHandler handler = iteraotr.next();
				sql = handler.parseUpdateSql(statementScope, conn, sql, parameters, this);
				if(sql == null) break;
			}
		}
		
		if(sql != null) {
			if (sqlExecutor==null) {
				printSql(sql, parameters, conn);
				return super.executeUpdate(statementScope, conn, sql, parameters);
			} else {
				return sqlExecutor.executeUpdate(statementScope, conn, sql, parameters);
			}
		}
		return 0;
	}


	
	@Override
	public void addBatch(StatementScope statementScope, Connection conn, String sql, Object[] parameters) throws SQLException {
		if(sqlHandlers.size() > 0) {
			Iterator<SqlHandler> iteraotr = this.sqlHandlers.iterator();
			while(iteraotr.hasNext()) {
				SqlHandler handler = iteraotr.next();
				sql = handler.parseUpdateSql(statementScope, conn, sql, parameters, this);
				if(sql == null) break;
			}
		}
		
		if(sql != null) {
			if (sqlExecutor==null) {
				printSql(sql, parameters, conn);
				super.addBatch(statementScope, conn, sql, parameters);
			} else {
				sqlExecutor.addBatch(statementScope, conn, sql, parameters);
			}
		}
		
	}
	
	
	
	
}
