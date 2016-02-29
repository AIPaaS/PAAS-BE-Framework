package com.binary.jdbc.util;

import java.sql.Connection;
import java.util.ArrayList;

import com.binary.core.bean.BMProxy;
import com.binary.core.lang.StringMap;
import com.binary.jdbc.Page;
import com.binary.jdbc.adapter.JdbcAdapter;
import com.binary.jdbc.db.JdbcExector;
import com.binary.jdbc.exception.JdbcException;


public class SqlUtils {
	
	
	
	/**
	 * 解析带${}参数SQL
	 * @param sql
	 * @return
	 */
	public static StringMap parseParameterSQL(String sql) {
		String[] subs = sql.split("[$][\\s]*[{]");
		String[] array = new String[subs.length-1];
		sql = "";
		for(int i=0; i<subs.length; i++) {
			if(i == 0) {
				sql = subs[0];
				continue ;
			}
			if(subs[i].indexOf("}") < 0) throw new JdbcException(" is not fond ${ paired sign:'}'!");
			array[i-1] = subs[i].substring(0,subs[i].indexOf("}")).trim();
			sql += ("?" + subs[i].substring(subs[i].indexOf("}")+1));
		}
		return new StringMap(sql, array);
	}
	
	
	
	/**
	 * 解析带${}参数SQL, 并连带解析参数
	 * @param sql
	 * @param paramObj
	 * @return
	 */
	public static StringMap parseParameterSQL(String sql, Object paramObj) {
		if(sql.indexOf('$') < 0) return new StringMap(sql, null);
		StringMap sm = parseParameterSQL(sql);
		String[] keys = sm.getKeys();
		Object[] params = parseParameters(paramObj, keys);
		sm.setValues(params);
		return sm;
	}
	
	
	
	/**
	 * 解析参数
	 * @param v
	 * @param keys
	 * @return
	 */
	public static Object[] parseParameters(Object v, String[] keys) {
		BMProxy<?> proxy = null;
		if(v != null) {
//			if(!Types.isBean(v.getClass())) {
//				throw new PreglacialException(" is not support param-bean:'"+v.getClass().getName()+"'! ");
//			}
			proxy = BMProxy.getInstance(v);
		}
		return parseParameters(proxy, keys);
	}
	
	
	/**
	 * 解析参数
	 * @param v
	 * @param keys
	 * @return
	 */
	public static Object[] parseParameters(BMProxy<?> proxy, String[] keys) {
		if(keys==null) return null;
		Object[] params = new Object[keys.length];
		if(keys.length == 0) return params;
		
		for(int i=0; i<keys.length; i++) {
			String k = keys[i];
			if(proxy!=null && proxy.containsKey(k)) {
				params[i] = proxy.get(k);
			}
		}
		
		return params;
	}
	
	
	
	/**
	 * 获取select count SQL
	 * @param querySql: 查询SQL, 注意: querySql不可以带order by
	 * @return
	 */
	public static String parseCountSql(String querySql, JdbcAdapter adapter) {
		return adapter.getSqlParser().parseSimpleCountSql(querySql);
	}
	
	
	
	
	public static <T> Page<T> parsePage(JdbcExector je, JdbcAdapter jdbcAdapter, Connection conn,
			long pageNum, long pageSize, String sql, Object[] params, boolean ignoreCount) {
		if(pageNum <= 0) pageNum = 1;
		if(pageSize <= 0) throw new JdbcException(" is wrong pageSize:'"+pageSize+"'! ");
		long totalRows = -1;
		long totalPages = -1;
		
		if(!ignoreCount) {
			String countSql = parseCountSql(sql, jdbcAdapter);
			totalRows = je.queryCount(jdbcAdapter, conn, countSql, params);
			totalPages = totalRows%pageSize==0 ? totalRows/pageSize : totalRows/pageSize+1;
			if(pageNum > totalPages) pageNum = totalPages;
			if(pageNum == 0) pageNum = 1;
			if(totalRows == 0) {
				return new Page<T>(pageNum, pageSize, totalRows, totalPages, new ArrayList<T>());
			}
		}
		return new Page<T>(pageNum, pageSize, totalRows, totalPages, null);
	}
	
}


