package com.binary.jdbc.print.support;

import com.binary.core.util.BinaryUtils;
import com.binary.jdbc.print.PrinterWriter;
import com.binary.jdbc.print.PrinterWriterType;

public abstract class AbstractPrinterWriter implements PrinterWriter {

	
	private PrinterWriterType printerWriterType;
	
	
	protected AbstractPrinterWriter(PrinterWriterType printerWriterType) {
		BinaryUtils.checkNull(printerWriterType, "printerWriterType");
		this.printerWriterType = printerWriterType;
	}
	
	
	public PrinterWriterType getPrinterWriterType() {
		return this.printerWriterType;
	}
	
	
	
	
}
