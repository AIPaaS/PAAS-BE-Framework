package com.binary.jdbc.adapter.support;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.binary.core.lang.Types;
import com.binary.jdbc.JdbcFactory;
import com.binary.jdbc.adapter.FieldMapping;
import com.binary.jdbc.adapter.JdbcAdapter;
import com.binary.jdbc.adapter.SqlFunction;
import com.binary.jdbc.adapter.SqlParser;
import com.binary.jdbc.exception.JdbcException;

public abstract class AbstractJdbcAdapter implements JdbcAdapter {
	
	private static final Map<String,Method> rsMethods = new HashMap<String,Method>();
	private static final Map<String,Method> psMethods = new HashMap<String,Method>();
	
	static {
		Method[] rms = ResultSet.class.getMethods();
		for(int i=0; i<rms.length; i++) {
			String name = rms[i].getName();
			if(name.equals("getClass")) continue;
			if(!name.substring(0,3).equals("get")) continue ;
			Class<?>[] pts = rms[i].getParameterTypes();
			
			if(pts!=null && pts.length==1 && (pts[0]==int.class || pts[0]==Integer.class)) {
				rsMethods.put(name, rms[i]);
			}
		}
		
		Method[] pms = PreparedStatement.class.getMethods();
		for(int i=0; i<pms.length; i++) {
			String name = pms[i].getName();
			if(!name.substring(0,3).equals("set")) continue ;
			Class<?>[] pts = pms[i].getParameterTypes();
			if(pts!=null && pts.length==2 && (pts[0]==int.class || pts[0]==Integer.class) && (Types.isSupportDBType(pts[1]))) {
				psMethods.put(name, pms[i]);
			}
		}
	}
	
	private final Object syncobj = new Object();
	
	private Map<String,FieldMapping> mapping;
	private Map<Class<?>, Method> javaRSMethods;
	private Map<Class<?>, Method> javaPSMethods;
	

	
	@Override
	public SqlFunction getSqlFunction() {
		return JdbcFactory.getSqlFunction(getJdbcType());
	}

	
	@Override
	public SqlParser getSqlParser() {
		return JdbcFactory.getSqlParser(getJdbcType());
	}
	
	
	
	
	protected Map<String,FieldMapping> getFieldMappings() {
		if(this.mapping == null) {
			synchronized (syncobj) {
				if(this.mapping == null) {
					this.mapping = newFieldMappings();
				}
			}
		}
		return this.mapping;
	}
	
	
	protected Map<Class<?>, Method> getJavaRSMethods() {
		if(this.javaRSMethods == null) {
			synchronized (syncobj) {
				if(this.javaRSMethods == null) {
					this.javaRSMethods = newJavaRSMethods();
				}
			}
		}
		return this.javaRSMethods;
	}
	
	protected Map<Class<?>, Method> getJavaPSMethods() {
		if(this.javaPSMethods == null) {
			synchronized (syncobj) {
				if(this.javaPSMethods == null) {
					this.javaPSMethods = newJavaPSMethods();
				}
			}
		}
		return this.javaPSMethods;
	}
	
