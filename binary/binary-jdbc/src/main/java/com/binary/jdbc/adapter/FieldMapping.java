package com.binary.jdbc.adapter;

import java.io.Serializable;


/**
 * 数据库字段与JAVA类型的映射关系
 */
public class FieldMapping implements Serializable {
	private static final long serialVersionUID = -7104346618294024519L;
	
	
	private String dbType;
	private Class<?> javaType;
	private String rsGetMethod;
	
	
	public FieldMapping() {
	}
	
	public FieldMapping(String dbType, Class<?> javaType, String rsGetMethod) {
		this.dbType = dbType;
		this.javaType = javaType;
		this.rsGetMethod = rsGetMethod;
	}
	
	
	public String getDbType() {
		return dbType;
	}
	
	
	public Class<?> getJavaType() {
		return javaType;
	}
	
	
	public String getRsGetMethod() {
		return rsGetMethod;
	}	
	
	
	
}
