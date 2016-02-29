package com.binary.core.bean;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * 属性的封装对象
 */
public interface Property {
	
	
	
	/**
	 * 获取属性名
	 * @return 属性名
	 */
	public String getName();
	
	
	
	/**
	 * 获取对应属性值所属对象属性值
	 * @param instance: 属性值所属对象
	 * @return 属性值
	 */
	public Object getValue(Object instance);
	
	
	
	/**
	 * 获取对应属性值所属对象属性值, 并将类型转换成客户端想要的类型
	 * @param instance : 属性值所属对象
	 * @param toType : 希望被转换的类型
	 * @return
	 */
	public <T> T getValue(Object instance, Class<T> toType);
	
	
	
	/**
	 * 设置对应属性值所属对象属性值
	 * @param instance: 属性值所属对象
	 * @param value: 属性值
	 */
	public void setValue(Object instance, Object value);
	
	
	
	/**
	 * 获取属性类型
	 * @return 属性类型
	 */
	public Class<?> getType();
	
	
	/**
	 * 获取属性的泛型类型
	 * @return 属性的泛型类型
	 */
	public Type getGenericType();
	
	
	/**
	 * 获取当前属性所属的Bean对象
	 * @return 所属的Bean对象
	 */
	public Bean<?> getBean();
	
	
	
	/**
	 * 获取属性写方法对象
	 * @return 属性写方法对象
	 */
	public Method getWriteMethod();
	
	
	
	
	/**
	 * 获取属性读方法对象
	 * @return 属性读方法对象
	 */
	public Method getReadMethod();
	
	
	
	/**
	 * 获取属性所声名的类
	 * @return
	 */
	public Class<?> getDeclaringClass();
	
	
}
