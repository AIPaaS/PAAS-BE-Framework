package com.binary.jdbc.print.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.binary.jdbc.print.PrinterWriterType;



public class LoggerPrinterWriter extends AbstractPrinterWriter {

	
	private Logger logger;
	
	
	public LoggerPrinterWriter() {
		this(null);
	}
	public LoggerPrinterWriter(Logger logger) {
		super(PrinterWriterType.LOGGER);
		if(logger == null) {
			this.logger = LoggerFactory.getLogger(LoggerPrinterWriter.class);
		}else {
			this.logger = logger;
		}
	}
	
	
	
	@Override
	public void write(String msg) {
		logger.info(msg);
	}
	
	
	
	
}
