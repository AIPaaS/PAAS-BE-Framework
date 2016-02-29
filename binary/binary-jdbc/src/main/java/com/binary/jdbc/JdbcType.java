package com.binary.jdbc;

import com.binary.jdbc.exception.JdbcException;


/**
 * 数据库连接类型
 */
public enum JdbcType {
	
	
	/**
	 * Oracle10G
	 */
	Oracle10G(101),
	
	
	/**
	 * SqlServer2005
	 */
	SqlServer2005(201),
	
	
	/**
	 * MySQL5
	 */
	MySQL5(301);
	
	
	
	private final int value;
	
	
    private JdbcType(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
    
    
    public static JdbcType valueOf(int v) {
    	switch(v) {
    		case 101: return Oracle10G;
    		case 201: return SqlServer2005;
    		case 301: return MySQL5;
    		default : throw new JdbcException(" is wrong JdbcType:'"+v+"'! ");
    	}
    }
	
}