	protected abstract Map<String,FieldMapping> newFieldMappings();
	
		
	protected Map<Class<?>, Method> newJavaRSMethods() {
		Map<Class<?>, Method> map = new HashMap<Class<?>, Method>();
		map.put(int.class, rsMethods.get("getInt"));
		map.put(long.class, rsMethods.get("getLong"));
		map.put(short.class, rsMethods.get("getShort"));
		map.put(byte.class, rsMethods.get("getByte"));
		map.put(double.class, rsMethods.get("getDouble"));
		map.put(float.class, rsMethods.get("getFloat"));
		map.put(char.class, rsMethods.get("getString"));
		map.put(boolean.class, rsMethods.get("getBoolean"));
		map.put(Integer.class, rsMethods.get("getBigDecimal"));
		map.put(Long.class, rsMethods.get("getBigDecimal"));
		map.put(Short.class, rsMethods.get("getBigDecimal"));
		map.put(Byte.class, rsMethods.get("getBigDecimal"));
		map.put(Double.class, rsMethods.get("getBigDecimal"));
		map.put(Float.class, rsMethods.get("getBigDecimal"));
		map.put(Character.class, rsMethods.get("getString"));
		map.put(Boolean.class, rsMethods.get("getBoolean"));
		map.put(String.class, rsMethods.get("getString"));
		map.put(BigInteger.class, rsMethods.get("getBigDecimal"));
		map.put(BigDecimal.class, rsMethods.get("getBigDecimal"));
		map.put(java.util.Date.class, rsMethods.get("getDate"));
		map.put(java.sql.Date.class, rsMethods.get("getDate"));
		map.put(java.sql.Time.class, rsMethods.get("getTime"));
		map.put(java.sql.Timestamp.class, rsMethods.get("getTimestamp"));
		map.put(Calendar.class, rsMethods.get("getTimestamp"));
		map.put(byte[].class, rsMethods.get("getBytes"));
		map.put(Byte[].class, rsMethods.get("getBytes"));
		map.put(Clob.class, rsMethods.get("getClob"));
		map.put(Blob.class, rsMethods.get("getBlob"));
		map.put(NClob.class, rsMethods.get("getNClob"));
		return map;
	}
	
	
	protected Map<Class<?>, Method> newJavaPSMethods() {
		Map<Class<?>, Method> map = new HashMap<Class<?>, Method>();
		map.put(int.class, psMethods.get("setInt"));
		map.put(long.class, psMethods.get("setLong"));
		map.put(short.class, psMethods.get("setShort"));
		map.put(byte.class, psMethods.get("setByte"));
		map.put(double.class, psMethods.get("setDouble"));
		map.put(float.class, psMethods.get("setFloat"));
		map.put(char.class, psMethods.get("setString"));
		map.put(boolean.class, psMethods.get("setBoolean"));
		map.put(Integer.class, psMethods.get("setInt"));
		map.put(Long.class, psMethods.get("setLong"));
		map.put(Short.class, psMethods.get("setShort"));
		map.put(Byte.class, psMethods.get("setByte"));
		map.put(Double.class, psMethods.get("setDouble"));
		map.put(Float.class, psMethods.get("setFloat"));
		map.put(Character.class, psMethods.get("setString"));
		map.put(Boolean.class, psMethods.get("setBoolean"));
		map.put(String.class, psMethods.get("setString"));
		map.put(BigInteger.class, psMethods.get("setBigDecimal"));
		map.put(BigDecimal.class, psMethods.get("setBigDecimal"));
		map.put(java.util.Date.class, psMethods.get("setDate"));
		map.put(java.sql.Date.class, psMethods.get("setDate"));
		map.put(java.sql.Time.class, psMethods.get("setTime"));
		map.put(java.sql.Timestamp.class, psMethods.get("setTimestamp"));
		map.put(Calendar.class, psMethods.get("setTimestamp"));
		map.put(byte[].class, psMethods.get("setBytes"));
		map.put(Byte[].class, psMethods.get("setBytes"));
		map.put(Clob.class, psMethods.get("setClob"));
		map.put(Blob.class, psMethods.get("setBlob"));
		map.put(NClob.class, psMethods.get("setNClob"));
		
		return map;
	}
	

	
	private String getRSMethodName(String dbtype) {
		FieldMapping type = getFieldMapping(dbtype);
		return type==null ? "getObject" : type.getRsGetMethod();
	}
	
	
	

	
	private FieldMapping getFieldMapping(String dbtype) {
		return getFieldMappings().get(dbtype.trim().toUpperCase());
	}

	
	

	
	@Override
	public Method getRSMethod(String dbtype) {
		String methodName = getRSMethodName(dbtype);
		Method method = rsMethods.get(methodName);
		if(method == null) throw new JdbcException(" is not found ResultSetMethod:'"+methodName+"'!");
		return method;
	}
	

	

	
	@Override
	public Method getJavaRSMethod(Class<?> javaFieldType) {
		Map<Class<?>, Method> rsms = getJavaRSMethods();
		Method method = rsms.get(javaFieldType);
		if(method == null) {
			if(Calendar.class.isAssignableFrom(javaFieldType)) {
				method = rsms.get(Calendar.class);
			}else if(Clob.class.isAssignableFrom(javaFieldType)) {
				method = rsms.get(Clob.class);
			}else if(Blob.class.isAssignableFrom(javaFieldType)) {
				method = rsms.get(Blob.class);
			}else if(NClob.class.isAssignableFrom(javaFieldType)) {
				method = rsms.get(NClob.class);
			}
		}
		if(method == null) method = rsMethods.get("getObject");
		return method;
	}
	


	

	
	@Override
	public PreparedStatement prepareQueryStatement(Connection conn, String sql) {
		try {
			return conn.prepareStatement(sql);
		} catch (SQLException e) {
			throw new JdbcException(e);
		}
	}
	
	
	

	
	@Override
	public void setPreparedStatementParams(PreparedStatement ps, Object[] params) {
		if(params==null || params.length==0) return ;
		try {
			for(int i=0; i<params.length; i++) {
				if(params[i]==null) {
					ps.setString(i+1, null);
				}else if((params[i] instanceof String) && ((String)params[i]).length()==0) {
					ps.setString(i+1, null);
				}else {
					if(params[i].getClass() == java.util.Date.class) {
						ps.setObject(i+1, new java.sql.Date(((java.util.Date)params[i]).getTime()));
					}else {
						ps.setObject(i+1, params[i]);
					}
				}
			}
		}catch(SQLException e) {
			throw new JdbcException(e);
		}
	}
	

	
	@Override
	public ResultSet executeQuery(PreparedStatement ps) {
		try {
			return ps.executeQuery();
		} catch (SQLException e) {
			throw new JdbcException(e);
		}
	}

	
	@Override
	public int executeUpdate(PreparedStatement ps) {
		try {
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw new JdbcException(e);
		}
	}

	
	@Override
	public int[] executeBatch(PreparedStatement ps) {
		try {
			return ps.executeBatch();
		} catch (SQLException e) {
			throw new JdbcException(e);
		}
	}
	

	
	@Override
	public void closePreparedStatement(PreparedStatement ps) {
		try {
			if(ps != null) ps.close();
		} catch (SQLException e) {
			throw new JdbcException(e);
		}
	}
	
	
	@Override
	public void closeResultSet(ResultSet rs) {
		try {
			if(rs != null) rs.close();
		} catch (SQLException e) {
			throw new JdbcException(e);
		}
	}
	
		
	
	
	
	
}
