package com.binary.core.bean;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * 值对象的封装对象
 */
public interface Bean<E> {
	
	
	/**
	 * 获取封装值对象类名
	 * @return 值对象类名
	 */
	public String getName();
	
	
	
	/**
	 * 获取封装值对象类型
	 * @return 值对象类型
	 */
	public Class<E> getBeanClass();
	
	
	
	/**
	 * 判断属性是否在值对象存在
	 * @param propertyName: 属性名
	 * @return true=存在		false=不存在
	 */
	public boolean containsProperty(String propertyName);
	
	
	
	/**
	 * 获取Bean属性
	 * @param propertyName: 属性名
	 * @return 属性的封装对象
	 */
	public Property getProperty(String propertyName);
	
	
	
	/**
	 * 获取属性个数
	 * @return 属性个数
	 */
	public int getPropertyCount();
	
	
	/**
	 * 获取属性类型
	 * @param propertyName: 属性名
	 * @return 属性类型
	 */
	public Class<?> getPorpertyType(String propertyName);
	
	
	
	/**
	 * 获取属性泛型类型
	 * @param propertyName: 属性名
	 * @return 属性泛型类型
	 */
	public Type getPorpertyGenericType(String propertyName);
	
	
	
	/**
	 * 获取属性迭代器
	 * @return 属性迭代器
	 */
	public Iterator<Property> getPropertyIterator();
	
	
	
	/**
	 * 获取属性名集合
	 * @return 属性名集合
	 */
	public Set<String> keySet();
	
	
	
	
	
	/**
	 * 创建一个新的封装值对象实例
	 * @return 值对象实例
	 */
	public E newInstance();
	
	
	
	/**
	 * 验证对象是否是合法对象
	 * @param instance: 值对象实例
	 * @return
	 */
	public boolean instanceOf(Object instance);
	
	
	
	/**
	 * 对属性赋值
	 * @param instance: 值对象实例
	 * @param propertyName: 属性名
	 * @param value: 属性值
	 */
	public void setPropertyValue(E instance, String propertyName, Object value);
	
	
	
	/**
	 * 获取属性值
	 * @param instance: 值对象实例
	 * @param propertyName: 属性名
	 * @return 属性值
	 */
	public Object getPropertyValue(E instance, String propertyName);
	
	
	/**
	 * 获取属性值
	 * @param instance: 值对象实例
	 * @param propertyName: 属性名
	 * @param toType: 希望值被转换的数据类型
	 * @return 属性值
	 */
	public <T> T getPropertyValue(E instance, String propertyName, Class<T> toType);
	
	
	
	/**
	 * 将值对象转换化Map
	 * @param instance: 值对象
	 * @return Map
	 */
	public Map<String,Object> toMap(E instance);
	
	
	
	/**
	 * 将值对象属性值全部赋给指定Map对象
	 * @param instance: 值对象
	 * @param map: 指定的Map对象
	 * @return Map
	 */
	public Map<String,Object> toMap(E instance, Map<String,Object> map);
	
	
	
	
	/**
	 * 将值对象属性值全部赋给指定Map对象
	 * @param instance: 值对象
	 * @param map: 指定的Map对象
	 * @param ignoreNull: 是否忽略NULL值
	 * @return Map
	 */
	public Map<String,Object> toMap(E instance, Map<String,Object> map, boolean ignoreNull);
	
	
	
	/**
	 * 将Map转换为当前值对象
	 * @param map: Map对象
	 * @return 值对象实例
	 */
	public E valueOf(Map<String,Object> map);
	
	
	
	/**
	 * 将Map中的值全部赋给值对象
	 * @param map: Map对象
	 * @param instance: 值对象
	 * @return 值对象
	 */
	public E valueOf(Map<String,Object> map, E instance);
	
	
	
	
	/**
	 * 将Map中的值全部赋给值对象
	 * @param map: Map对象
	 * @param instance: 值对象
	 * @param ignoreNull: 是否忽略Map中的NULL值
	 * @return 值对象
	 */
	public E valueOf(Map<String,Object> map, E instance, boolean ignoreNull);
	
	
	
	/**
	 * 销毁对象内部所有属性
	 */
	public void destroy();
	
	
	
		
}


