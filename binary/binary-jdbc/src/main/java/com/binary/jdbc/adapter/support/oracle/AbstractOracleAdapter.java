package com.binary.jdbc.adapter.support.oracle;

import java.math.BigDecimal;
import java.sql.Blob;
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

public abstract class AbstractOracleAdapter extends AbstractJdbcAdapter {
	
	
	protected Map<String,FieldMapping> newFieldMappings() {
		Map<String,FieldMapping> mapping = new HashMap<String,FieldMapping>();
		mapping.put("BINARY_DOUBLE", new FieldMapping("BINARY_DOUBLE", Double.class, "getDouble"));
		mapping.put("BINARY_FLOAT", new FieldMapping("BINARY_FLOAT", Float.class, "getFloat"));
		mapping.put("CLOB", new FieldMapping("CLOB", String.class, "getString"));
		mapping.put("BLOB", new FieldMapping("BLOB", Blob.class, "getBlob"));
		mapping.put("CHAR", new FieldMapping("CHAR", String.class, "getString"));
		mapping.put("DATE", new FieldMapping("DATE", Timestamp.class, "getTimestamp"));
		mapping.put("LONG", new FieldMapping("LONG", String.class, "getString"));
		mapping.put("LONG RAW", new FieldMapping("LONG RAW", byte[].class, "getBytes"));
		mapping.put("NUMBER", new FieldMapping("NUMBER", BigDecimal.class, "getBigDecimal"));
		mapping.put("RAW", new FieldMapping("RAW", byte[].class, "getBytes"));
		mapping.put("TIMESTAMP", new FieldMapping("TIMESTAMP", Timestamp.class, "getTimestamp"));
		mapping.put("TIMESTAMPTZ", new FieldMapping("TIMESTAMPTZ", Timestamp.class, "getTimestamp"));
		mapping.put("VARCHAR2", new FieldMapping("VARCHAR2", String.class, "getString"));
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
