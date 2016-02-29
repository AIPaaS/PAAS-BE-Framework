package com.binary.core.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Calendar;

import com.binary.core.exception.CoreException;

@SuppressWarnings({"rawtypes"})
public abstract class NumberUtils {
	
	
	
	
	
	public static String toString(Number v) {
		return toString(v, null);
	}
	
	public static String toString(Number v, String format) {
		return v==null ? null : (format!=null ? new DecimalFormat(format).format(v) : v.toString());
	}
	
	public static Integer toInteger(Number v) {
		return v==null||(v instanceof Integer) ? (Integer)v : v.intValue();
	}
	
	public static Long toLong(Number v) {
		return v==null||(v instanceof Long) ? (Long)v : v.longValue();
	}
	
	public static Short toShort(Number v) {
		return v==null||(v instanceof Short) ? (Short)v : v.shortValue();
	}
	
	public static Byte toByte(Number v) {
		return v==null||(v instanceof Byte) ? (Byte)v : v.byteValue();
	}
	
	
	public static Double toDouble(Number v) {
		return v==null||(v instanceof Double) ? (Double)v : v.doubleValue();
	}
	
	
	public static Float toFloat(Number v) {
		return v==null||(v instanceof Float) ? (Float)v : v.floatValue();
	}
	
	public static Character toCharacter(Number v) {
		return v==null ? null : Character.valueOf((char)v.intValue());
	}
	
	public static Boolean toBoolean(Number v) {
		return v==null ? null : Boolean.valueOf(v.intValue()!=0);
	}
	
	public static BigInteger toBigInteger(Number v) {
		return v==null||(v instanceof BigInteger) ? (BigInteger)v : new BigInteger(v.toString());
	}
	
	public static BigDecimal toBigDecimal(Number v) {
		return v==null||(v instanceof BigDecimal) ? (BigDecimal)v : new BigDecimal(v.toString());
	}
	
	public static java.util.Date toDate(Number v) {
		return v==null ? null : new java.util.Date(v.longValue());
	}
	
	
	public static java.sql.Date toSqlDate(Number v) {
		return v==null ? null : new java.sql.Date(v.longValue());
	}
	
	
	public static Time toTime(Number v) {
		return v==null ? null : new java.sql.Time(v.longValue());
	}
	
	
	public static Timestamp toTimestamp(Number v) {
		return v==null ? null : new java.sql.Timestamp(v.longValue());
	}
	
	
	public static Calendar toCalendar(Number v) {
		java.util.Date d = toDate(v);
		if(d == null) return null;
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c;
	}
	
	
	
	public static <T extends Enum<T>> Enum<T> toEnum(Class<T> enumType, Number v) {
		return v==null ? null : EnumUtils.valueOf(enumType, v.intValue());
	}
	
	
	
	public static Object toAny(Number v, Class toclass) {
		return toAny(v, toclass, null);
	}
	
	@SuppressWarnings("unchecked")
	public static Object toAny(Number v, Class toclass, String format) {
		if(v==null || toclass==null) return null;
		if(toclass==int.class || toclass==Integer.class) {
			return toInteger(v);
		}else if(toclass==long.class || toclass==Long.class) {
			return toLong(v);
		}else if(toclass==short.class || toclass==Short.class) {
			return toShort(v);
		}else if(toclass==byte.class || toclass==Byte.class) {
			return toByte(v);
		}else if(toclass==double.class || toclass==Double.class) {
			return toDouble(v);
		}else if(toclass==float.class || toclass==Float.class) {
			return toFloat(v);
		}else if(toclass==char.class || toclass==Character.class) {
			return toCharacter(v);
		}else if(toclass==boolean.class || toclass==Boolean.class) {
			return toBoolean(v);
		}else if(toclass==String.class) {
			return toString(v, format);
		}else if(toclass==BigInteger.class) {
			return toBigInteger(v);
		}else if(toclass==BigDecimal.class) {
			return toBigDecimal(v);
		}else if(toclass==java.util.Date.class) {
			return toDate(v);
		}else if(toclass==java.sql.Date.class) {
			return toSqlDate(v);
		}else if(toclass==java.sql.Time.class) {
			return toTime(v);
		}else if(toclass==java.sql.Timestamp.class) {
			return toTimestamp(v);
		}else if(toclass==Calendar.class) {
			return toCalendar(v);
		}else if(Enum.class.isAssignableFrom(toclass)) {
			return toEnum(toclass, v);
		}else {
			throw new CoreException(" is not support toclass:'"+toclass.getName()+"'! ");
		}
	}
	
	
	
}
