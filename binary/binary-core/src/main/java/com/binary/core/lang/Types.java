package com.binary.core.lang;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.NClob;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;

import com.binary.core.exception.CoreException;

@SuppressWarnings({"rawtypes"})
public abstract class Types {
	
	
	public static final Class<?>[] EMPTY = new Class<?>[0];
	
	
		
	/**
	 * 判断当类型是否是当前系统所支持的类型
	 * @param c
	 * @return
	 */
	public static boolean isSupport(Class c) {
		return isPrimitive(c) || isPrimitiveObject(c) || isExtPrimitiveObject(c);
	}
	
	
	/**
	 * 是否支持数据库类型
	 * @param c
	 * @return
	 */
	public static boolean isSupportDBType(Class c) {
		return isSupport(c) || isDBExtType(c);
	}
	
	
	/**
	 * 判断类型是否是基础类型
	 * @param c
	 * @return
	 */
	public static boolean isPrimitive(Class c) {
		return c==int.class || c==long.class || c==short.class || c==byte.class || c==double.class || c==float.class || c==char.class || c==boolean.class;
	}
	
	
	/**
	 * 判断类型是否是基础封装类型
	 * @param c
	 * @return
	 */
	public static boolean isPrimitiveObject(Class c) {
		return c==Integer.class || c==Long.class || c==Short.class || c==Byte.class || c==Double.class || c==Float.class || c==Character.class || c==Boolean.class;
	}
	
	
	/**
	 * 判断类型是否是当前系统所支持的扩展类型
	 * @param c
	 * @return
	 */
	public static boolean isExtPrimitiveObject(Class c) {
		return c==String.class || c==BigInteger.class || c==BigDecimal.class
				|| c==java.util.Date.class || c==java.sql.Date.class || c==java.sql.Time.class || c==java.sql.Timestamp.class || Calendar.class.isAssignableFrom(c)
				|| Enum.class.isAssignableFrom(c);
	}
	
	
	/**
	 * 判断类型是否是基础类型数组
	 * @param c
	 * @return
	 */
	public static boolean isPrimitiveArray(Class c) {
		if(!c.isArray()) return false;
		return isPrimitive(c.getComponentType());
	}
	
	
	
	
	/**
	 * 判断类型是否是基础封装类型数组
	 * @param c
	 * @return
	 */
	public static boolean isPrimitiveObjectArray(Class c) {
		if(!c.isArray()) return false;
		return isPrimitiveObject(c.getComponentType());
	}
	
	
	
	/**
	 * 判断类型是否是当前系统所支持的扩展类型数组
	 * @param c
	 * @return
	 */
	public static boolean isExtPrimitiveObjectArray(Class c) {
		if(!c.isArray()) return false;
		return isExtPrimitiveObject(c.getComponentType());
	}
	
	
	
	/**
	 * 判断类型是否是日期类型
	 * @param c
	 * @return
	 */
	public static boolean isDate(Class c) {
		return c==java.util.Date.class || c==java.sql.Date.class || c==java.sql.Time.class || c==java.sql.Timestamp.class || Calendar.class.isAssignableFrom(c);
	}
	
	
	
	/**
	 * 判断类型是否是数值类型
	 * @param c
	 * @return
	 */
	public static boolean isNumber(Class c) {
		return c==int.class || c==long.class || c==short.class || c==byte.class || c==double.class || c==float.class
			|| c==Integer.class || c==Long.class || c==Short.class || c==Byte.class || c==Double.class || c==Float.class
			|| c==BigInteger.class || c==BigDecimal.class;
	}
	
	
	/**
	 * 是否是数据库扩展类型
	 * @param c
	 * @return
	 */
	public static boolean isDBExtType(Class c) {
		return c==byte[].class || c==Byte[].class
				|| Clob.class.isAssignableFrom(c) || Blob.class.isAssignableFrom(c) || NClob.class.isAssignableFrom(c);
	}
	
	
	/**
	 * 判断类型是否是小数类型
	 * @param c
	 * @return
	 */
	public static boolean isDecimal(Class c) {
		return c==double.class || c==float.class || c==Double.class || c==Float.class;
	}
	
	
	/**
	 * 判断是否是枚举类型
	 * @param c
	 * @return
	 */
	public static boolean isEnum(Class c) {
		return Enum.class.isAssignableFrom(c);
	}
	
	
	
	/**
	 * 判断类型是否是Bean
	 * @param c
	 * @return
	 */
	public static boolean isBean(Class c) {
		return !isSupport(c) && !c.isArray() && !Collection.class.isAssignableFrom(c) && !Map.class.isAssignableFrom(c) && !Enum.class.isAssignableFrom(c);
	}
	
	
	
	/**
	 * 跟据封装类型来获取对应的基础类型
	 * @param c
	 * @return
	 */
	public static Class getPrimitiveClass(Class c) {
		if(isPrimitive(c)) return c;
		if(!isPrimitiveObject(c)) throw new CoreException(" is not PrimitiveObject:'"+c.getName()+"'!");
		if(c == Integer.class) {
			return int.class;
		}else if(c == Long.class) {
			return long.class;
		}else if(c == Short.class) {
			return short.class;
		}else if(c == Byte.class) {
			return byte.class;
		}else if(c == Double.class) {
			return double.class;
		}else if(c == Float.class) {
			return float.class;
		}else if(c == Character.class) {
			return char.class;
		}else if(c == Boolean.class) {
			return boolean.class;
		}else {
			return null;
		}
	}
	
	
	
	/**
	 * 跟据基础类型来获取对应的封装类型
	 * @param c
	 * @return
	 */
	public static Class getPrimitiveObjectClass(Class c) {
		if(isPrimitiveObject(c)) return c;
		if(!isPrimitive(c)) throw new CoreException(" is not Primitive:'"+c.getName()+"'!");
		if(c == int.class) {
			return Integer.class;
		}else if(c == long.class) {
			return Long.class;
		}else if(c == short.class) {
			return Short.class;
		}else if(c == byte.class) {
			return Byte.class;
		}else if(c == double.class) {
			return Double.class;
		}else if(c == float.class) {
			return Float.class;
		}else if(c == char.class) {
			return Character.class;
		}else if(c == boolean.class) {
			return Boolean.class;
		}else {
			return null;
		}
	}
	
	
	/**
	 * 获取c的泛型类型, 如果c有接口则从第一个接口中取，否则从父类中取
	 * @param c
	 * @return
	 */
	public static Type getGenericType(Class<?> c) {
		Type[] ts = c.getGenericInterfaces();
		Type type = ts.length>0 ? ts[0] : c.getGenericSuperclass();
		if(type instanceof ParameterizedType) {
			return getGenericType((ParameterizedType) type);
		}else {
			return Object.class;
		}
	}
	
	/**
	 * 获取c的泛型类型
	 * @param c
	 * @param superclass: 指定是从父类还是父接口中取, true=父类, false=接口
	 * @param index: 如果是从接口中找泛型, 则指定第几个接口
	 * @return
	 */
	public static Type getGenericType(Class<?> c, boolean superclass, int index) {
		Type type = superclass ? c.getGenericSuperclass() : c.getGenericInterfaces()[index];
		if(type instanceof ParameterizedType) {
			return getGenericType((ParameterizedType) type);
		}else {
			return Object.class;
		}
	}
	
	/**
	 * 获取泛型类型
	 * @param pt
	 * @return
	 */
	public static Type getGenericType(ParameterizedType pt) {
		Type[] ts = pt.getActualTypeArguments();
		return ts.length>0 ? ts[0] : Object.class;
	}
	
	
	
	
	
	
	
}


