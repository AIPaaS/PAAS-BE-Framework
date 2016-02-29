package com.binary.jdbc.print.support;

import java.util.List;

import com.binary.jdbc.adapter.JdbcAdapter;
import com.binary.jdbc.print.PrinterType;
import com.binary.jdbc.print.PrinterWriter;

public class NonePrinter extends AbstractPrinter {

	
	public NonePrinter(PrinterWriter printerWriter) {
		super(PrinterType.NONE, printerWriter);
	}

	
	
	@Override
	public String printInfo(String info) {
		return null;
	}

	
	@Override
	public String printSql(String sql) {
		return null;
	}

	
	@Override
	public String printSql(String sql, Object[] params, JdbcAdapter adapter) {
		return null;
	}

	
	@Override
	public String[] printSql(String sql, List<Object[]> paramsList, JdbcAdapter adapter) {
		return null;
	}

}
