package com.binary.json;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.binary.core.bean.Bean;
import com.binary.core.bean.BeanManager;
import com.binary.core.bean.Property;
import com.binary.core.lang.Conver;
import com.binary.core.lang.Types;



/**
 * JSON通用类
 * @author wanwb
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class JSON {
	
	
	
	/**
	 * 将一个JAVA对象转换成JSON字符串
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		try {
			if(obj == null) return "";
			Class<?> c = obj.getClass();
			if(c.isArray()) {
				return new JSONArray(obj).toString();
			}else if(obj instanceof Collection) {
				return new JSONArray((Collection<?>)obj).toString();
			}else if(obj instanceof Map) {
				return new JSONObject((Map<?,?>)obj).toString();
			}else if(Types.isBean(c)) {
				return new JSONObject(obj).toString();
			}else {
				return JSONObject.valueToString(obj);
			}
		} catch (Exception e) {
			throw e instanceof JSONException ? (JSONException)e : new JSONException(e);
		}
	}
	
	
	
	
	/**
	 * 将JSON字符串转换在JAVA对象
	 * @param jsonstring
	 * @return
	 */
	public static Object toObject(String jsonstring) {
		return toObject(jsonstring, null);
	}
	
	
	/**
	 * 指定映射类型将JSON字符串转换在JAVA对象
	 * @param jsonstring
	 * @param beanClass
	 * @return
	 */
	public static <T> T toObject(String jsonstring, Class<T> beanClass) {
		Object jsonobj = valueOf(jsonstring);
		return (T)mapping(jsonobj, beanClass);
	}
	
	
	
	/**
	 * 将数组对象类型json字符串转换成List<Object>
	 * @param arrayJson
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> toList(String arrayJson, Class<T> clazz) {
		if(arrayJson==null || (arrayJson=arrayJson.trim()).length()==0) return null;
		if(arrayJson.length()<5 || arrayJson.charAt(0)!='[' || arrayJson.charAt(arrayJson.length()-1)!=']') throw new JSONException(" is wrong json-array-string '"+arrayJson+"'");
		
		List<?> list = (List<?>)JSON.toObject(arrayJson);
		List<T> array = new ArrayList<T>();
		
		for(int i=0; i<list.size(); i++) {
			Object item = list.get(i);
			array.add(Conver.mapping(clazz, item));
		}
		return array;
	}
	
	
	
	public static Object valueOf(String s) {
		try {
			String t = s.trim();
			if(t.length()>0) {
				char c = t.charAt(0);
				if(c == '{') {
					return new JSONObject(s);
				}else if(c == '[') {
					return new JSONArray(s);
				}else {
					return JSONObject.stringToValue(s);
				}
			}else {
				return s;
			}
		}catch(Exception e) {
			throw e instanceof JSONException ? (JSONException)e : new JSONException(e);
		}
	}
	
	
	public static Object mapping(Object value) {
		return mapping(value, null);
	}
	
	
	public static Object mapping(Object value, Class beanClass) {
		if(value==null || value==JSONObject.NULL || (!(value instanceof String) && value.toString().equalsIgnoreCase("null"))) {
			return null;
		}else if(value instanceof JSONArray) {
			return mappingArray((JSONArray)value, beanClass);
		}else if(value instanceof JSONObject) {
			return beanClass==null ? mappingObject((JSONObject)value) : mappingObject((JSONObject)value, beanClass);
		}else if(value instanceof JSONObject[]) {
			return mappingArray((JSONObject[])value, beanClass);
		}else {
			if(beanClass != null) {
				return Conver.mapping(beanClass, value);
			}else {
				return value;
			}
		}
	}
	
	public static List mappingArray(JSONArray jsonarray) {
		return mappingArray(jsonarray, null);
	}
	
	
	public static List mappingArray(JSONArray jsonarray, Class beanClass) {
		try {
			if(jsonarray==null) return null;
			List list = new ArrayList();
			for(int i=0; i<jsonarray.length(); i++) {
				Object value = jsonarray.get(i);
				list.add(mapping(value, beanClass));
			}
			return list;
		}catch(Exception e) {
			throw e instanceof JSONException ? (JSONException)e : new JSONException(e);
		}
	}
	
	
	public static List mappingArray(JSONObject[] array) {
		return mappingArray(array, null);
	}
	
	public static List mappingArray(JSONObject[] array, Class beanClass) {
		if(array == null) return null;
		List list = new ArrayList();
		for(int i=0; i<array.length; i++) {
			Object value = mapping(array[i], beanClass);
			list.add(value);
		}
		return list;
	}
	
	public static Object mappingObject(JSONObject jsonobj) {
		return mappingObject(jsonobj, null);
	}
	
	public static Object mappingObject(JSONObject jsonobj, Class beanClass) {
		if(beanClass==null || (Map.class.isAssignableFrom(beanClass))) {
			return mappingMap(jsonobj, beanClass);
		}else {
			return mappingBean(jsonobj, beanClass);
		}
	}
	
	
	private static Object mappingBean(JSONObject jsonobj, Class beanClass) {
		if(jsonobj == null) return null;
		try {
			Iterator iterator = jsonobj.keys();
			Bean bean = BeanManager.getBean(beanClass);
			Object returnObj = bean.newInstance();
			while(iterator.hasNext()) {
				String key = (String) iterator.next();
				if(!bean.containsProperty(key)) continue ;
				Property pro = bean.getProperty(key);
				
				Object value = jsonobj.get(key);
				if(value == null) {
					pro.setValue(returnObj, null);
				}else {
					Class<?> type = pro.getType();
					if(List.class.isAssignableFrom(type)) {
						Class<?> toType = null;
						
						Type t = pro.getGenericType();
						if(t instanceof ParameterizedType) {
							Type tx = Types.getGenericType((ParameterizedType)t);
							if(tx instanceof Class<?>) {
								toType = (Class<?>)tx;
							}
						}
						if(toType == null) {
							throw new JSONException(" cat not mapping '" + pro.getName() + " : "+type.getName()+"'! ");
						}
						type = toType;
					}
					
					pro.setValue(returnObj, mapping(value, type));
				}
			}
			return returnObj;
		}catch(Exception e) {
			throw e instanceof JSONException ? (JSONException)e : new JSONException(e);
		}
	}
	
	
	private static Map mappingMap(JSONObject jsonobj, Class mapClass) {
		if(jsonobj == null) return null;
		try {
			Map map = mapClass!=null&&Map.class.isAssignableFrom(mapClass)&&!mapClass.isInterface() ? (Map)mapClass.newInstance() : new HashMap();
			Iterator iterator = jsonobj.keys();
			while(iterator.hasNext()) {
				String key = (String) iterator.next();
				map.put(key, mapping(jsonobj.get(key)));
			}
			return map;
		}catch(Exception e) {
			throw e instanceof JSONException ? (JSONException)e : new JSONException(e);
		}
	}
	
	

	
	
	
	
}




