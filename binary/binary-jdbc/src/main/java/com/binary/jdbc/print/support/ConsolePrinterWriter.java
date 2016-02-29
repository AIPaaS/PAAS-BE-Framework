package com.binary.jdbc.print.support;

import com.binary.jdbc.print.PrinterWriterType;


public class ConsolePrinterWriter extends AbstractPrinterWriter {

	
	
	
	public ConsolePrinterWriter() {
		super(PrinterWriterType.CONSOLE);
	}
	
	
	

	@Override
	public void write(String msg) {
		System.out.println(msg);
	}
	
	
	
}
