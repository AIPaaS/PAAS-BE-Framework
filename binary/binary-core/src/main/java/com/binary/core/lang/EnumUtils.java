package com.binary.core.lang;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.binary.core.exception.CoreException;
import com.binary.core.exception.BinaryException;


@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class EnumUtils {
	
	
	private static Map<Class<?>, Map<Integer, Enum<?>>> enumkeymap = new HashMap<Class<?>, Map<Integer, Enum<?>>>();
	private static Map<Class<?>, Map<String, Enum<?>>> enumnamemap = new HashMap<Class<?>, Map<String, Enum<?>>>();
	
	
	
	
	private synchronized static Map<?, Enum<?>> parseEnum(Class<?> enumType, boolean iskey) {
		Map<?, Enum<?>> map = iskey ? enumkeymap.get(enumType) : enumnamemap.get(enumType);
		if(map == null) {
			Map<Integer, Enum<?>> keymap = new HashMap<Integer, Enum<?>>();
			Map<String, Enum<?>> namemap = new HashMap<String, Enum<?>>();
			
			Method method = null;
			try {
				method = enumType.getMethod("value");
			} catch (Exception e1) {
			}
			
			Enum<?>[] es = getEnums(enumType);
			if(es!=null && es.length>0) {
				for(int i=0; i<es.length; i++) {
					Enum<?> e = es[i];
					Integer v = null;
					if(method == null) {
						v = e.ordinal();
					}else {
						try {
							Object ov = method.invoke(e);
							if(ov instanceof Integer) v = (Integer)ov;
						} catch (Exception e1) {
						}
					}
					keymap.put(v, e);
					namemap.put(e.name().toUpperCase(), e);
				}
			}
			
			enumkeymap.put(enumType, keymap);
			enumnamemap.put(enumType, namemap);
			
			map = iskey ? keymap : namemap;
		}
		
		return map;
	}
	
	
	
	
	
	/**
	 * 跟据枚举int值获取对应的枚举类型值
	 * @param enumType
	 * @param value
	 * @return
	 */
	public static <T extends Enum<T>> Enum<T> valueOf(Class<T> enumType, Integer value) {
		if(value==null || !Enum.class.isAssignableFrom(enumType)) return null;
		Map<Integer, Enum<?>> map = enumkeymap.get(enumType);
		if(map == null) map = (Map)parseEnum(enumType, true);
		return (Enum)map.get(value);
	}
	
	
	
	/**
	 * 跟据枚举名称值获取对应的枚举类型值
	 * @param enumType
	 * @param name
	 * @return
	 */
	public static <T extends Enum<T>> Enum<T> valueOf(Class<T> enumType, String name) {
		if(!Enum.class.isAssignableFrom(enumType) || name==null||(name=name.trim()).length()==0) return null;
		Map<String, Enum<?>> map = enumnamemap.get(enumType);
		if(map == null) map = (Map)parseEnum(enumType, false);
		return (Enum)map.get(name);
	}
	
	
	
	
	/**
	 * 获取指定枚举类型中的所有枚举值
	 * @param enumType
	 * @return
	 */
	public static <T extends Enum<?>> Enum<?>[] getEnums(Class<?> enumType) {
		if(enumType == null) throw new IllegalArgumentException(" the 'enumType' is null argument! ");
		
		if(!Enum.class.isAssignableFrom(enumType)) {
			throw new BinaryException(" type:'"+enumType.getName()+"' is not enum-type! ");
		}
		
		Enum<?>[] enums = (Enum<?>[])enumType.getEnumConstants();
		
		return enums;
	}
	
	
	
	/**
	 * 获取枚举类型所有名称
	 * @param enumType
	 * @return
	 */
	public static <T extends Enum<?>> String[] getEnumNames(Class<?> enumType) {
		Enum<?>[] enums = getEnums(enumType);
		
		String[] names = new String[enums.length];
		
		for(int i=0; i<enums.length; i++) {
			names[i] = enums[i].name();
		}
		
		return names;
	}
	
	
	
	
	
	public static Integer toInteger(Enum<?> e) {
		return e==null ? null : e.ordinal();
	}
	
	public static Long toLong(Enum<?> e) {
		return e==null ? null : Long.valueOf(e.ordinal());
	}
	
	public static Short toShort(Enum<?> e) {
		return e==null ? null : Short.valueOf((short)e.ordinal());
	}
	
	public static Byte toByte(Enum<?> e) {
		return e==null ? null : Byte.valueOf((byte)e.ordinal());
	}
	
	
	public static Double toDouble(Enum<?> e) {
		return e==null ? null : Double.valueOf(e.ordinal());
	}
	
	
	public static Float toFloat(Enum<?> e) {
		return e==null ? null : Float.valueOf(e.ordinal());
	}
	
	
	public static Character toCharacter(Enum<?> e) {
		return e==null ? null : e.name().charAt(0);
	}
	
	public static Boolean toBoolean(Enum<?> e) {
		if(e == null) return null;
		return e.name().equalsIgnoreCase("true") || e.ordinal()!=0;
	}
	
	public static BigInteger toBigInteger(Enum<?> e) {
		return e==null ? null : BigInteger.valueOf(e.ordinal());
	}
	
	public static BigDecimal toBigDecimal(Enum<?> e) {
		return e==null ? null : BigDecimal.valueOf(e.ordinal());
	}
	
	public static java.util.Date toDate(Enum<?> e) {
		return toDate(e, null);
	}
	
	public static java.util.Date toDate(Enum<?> e, String format) {
		return e==null ? null : StringUtils.toDate(e.name(), format);
	}
	
	public static java.sql.Date toSqlDate(Enum<?> e) {
		return toSqlDate(e, null);
	}
	
	public static java.sql.Date toSqlDate(Enum<?> e, String format) {
		return e==null ? null : StringUtils.toSqlDate(e.name(), format);
	}
	
	public static Time toTime(Enum<?> e) {
		return toTime(e, null);
	}
	
	public static Time toTime(Enum<?> e, String format) {
		return e==null ? null : StringUtils.toTime(e.name(), format);
	}
	
	public static Timestamp toTimestamp(Enum<?> e) {
		return toTimestamp(e, null);
	}
	
	public static Timestamp toTimestamp(Enum<?> e, String format) {
		return e==null ? null : StringUtils.toTimestamp(e.name(), format);
	}
	
	public static Calendar toCalendar(Enum<?> e) {
		return toCalendar(e, null);
	}
	
	public static Calendar toCalendar(Enum<?> e, String format) {
		return e==null ? null : StringUtils.toCalendar(e.name(), format);
	}
	
	
	public static String toString(Enum<?> e) {
		return e==null ? null : e.name();
	}
	
	
	public static Object toAny(Enum<?> e, Class toclass) {
		return toAny(e, null, toclass);
	}
	
	public static Object toAny(Enum<?> v, String format, Class toclass) {
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
			return v;
		}else if(toclass==BigInteger.class) {
			return toBigInteger(v);
		}else if(toclass==BigDecimal.class) {
			return toBigDecimal(v);
		}else if(toclass==java.util.Date.class) {
			return toDate(v, format);
		}else if(toclass==java.sql.Date.class) {
			return toSqlDate(v, format);
		}else if(toclass==java.sql.Time.class) {
			return toTime(v, format);
		}else if(toclass==java.sql.Timestamp.class) {
			return toTimestamp(v, format);
		}else if(toclass==Calendar.class) {
			return toCalendar(v, format);
		}else {
			throw new CoreException(" is not support toclass:'"+toclass.getName()+"'! ");
		}
	}
	
	
	
	
	
	
}


