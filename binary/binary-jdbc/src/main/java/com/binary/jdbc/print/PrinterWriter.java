package com.binary.jdbc.print;



/**
 * jdbc打印输出器
 * @author wanwb
 */
public interface PrinterWriter {
	
	
	
	/**
	 * 获取打印输出器类型
	 * @return
	 */
	public PrinterWriterType getPrinterWriterType();
	
	
	
	
	
	/**
	 * 打印信息
	 * @param msg
	 */
	public void write(String msg);
	
	
	
}
