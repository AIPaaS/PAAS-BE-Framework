package com.binary.core.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;

import com.binary.core.exception.CoreException;


@SuppressWarnings({"rawtypes"})
public abstract class BooleanUtils {
	
	
		
	
	public static boolean is(Boolean v) {
		return v!=null && v.booleanValue();
	}
	
	
	public static String toString(Boolean v) {
		return v==null ? null : v.toString();
	}
	
	
	public static Integer toInteger(Boolean v) {
		return v==null ? null : new Integer(v.booleanValue()?1:0);
	}
	
	public static Long toLong(Boolean v) {
		return v==null ? null : new Long(v.booleanValue()?1:0);
	}
	
	public static Short toShort(Boolean v) {
		return v==null ? null : new Short((short)(v.booleanValue()?1:0));
	}
	
	public static Byte toByte(Boolean v) {
		return v==null ? null : new Byte((byte)(v.booleanValue()?1:0));
	}
	
	
	public static Double toDouble(Boolean v) {
		return v==null ? null : new Double(v.booleanValue()?1:0);
	}
	
	
	public static Float toFloat(Boolean v) {
		return v==null ? null : new Float(v.booleanValue()?1:0);
	}
	
	public static Character toCharacter(Boolean v) {
		return v==null ? null : Character.valueOf(v.booleanValue()?'1':'0');
	}
		
	public static BigInteger toBigInteger(Boolean v) {
		return v==null ? null : new BigInteger(v.booleanValue()?"1":"0");
	}
	
	public static BigDecimal toBigDecimal(Boolean v) {
		return v==null ? null : new BigDecimal(v.booleanValue()?1:0);
	}
	
	
	public static <T extends Enum<T>> Enum<T> toEnum(Class<T> enumType, Boolean v) {
		return v==null ? null : EnumUtils.valueOf(enumType, v.toString());
	}
	
		
	@SuppressWarnings("unchecked")
	public static Object toAny(Boolean v, Class toclass) {
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
			return v;
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
