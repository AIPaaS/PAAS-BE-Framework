package com.binary.jdbc.adapter.support.mssql;

import com.binary.jdbc.adapter.SqlDateFormat;
import com.binary.jdbc.adapter.support.AbstractSqlFunction;

public abstract class AbstractMssqlSqlFunction extends AbstractSqlFunction {
	
	
	
	public String ceil(String value) {
		return "ceiling("+value+")";
	}
	
	public String trunc(String value) {
		return "cast("+value+")";
	}
	
	
	public String log(String value) {
		return "log("+value+")";
	}
	
	
	public String log10(String value) {
		return "log10("+value+")";
	}
	
	
	public String square(String value) {
		return "square("+value+")";
	}
	
	
	public String random() {
		return "rand()";
	}
	
	
	public String pi() {
		return "pi()";
	}
	
	
	public String degrees(String value) {
		return "degrees("+value+")";
	}
	public String radians(String value) {
		return "radians("+value+")";
	}
	
	
	public String isNull(String field, String value) {
		return "isnull("+field+", "+value+")";
	}
	
	public String character(String value) {
		return "char("+value+")";
	}
	
	
	public String link(String... vs) {
		String str = "''";
		for(int i=0; i<vs.length; i++) {
			str += "+"+vs[i];
		}
		return str;
	}
	
	
	public String charindex(String parent, String sub, String count) {
		return "charindex("+sub+","+parent+","+count+")";
	}
	
	
	public String substring(String parent, String start, String length) {
		return "substring("+parent+","+start+","+length+")";
	}
	
	
	public String length(String value) {
		return "len("+value+")";
	}
	
	
	public String trim(String value) {
		return "rtrim(ltrim("+value+"))";
	}
	
	
	public String getDate() {
		return "getDate()";
	}
	
	
	public String getFormat(SqlDateFormat format) {
		return String.valueOf(format.getValue());
	}
	
	
	public String toChar(String datestring, SqlDateFormat format) {
		return "convert(varchar,"+datestring+","+getFormat(format)+")";
	}
	
	
	
	public String toDate(String str, SqlDateFormat format) {
		return "cast("+str+" as datetime)";
	}
	
	
	
}
