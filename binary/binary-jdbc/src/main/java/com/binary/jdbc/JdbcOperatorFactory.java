package com.binary.jdbc;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.binary.core.exception.MultipleException;
import com.binary.core.util.BinaryUtils;
import com.binary.jdbc.ds.DataSource;
import com.binary.jdbc.ds.DataSourceManager;
import com.binary.jdbc.ds.support.DefaultDataSourceManager;
import com.binary.jdbc.exception.JdbcConfigException;
import com.binary.jdbc.exception.JdbcException;
import com.binary.jdbc.exception.JdbcOperatorException;
import com.binary.jdbc.exception.TransactionException;
import com.binary.jdbc.print.Printer;
import com.binary.jdbc.print.PrinterFactory;
import com.binary.jdbc.print.PrinterType;
import com.binary.jdbc.print.PrinterWriter;
import com.binary.jdbc.print.PrinterWriterType;
import com.binary.jdbc.print.support.ConsolePrinterWriter;
import com.binary.jdbc.print.support.FilePrinterWriter;
import com.binary.jdbc.print.support.LoggerPrinterWriter;
import com.binary.jdbc.support.DefaultJdbcOperator;
import com.binary.jdbc.support.DefaultTransaction;

public class JdbcOperatorFactory {
	
	
	private static class JdbcOperatorThreadSingleFactory {
		final Map<String, JdbcOperator> jdbcOperatorMap = new HashMap<String, JdbcOperator>();
		final Map<Connection, JdbcOperator> connOperatorMap = new HashMap<Connection, JdbcOperator>();
		DataSourceManager dsmgr;
		Printer printer;
		
		JdbcOperatorThreadSingleFactory(Printer printer, DataSourceManager dsmgr) {
			this.printer = printer;
			this.dsmgr = dsmgr;
		}
		JdbcOperator getJdbcOperator(String dsname, boolean isnew) {
			DataSource ds = null;
			if(BinaryUtils.isEmpty(dsname)) {
				ds = dsmgr.getDefaultDataSource();
				if(ds == null) throw new JdbcOperatorException(" is not initialize default dataSource! ");
				dsname = ds.getName();
			}
			
			JdbcOperator operator = null;
			if(isnew) {
				if(ds == null) {
					ds = this.dsmgr.getDataSource(dsname);
					if(ds == null) throw new JdbcOperatorException(" is not found dataSource '"+dsname+"'! ");
				}
				Connection conn = ds.getConnection();
				Transaction transaction = new DefaultTransaction(ds.getJdbcType(), conn);
				operator = new DefaultJdbcOperator(transaction, this.printer);
				jdbcOperatorMap.put(BinaryUtils.getUUID(), operator);
				connOperatorMap.put(conn, operator);
			}else {
				String key = dsname.trim().toUpperCase();
				operator = jdbcOperatorMap.get(key);
				if(operator == null) {
					if(ds == null) {
						ds = this.dsmgr.getDataSource(dsname);
						if(ds == null) throw new JdbcOperatorException(" is not found dataSource '"+dsname+"'! ");
					}
					Connection conn = ds.getConnection();
					Transaction transaction = new DefaultTransaction(ds.getJdbcType(), conn);
					operator = new DefaultJdbcOperator(transaction, this.printer);
					jdbcOperatorMap.put(key, operator);
					connOperatorMap.put(conn, operator);
				}
			}
			
			return operator;
		}
		
		
		JdbcOperator getJdbcOperator(Connection conn) {
			return connOperatorMap.get(conn);
		}
		
		
		void doTransaction(boolean iscommit) {
			MultipleException me = null;
			Iterator<JdbcOperator> itor = jdbcOperatorMap.values().iterator();
			while(itor.hasNext()) {
				JdbcOperator operator = itor.next();
				Transaction tx = operator.getTransaction();
				try {
					//if(!tx.isCompleted()) {
						if(iscommit) {
							tx.commit();
						}else {
							tx.rollback();
						}
					//}
				}catch(Exception e) {
					me = MultipleException.appendException(me, e);
				}
			}
			if(me!=null && me.size()>0) throw new TransactionException(" the transaction-"+(iscommit?"commit":"rollback")+" error!", me);
		}
		
		
		void close() {
			MultipleException me = null;
			Iterator<JdbcOperator> itor = jdbcOperatorMap.values().iterator();
			while(itor.hasNext()) {
				JdbcOperator operator = itor.next();
				Transaction tx = operator.getTransaction();
				Connection connection = tx.getConnection();
				try {
					connection.close();
				}catch(Exception e) {
					me = MultipleException.appendException(me, e);
				}
			}
			jdbcOperatorMap.clear();
			connOperatorMap.clear();
			
			if(me!=null && me.size()>0) throw new JdbcException(" jdbc close error! ", me);
		}
		
	}
	
	
	
	
	/** 为方便外部使用 **/
	private static JdbcOperatorFactory factory;
	
	private Printer printer;
	private DataSourceManager dsmgr;
	
