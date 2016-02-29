package com.binary.jdbc.print.support;

import java.util.List;

import com.binary.jdbc.adapter.JdbcAdapter;
import com.binary.jdbc.print.PrinterType;
import com.binary.jdbc.print.PrinterWriter;

public class RealPrinter extends AbstractPrinter {

	
	public RealPrinter(PrinterWriter printerWriter) {
		super(PrinterType.REAL, printerWriter);
	}

	
	
	@Override
	public String printSql(String sql) {
		String s = getSqlPrefix()+sql;
		getPrinterWriter().write(s);
		return s;
	}
	
	
	
	@Override
	public String printSql(String sql, Object[] params, JdbcAdapter adapter) {
		return printSql(sql);
	}
	
	
	
	@Override
	public String[] printSql(String sql, List<Object[]> paramsList, JdbcAdapter adapter) {
		String s = printSql(sql);
		return new String[]{s};
	}
	
	

}
