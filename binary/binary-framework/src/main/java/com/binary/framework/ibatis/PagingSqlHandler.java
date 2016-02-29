package com.binary.framework.ibatis;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.sql.DataSource;

import com.binary.framework.exception.IBatisException;
import com.binary.jdbc.JdbcFactory;
import com.binary.jdbc.JdbcOperator;
import com.binary.jdbc.JdbcOperatorFactory;
import com.binary.jdbc.JdbcType;
import com.binary.jdbc.Page;
import com.binary.jdbc.adapter.SqlParser;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback;
import com.ibatis.sqlmap.engine.scope.StatementScope;



/**
 *<p>Title: 对SQL分页处理</p>
 *@author wanwb
 */
public class PagingSqlHandler implements SqlHandler {
	
	
	private static class LocalObject {
		private Page<?> page;
		private DataSource dataSource;
		private boolean appendCount;
		public LocalObject(Page<?> page, DataSource dataSource, boolean appendCount) {
			this.page = page;
			this.dataSource = dataSource;
			this.appendCount = appendCount;
		}
		public Page<?> getPage() {
			return page;
		}
		public DataSource getDataSource() {
			return dataSource;
		}
		public boolean isAppendCount() {
			return appendCount;
		}
	}


	/**
	 * PageBean中属性定义：
	 * 		pageNum或pageSize为null表示不执行分页查询
	 * 		totalRows=-1表示不查询总行数
	 */
	private static final ThreadLocal<LocalObject> threadLocal = new ThreadLocal<LocalObject>();

	

	public static void setLocalObject(Page<?> page, DataSource dataSource, boolean appendCount) {
		threadLocal.set(new LocalObject(page, dataSource, appendCount));
	}

	private static LocalObject getLocalObject() {
		return threadLocal.get();
	}
	public static Page<?> getPage() {
		LocalObject localobj = threadLocal.get();
		return localobj!=null ? localobj.getPage() : null;
	}
	public static DataSource getDataSource() {
		LocalObject localobj = threadLocal.get();
		return localobj!=null ? localobj.getDataSource() : null;
	}
	
	public static void removeLocalObject() {
		threadLocal.remove();
	}



	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String parseQuerySql(StatementScope statementScope, Connection conn,
			String sql, Object[] parameters, int skipResults, int maxResults,
			RowHandlerCallback callback, IBatisSqlExecutor sqlExecutor) throws SQLException {
		LocalObject localObj = getLocalObject();
		if(localObj != null) {
			Page<?> page = localObj.getPage();
			DataSource ds = localObj.getDataSource();
			removeLocalObject();
			
			long pageNum = page.getPageNum();
			long pageSize = page.getPageSize();
			if(pageSize <= 0) throw new IBatisException(" the pageSize is wrong '"+pageSize+"'! ");
			if(pageNum < 1) pageNum = 1;
			
			JdbcOperatorFactory factory = JdbcOperatorFactory.getMomentFactory();
			JdbcOperator operator = null;
			if(ds instanceof com.binary.jdbc.ds.DataSource) {
				String dsname = ((com.binary.jdbc.ds.DataSource)ds).getName();
				operator = factory.getJdbcOperator(dsname);
			}else {
				operator = factory.getJdbcOperator();
			}
			
			JdbcType jdbcType = operator.getTransaction().getJdbcType();
			SqlParser sqlparser = JdbcFactory.getSqlParser(jdbcType);
						
			if(localObj.isAppendCount()) {
				long totalRows = queryCount(statementScope, conn, sql, parameters, SqlExecutor.NO_SKIPPED_RESULTS, SqlExecutor.NO_MAXIMUM_RESULTS, callback, sqlExecutor, operator, sqlparser);
				page.setTotalRows(totalRows);
				
				long totalPages = totalRows%pageSize==0 ? totalRows/pageSize:totalRows/pageSize+1;
				page.setTotalPages(totalPages);
				
				if(pageNum > totalPages) pageNum = totalPages;
				if(pageNum == 0) pageNum = 1;
				page.setPageNum(pageNum);
			}else {
				page.setTotalPages(-1);
				page.setTotalRows(-1);
			}
			
			long totalRows = page.getTotalRows();
			
			if(totalRows==-1 || totalRows>0) {
//				String tmpsql = sql.toLowerCase();
//				int index = tmpsql.indexOf(" order ");
//				if(index < 0) throw new IBatisException(" is not found 'order by' subsql! ");
				
				String[] arr = sql.split("(?i)[\\s]+order[\\s]+by[\\s]+");
				if(arr.length < 2) throw new IBatisException(" is not found 'order by' subsql! ");
				if(arr.length > 2) throw new IBatisException(" has multiple 'order by' subsql! ");
				
//				String selsql = sql.substring(0, index);
//				String ordersql = sql.substring(index+5).trim();
//				if(!ordersql.substring(0, 2).equalsIgnoreCase("by")) throw new IBatisException(" is not found 'order by' subsql! ");
				
//				ordersql = ordersql.substring(2);
				
				String selsql = arr[0];
				String ordersql = arr[1];
				
				sql = sqlparser.parsePagingSql(selsql, ordersql, pageNum, pageSize);
			}else {
				page.setTotalPages(0);
				page.setData(new ArrayList());
				sql = null;
			}
		}

		return sql;
	}
	


	private long queryCount(StatementScope statementScope, Connection conn,
								String sql, Object[] params, int skipResults, int maxResults,
								RowHandlerCallback callback, IBatisSqlExecutor sqlExecutor, JdbcOperator operator, SqlParser sqlparser) throws SQLException {
		Iterator<SqlHandler> iterator = sqlExecutor.getSqlHandlerIterator();
		while(iterator.hasNext()) {
			SqlHandler handler = iterator.next();
			sql = handler.parseQuerySql(statementScope, conn, sql, params, skipResults, maxResults, callback, sqlExecutor);
		}

		if(sql == null) throw new SQLException(" is NULL sql by query-count! ");
		sql = sqlparser.parseCountSql(sql);
		
		return operator.selectCount(sql, params);
	}

	
	
	
	
	public String parseUpdateSql(StatementScope statementScope,
			Connection conn, String sql, Object[] parameters,
			IBatisSqlExecutor sqlExecutor) throws SQLException {
		return sql;
	}


}
