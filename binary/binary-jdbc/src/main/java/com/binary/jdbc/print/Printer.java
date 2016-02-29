package com.binary.jdbc.print;

import java.util.List;

import com.binary.jdbc.adapter.JdbcAdapter;



/**
 * jdbc打印器
 * @author wanwb
 */
public interface Printer {
	
	
	/**
	 * 获取当前打印类型
	 * @return
	 */
	public PrinterType getPrinterType();
	
	
	
	/**
	 * 获取当前打印输出器
	 * @return
	 */
	public PrinterWriter getPrinterWriter();
	
	
	
	/**
	 * 设置当前打印输出器
	 * @param printerWriter
	 */
	public void setPrinterWriter(PrinterWriter printerWriter);
	
	
	
	
	/**
	 * 打印一般信息
	 * @param info
	 * @return
	 */
	public String printInfo(String info);
	
	
	
	
	
	
	/**
	 * 打印SQL
	 * @param sql
	 * @return
	 */
	public String printSql(String sql);
	
	
	
	
	
	/**
	 * 打印SQL
	 * @param sql
	 * @param params
	 * @return
	 */
	public String printSql(String sql, Object[] params, JdbcAdapter adapter);
	
	
	
	
	
	
	/**
	 * 打印调式状态下SQL
	 * @param sql
	 * @param mapList
	 * @return
	 */
	public String[] printSql(String sql, List<Object[]> paramsList, JdbcAdapter adapter);
	
	
	
	
}




