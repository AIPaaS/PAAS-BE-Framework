package com.binary.jdbc.adapter;

import java.io.Serializable;
import java.util.LinkedList;

import com.binary.core.lang.ArrayUtils;
import com.binary.core.lang.StringMap;
import com.binary.core.lang.StringUtils;
import com.binary.jdbc.exception.JdbcException;


/**
 * SQL容解器
 */
public class SqlDissolver implements Serializable {
	private static final long serialVersionUID = -8913800637220747222L;
	
	
	/**
	 * 原始SQL
	 */
	private String baseSql;
	
	
	/**
	 * 去除括号内的子SQL
	 */
	private String outBracketsSql;
	
	
	/**
	 * 括号内部的子串, 如果SQL不带括号, 则此属性值为NULL
	 */
	private String[] inBracketsSubs;
	
	
	/**
	 * SQL中select子句
	 */
	private String selectSql;
	
	
	/**
	 * SQL中from子句
	 */
	private String fromSql;
	
	
	/**
	 * 去除括号内的子select
	 */
	private String outBracketsSelectSql;
	
	
	
	/**
	 * 去除括号内的子from
	 */
	private String outBracketsFromSql;
	
	
	/**
	 * 查询字段
	 */
	private String[] fields;
	
	
	public SqlDissolver(String sql) {
		dissolve(sql);
	}
	
	
	private void dissolve(String sql) {
		this.baseSql = sql;
		
		if(sql.indexOf("(") < 0) {
			this.outBracketsSql = sql;
		}else {
			StringMap sm = StringUtils.parseOutlimit(sql, "(", ")");
			this.outBracketsSql = sm.getString();
			this.inBracketsSubs = sm.getKeys();
		}
		
		String fromkey = " from ";
		this.outBracketsSql = this.outBracketsSql.replaceAll("[\\s]+[f|F][r|R][o|O][m|M][\\s]+", fromkey);
		int count = StringUtils.getFrequence(this.outBracketsSql, fromkey);
		if(count == 0) throw new JdbcException(" is not found key:' from '! is invalid SQL："+sql);
		if(count > 1) throw new JdbcException(" is multiple key:' from '! is invalid SQL："+sql);
		
		LinkedList<String> keyList = null;
		if(this.inBracketsSubs != null) {
			keyList = new LinkedList<String>();
			ArrayUtils.toList(this.inBracketsSubs, keyList);
		}
		StringBuilder sb = new StringBuilder();
		
		this.outBracketsSelectSql = this.outBracketsSql.substring(0, this.outBracketsSql.indexOf(fromkey));
		this.outBracketsFromSql = this.outBracketsSql.substring(this.outBracketsSql.indexOf(fromkey));
		
		if(keyList == null) {
			this.selectSql = this.outBracketsSelectSql;
			this.fromSql = this.outBracketsFromSql;
		}else {
			sb.delete(0, sb.length());
			fillBrackets(sb, this.outBracketsSelectSql, keyList);
			this.selectSql = sb.toString();
			
			sb.delete(0, sb.length());
			fillBrackets(sb, this.outBracketsFromSql, keyList);
			this.fromSql = sb.toString();
		}
		
		
		String select = this.outBracketsSelectSql.replaceAll("[\\s]", " ").trim();
		String[] subs = select.split(",");
		this.fields = new String[subs.length];
		for(int i=0; i<subs.length; i++) {
			String f = subs[i].trim();
			if(f.indexOf(' ') > -1) f = f.substring(f.lastIndexOf(' ')+1);
			if(f.indexOf('.') > -1) f = f.substring(f.lastIndexOf('.')+1);
			if(f.length()==0 || f.indexOf('(')>-1 || f.indexOf(')')>-1 || f.indexOf('\'')>-1) {
				throw new JdbcException(" is can not parse fields! is invalid SQL："+sql);
			}
			this.fields[i] = f;
		}
	}
	
	
	private void fillBrackets(StringBuilder sb, String outBrackets, LinkedList<String> inBracketsSubs) {
		int index = -1;
		while((index=outBrackets.indexOf('(')) > -1) {
			sb.append(outBrackets.substring(0, index+1)).append(inBracketsSubs.getFirst()).append(')');
			outBrackets = outBrackets.substring(index+1);
			outBrackets = outBrackets.substring(outBrackets.indexOf(')')+1);
			inBracketsSubs.removeFirst();
		}
		if(outBrackets.length()>0) sb.append(outBrackets);
	}

	

	public String getBaseSql() {
		return baseSql;
	}


	public String getOutBracketsSql() {
		return outBracketsSql;
	}


	public String[] getInBracketsSubs() {
		return inBracketsSubs;
	}


	public String getSelectSql() {
		return selectSql;
	}


	public String getFromSql() {
		return fromSql;
	}


	public String getOutBracketsSelectSql() {
		return outBracketsSelectSql;
	}


	public String getOutBracketsFromSql() {
		return outBracketsFromSql;
	}


	public String[] getFields() {
		return fields;
	}
	
	
	
	
	
	
	
}


