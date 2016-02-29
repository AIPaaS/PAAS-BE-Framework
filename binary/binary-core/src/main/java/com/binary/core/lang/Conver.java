package com.binary.core.lang;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.binary.core.bean.BMProxy;
import com.binary.core.exception.CoreException;
import com.binary.core.util.BinaryUtils;


@SuppressWarnings({"unchecked","rawtypes"})
public abstract class Conver {
	
	
	
	
	
	public static Object toAny(Object v, Class toclass) {
		return toAny(v, toclass, null);
	}
	
	
	public static Object toAny(Object v, Class toclass, String toformat) {
		if(v==null || toclass==null) return null;
		Class vclass = v.getClass();
		if(vclass.isArray()) return ArrayUtils.toAny(v, toclass, toformat);
		if(!Types.isSupport(vclass)) {
			throw new CoreException(" is not support vclass:'"+vclass.getName()+"'! ");
		}
		if(!Types.isSupport(toclass)) {
			throw new CoreException(" is not support toclass:'"+toclass.getName()+"'! ");
		}
		if(toclass.isAssignableFrom(vclass)) return v;
		if(vclass == String.class) {
			return StringUtils.toAny((String)v, toformat, toclass);
		}else if(vclass == Character.class) {
			return CharUtils.toAny((Character)v, toclass);
		}else if(vclass == Boolean.class) {
			return BooleanUtils.toAny((Boolean)v, toclass);
		}else if(Types.isNumber(vclass)) {
			return NumberUtils.toAny((Number)v, toclass, toformat);
		}else if(Types.isDate(vclass)) {
			if(Calendar.class.isAssignableFrom(vclass)) {
				return DateUtils.toAny((Calendar)v, toclass, toformat);
			}else {
				return DateUtils.toAny((Date)v, toclass, toformat);
			}
		}else if(Types.isEnum(vclass)) {
			return EnumUtils.toAny((Enum)v, toformat, toclass);
		}
		return null;
	}
	
	
		
	public static <T> T to(Object v, Class<T> toclass) {
		return to(v, toclass, null);
	}
	
	public static String toString(Object v) {
		return toString(v, null);
	}
	
	public static String toString(Object v, String toformat) {
		return to(v, String.class, toformat);
	}
	
	public static String toString(int[] array) {
		return ArrayUtils.toString(array);
	}
	public static String toString(byte[] array) {
		return ArrayUtils.toString(array);
	}
	public static String toString(double[] array) {
		return ArrayUtils.toString(array);
	}
	public static String toString(float[] array) {
		return ArrayUtils.toString(array);
	}
	public static String toString(boolean[] array) {
		return ArrayUtils.toString(array);
	}
	public static String toString(char[] array) {
		return ArrayUtils.toString(array);
	}
	public static String toString(short[] array) {
		return ArrayUtils.toString(array);
	}
	public static String toString(long[] array) {
		return ArrayUtils.toString(array);
	}
	public static String toString(Object[] array) {
		return toString(array, null);
	}
	public static String toString(Object[] array, String toformat) {
		return ArrayUtils.toString(array, toformat);
	}
	
	/**
	 * 将对象V转换成任意当前环境所支持的类型(Types)
	 * @param v
	 * @param toclass
	 * @param toformat
	 * @return
	 */
	public static <T> T to(Object v, Class<T> toclass, String toformat) {
		return (T) toAny(v, toclass, toformat);
	}
	
	private static Object validateArray(Object array, Class toclass) {
		if(array!=null && toclass!=null && Types.isPrimitive(toclass)) {
			array = ArrayUtils.toPrimitiveObjectArray(array);
		}
		return array;
	}
	
	public static <T> T[] to(Object[] v, Class<T> toclass) {
		return to(v, toclass, null);
	}
	
	public static <T> T[] to(Object[] v, Class<T> toclass, String toformat) {
		return (T[]) validateArray(toAny(v, toclass, toformat), toclass);
	}
	
	
	public static <T> T[] to(int[] v, Class<T> toclass) {
		return to(v, toclass, null);
	}
	
	public static <T> T[] to(int[] v, Class<T> toclass, String toformat) {
		return (T[]) validateArray(toAny(v, toclass, toformat), toclass);
	}
	
	public static <T> T[] to(long[] v, Class<T> toclass) {
		return to(v, toclass, null);
	}
	
	public static <T> T[] to(long[] v, Class<T> toclass, String toformat) {
		return (T[]) validateArray(toAny(v, toclass, toformat), toclass);
	}
	
	public static <T> T[] to(short[] v, Class<T> toclass) {
		return to(v, toclass, null);
	}
	
