package com.binary.jdbc.adapter.support.mssql;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.binary.jdbc.adapter.FieldMapping;
import com.binary.jdbc.adapter.support.AbstractJdbcAdapter;
import com.binary.jdbc.exception.JdbcException;

public abstract class AbstractMssqlAdapter extends AbstractJdbcAdapter {
	
	
	
	protected Map<String,FieldMapping> newFieldMappings() {
		Map<String,FieldMapping> mappings = new HashMap<String,FieldMapping>();
		mappings.put("BIGINT", new FieldMapping("BIGINT", Long.class, "getLong"));
		mappings.put("BINARY", new FieldMapping("BINARY", byte[].class, "getBytes"));
		mappings.put("BIT", new FieldMapping("BIT", Boolean.class, "getBoolean"));
		mappings.put("CHAR", new FieldMapping("CHAR", String.class, "getString"));
		mappings.put("DATETIME", new FieldMapping("DATETIME", Timestamp.class, "getTimestamp"));
		mappings.put("DECIMAL", new FieldMapping("DECIMAL", BigDecimal.class, "getBigDecimal"));
		mappings.put("FLOAT", new FieldMapping("FLOAT", Double.class, "getDouble"));
		mappings.put("IMAGE", new FieldMapping("IMAGE", byte[].class, "getBytes"));
		mappings.put("INT", new FieldMapping("INT", Integer.class, "getInt"));
		mappings.put("MONEY", new FieldMapping("MONEY", BigDecimal.class, "getBigDecimal"));
		mappings.put("NCHAR", new FieldMapping("NCHAR", String.class, "getString"));
		mappings.put("NTEXT", new FieldMapping("NTEXT", String.class, "getString"));
		mappings.put("NUMERIC", new FieldMapping("NUMERIC", BigDecimal.class, "getBigDecimal"));
		mappings.put("NVARCHAR", new FieldMapping("NVARCHAR", String.class, "getString"));
		mappings.put("REAL", new FieldMapping("REAL", Float.class, "getFloat"));
		mappings.put("SMALLDATETIME", new FieldMapping("SMALLDATETIME", Timestamp.class, "getTimestamp"));
		mappings.put("SMALLINT", new FieldMapping("SMALLINT", Short.class, "getShort"));
		mappings.put("SMALLMONEY", new FieldMapping("SMALLMONEY", BigDecimal.class, "getBigDecimal"));
		mappings.put("TEXT", new FieldMapping("TEXT", String.class, "getString"));
		mappings.put("TIMESTAMP", new FieldMapping("TIMESTAMP", byte[].class, "getBytes"));
		mappings.put("TINYINT", new FieldMapping("TINYINT", Short.class, "getShort"));
		mappings.put("UNIQUEIDENTIFIER", new FieldMapping("UNIQUEIDENTIFIER", String.class, "getString"));
		mappings.put("VARBINARY", new FieldMapping("VARBINARY", byte[].class, "getBytes"));
		mappings.put("VARCHAR", new FieldMapping("VARCHAR", String.class, "getString"));
		mappings.put("XML", new FieldMapping("XML", String.class, "getString"));
		return mappings;
	}
	
	

	
	@Override
	public PreparedStatement prepareUpdateStatement(Connection conn, String sql) {
		try {
			return conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			throw new JdbcException(e);
		}
	}
	
}
