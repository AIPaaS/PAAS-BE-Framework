package com.binary.jdbc.adapter.support;

import com.binary.jdbc.adapter.SqlFunction;

public abstract class AbstractSqlFunction implements SqlFunction {
	
	
	
	public String abs(String value) {
		return "abs("+value+")";
	}
	
	
	public String floor(String value) {
		return "floor("+value+")";
	}
	
	
	public String round(String value) {
		return "round("+value+")";
	}
	
	
	public String exp(String value) {
		return "exp("+value+")";
	}
	
	
	public String sqrt(String value) {
		return "sqrt("+value+")";
	}
	
	
	public String power(String n, String e) {
		return "sqrt("+n+","+e+")";
	}
	
	
	public String sign(String value) {
		return "sign("+value+")";
	}
	
	public String sin(String value) {
		return "sin("+value+")";
	}
	public String cos(String value) {
		return "cos("+value+")";
	}
	public String tan(String value) {
		return "tan("+value+")";
	}
	
	
	public String asin(String value) {
		return "asin("+value+")";
	}
	public String acos(String value) {
		return "acos("+value+")";
	}
	public String atan(String value) {
		return "atan("+value+")";
	}
	
	
	
	public String ascii(String value) {
		return "ascii("+value+")";
	}
	
	
	public String replace(String parent, String oldsub, String newsub) {
		return "replace("+parent+","+oldsub+","+newsub+")";
	}
	
	
	
	public String lower(String value) {
		return "lower("+value+")";
	}
	public String upper(String value) {
		return "upper("+value+")";
	}
	
	
	public String ltrim(String value) {
		return "ltrim("+value+")";
	}
	public String rtrim(String value) {
		return "rtrim("+value+")";
	}
	
	
	public String decode(String ...vs) {
		String fun = "(case "+vs[0];
		int length = vs.length%2==0 ? vs.length-1 : vs.length;
		for(int i=1; i<length; i++) {
			fun += (i%2==1 ? " when "+vs[i] : " then "+vs[i]);
		}
		if(length<vs.length) fun += " else "+vs[length];
		fun += " end)";
		return fun;
	}
	
	
	
}
