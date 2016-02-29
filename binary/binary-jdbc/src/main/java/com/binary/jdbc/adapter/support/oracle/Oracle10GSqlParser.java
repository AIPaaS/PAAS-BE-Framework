package com.binary.jdbc.adapter.support.oracle;

import com.binary.jdbc.JdbcType;

public class Oracle10GSqlParser extends AbstractOracleSqlParser {
	

	@Override
	public JdbcType getJdbcType() {
		return JdbcType.Oracle10G;
	}
	
	
	
}
