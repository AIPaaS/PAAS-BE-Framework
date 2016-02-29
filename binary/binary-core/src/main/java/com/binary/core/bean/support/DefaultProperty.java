package com.binary.core.bean.support;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import com.binary.core.bean.Bean;
import com.binary.core.bean.Property;
import com.binary.core.exception.BeanException;
import com.binary.core.lang.Conver;
import com.binary.core.util.BinaryUtils;


@SuppressWarnings("rawtypes")
public class DefaultProperty implements Property {	
	
	private String name;
	private Type genericPropertyType;
	private Class propertyType;
	private Method readMethod;
	private Method writeMethod;
	
	private Bean bean;
	private Class<?> declaringClass;
	
	
	public DefaultProperty(Bean bean, String name, Class propertyType, Method readMethod, Method writeMethod) {
		this.bean = bean;
		this.name = name;
		this.propertyType = propertyType;
		this.readMethod = readMethod;
		this.writeMethod = writeMethod;
		this.genericPropertyType = readMethod!=null ? readMethod.getGenericReturnType()
													: writeMethod.getGenericParameterTypes()[0];
		
		Class<?> readDeclaring = this.readMethod.getDeclaringClass();
		Class<?> writeDeclaring = this.writeMethod.getDeclaringClass();
		if(readDeclaring == writeDeclaring) {
			this.declaringClass = readDeclaring;
		}else {
			if(readDeclaring.isAssignableFrom(writeDeclaring)) {
				this.declaringClass = writeDeclaring;
			}else {
				this.declaringClass = readDeclaring;
			}
		}
	}
	
	
	@Override
	public String getName() {
		return this.name;
	}
	
	

	@Override
	public Object getValue(Object instance) {
		if(readMethod==null) throw new BeanException(" Bean:'"+bean.getName()+"-"+this.name+"' is not found ReadMethod! ");
		try {
			return readMethod.invoke(instance, (Object[])null);
		} catch (Exception e) {
			throw BinaryUtils.transException(e, BeanException.class, " Bean:'"+bean.getName()+"-"+this.name+"' ");
		}
	}
	
	
	public <T> T getValue(Object instance, Class<T> toType) {
		Object value = getValue(instance);
		return Conver.to(value, toType);
	}
	
	

	@Override
	public void setValue(Object instance, Object value) {
		if(writeMethod==null) throw new BeanException(" Bean:'"+bean.getName()+"-"+this.name+"': is not found ReadMethod! ");
		try {
			if(value != null) {
//				Class valueClass = value.getClass();
//				if(!propertyType.isAssignableFrom(valueClass) && Types.isSupport(valueClass)) {
//					Class type = propertyType.isArray() ? propertyType.getComponentType() : propertyType;
//					value = Conver.to(value,type);
//				} 
				value = Conver.mapping(this.genericPropertyType, value);
			}
			writeMethod.invoke(instance, new Object[]{value});
		} catch (Exception e) {
			throw BinaryUtils.transException(e, BeanException.class, " Bean:'"+bean.getName()+"-"+this.name+"' ");
		}
	}
	
	

	@Override
	public Class<?> getType() {
		return this.propertyType;
	}
	

	@Override
	public Type getGenericType() {
		return this.genericPropertyType;
	}
	

	@Override
	public Bean<?> getBean() {
		return this.bean;
	}
	
	

	@Override
	public Method getWriteMethod() {
		return this.writeMethod;
	}
	
	

	@Override
	public Method getReadMethod() {
		return this.readMethod;
	}
	
	

	@Override
	public Class<?> getDeclaringClass() {
		return this.declaringClass;
	}
	
	
	
}
