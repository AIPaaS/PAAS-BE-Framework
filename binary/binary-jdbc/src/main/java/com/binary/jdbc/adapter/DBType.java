package com.binary.jdbc.adapter;

import java.sql.Blob;
import java.sql.Clob;

public enum DBType {
	
	
	VARCHAR,
	DECIMAL,
	INTEGER,
	LONG,
	FLOAT,
	DOUBLE,
	DATE,
	TIME,
	TIMESTAMP,
	CLOB,
	BLOB;

	 

	public Class<?> mappingJavaType() {
		switch(this) {
			case VARCHAR: return String.class;
			case DECIMAL: return java.math.BigDecimal.class;
			case INTEGER: return int.class;
			case LONG: return long.class;
			case FLOAT: return double.class;
			case DOUBLE: return double.class;
			case DATE: return java.sql.Date.class;
			case TIME: return java.sql.Time.class;
			case TIMESTAMP: return java.sql.Timestamp.class;
			case CLOB: return Clob.class;
			case BLOB: return Blob.class;
			default: return null;
		}
	}
}
