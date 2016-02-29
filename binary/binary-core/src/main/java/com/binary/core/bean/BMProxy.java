package com.binary.core.bean;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.binary.core.bean.support.BeanProxy;
import com.binary.core.bean.support.MapProxy;
import com.binary.core.exception.BeanException;
import com.binary.core.lang.Types;


/**
 * 值对象与Map的统一代理对象(Bean-Map-Proxy)
 */
public abstract class BMProxy<E> {
	
	
	/**
	 * 获取封装对象类型
	 * @return
	 */
	public abstract Class<E> getInnerType();
	
	
	/**
	 * 替换掉封装对象
	 * @param data: 封装对象
	 */
	public abstract void replaceInnerObject(E o);
	
	
	/**
	 * 对属性赋值
	 * @param key: 键
	 * @param value: 值
	 */
	public abstract void set(String key, Object value);
	
	
	/**
	 * 获取属性值
	 * @param key: 键
	 * @return 值
	 */
	public abstract Object get(String key);
	
	
	
	/**
	 * 获取属性值, 并将值类型转换成toType类型
	 * @param key: 键
	 * @param toType: 希望被转换的数据类型
	 * @return 值
	 */
	public abstract <T> T get(String key, Class<T> toType);
	
	
	
	/**
	 * 设置指定对象属性值
	 * @param instance: 指定对象
	 * @param key: 键
	 * @param value: 值
	 */
	public abstract void set(E instance, String key, Object value);
	
	
	
	/**
	 * 获取指定对象属性值 
	 * @param instance: 指定对象
	 * @param key: 键
	 * @return 值
	 */
	public abstract Object get(E instance, String key);
	
	
	
	/**
	 * 获取指定对象属性值 , 并将值类型转换成toType类型
	 * @param instance: 指定对象
	 * @param key: 键
	 * @param toType: 希望被转换的数据类型
	 * @return 值
	 */
	public abstract <T> T get(E instance, String key, Class<T> toType);
	
		
	/**
	 * 获取属性个数
	 */
	public abstract int getPorpertyCount();
	
	
	
	/**
	 * 获取属性类型, 没有找到属性则反回null
	 * @param key: 属性名
	 * @return 属性类型
	 */
	public abstract Class<?> getPorpertyType(String key);
	
	
	
	/**
	 * 获取属性泛型类型, 没有找到属性则反回null
	 * @param key: 属性名
	 * @return 属性泛型类型
	 */
	public abstract Type getPorpertyGenericType(String key);
	
	
	/**
	 * 获取所有属性名
	 * @return 属性名集合
	 */
	public abstract Set<String> keySet();
	
	
	/**
	 * 获取所有属性值
	 * @return 属性值集合
	 */
	public abstract Iterator<Object> valuesIterator();
	
	
	
	/**
	 * 获取所有键值对迭代器
	 * @return 键值对迭代器
	 */
	public abstract Iterator<Entry<String,Object>> entryIterator();
	
	
	
	/**
	 * 判断属性是否存在
	 * @param key: 属性名
	 * @return true=存在		false=不存在
	 */
	public abstract boolean containsKey(String key);
	
	
	
	/**
	 * 创建封装对象实例, 同时并替换内部所聚集的封装对象
	 * @return 封装对象实例
	 */
	public abstract E newInstance();
	
	
	
	/**
	 * 获取内部封装对象
	 * @return 内部封装对象
	 */
	public abstract E getInnerObject();
	
	
	
	/**
	 * 将对象O中的属性复制至当前对象中
	 * @param o
	 */
	public abstract void copyFrom(Object o);
	
	
	
	/**
	 * 将对象O中的属性复制至当前对象中
	 * @param o
	 * @param ignoreNull : 是否忽略null值
	 */
	public abstract void copyFrom(Object o, boolean ignoreNull);
	
	
	
	/**
	 * 将当前对象中的属性复制至对象O中
	 * @param o
	 */
	public abstract void copyTo(Object o);
	
	
	
	/**
	 * 将当前对象中的属性复制至对象O中
	 * @param o
	 * @param ignoreNull : 是否忽略null值
	 */
	public abstract void copyTo(Object o, boolean ignoreNull);
	
	
	
	
	/**
	 * 跟据对象类型获取代理对象
	 * @param type
	 * @return
	 */
	public static <T> BMProxy<T> getInstance(Class<T> type) {
		if(Map.class.isAssignableFrom(type)) {
			return new MapProxy<T>(type);
		}else {
			if(!Types.isBean(type)) throw new BeanException(" is not Bean:'"+type+"'!");
			Bean<T> bean = BeanManager.getBean(type);
			return new BeanProxy<T>(bean);
		}
	}
	
	
	
	/**
	 * 获取BMProxy代理实例
	 * @param data: Bean\Map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> BMProxy<T> getInstance(T o) {
		if(o == null) throw new BeanException(" is NULL data argument! ");
		BMProxy<T> proxy = getInstance((Class<T>)o.getClass());
		proxy.replaceInnerObject(o);
		return proxy;
	}
	
	
}