	public static <T> T[] to(short[] v, Class<T> toclass, String toformat) {
		return (T[]) validateArray(toAny(v, toclass, toformat), toclass);
	}
	
	public static <T> T[] to(byte[] v, Class<T> toclass) {
		return to(v, toclass, null);
	}
	
	public static <T> T[] to(byte[] v, Class<T> toclass, String toformat) {
		return (T[]) validateArray(toAny(v, toclass, toformat), toclass);
	}
	
	public static <T> T[] to(double[] v, Class<T> toclass) {
		return to(v, toclass, null);
	}
	
	public static <T> T[] to(double[] v, Class<T> toclass, String toformat) {
		return (T[]) validateArray(toAny(v, toclass, toformat), toclass);
	}
	
	public static <T> T[] to(float[] v, Class<T> toclass) {
		return to(v, toclass, null);
	}
	
	public static <T> T[] to(float[] v, Class<T> toclass, String toformat) {
		return (T[]) validateArray(toAny(v, toclass, toformat), toclass);
	}
	
	public static <T> T[] to(char[] v, Class<T> toclass) {
		return to(v, toclass, null);
	}
	
	public static <T> T[] to(char[] v, Class<T> toclass, String toformat) {
		return (T[]) validateArray(toAny(v, toclass, toformat), toclass);
	}
	
	public static <T> T[] to(boolean[] v, Class<T> toclass) {
		return to(v, toclass, null);
	}
	
	public static <T> T[] to(boolean[] v, Class<T> toclass, String toformat) {
		return (T[]) validateArray(toAny(v, toclass, toformat), toclass);
	}
	
	public static <T> List<T> toList(T[] array) {
		List<T> list = new ArrayList<T>();
		for(int i=0; i<array.length; i++)list.add(array[i]);
		return list;
	}
	
