package com.binary.jdbc.adapter.support.oracle;

import com.binary.jdbc.adapter.SqlDissolver;
import com.binary.jdbc.adapter.support.AbstractSqlParser;
import com.binary.jdbc.exception.SqlParserException;

public abstract class AbstractOracleSqlParser extends AbstractSqlParser {
	
	
	
	
	@Override
	public String parseCountSql(String sql) {
		return "select count(1) from ("+sql+")";
	}
	

	
	@Override
	public String parseSimpleCountSql(String sql) {
		return parseCountSql(sql);
	}
	
	

	
	@Override
	public String parsePagingSql(SqlDissolver dissolver, String orderByFields, long pageNum, long pageSize) {
		String[] fields = dissolver.getFields();
		return paging(dissolver.getBaseSql(), fields, orderByFields, pageNum, pageSize);
	}
	
	
	
	private String paging(String sql, String[] fields, String orderByFields, long pageNum, long pageSize) {
		if(orderByFields==null || (orderByFields=orderByFields.trim()).length()==0) throw new SqlParserException(" the orderByFields is NULL argument! ");
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		
		for(int i=0; i<fields.length; i++) {
			if(i > 0) sb.append(", ");
			sb.append(fields[i]);
		}
		
		String baseSelect = sb.substring(0, sb.length());
		long startRow = (pageNum - 1) * pageSize + 1;
		long endRow = startRow + pageSize;
		
		sb.delete(0, sb.length());
		sb.append(baseSelect).append(" from (").append(baseSelect).append(", rownum alias_for_rownum from (")
					.append(sql).append(" order by ").append(orderByFields).append(") where rownum<").append(endRow).append(") where alias_for_rownum>=").append(startRow);
		return sb.toString();
	}
	
	
	
	
}



