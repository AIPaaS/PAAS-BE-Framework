package com.binary.jdbc.adapter.support.mssql;

import com.binary.core.lang.StringMap;
import com.binary.core.lang.StringUtils;
import com.binary.jdbc.adapter.SqlDissolver;
import com.binary.jdbc.adapter.support.AbstractSqlParser;
import com.binary.jdbc.exception.SqlParserException;

public abstract class AbstractMssqlSqlParser extends AbstractSqlParser {
	
	

	
	@Override
	public String parseCountSql(String sql) {
		sql = sql.trim();
		String temp_sql = sql.toLowerCase();
		StringMap sm = StringUtils.parseOutlimit(temp_sql, "(", ")");
		String outbarsql = sm.getString();
		if(outbarsql.indexOf(" order ") > 0) {
			sql = sql.substring(0, temp_sql.lastIndexOf(" order "));
		}
		if(outbarsql.indexOf(",") > 0) {
			String firstfield = sql.substring(6,sql.indexOf(",")).trim().toLowerCase();
			if(firstfield.matches("distinct[ ]+[0-9]+")) {
				sql = "select distinct "+sql.substring(sql.indexOf(",")+1);
			}
		}
		return "select count(1) from ("+sql+") as alias_for_selectcount";
	}
	
	

	
	@Override
	public String parseSimpleCountSql(String sql) {
		return "select count(1) from ("+sql+") as alias_for_selectcount";
	}
	
	
	private String removeAlias(String orderByFields) {
		String[] fields = orderByFields.split(",");
		String order = "";
		for(int i=0; i<fields.length; i++) {
			if(i > 0) order += ", ";
			if(fields[i].indexOf(".")>0) fields[i] = fields[i].substring(fields[i].indexOf(".")+1);
			order += fields[i];
		}
		return order;
	}
	
	
	
	
	@Override
	public String parsePagingSql(SqlDissolver dissolver, String orderByFields, long pageNum, long pageSize) {
		if(orderByFields==null || (orderByFields=orderByFields.trim()).length()==0) throw new SqlParserException(" the orderByFields is NULL argument! ");
		String sql = dissolver.getBaseSql();
		String[] fields = dissolver.getFields();
		String outSelectSql = dissolver.getOutBracketsSelectSql();
		
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<fields.length; i++) {
			if(sb.length() > 0) sb.append(",");
			sb.append(fields[i]);
		}
		String topfieldsql = sb.toString();
		sql = sql.trim().substring(6).trim();
		String temp_sql = outSelectSql.trim().toLowerCase().replaceAll("[\\s]", " ");
		boolean hasdistinct = temp_sql.substring(0, temp_sql.indexOf(" ")).equals("distinct");
		
		String pagingsql = null;
		sb.delete(0, sb.length());
		if(hasdistinct) {
			if(orderByFields.indexOf('.')>-1) orderByFields = removeAlias(orderByFields);
			sb.append("select top ").append(pageSize).append(" ").append(topfieldsql).append(" from (select row_number() over (")
	            .append("order by ").append(orderByFields).append(") as alias_for_rownumber,").append(topfieldsql).append(" from (select ").append(sql).append(") alias_for_tabalias0) alias_for_tabalias1 ")
	            .append(" where alias_for_rownumber>").append(pageSize).append("*(").append(pageNum-1).append(") order by alias_for_rownumber ");
	    	pagingsql = sb.toString();
		}else {
			sb.append("select top ").append(pageSize).append(" ").append(topfieldsql).append(" from (select row_number() over (")
	            .append("order by ").append(orderByFields).append(") as alias_for_rownumber,").append(sql).append(") alias_for_tabalias0 ")
	            .append(" where alias_for_rownumber>").append(pageSize).append("*(").append(pageNum-1).append(") order by alias_for_rownumber ");
	    	pagingsql = sb.toString();
		}
		return pagingsql;
	}
	
	
	
	
	
	
}



