package com.binary.jdbc.print.support;

import com.binary.core.util.BinaryUtils;
import com.binary.jdbc.print.Printer;
import com.binary.jdbc.print.PrinterType;
import com.binary.jdbc.print.PrinterWriter;

public abstract class AbstractPrinter implements Printer {

	
	private PrinterType printerType;
	
	private PrinterWriter printerWriter;
	
	
	public AbstractPrinter(PrinterType printerType, PrinterWriter printerWriter) {
		BinaryUtils.checkNull(printerType, "printerType");
		if(printerWriter == null) {
			this.printerWriter = new ConsolePrinterWriter();
		}else {
			this.printerWriter = printerWriter;
		}
	}
	
	
	
	protected String getInfoPrefix() {
		return "BINARY-JDBC-INFO: ";
	}
	
	
	
	
	
	protected String getSqlPrefix() {
		return "BINARY-JDBC-SQL: ";
	}
	
	
	
	
	
	protected String getDebugSqlPrefix() {
		return "BINARY-JDBC-SQL-Debug: ";
	}
	
	
	
	
	@Override
	public String printInfo(String info) {
		String s = getInfoPrefix()+info;
		getPrinterWriter().write(s);
		return s;
	}
	
	
	
	
	@Override
	public PrinterType getPrinterType() {
		return this.printerType;
	}
	
	
	
	@Override
	public PrinterWriter getPrinterWriter() {
		return this.printerWriter;
	}
	
	
	
	@Override
	public void setPrinterWriter(PrinterWriter printerWriter) {
		BinaryUtils.checkNull(printerWriter, "printerWriter");
		this.printerWriter = printerWriter;
	}
	
	
	
	
	
	
	
}
