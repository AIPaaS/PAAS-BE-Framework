package com.binary.jdbc.print;

import java.util.HashMap;
import java.util.Map;

import com.binary.jdbc.print.support.DebugPrinter;
import com.binary.jdbc.print.support.NonePrinter;
import com.binary.jdbc.print.support.RealPrinter;



public class PrinterFactory {
	
	
	private static Map<PrinterType, Printer> printerStore = new HashMap<PrinterType, Printer>();	
	
	
	/**
	 * 根据指定打印类型获取打印对象
	 * @param type: 打印类型
	 * @return
	 */
	public static Printer getPrinter(PrinterType type) {
		return getPrinter(type, null);
	}
	
	
	
	/**
	 * 根据指定打印类型获取打印对象
	 * @param type: 打印类型
	 * @param printerWriter : 打印输出器
	 * @return
	 */
	public static Printer getPrinter(PrinterType type, PrinterWriter printerWriter) {
		Printer p = printerStore.get(type);
		if(p == null) {
			p = buildPrinter(type, printerWriter);
		}
		return p;
	}
	
	
	
	private synchronized static Printer buildPrinter(PrinterType type, PrinterWriter printerWriter) {
		Printer p = printerStore.get(type);
		if(p != null) return p;
		
		switch(type) {
			case NONE: p = new NonePrinter(printerWriter); break;
			case REAL: p = new RealPrinter(printerWriter); break;
			case DEBUG: p = new DebugPrinter(printerWriter); break;
		}
		
		printerStore.put(type, p);
		
		return p;
	}
	
	
	
	
}
