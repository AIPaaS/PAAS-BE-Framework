package com.binary.core.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;

import com.binary.core.exception.CoreException;


@SuppressWarnings({"rawtypes"})
public abstract class CharUtils {
	
	
	
	public static String toString(Character v) {
		return v==null ? null : v.toString();
	}
	
	
	public static Integer toInteger(Character v) {
		return v==null ? null : new Integer(v.charValue());
	}
	
	public static Long toLong(Character v) {
		return v==null ? null : new Long(v.charValue());
	}
	
	public static Short toShort(Character v) {
		return v==null ? null : new Short((short)v.charValue());
	}
	
	public static Byte toByte(Character v) {
		return v==null ? null : new Byte((byte)v.charValue());
	}
	
	
	public static Double toDouble(Character v) {
		return v==null ? null : new Double(v.charValue());
	}
	
	
	public static Float toFloat(Character v) {
		return v==null ? null : new Float(v.charValue());
	}
	
	public static Boolean toBoolean(Character v) {
		if(v == null) return null;
		char c = v.charValue();
		return Boolean.valueOf(c!='0' && c!='F' && c!='f');
	}
		
	public static BigInteger toBigInteger(Character v) {
		return v==null ? null : new BigInteger(String.valueOf((int)v.charValue()));
	}
	
	public static BigDecimal toBigDecimal(Character v) {
		return v==null ? null : new BigDecimal(String.valueOf((int)v.charValue()));
	}
	
	
	public static <T extends Enum<T>> Enum<T> toEnum(Class<T> enumType, Character v) {
		return v==null ? null : EnumUtils.valueOf(enumType, v.toString());
	}
	
		
	@SuppressWarnings("unchecked")
	public static Object toAny(Character v, Class toclass) {
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
			return v;
		}else if(toclass==boolean.class || toclass==Boolean.class) {
			return toBoolean(v);
		}else if(toclass==String.class) {
			return toString(v);
		}else if(toclass==BigInteger.class) {
			return toBigInteger(v);
		}else if(toclass==BigDecimal.class) {
			return toBigDecimal(v);
		}else if(toclass==java.util.Date.class) {
			return null;
		}else if(toclass==java.sql.Date.class) {
			return null;
		}else if(toclass==java.sql.Time.class) {
			return null;
		}else if(toclass==java.sql.Timestamp.class) {
			return null;
		}else if(Calendar.class.isAssignableFrom(toclass)) {
			return null;
		}else if(Enum.class.isAssignableFrom(toclass)) {
			return toEnum(toclass, v);
		}else {
			throw new CoreException(" is not support toclass:'"+toclass.getName()+"'! ");
		}
	}
	
	
}


