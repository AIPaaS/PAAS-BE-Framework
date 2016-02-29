package com.binary.core.lang;



public enum SimpleType {
	
	Integer,
	Long,
	Short,
	Byte,
	Double,
	Float,
	Character,
	Boolean,
	String,
	BigInteger,
	BigDecimal,
	Date,
	SqlDate,
	Time,
	Timestamp,
	Calendar;
	
	
	
	public Class<?> getJavaType() {
		switch(this) {
			case Integer: return java.lang.Integer.class;
			case Long: return java.lang.Long.class;
			case Short: return java.lang.Short.class;
			case Byte: return java.lang.Byte.class;
			case Double: return java.lang.Double.class;
			case Float: return java.lang.Float.class;
			case Character: return java.lang.Character.class;
			case Boolean: return java.lang.Boolean.class;
			case String: return java.lang.String.class;
			case BigInteger: return java.math.BigInteger.class;
			case BigDecimal: return java.math.BigDecimal.class;
			case Date: return java.util.Date.class;
			case SqlDate: return java.sql.Date.class;
			case Time: return java.sql.Time.class;
			case Timestamp: return java.sql.Timestamp.class;
			case Calendar: return java.util.Calendar.class;
			default: return null;
		}
	}
	
	
	
	
	
}




