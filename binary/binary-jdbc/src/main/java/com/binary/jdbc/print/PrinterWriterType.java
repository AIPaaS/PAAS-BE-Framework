package com.binary.jdbc.print;

import com.binary.jdbc.exception.JdbcException;


/**
 * SQL打印输出器类型
 */
public enum PrinterWriterType {
	
	/**
	 * 控制台输出
	 */
	CONSOLE(0),
	
	
	/**
	 * 日志(Log4J)输出
	 */
	LOGGER(1),
	
	
	/**
	 * 指定文件输出
	 */
	FILE(2);

	
	private final int value;
	
	
	
    private PrinterWriterType(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
    
    public static PrinterWriterType valueOf(int v) {
    	switch(v) {
	    	case 0: return CONSOLE;
	    	case 1: return LOGGER;
	    	case 2: return FILE;
    		default : throw new JdbcException(" is wrong PrinterType:'"+v+"'! ");
    	}
    }
	
    
    
}



