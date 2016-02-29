package com.binary.jdbc.adapter.support.mysql;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.binary.jdbc.adapter.FieldMapping;
import com.binary.jdbc.adapter.support.AbstractJdbcAdapter;
import com.binary.jdbc.exception.JdbcException;

public abstract class AbstractMysqlAdapter extends AbstractJdbcAdapter {
	
	
	
	protected Map<String,FieldMapping> newFieldMappings() {
		Map<String,FieldMapping> mapping = new HashMap<String,FieldMapping>();
		mapping.put("TINYINT", new FieldMapping("TINYINT", Integer.class, "getInt"));
		mapping.put("SMALLINT", new FieldMapping("SMALLINT", Integer.class, "getInt"));
		mapping.put("MEDIUMINT", new FieldMapping("MEDIUMINT", Integer.class, "getInt"));
		mapping.put("INT", new FieldMapping("INT", Integer.class, "getInt"));
		mapping.put("BIGINT", new FieldMapping("BIGINT", Long.class, "getLong"));
		mapping.put("REAL", new FieldMapping("REAL", Double.class, "getDouble"));
		mapping.put("DOUBLE", new FieldMapping("DOUBLE", Double.class, "getDouble"));
		mapping.put("FLOAT", new FieldMapping("FLOAT", Float.class, "getFloat"));
		mapping.put("DECIMAL", new FieldMapping("DECIMAL", BigDecimal.class, "getBigDecimal"));
		mapping.put("NUMERIC", new FieldMapping("NUMERIC", BigDecimal.class, "getBigDecimal"));
		mapping.put("DATE", new FieldMapping("DATE", java.sql.Date.class, "getDate"));
		mapping.put("YEAR", new FieldMapping("YEAR", Integer.class, "getInt"));
		mapping.put("TIME", new FieldMapping("TIME", Time.class, "getTime"));
		mapping.put("TIMESTAMP", new FieldMapping("TIMESTAMP", Timestamp.class, "getTimestamp"));
		mapping.put("DATETIME", new FieldMapping("DATETIME", Timestamp.class, "getTimestamp"));
		mapping.put("BIT", new FieldMapping("BIT", Boolean.class, "getBoolean"));
		mapping.put("CHAR", new FieldMapping("CHAR", String.class, "getString"));
		mapping.put("VARCHAR", new FieldMapping("VARCHAR", String.class, "getString"));
		mapping.put("BINARY", new FieldMapping("BINARY", byte[].class, "getBytes"));
		mapping.put("VARBINARY", new FieldMapping("VARBINARY", byte[].class, "getBytes"));
		mapping.put("TINYBLOB", new FieldMapping("TINYBLOB", byte[].class, "getBytes"));
		mapping.put("BLOB", new FieldMapping("BLOB", byte[].class, "getBytes"));
		mapping.put("MEDIUMBLOB", new FieldMapping("MEDIUMBLOB", byte[].class, "getBytes"));
		mapping.put("LONGBLOB", new FieldMapping("LONGBLOB", byte[].class, "getBytes"));
		mapping.put("TINYTEXT", new FieldMapping("TINYTEXT", String.class, "getString"));
		mapping.put("TEXT", new FieldMapping("TEXT", String.class, "getString"));
		mapping.put("MEDIUMTEXT", new FieldMapping("MEDIUMTEXT", String.class, "getString"));
		mapping.put("LONGTEXT", new FieldMapping("LONGTEXT", String.class, "getString"));
		return mapping;
	}
	
	

	
	@Override
	public PreparedStatement prepareUpdateStatement(Connection conn, String sql) {
		try {
			return conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e) {
			throw new JdbcException(e);
		}
	}
	
	
	
}
