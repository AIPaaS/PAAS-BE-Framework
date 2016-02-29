package com.binary.jdbc.adapter;

import com.binary.jdbc.exception.JdbcException;


/**
 * SQL日期格式
 */
public enum SqlDateFormat {
	
	
	/**
	 * yyyy-MM-dd
	 */
	yyyyMMdd(23),
	
	
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	yyyyMMddHHmmss(120),
	
	
	/**
	 * HH:mm:ss
	 */
	HHmmss(24);
	
	
	
	private int value;
	
	
	private SqlDateFormat(int value) {
		this.value = value;
	}
	
	
	public int getValue() {
        return value;
    }
	
	
	
	public static SqlDateFormat parseFormat(String format) {
		if(format.equalsIgnoreCase("yyyy-MM-dd")) {
			return yyyyMMdd;
		}else if(format.equals("yyyy-MM-dd HH:mm:ss") || format.equalsIgnoreCase("yyyy-mm-dd hh24:mi:ss")) {
			return yyyyMMddHHmmss;
		}else if(format.equals("HH:mm:ss") || format.equalsIgnoreCase("hh24:mi:ss")) {
			return HHmmss;
		}else {
			throw new JdbcException(" is not parse format:'"+format+"'! ");
		}
	}
	
	
	
	public static SqlDateFormat valueOf(int value) {
		switch(value) {
			case 23: return yyyyMMdd;
			case 120: return yyyyMMddHHmmss;
			case 24: return HHmmss;
			default : throw new JdbcException(" is not parse SqlDateFormat:'"+value+"'! ");
		}
	}
	
	
	public String toJavaFormat() {
		switch(value) {
			case 23: return "yyyy-MM-dd";
			case 120: return "yyyy-MM-dd HH:mm:ss";
			case 24: return "HH:mm:ss";
			default : return "";
		}
	}
	
	
	
	
	
}




