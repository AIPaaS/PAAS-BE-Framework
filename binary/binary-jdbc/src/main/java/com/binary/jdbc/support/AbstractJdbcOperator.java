package com.binary.jdbc.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.binary.core.bean.BMProxy;
import com.binary.core.lang.StringMap;
import com.binary.core.util.BinaryUtils;
import com.binary.jdbc.JdbcFactory;
import com.binary.jdbc.JdbcOperator;
import com.binary.jdbc.Page;
import com.binary.jdbc.Transaction;
import com.binary.jdbc.adapter.JdbcAdapter;
import com.binary.jdbc.adapter.SqlParser;
import com.binary.jdbc.db.JdbcExector;
import com.binary.jdbc.exception.JdbcException;
import com.binary.jdbc.print.Printer;
import com.binary.jdbc.util.SqlUtils;



public abstract class AbstractJdbcOperator implements JdbcOperator {

	
	private Transaction transaction;
	private Printer printer;
	private JdbcAdapter jdbcAdapter;
	private JdbcExector jdbcExector;
	
	
	
	public AbstractJdbcOperator(Transaction transaction, Printer printer) {
		BinaryUtils.checkNull(transaction, "transaction");
		BinaryUtils.checkNull(printer, "printer");
		this.transaction = transaction;
		this.printer = printer;
		this.jdbcAdapter = JdbcFactory.getJdbcAdapter(transaction.getJdbcType());
		this.jdbcExector = JdbcFactory.getJdbcExector(printer);
	}
	
	
	
	

	@Override
	public Transaction getTransaction() {
		return transaction;
	}




	@Override
	public Printer getPrinter() {
		return printer;
	}
	
	
	@Override
	public JdbcAdapter getJdbcAdapter() {
		return this.jdbcAdapter;
	}




	@Override
	public List<Map<String,Object>> executeQuery(String sql) {
		return executeQuery(sql, (Object[])null);
	}
	
	

	@Override
	public <T> List<T> executeQuery(String sql, Class<T> mapping) {
		return executeQuery(sql, (Object[])null, mapping);
	}
	
	
	

	@Override
	public Page<Map<String,Object>> executeQuery(long pageNum, long pageSize, String sql, String orderByFields) {
		return executeQuery(pageNum, pageSize, sql, orderByFields, false);
	}
	
	

	@Override
	public Page<Map<String,Object>> executeQuery(long pageNum, long pageSize, String sql, String orderByFields, Object[] params) {
		return executeQuery(pageNum, pageSize, sql, orderByFields, params, false);
	}
	
	

	@Override
	public Page<Map<String,Object>> executeQuery(long pageNum, long pageSize, String sql, String orderByFields, Object paramObj) {
		return executeQuery(pageNum, pageSize, sql, orderByFields, paramObj, false);
	}
	
	

	@Override
	public Page<Map<String,Object>> executeQuery(long pageNum, long pageSize, String sql, String orderByFields, boolean ignoreCount) {
		return executeQuery(pageNum, pageSize, sql, orderByFields, (Object[])null, ignoreCount);
	}
	
	

	@Override
	public <T> Page<T> executeQuery(long pageNum, long pageSize, String sql, String orderByFields, Class<T> mapping) {
		return executeQuery(pageNum, pageSize, sql, orderByFields, mapping, false);
	}
	
	

	@Override
	public <T> Page<T> executeQuery(long pageNum, long pageSize, String sql, String orderByFields, Object[] params, Class<T> mapping) {
		return executeQuery(pageNum, pageSize, sql, orderByFields, params, mapping, false);
	}
	

	@Override
	public <T> Page<T> executeQuery(long pageNum, long pageSize, String sql, String orderByFields, Object paramObj, Class<T> mapping) {
		return executeQuery(pageNum, pageSize, sql, orderByFields, paramObj, mapping, false);
	}
	

	@Override
	public <T> Page<T> executeQuery(long pageNum, long pageSize, String sql, String orderByFields, Class<T> mapping, boolean ignoreCount) {
		return executeQuery(pageNum, pageSize, sql, orderByFields, (Object[])null, mapping, ignoreCount);
	}
	
	
	
	
	

	@Override
	public int executeUpdate(String sql) {
		return executeUpdate(sql, (Object[])null);
	}

		
	

	@Override
	public long selectCount(String sql) {
		return selectCount(sql, (Object[])null);
	}	

	

	@Override
	public Map<String,Object> selectFirst(String sql) {
		return selectFirst(sql, (Object[])null);
	}
	
	
	

	@Override
	public <T> T selectFirst(String sql, Class<T> mapping) {
		return selectFirst(sql, (Object[])null, mapping);
	}
	
	
	
	
	
	
	
	
	/** 最终执行方法 ***********************************************************************************************************/
	@Override
	public List<Map<String,Object>> executeQuery(String sql, Object paramObj) {
		StringMap sm = SqlUtils.parseParameterSQL(sql, paramObj);
		return executeQuery(sm.getString(), sm.getValues());
	}

	@Override
	public List<Map<String, Object>> executeQuery(String sql, Object[] params) {
		return this.jdbcExector.queryList(this.jdbcAdapter, this.transaction.getConnection(), sql, params);
	}
	

	@Override
	public <T> List<T> executeQuery(String sql, Object paramObj, Class<T> mapping) {
		StringMap sm = SqlUtils.parseParameterSQL(sql, paramObj);
		return executeQuery(sm.getString(), sm.getValues(), mapping);
	}

