package com.binary.jdbc;

import java.util.HashMap;
import java.util.Map;

import com.binary.jdbc.adapter.JdbcAdapter;
import com.binary.jdbc.adapter.SqlFunction;
import com.binary.jdbc.adapter.SqlParser;
import com.binary.jdbc.adapter.support.mssql.SqlServer2005Adapter;
import com.binary.jdbc.adapter.support.mssql.SqlServer2005SqlFunction;
import com.binary.jdbc.adapter.support.mssql.SqlServer2005SqlParser;
import com.binary.jdbc.adapter.support.mysql.MySQL5Adapter;
import com.binary.jdbc.adapter.support.mysql.MySQL5SqlFunction;
import com.binary.jdbc.adapter.support.mysql.MySQL5SqlParser;
import com.binary.jdbc.adapter.support.oracle.Oracle10GAdapter;
import com.binary.jdbc.adapter.support.oracle.Oracle10GSqlFunction;
import com.binary.jdbc.adapter.support.oracle.Oracle10GSqlParser;
import com.binary.jdbc.db.JdbcExector;
import com.binary.jdbc.db.JdbcExectorListener;
import com.binary.jdbc.db.support.DefaultJdbcExector;
import com.binary.jdbc.exception.JdbcException;
import com.binary.jdbc.print.Printer;
import com.binary.jdbc.print.PrinterFactory;
import com.binary.jdbc.print.PrinterType;
import com.binary.jdbc.print.PrinterWriter;


/**
 * Jdbc相关实例生产工厂
 */
public class JdbcFactory {
	
	
	private static class JdbcAdapterItem {
		JdbcAdapter adapter;
		SqlFunction function;
		SqlParser parser;
	}
	
	
	private static final Map<JdbcType, JdbcAdapterItem> adapterStore = new HashMap<JdbcType, JdbcAdapterItem>();
	
	
	
	private static JdbcAdapterItem getJdbcAdapterItem(JdbcType jdbcType) {
		if(jdbcType == null) throw new JdbcException(" is NULL jdbcType argument! ");
		JdbcAdapterItem item = adapterStore.get(jdbcType);
		if(item == null) item = buildJdbcAdapterItem(jdbcType);
		return item;
	}
	
	private synchronized static JdbcAdapterItem buildJdbcAdapterItem(JdbcType jdbcType) {
		JdbcAdapterItem item = adapterStore.get(jdbcType);
		if(item == null) {
			item = new JdbcAdapterItem();
			switch(jdbcType) {
				case Oracle10G: {
					item.adapter = new Oracle10GAdapter(); 
					item.function = new Oracle10GSqlFunction();
					item.parser = new Oracle10GSqlParser();
					break;
				}
				case SqlServer2005: {
					item.adapter = new SqlServer2005Adapter();
					item.function = new SqlServer2005SqlFunction();
					item.parser = new SqlServer2005SqlParser();
					break;
				}
				case MySQL5: {
					item.adapter = new MySQL5Adapter();
					item.function = new MySQL5SqlFunction();
					item.parser = new MySQL5SqlParser();
					break;
				}
				default : throw new JdbcException(" is wrong jdbc-type '"+jdbcType+"'! ");
			}
			adapterStore.put(jdbcType, item);
		}
		return item;
	}
	
	
	
	
	/**
	 * 获取JdbcAdapter实例
	 * @param jdbcType: 数据库类型
	 * @return
	 */
	public static JdbcAdapter getJdbcAdapter(JdbcType jdbcType) {
		return getJdbcAdapterItem(jdbcType).adapter;
	}
	
	
	
	/**
	 * 获取SqlFunction实例
	 * @param jdbcType: 数据库类型
	 * @return
	 */
	public static SqlFunction getSqlFunction(JdbcType jdbcType) {
		return getJdbcAdapterItem(jdbcType).function;
	}
	
	
	
	/**
	 * 获取SqlParser实例
	 * @param jdbcType: 数据库类型
	 * @return
	 */
	public static SqlParser getSqlParser(JdbcType jdbcType) {
		return getJdbcAdapterItem(jdbcType).parser;
	}
	
	
	
	/**
	 * 获取JDBC打印器
	 * @param printerType: 打印器类型
	 * @return
	 */
	public static Printer getPrinter(PrinterType printerType) {
		return PrinterFactory.getPrinter(printerType);
	}
	
	
	/**
	 * 获取JDBC打印器
	 * @param printerType: 打印器类型
	 * @param printerWriter: 打印器类型输出器
	 * @return
	 */
	public static Printer getPrinter(PrinterType printerType, PrinterWriter printerWriter) {
		return PrinterFactory.getPrinter(printerType, printerWriter);
	}
	
	
	
	/**
	 * 获取JDBC操作对象(非单例对象)
	 * @param printerType: 打印类型
	 * @return
	 */
	public static JdbcExector getJdbcExector(Printer printer) {
		return new DefaultJdbcExector(printer);
	}
	
	
	
	/**
	 * 获取JDBC操作对象(非单例对象)
	 * @param printerType: 打印类型
	 * @param listener: SQL执行监听器
	 * @return
	 */
	public static JdbcExector getJdbcExector(Printer printer, JdbcExectorListener listener) {
		return new DefaultJdbcExector(printer, listener);
	}
	
	
	
	
	
}







