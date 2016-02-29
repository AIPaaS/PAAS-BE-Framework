package com.binary.core.lang;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.binary.core.exception.CoreException;

@SuppressWarnings({"unchecked","rawtypes"})
public abstract class ArrayUtils {
	
	
	
	
	public static String toString(Object array) {
		return toString(array, null);
	}
	
	public static String toString(Object array, String toformat) {
		if(array==null) return null;
		Class vclass = array.getClass();
		if(!vclass.isArray()) throw new CoreException(" is not Array:'"+vclass.getName()+"'!");
		Class innerclass = vclass.getComponentType();
		if(!Types.isSupport(innerclass)) {
			throw new CoreException(" is not support innerclass:'"+innerclass.getName()+"'! ");
		}
		int length = getLength(array);
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<length; i++) {
			if(i > 0) sb.append(",");
			sb.append(Conver.to(Array.get(array, i), String.class, toformat));
		}
		return sb.toString();
	}
	
	
	/**
	 * 获取数组长度
	 * @param array
	 * @return
	 */
	public static int getLength(Object array) {
		if(array==null) return 0;
		Class c = array.getClass();
		if(!c.isArray()) throw new CoreException(" is not Array:'"+c.getName()+"'!");
		return Array.getLength(array);
	}
	
	/**
	 * 将封装类型数据转换成基出类型数据
	 * @param value
	 * @return
	 */
	public static Object toPrimitiveArray(Object v) {
		if(v==null) return null;
		Class c = v.getClass();
		if(!c.isArray()) throw new CoreException(" is not PrimitiveObjectArray:'"+c.getName()+"'!");
		Class innerc = c.getComponentType();
		if(!Types.isPrimitiveObject(innerc)) throw new CoreException(" is not PrimitiveObjectArray:'"+innerc.getName()+"'!");
		Class toclass = Types.getPrimitiveClass(innerc);
		Object returnarray = Array.newInstance(toclass, Array.getLength(v));
		if(toclass == int.class) {
			int[] array = (int[])returnarray;
			Integer[] vs = (Integer[])v;
			for(int m=0; m<array.length; m++) array[m]=vs[m].intValue();
		}else if(toclass == long.class) {
			long[] array = (long[])returnarray;
			Long[] vs = (Long[])v;
			for(int m=0; m<array.length; m++) array[m]=vs[m].longValue();
		}else if(toclass == short.class) {
			short[] array = (short[])returnarray;
			Short[] vs = (Short[])v;
			for(int m=0; m<array.length; m++) array[m]=vs[m].shortValue();
		}else if(toclass == byte.class) {
			byte[] array = (byte[])returnarray;
			Byte[] vs = (Byte[])v;
			for(int m=0; m<array.length; m++) array[m]=vs[m].byteValue();
		}else if(toclass == double.class) {
			double[] array = (double[])returnarray;
			Double[] vs = (Double[])v;
			for(int m=0; m<array.length; m++) array[m]=vs[m].doubleValue();
		}else if(toclass == float.class) {
			float[] array = (float[])returnarray;
			Float[] vs = (Float[])v;
			for(int m=0; m<array.length; m++) array[m]=vs[m].floatValue();
		}else if(toclass == char.class) {
			char[] array = (char[])returnarray;
			Character[] vs = (Character[])v;
			for(int m=0; m<array.length; m++) array[m]=vs[m].charValue();
		}else if(toclass == boolean.class) {
			boolean[] array = (boolean[])returnarray;
			Boolean[] vs = (Boolean[])v;
			for(int m=0; m<array.length; m++) array[m]=vs[m].booleanValue();
		}
		return returnarray;
	}
	
	
	
	/**
	 * 装基础类型数据转换成封装类型数组
	 * @param value
	 * @return
	 */
	public static Object[] toPrimitiveObjectArray(Object v) {
		if(v==null) return null;
		Class c = v.getClass();
		if(!c.isArray()) throw new CoreException(" is not PrimitiveArray:'"+c.getName()+"'!");
		Class type = c.getComponentType();
		if(!Types.isPrimitive(type)) throw new CoreException(" is not PrimitiveArray:'"+c.getName()+"'!");
		Class totype = Types.getPrimitiveObjectClass(type);
		Object[] returnArray = (Object[]) Array.newInstance(totype, Array.getLength(v));
		if(type == int.class) {
			int[] array = (int[])v;
			for(int m=0; m<array.length; m++)returnArray[m]=Integer.valueOf(array[m]);
		}else if(type == long.class) {
			long[] array = (long[])v;
			for(int m=0; m<array.length; m++)returnArray[m]=Long.valueOf(array[m]);
		}else if(type == short.class) {
			short[] array = (short[])v;
			for(int m=0; m<array.length; m++)returnArray[m]=Short.valueOf(array[m]);
		}else if(type == byte.class) {
			byte[] array = (byte[])v;
			for(int m=0; m<array.length; m++)returnArray[m]=Byte.valueOf(array[m]);
		}else if(type == double.class) {
			double[] array = (double[])v;
			for(int m=0; m<array.length; m++)returnArray[m]=Double.valueOf(array[m]);
		}else if(type == float.class) {
			float[] array = (float[])v;
			for(int m=0; m<array.length; m++)returnArray[m]=Float.valueOf(array[m]);
		}else if(type == char.class) {
			char[] array = (char[])v;
			for(int m=0; m<array.length; m++)returnArray[m]=Character.valueOf(array[m]);
		}else if(type == boolean.class) {
			boolean[] array = (boolean[])v;
			for(int m=0; m<array.length; m++)returnArray[m]=Boolean.valueOf(array[m]);
		}
		return returnArray;
	}
	
	
	public static Object toAny(Object v, Class toclass) {
		return toAny(v, toclass, null);
	}
	
	public static Object toAny(Object v, Class toclass, String toformat) {
		if(v==null || toclass==null) return null;
		Class vclass = v.getClass();
		if(!vclass.isArray()) throw new CoreException(" is not Array:'"+vclass.getName()+"'!");
		int length = getLength(v);
		Class innerclass = vclass.getComponentType();
		if(toclass.isAssignableFrom(innerclass)) return v;
		if(!Types.isSupport(innerclass)) {
			throw new CoreException(" is not support innerclass:'"+innerclass.getName()+"'! ");
		}
		if(!Types.isSupport(toclass)) {
			throw new CoreException(" is not support toclass:'"+toclass.getName()+"'! ");
		}
		Object returnarray = Array.newInstance(toclass, length);
		if(innerclass == String.class) {
			String[] array = (String[])v;
			for(int m=0; m<length; m++)Array.set(returnarray, m, StringUtils.toAny(array[m], toclass));
		}else if(innerclass == Character.class || innerclass==char.class) {
			Character[] array = innerclass==Character.class ? (Character[])v : (Character[])toPrimitiveObjectArray(v);
			for(int m=0; m<length; m++)Array.set(returnarray, m, CharUtils.toAny(array[m], toclass));
		}else if(innerclass == Boolean.class || innerclass==boolean.class) {
			Boolean[] array = innerclass==Boolean.class ? (Boolean[])v : (Boolean[])toPrimitiveObjectArray(v);
			for(int m=0; m<length; m++)Array.set(returnarray, m, BooleanUtils.toAny(array[m], toclass));
		}else if(Types.isNumber(innerclass)) {
			if(Types.isPrimitive(innerclass))v=toPrimitiveObjectArray(v);
			Number[] array = (Number[])v;
			for(int m=0; m<length; m++)Array.set(returnarray, m, NumberUtils.toAny(array[m], toclass, toformat));
		}else if(Types.isDate(innerclass)) {
			if(Calendar.class.isAssignableFrom(innerclass)) {
				Calendar[] array = (Calendar[])v;
				for(int m=0; m<length; m++)Array.set(returnarray, m, DateUtils.toAny(array[m], toclass, toformat));
			}else {
				Date[] array = (Date[])v;
				for(int m=0; m<length; m++)Array.set(returnarray, m, DateUtils.toAny(array[m], toclass, toformat));
			}
		}else if(Types.isEnum(innerclass)) {
			Enum[] array = (Enum[])v;
			for(int m=0; m<length; m++)Array.set(returnarray, m, EnumUtils.toAny(array[m], toformat, toclass));
		}
		return returnarray;
	}
	
	
	/**
	 * 将数组转换成List
	 * @param array
	 * @return
	 */
	public static <T> List<T> toList(T[] array) {
		return toList(array, null);
	}
	
	
	/**
	 * 将数组转换成List
	 * @param array
	 * @param list
	 * @return
	 */
	public static <T> List<T> toList(T[] array, List<T> list) {
		if(array == null) return null;
		if(list == null) list = new ArrayList<T>();
		if(array != null) {
			for(int i=0; i<array.length; i++) {
				list.add(array[i]);
			}
		}
		return list;
	}
	
	
	/**
	 * 将List转换成数组
	 * @param list
	 * @return
	 */
	public static <T> T[] toArray(List<T> list, Class<T> c) {
		return toArray(list, null, c);
	}
	
	
	/**
	 * 将List转换成数组
	 * @param list
	 * @param array
	 * @return
	 */
	public static <T> T[] toArray(List<T> list, T[] array, Class<T> c) {
		if(list == null) return null;
		int length = list!=null ? list.size() : 0;
		if(array != null) length += array.length;
		T[] rearr = (T[]) Array.newInstance(c, length);
		if(array != null) {
			for(int i=0; i<array.length; i++)rearr[i]=array[i];
		}
		if(list != null) {
			int fix = array != null ? array.length : 0;
			for(int i=0; i<list.size(); i++)rearr[fix+i]=list.get(i);
		}
		return rearr;
	}
	
	
	/**
	 * 将两个数组连接起来
	 * @param master
	 * @param array
	 * @return
	 */
	public static <T> T[] link(T[] master, T[] array) {
		if(array == null) return master;
		int count = master!=null ? (master.length+array.length) : array.length;
		T[] links = (T[])Array.newInstance(master.getClass().getComponentType(), count);
		
		int index = 0;
		if(master!=null && master.length>0) {
			for(int i=0; i<master.length; i++,index++) {
				links[index] = master[i];
			}
		}
		if(array!=null && array.length>0) {
			for(int i=0; i<array.length; i++,index++) {
				links[index] = array[i];
			}
		}
		return links;
	}
	
	
}