	public static <T> T[] toArray(List<T> list, Class<T> componentType) {
		int length = list.size();
		T[] array = (T[])Array.newInstance(componentType, length);
		list.toArray(array);
		return array;
	}
	
	
	public static Object mapping(Type totype, Object value) {
		if(value == null) return null;
		if(totype instanceof Class) return mapping((Class)totype, value);
		
		if(totype instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) totype;
			Type rawtype = pt.getRawType();
			if(!(rawtype instanceof Class)) return null;
			Class rawclass = (Class) rawtype ;
			if(List.class.isAssignableFrom(rawclass)) {
				Type t = pt.getActualTypeArguments()[0];
				List list = null;
				if(t instanceof Class) {
					Class c = (Class)t;
					Object array = mapping(Array.newInstance(c, 0).getClass(), value);
					if(array != null) {
						int length = Array.getLength(array);
						list = BinaryUtils.getListInstance(rawclass);
						for(int i=0; i<length; i++) list.add(Array.get(array, i));
					}
				}
				return list;
			}else if(Map.class.isAssignableFrom(rawclass)) {
				return mapping(rawclass, value);
			}
		}else if(totype instanceof GenericArrayType) {
			Type t = ((GenericArrayType)totype).getGenericComponentType();
			if(BinaryUtils.isArray(value.getClass())) {
				int length = BinaryUtils.getArrayLength(value);
				if(length == 0) return null;
				Object[] temparr = new Object[length];
				Class itemtype = null;
				for(int i=0; i<length; i++) {
					Object item = BinaryUtils.getArrayValue(value, i);
					Object mappvalue = mapping(t, item);
					temparr[i] = mappvalue;
					if(mappvalue!=null) itemtype = mappvalue.getClass();
				}
				if(itemtype == null) return null;
				Object array = Array.newInstance(itemtype, length);
				for(int i=0; i<length; i++)Array.set(array, i, temparr[i]);
				return array;
			}else {
				Object v = mapping(t, value);
				if(v == null) return null;
				Object array = Array.newInstance(v.getClass(), 1);
				Array.set(array, 0, v);
				return array;
			}
		}
		return null;
	}
	
	
	
	public static <T> T mapping(Class<T> totype, Object value) {
		if(value == null) return null;
		
		Class<?> vtype = value.getClass();
		if(vtype.isArray() || List.class.isAssignableFrom(vtype)) {
			int length = BinaryUtils.getArrayLength(value);
			if(totype.isArray() || List.class.isAssignableFrom(totype)) {
				if(length==0) return totype.isArray() ? (T)Array.newInstance(totype.getComponentType(), 0) : (T)BinaryUtils.getListInstance(totype);
				
				Class<?> vcomptype = BinaryUtils.getComponentType(value);
				if(vcomptype == null) {
					if(totype.isArray()) {
						return (T)Array.newInstance(totype.getComponentType(), length);
					}else {
						List<?> list = BinaryUtils.getListInstance(totype);
						for(int i=0; i<length; i++) list.add(null);
						return (T)list;
					}
				}
				Class<?> tocomptype = totype.isArray() ? totype.getComponentType() : Object.class;
				
				if(Types.isBean(vcomptype) || Map.class.isAssignableFrom(vcomptype)) {
					if(Types.isBean(tocomptype) || Map.class.isAssignableFrom(tocomptype) || tocomptype==Object.class) {	//对像数组 -> 对象数组
						return (T)mappingObjectArray(totype, tocomptype, value, vcomptype, length);
					}else {			//对象数组 -> 基础数组
						return null;
					}
				}else {
					if(Types.isBean(tocomptype) || Map.class.isAssignableFrom(tocomptype) || tocomptype==Object.class) {	//基础数组 -> 对象数组
						return null;
					}else {			//基础数组 -> 基础数组
						return (T)mappingArray(totype, tocomptype, value, vcomptype, length);
					}
				}
			}else {
				if(length==0) return null;
				Class<?> vcomptype = BinaryUtils.getComponentType(value);
				if(vcomptype == null) return null;
				
				if(Types.isBean(vcomptype) || Map.class.isAssignableFrom(vcomptype)) {
					if(Types.isBean(totype) || Map.class.isAssignableFrom(totype) || totype==Object.class) {	//对像数组 -> 对象
						Object firstitem = BinaryUtils.getArrayValue(value, 0);
						return firstitem==null ? null : (T)mappingObject(totype, firstitem, null, true, BMProxy.getInstance(vcomptype));
					}else {			//对象数组 -> 基础
						return null;
					}
				}else {
					if(Types.isBean(totype) || Map.class.isAssignableFrom(totype) || totype==Object.class) {	//基础数组 -> 对象
						return null;
					}else {			//基础数组 -> 基础
						Object firstitem = BinaryUtils.getArrayValue(value, 0);
						return to(firstitem, totype);
					}
				}
			}
		}else {
			if(totype.isArray() || List.class.isAssignableFrom(totype)) {
				Class<?> tocomptype = totype.isArray() ? totype.getComponentType() : Object.class;
				if(Types.isBean(vtype) || Map.class.isAssignableFrom(vtype)) {
					if(Types.isBean(tocomptype) || Map.class.isAssignableFrom(tocomptype) || tocomptype==Object.class) {	//对像 -> 对象数组
						Object obj = mappingObject(tocomptype, value, null, true, BMProxy.getInstance(vtype));
						Object array = Array.newInstance(tocomptype, 1);
						Array.set(array, 0, obj);
						return (T)array;
					}else {			//对象 -> 基础数组
						return null;
					}
				}else {
					if(Types.isBean(tocomptype) || Map.class.isAssignableFrom(tocomptype) || tocomptype==Object.class) {	//基础 -> 对象数组
						return null;
					}else {			//基础 -> 基础数组
						Object obj = to(value, tocomptype);
						Object array = Array.newInstance(tocomptype, 1);
						Array.set(array, 0, obj);
						return (T)array;
					}
				}
			}else {
				if(Types.isBean(vtype) || Map.class.isAssignableFrom(vtype)) {
					if(Types.isBean(totype) || Map.class.isAssignableFrom(totype) || totype==Object.class) {	//对像 -> 对象
						return (T)mappingObject(totype, value, null, true, BMProxy.getInstance(vtype));
					}else {			//对象 -> 基础
						return null;
					}
				}else {
					if(Types.isBean(totype) || Map.class.isAssignableFrom(totype) || totype==Object.class) {	//基础 -> 对象
						return null;
					}else {			//基础 -> 基础
						return (T)to(value, totype);
					}
				}
			}
		}
	}
	
	
	private static Object mappingArray(Class<?> totype, Class<?> tocomptype, Object value, Class<?> vcomptype, int length) {
		if(value==null) return null;
		
		if(value instanceof List) {
			List valuelist = (List) value;
			if(List.class.isAssignableFrom(totype)) {		//List -> List
				if(totype.isAssignableFrom(value.getClass())) {
					return value;
				}else {
					List<?> list = BinaryUtils.getListInstance(totype);
					list.addAll(valuelist);
					return list;
				}
			}else {			//List -> Array
				if(tocomptype == Object.class) {
					return valuelist.toArray();
				}else {
					Object array = Array.newInstance(tocomptype, length);
					for(int i=0; i<length; i++) {
						Array.set(array, i, to(valuelist.get(i), tocomptype));
					}
					return array;
				}
			}
		}else {
			if(List.class.isAssignableFrom(totype)) {	//Array -> List
				List list = BinaryUtils.getListInstance(totype);
				for(int i=0; i<length; i++) {
					Object item = Array.get(value, i);
					list.add(item);
				}
				return list;
			}else {		//Array -> Array
				if(tocomptype.isAssignableFrom(vcomptype)) {
					return value;
				}else {
					Object array = Array.newInstance(tocomptype, length);
					for(int i=0; i<length; i++) {
						Object item = Array.get(value, i);
						Array.set(array, i, to(item, tocomptype));
					}
					return array;
				}
			}
		}
	}
	
	
	private static Object mappingObjectArray(Class<?> totype, Class<?> tocomptype, Object value, Class<?> vcomptype, int length) {
		if(value==null) return null;
		
		if(value instanceof List) {
			List valuelist = (List) value;
			if(List.class.isAssignableFrom(totype)) {		//List -> List
				if(totype.isAssignableFrom(value.getClass())) {
					return value;
				}else {
					List<?> list = BinaryUtils.getListInstance(totype);
					list.addAll(valuelist);
					return list;
				}
			}else {			//List -> Array
				if(tocomptype == Object.class) {
					return valuelist.toArray();
				}else {
					BMProxy toproxy = BMProxy.getInstance(tocomptype);
					BMProxy vproxy = BMProxy.getInstance(vcomptype);
					Object array = Array.newInstance(tocomptype, length);
					for(int i=0; i<length; i++) {
						Array.set(array, i, mappingObject(tocomptype, valuelist.get(i), toproxy, true, vproxy));
					}
					return array;
				}
			}
		}else {
			if(List.class.isAssignableFrom(totype)) {	//Array - List
				List list = BinaryUtils.getListInstance(totype);
				for(int i=0; i<length; i++) {
					Object item = Array.get(value, i);
					list.add(item);
				}
				return list;
			}else {		//Array -> Array
				if(tocomptype.isAssignableFrom(vcomptype)) {
					return value;
				}else {
					BMProxy toproxy = BMProxy.getInstance(tocomptype);
					BMProxy vproxy = BMProxy.getInstance(vcomptype);
					Object array = Array.newInstance(tocomptype, length);
					for(int i=0; i<length; i++) {
						Object item = Array.get(value, i);
						Array.set(array, i, mappingObject(tocomptype, item, toproxy, true, vproxy));
					}
					return array;
				}
			}
		}
	}
	
	private static Object mappingObject(Class<?> totype, Object value, BMProxy toproxy, boolean newinstance, BMProxy vproxy) {
		if(value==null) return null;
		
		if(value instanceof Map) {
			Map valuemap = (Map) value;
			if(Map.class.isAssignableFrom(totype)) {		//Map -> Map
				if(totype.isAssignableFrom(value.getClass())) {
					return value;
				}else {
					Map<?,?> map = BinaryUtils.getMapInstance(totype);
					map.putAll(valuemap);
					return map;
				}
			}else {		//Map -> Bean
				if(toproxy==null)toproxy=BMProxy.getInstance(totype);
				if(newinstance)toproxy.newInstance();
				Iterator<Entry<Object,Object>> iterator = valuemap.entrySet().iterator();
				while(iterator.hasNext()) {
					Entry e = iterator.next();
					String key = toString(e.getKey());
					if(key==null || !toproxy.containsKey(key)) continue ;
					Object v = e.getValue();
					toproxy.set(key, v==null?null:mapping(toproxy.getPorpertyGenericType(key), v));
				}
				return toproxy.getInnerObject();
			}
		}else {
			if(Map.class.isAssignableFrom(totype)) {	//Bean -> Map
				Map map = BinaryUtils.getMapInstance(totype);
				vproxy.replaceInnerObject(value);
				Iterator<Entry<String,Object>> iterator = vproxy.entryIterator();
				while(iterator.hasNext()) {
					Entry<String,Object> e = iterator.next();
					map.put(e.getKey(), e.getValue());
				}
				return map;
			}else {		//Bean -> Bean
				if(totype.isAssignableFrom(value.getClass())) {
					return value;
				}else {
					if(toproxy==null)toproxy=BMProxy.getInstance(totype);
					if(newinstance)toproxy.newInstance();
					vproxy.replaceInnerObject(value);
					Iterator<Entry<String,Object>> iterator = vproxy.entryIterator();
					while(iterator.hasNext()) {
						Entry<String,Object> e = iterator.next();
						String key = e.getKey();
						if(key==null || !toproxy.containsKey(key)) continue ;
						Object v = e.getValue();
						toproxy.set(key, v==null?null:mapping(toproxy.getPorpertyGenericType(key), v));
					}
					return toproxy.getInnerObject();
				}
			}
		}
	}
	
	
	
	
}