	@Override
	public <T> List<T> executeQuery(String sql, Object[] params, Class<T> mapping) {
		return this.jdbcExector.queryList(this.jdbcAdapter, this.transaction.getConnection(), sql, params, mapping);
	}

	@Override
	public Page<Map<String,Object>> executeQuery(long pageNum, long pageSize, String sql, String orderByFields, Object paramObj, boolean ignoreCount) {
		StringMap sm = SqlUtils.parseParameterSQL(sql, paramObj);
		return executeQuery(pageNum, pageSize, sm.getString(), orderByFields, sm.getValues(), ignoreCount);
	}

	@Override
	public Page<Map<String,Object>> executeQuery(long pageNum, long pageSize, String sql, String orderByFields, Object[] params, boolean ignoreCount) {
		return executeQuery(pageNum, pageSize, sql, orderByFields, params, null, ignoreCount);
	}
	

	@Override
	public <T> Page<T> executeQuery(long pageNum, long pageSize, String sql, String orderByFields, Object paramObj, Class<T> mapping, boolean ignoreCount) {
		StringMap sm = SqlUtils.parseParameterSQL(sql, paramObj);
		return executeQuery(pageNum, pageSize, sm.getString(), orderByFields, sm.getValues(), mapping, ignoreCount);
	}
	
	

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> Page<T> executeQuery(long pageNum, long pageSize, String sql, String orderByFields, Object[] params, Class<T> mapping, boolean ignoreCount) {
		SqlParser parser = JdbcFactory.getSqlParser(this.transaction.getJdbcType());
		
		Page<T> page = SqlUtils.parsePage(this.jdbcExector, this.jdbcAdapter, this.transaction.getConnection(), pageNum, pageSize, sql, params, ignoreCount);
		if(page.getData() != null) return page;
		
		sql = parser.parsePagingSql(sql, orderByFields, pageNum, pageSize);
		List data = null;
		if(mapping == null) {
			data = this.jdbcExector.queryList(this.jdbcAdapter, this.transaction.getConnection(), sql, params);
		}else {
			data = this.jdbcExector.queryList(this.jdbcAdapter, this.transaction.getConnection(), sql, params, mapping);
		}
		
		page.setData(data);
		return page;
	}
	
	

	@Override
	public int executeUpdate(String sql, Object paramObj) {
		StringMap sm = SqlUtils.parseParameterSQL(sql, paramObj);
		return executeUpdate(sm.getString(), sm.getValues());
	}
	

	@Override
	public int executeUpdate(String sql, Object[] params) {
		int count = this.jdbcExector.update(this.jdbcAdapter, this.transaction.getConnection(), sql, params);
		this.transaction.updated();
		return count;
	}
	

	@Override
	public int[] executeUpdateBatch(String sql, List<Object[]> paramsList) {
		if(paramsList==null || paramsList.size()==0) throw new JdbcException(" the update batch paramsList is empty argument! ");
		
		int[] counts = this.jdbcExector.updateBatch(this.jdbcAdapter, this.transaction.getConnection(), sql, paramsList);
		this.transaction.updated();
		return counts;
	}
	

	@Override
	public <T> int[] executeUpdateBatch(String sql, List<T> beanList, Class<T> mapping) {
		if(beanList==null || beanList.size()==0) throw new JdbcException(" the update batch paramsList is empty argument! ");
		
		if(sql.indexOf('$') < 0) throw new JdbcException(" is not found dynamic parameter! can not executeUpdateBatch! sql:'"+sql+"'! ");
		StringMap sm = SqlUtils.parseParameterSQL(sql);
		String[] keys = sm.getKeys();
		if(keys==null || keys.length==0) throw new JdbcException(" is not found dynamic parameter! can not executeUpdateBatch! sql:'"+sql+"'! ");
		
		List<Object[]> paramsList = new ArrayList<Object[]>();
		BMProxy<T> proxy = BMProxy.getInstance(mapping);
		for(int i=0; i<beanList.size(); i++) {
			T t = beanList.get(i);
			proxy.replaceInnerObject(t);
			
			Object[] params = SqlUtils.parseParameters(proxy, keys);
			paramsList.add(params);
		}
		
		return executeUpdateBatch(sm.getString(), paramsList);
	}
	
	
	

	@Override
	public long selectCount(String sql, Object paramObj) {
		StringMap sm = SqlUtils.parseParameterSQL(sql, paramObj);
		return selectCount(sm.getString(), sm.getValues());
	}
	

	@Override
	public long selectCount(String sql, Object[] params) {
		return this.jdbcExector.queryCount(this.jdbcAdapter, this.transaction.getConnection(), sql, params);
	}
	
	

	@Override
	public Map<String,Object> selectFirst(String sql, Object paramObj) {
		StringMap sm = SqlUtils.parseParameterSQL(sql, paramObj);
		return selectFirst(sm.getString(), sm.getValues());
	}
	

	@Override
	public Map<String, Object> selectFirst(String sql, Object[] params) {
		return this.jdbcExector.queryFirst(this.jdbcAdapter, this.transaction.getConnection(), sql, params);
	}

	

	@Override
	public <T> T selectFirst(String sql, Object paramObj, Class<T> mapping) {
		StringMap sm = SqlUtils.parseParameterSQL(sql, paramObj);
		return selectFirst(sm.getString(), sm.getValues(), mapping);
	}
	
	

	@Override
	public <T> T selectFirst(String sql, Object[] params, Class<T> mapping) {
		return this.jdbcExector.queryFirst(this.jdbcAdapter, this.transaction.getConnection(), sql, params, mapping);
	}
	/** ------最终执行方法 ***********************************************************************************************************/
	
	
	
	
}
