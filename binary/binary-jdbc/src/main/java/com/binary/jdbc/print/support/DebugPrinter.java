package com.binary.jdbc.print.support;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import com.binary.core.lang.Conver;
import com.binary.jdbc.adapter.JdbcAdapter;
import com.binary.jdbc.adapter.SqlDateFormat;
import com.binary.jdbc.adapter.SqlFunction;
import com.binary.jdbc.print.PrinterType;
import com.binary.jdbc.print.PrinterWriter;

public class DebugPrinter extends AbstractPrinter {

	
	public DebugPrinter(PrinterWriter printerWriter) {
		super(PrinterType.DEBUG, printerWriter);
	}

	
	

	public String printSql(String sql) {
		String s = getDebugSqlPrefix()+sql;
		getPrinterWriter().write(s);
		return s;
	}
	

	
	
	
	public String printSql(String sql, Object[] params, JdbcAdapter adapter) {
		if(params != null) {
			sql = replaceQuestionRemark(sql, params, adapter);
		}
		String s = printSql(sql);
		return s;
	}
	
	
	
	
	public String[] printSql(String sql, List<Object[]> paramsList, JdbcAdapter adapter) {
		String[] ss = null;
		if(paramsList != null) {
			ss = new String[paramsList.size()];
			for(int i=0; i<ss.length; i++) {
				Object[] params = (Object[]) paramsList.get(i);
				ss[i] = replaceQuestionRemark(sql, params, adapter);
				ss[i] = printSql(ss[i]);
			}
		}
		return ss;
	}
	
	
	
	public String replaceQuestionRemark(String sql, Object[] params, JdbcAdapter adapter) {
		return parseParameterSQLValues(sql, params, adapter);
	}
	
	
	
	public static String parseParameterSQLValues(String sql, Object[] params, JdbcAdapter adapter) {
		if(params==null || params.length==0) return sql;
		int index = -1;
		String parent = "";
		for(int i=0; i<params.length&&(index=sql.indexOf("?"))>-1; i++) {
			String param = "";
			if(params[i] == null) {
				param = "''";
			}else if((params[i] instanceof java.util.Date) || (params[i] instanceof java.util.Calendar)) {
				SqlDateFormat format = null;
				if(params[i] instanceof Timestamp) {
					format = SqlDateFormat.yyyyMMddHHmmss;
				}else if(params[i] instanceof Time) {
					format = SqlDateFormat.HHmmss;
				}else {
					format = SqlDateFormat.yyyyMMdd;
				}
				
				SqlFunction func = adapter.getSqlFunction();
				param = func.toDate("'"+Conver.toString(params[i], format.toJavaFormat())+"'", format);
			}else if((params[i] instanceof String) || (params[i] instanceof Character)) {
				param = "'"+Conver.toString(params[i])+"'";
			}else {
				param = Conver.toString(params[i]);
			}
			parent += sql.substring(0, index)+param;
			sql = sql.substring(index+1);
		}
		parent += sql;
		return parent;
	}
	

}
