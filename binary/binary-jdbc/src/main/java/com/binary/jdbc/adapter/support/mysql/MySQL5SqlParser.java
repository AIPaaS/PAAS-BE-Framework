package com.binary.jdbc.adapter.support.mysql;

import com.binary.jdbc.JdbcType;

public class MySQL5SqlParser extends AbstractMysqlSqlParser {
	
	
	@Override
	public JdbcType getJdbcType() {
		return JdbcType.MySQL5;
	}
	
	
	
	
}
