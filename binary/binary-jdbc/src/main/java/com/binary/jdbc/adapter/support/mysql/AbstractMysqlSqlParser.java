package com.binary.jdbc.adapter.support.mysql;

import com.binary.jdbc.adapter.SqlDissolver;
import com.binary.jdbc.adapter.support.AbstractSqlParser;
import com.binary.jdbc.exception.SqlParserException;

public abstract class AbstractMysqlSqlParser extends AbstractSqlParser {
	

	
	@Override
	public String parseCountSql(String sql) {
		return "select count(1) from ("+sql+") as alias_for_selectcount";
	}
	

	
	@Override
	public String parseSimpleCountSql(String sql) {
		return parseCountSql(sql);
	}
	
	
	
	
	public String parsePagingSql(String sql, String orderByFields, long pageNum, long pageSize) {
		if(orderByFields==null || (orderByFields=orderByFields.trim()).length()==0) throw new SqlParserException(" the orderByFields is NULL argument! ");
		StringBuffer sb = new StringBuffer();
		long startRow = (pageNum - 1) * pageSize + 1;
		sb.append(sql).append(" order by ").append(orderByFields).append(" limit ").append(startRow-1).append(",").append(pageSize).append(" ");
		return sb.toString();
	}
	

	
	@Override
	public String parsePagingSql(SqlDissolver dissolver, String orderByFields, long pageNum, long pageSize) {
		return parsePagingSql(dissolver.getBaseSql(), orderByFields, pageNum, pageSize);
	}
	
	
	
	
}