	private final ThreadLocal<JdbcOperatorThreadSingleFactory> singleFacotryThreadLocal = new ThreadLocal<JdbcOperatorThreadSingleFactory>();
	
	
	public JdbcOperatorFactory(JdbcOperatorConfig config) {
		BinaryUtils.checkEmpty(config, "config");
		
		PrinterType printerType = config.getPrinterType();
		BinaryUtils.checkEmpty(printerType, "config.printerType");
		
		PrinterWriterType printerWriterType = config.getPrinterWriterType();
		String writePath = config.getPrinterWritePath();
		
		PrinterWriter printerWriter = null;
		if(printerWriterType != null) {
			switch(printerWriterType) {
				case CONSOLE: printerWriter = new ConsolePrinterWriter(); break;
				case LOGGER: printerWriter = new LoggerPrinterWriter(); break;
				case FILE: 
					if(writePath==null || writePath.length()==0) throw new JdbcConfigException(" the jdbc-config.printer file-path is empty! ");
					printerWriter = new FilePrinterWriter(writePath);
					break;
				default: throw new JdbcConfigException(" is wrong jdbc-printer-writer-type '"+printerWriterType+"'! ");
			}
		}
		this.printer = PrinterFactory.getPrinter(printerType, printerWriter);
		
		String defaultDataSource = config.getDefaultDataSource();
		List<DataSource> dataSourceStore = config.getDataSourceStore();
		BinaryUtils.checkEmpty(defaultDataSource, "config.defaultDataSource");
		BinaryUtils.checkEmpty(dataSourceStore, "config.dataSourceStore");
		
		dsmgr = new DefaultDataSourceManager();
		for(int i=0; i<dataSourceStore.size(); i++) {
			DataSource ds = dataSourceStore.get(i);
			if(ds == null) throw new JdbcConfigException(" the dataSourceList["+i+"] is null! ");
			dsmgr.addDataSource(ds);
		}
		dsmgr.setDefaultDataSource(defaultDataSource);
		
		factory = this;
	}
	
	
	private JdbcOperatorThreadSingleFactory getThreadSingleFactory(boolean create) {
		JdbcOperatorThreadSingleFactory singleFactory = singleFacotryThreadLocal.get();
		if(singleFactory==null && create) {
			singleFactory = new JdbcOperatorThreadSingleFactory(this.printer, this.dsmgr);
			singleFacotryThreadLocal.set(singleFactory);
		}
		return singleFactory;
	}
	
	
	
	
	/**
	 * 获取打印对象
	 * @return
	 */
	public Printer getPrinter() {
		return this.printer;
	}
	
	
	/**
	 * 获取JdbcOperator操作对象, 每一次获取的对象在同一个线程中唯一
	 * @return
	 */
	public JdbcOperator getJdbcOperator() {
		return getJdbcOperator(null);
	}
	public JdbcOperator getJdbcOperator(String dsname) {
		JdbcOperatorThreadSingleFactory singleFactory = getThreadSingleFactory(true);
		return singleFactory.getJdbcOperator(dsname, false);
	}
	
	
	
	
	/**
	 * 创建一个新的JdbcOperator对象
	 * @return
	 */
	public JdbcOperator newJdbcOperator() {
		return newJdbcOperator(null);
	}
	public JdbcOperator newJdbcOperator(String dsname) {
		JdbcOperatorThreadSingleFactory singleFactory = getThreadSingleFactory(true);
		return singleFactory.getJdbcOperator(dsname, true);
	}
	
	
	
	
	
	/**
	 * 跟据已存在的connection来签定出对应哪个JdbcOperator
	 * @param conn
	 * @return
	 */
	public JdbcOperator appraisalJdbcOperator(Connection conn) {
		if(conn == null) return null;
		
		JdbcOperatorThreadSingleFactory singleFactory = getThreadSingleFactory(false);
		if(singleFactory == null) return null;
		
		return singleFactory.getJdbcOperator(conn);
	}
	
	
	
	
	
	private void doTransaction(boolean iscommit) {
		JdbcOperatorThreadSingleFactory singleFactory = getThreadSingleFactory(false);
		if(singleFactory != null) {
			singleFactory.doTransaction(iscommit);
		}
	}
	
	
	
	/**
	 * 提交事物
	 */
	public void commit() {
		doTransaction(true);
	}
	
	
	
	/**
	 * 回滚事物
	 */
	public void rollback() {
		doTransaction(false);
	}
	
	
	
	
	/**
	 * 关闭所有资源
	 */
	public void close() {
		JdbcOperatorThreadSingleFactory singleFactory = getThreadSingleFactory(false);
		if(singleFactory != null) {
			try {
				singleFactory.close();
			}finally {
				singleFacotryThreadLocal.remove();
			}
		}
	}
	
	
	/**
	 * 获取数据源管理器
	 * @return
	 */
	public DataSourceManager getDataSourceManager() {
		return this.dsmgr;
	}
	
	
	/**
	 * 获取Jdbc操作工厂
	 * @param config : 配置属性
	 * @return
	 */
	public static JdbcOperatorFactory getFactory(JdbcOperatorConfig config) {
		return new JdbcOperatorFactory(config);
	}
	
	
	
	
	public static JdbcOperatorFactory getMomentFactory() {
		//if(factory == null) throw new JdbcOperatorException(" is not initialize JdbcOperatorFactory! ");
		return factory;
	}
	
	
	
	
	
	
	

}
