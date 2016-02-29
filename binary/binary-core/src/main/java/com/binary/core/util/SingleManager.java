package com.binary.core.util;

import java.util.HashMap;
import java.util.Map;

import com.binary.core.lang.ClassUtils;


/**
 * 单例对象管理
 * @author JIE
 *
 */
public class SingleManager {
	
	
	private SingleManager() {
	}
	
	private static final Object syncobj = new Object();
	private static final Map<String,Object> singlemap = new HashMap<String,Object>();
	
	
	
	/**
	 * 获取单例对象
	 * @param <T>
	 * @param c
	 * @return
	 */
	public static <T> T getInstance(Class<T> c) {
		return getInstance(c, null, null);
	}
	
	
	/**
	 * 获取单例对象
	 * @param <T>
	 * @param c
	 * @param paramTypes: 指定构造函数
	 * @param paramValues: 构造函数参数值
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getInstance(Class<T> c, Class<?>[] paramTypes, Object[] paramValues) {
		T obj = (T) singlemap.get(c.getName());
		if(obj == null) {
			synchronized (syncobj) {
				obj = (T) singlemap.get(c.getName());
				if(obj == null) {
					obj = ClassUtils.newInstance(c, paramTypes, paramValues);
					singlemap.put(c.getName(), obj);
				}
			}
		}
		return obj;
	}
	
	
	
	/**
	 * 删除单例对象
	 * @param <T>
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T removeInstance(Class<T> c) {
		return (T)removeInstance(c.getName());
	}
	
	
	/**
	 * 删除单例对象
	 * @param className
	 * @return
	 */
	public static Object removeInstance(String className) {
		synchronized (syncobj) {
			return singlemap.remove(className);
		}
	}
	
	
	/**
	 * 根据类型判断是否在单例对象管理器中
	 * @param c
	 * @return
	 */
	public boolean contains(Class<?> c) {
		return contains(c.getName());
	}
	
	
	/**
	 * 根据类名判断是否在单例对象管理器中
	 * @param className
	 * @return
	 */
	public boolean contains(String className) {
		return singlemap.containsKey(className);
	}
	
	
	/**
	 * 根据实例判断是否在单例对象管理器中
	 * @param instance
	 * @return
	 */
	public boolean contains(Object instance) {
		return singlemap.containsValue(instance);
	}
	
	
	/**
	 * 清除所有单例对象
	 */
	public static void clear() {
		synchronized(syncobj) {
			singlemap.clear();
		}
	}
	
	
}




