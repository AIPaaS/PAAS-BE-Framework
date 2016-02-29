package com.binary.jdbc.print;

import com.binary.jdbc.exception.JdbcException;


/**
 * SQL打印类型
 */
public enum PrinterType {
	
	/**
	 * 不打印
	 */
	NONE(0),
	
	
	/**
	 * 打印最终执行SQL
	 */
	REAL(1),
	
	
	/**
	 * 打印调试SQL
	 */
	DEBUG(2);

	
	private final int value;
	
	
	
    private PrinterType(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
    
    public static PrinterType valueOf(int v) {
    	switch(v) {
	    	case 0: return NONE;
	    	case 1: return REAL;
	    	case 2: return DEBUG;
    		default : throw new JdbcException(" is wrong PrinterType:'"+v+"'! ");
    	}
    }
	
    
    
}



