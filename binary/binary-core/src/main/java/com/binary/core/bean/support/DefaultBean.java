package com.binary.core.bean.support;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.binary.core.bean.Bean;
import com.binary.core.bean.Property;
import com.binary.core.exception.BeanException;
import com.binary.core.lang.ClassUtils;
import com.binary.core.lang.StringUtils;
import com.binary.core.lang.Types;
import com.binary.core.util.SecurityIterator;
import com.binary.core.util.BinaryUtils;


public class DefaultBean<E> implements Bean<E> {	
	
	private String name;
	private Class<E> beanClass;
	
	private Map<String,Property> propertyStore = new HashMap<String,Property>();
	
	
	
	public DefaultBean(Class<E> beanClass) {
		if(!Types.isBean(beanClass)) throw new BeanException(" is not Bean:'"+beanClass+"'! ");
		this.name = beanClass.getName();
		if(StringUtils.isEmpty(this.name)) throw new BeanException(" is not support Bean:'"+beanClass+"'! ");
		this.beanClass = beanClass;
		try {
			PropertyDescriptor[] properties = Introspector.getBeanInfo(beanClass).getPropertyDescriptors();
			if(properties!=null && properties.length>0) {
				for(int i=0; i<properties.length; i++) {
					Method readMethod = properties[i].getReadMethod();
					Method writeMethod = properties[i].getWriteMethod();
					if(readMethod==null || writeMethod==null) continue ;
					String name = properties[i].getName();
					Class<?> propertyType = properties[i].getPropertyType();
					propertyStore.put(name.toUpperCase(), new DefaultProperty(this, name, propertyType, readMethod, writeMethod));
				}
			}
		} catch (IntrospectionException e) {
			throw new BeanException(" Bean:'"+this.name+"' ", e);
		}
	}
	
	
	@Override
	public String getName() {
		return this.name;
	}
	
	

	@Override
	public Class<E> getBeanClass() {
		return this.beanClass;
	}
	
	

	@Override
	public boolean containsProperty(String propertyName) {
		return propertyStore.containsKey(propertyName.trim().toUpperCase());
	}
	
	

	@Override
	public Property getProperty(String propertyName) {
		return propertyStore.get(propertyName.trim().toUpperCase());
	}
	
	

	@Override
	public int getPropertyCount() {
		return propertyStore.size();
	}
	

	@Override
	public Class<?> getPorpertyType(String propertyName) {
		Property p = propertyStore.get(propertyName.trim().toUpperCase());
		return p==null ? null : p.getType();
	}
	
	

	@Override
	public Type getPorpertyGenericType(String propertyName) {
		Property p = propertyStore.get(propertyName.trim().toUpperCase());
		return p==null ? null : p.getGenericType();
	}
	
	

	@Override
	public Iterator<Property> getPropertyIterator() {
		Iterator<Property> iterator = propertyStore.values().iterator();
		return new SecurityIterator<Property>(iterator);
	}
	
	

	@Override
	public Set<String> keySet() {
		return this.propertyStore.keySet();
	}
	
	

	@Override
	public E newInstance() {
		try {
			return ClassUtils.newInstance(this.beanClass);
		} catch (Exception e) {
			throw BinaryUtils.transException(e, BeanException.class, " Bean:'"+this.name+"' ");
		}
	}
	
	

	@Override
	public boolean instanceOf(Object instance) {
		return instance!=null && this.beanClass.isAssignableFrom(instance.getClass());
	}
	
	
	private void faultInstance(Object instance) {
		if(instance==null) throw new BeanException(" Bean:'"+getName()+"': is 'NULL' BeanInstance!");
		boolean v = instanceOf(instance);
		if(!v) throw new BeanException(" Bean:'"+getName()+"': is not support BeanClass:'"+getName()+"' by Instance:'"+instance.getClass().getName()+"'!");
	}
	

	@Override
	public void setPropertyValue(E instance, String propertyName, Object value) {
		faultInstance(instance);
		Property property = getProperty(propertyName);
		if(property==null) throw new BeanException(" Bean:'"+getName()+"': is not found Property:'"+propertyName+"'!");
		property.setValue(instance, value);
	}
	
	

	@Override
	public Object getPropertyValue(E instance, String propertyName) {
		faultInstance(instance);
		Property property = getProperty(propertyName);
		if(property==null) throw new BeanException(" Bean:'"+getName()+"': is not found Property:'"+propertyName+"'!");
		return property.getValue(instance);
	}
	
	

	@Override
	public <T> T getPropertyValue(E instance, String propertyName, Class<T> toType) {
		faultInstance(instance);
		Property property = getProperty(propertyName);
		if(property==null) throw new BeanException(" Bean:'"+getName()+"': is not found Property:'"+propertyName+"'!");
		return property.getValue(instance, toType);
	}
	
	

	@Override
	public Map<String,Object> toMap(E instance) {
		return toMap(instance, null, false);
	}
	
	

	@Override
	public Map<String,Object> toMap(E instance, Map<String,Object> map) {
		return toMap(instance, map, false);
	}
	
	

	@Override
	public Map<String,Object> toMap(E instance, Map<String,Object> map, boolean ignoreNull) {
		faultInstance(instance);
		if(map == null) map = new HashMap<String,Object>();
		Iterator<Property> iterator = getPropertyIterator();
		while(iterator.hasNext()) {
			Property p = iterator.next();
			Object value = p.getValue(instance);
			if(ignoreNull && value==null) continue ;
			map.put(p.getName(), value);
		}
		return map;
	}
	
	

	@Override
	public E valueOf(Map<String,Object> map) {
		return valueOf(map, null, false);
	}
	
	

	@Override
	public E valueOf(Map<String,Object> map, E instance) {
		return valueOf(map, instance, false);
	}
	
	

	@Override
	public E valueOf(Map<String,Object> map, E instance, boolean ignoreNull) {
		if(map == null) return null;
		if(instance==null) {
			instance = newInstance();
		}else {
			faultInstance(instance);
		}
		Iterator<Entry<String,Object>> iterator = map.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<String,Object> entry = iterator.next();
			Object objkey = entry.getKey();
			Object value = entry.getValue();
			if(!(objkey instanceof String) || (ignoreNull&&value==null)) continue ;
			String key = (String) objkey;
			Property property = getProperty(key);
			if(property == null) continue ;
			property.setValue(instance, value);
		}
		return instance;
	}
	
	

	@Override
	public void destroy() {
		this.name = null;
		this.beanClass = null;
		propertyStore.clear();
		propertyStore = null;
	}
	
	
	
	
	
}
