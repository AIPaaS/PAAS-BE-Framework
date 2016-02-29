package com.binary.jdbc.adapter.support;

import com.binary.jdbc.adapter.SqlDissolver;
import com.binary.jdbc.adapter.SqlParser;
import com.binary.jdbc.exception.SqlParserException;


public abstract class AbstractSqlParser implements SqlParser {
	
	
	
	protected void validateTableInfo(String tableName, String[] fields, String[] alias) {
		if(tableName==null || tableName.length()==0) throw new SqlParserException(" the tableName is emtpy argument! ");
		if(fields==null || fields.length==0) throw new SqlParserException(" the fields is empty argument! ");
		if(alias!=null && alias.length!=fields.length) throw new SqlParserException(" the fields.length!=alias.length ! ");
	}
	
	
	protected String[] switchFields(String[] fields, String[] alias) {
		String[] selfields = null;
		if(alias == null) {
			selfields = fields;
		}else {
			selfields = new String[fields.length];
			for(int i=0; i<fields.length; i++) {
				String s = alias[i];
				selfields[i] = s!=null&&(s=s.trim()).length()>0 ? s : fields[i];
			}
		}
		return selfields;
	}
	
	

	@Override
	public String[] parseSqlFields(String sql) {
		SqlDissolver dissolver = new SqlDissolver(sql);
		return dissolver.getFields();
	}
	
	
	
	
	@Override
	public String parsePagingSql(String sql, String orderByFields,long pageNum, long pageSize) {
		SqlDissolver dissolver = new SqlDissolver(sql);
		return parsePagingSql(dissolver, orderByFields, pageNum, pageSize);
	}
	
	
	
	
}







