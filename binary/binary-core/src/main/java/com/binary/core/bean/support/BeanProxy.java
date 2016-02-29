package com.binary.core.bean.support;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.binary.core.bean.BMProxy;
import com.binary.core.bean.Bean;
import com.binary.core.bean.Property;
import com.binary.core.exception.BeanException;

public class BeanProxy<E> extends BMProxy<E> {
	
	
	private Bean<E> bean;
	private E beanInstance;	
	private Map<Property,BeanEntry> entryMap;
	
	
	public BeanProxy(Bean<E> bean) {
		this.bean = bean;
	}
	
	
	public Class<E> getInnerType() {
		return this.bean.getBeanClass();
	}
	
	
	public void replaceInnerObject(E o) {
		if(!bean.instanceOf(o)) throw new BeanException(" is not typeof BeanType:'"+bean.getBeanClass()+"' - InstanceType:'"+o.getClass()+"'! ");
		this.beanInstance = o;
	}
	
	
	public void set(String key, Object value) {
		this.bean.setPropertyValue(this.beanInstance, key, value);
	}
	
	
	public Object get(String key) {
		return this.bean.getPropertyValue(this.beanInstance, key);
	}
	
	
	public <T> T get(String key, Class<T> toType) {
		return this.bean.getPropertyValue(this.beanInstance, key, toType);
	}
	
	
	public void set(E instance, String key, Object value) {
		this.bean.setPropertyValue(instance, key, value);
	}
	
	
	
	public Object get(E instance, String key) {
		return this.bean.getPropertyValue(instance, key);
	}
	
	
	public <T> T get(E instance, String key, Class<T> toType) {
		return this.bean.getPropertyValue(instance, key, toType);
	}
	
	
	public int getPorpertyCount() {
		return this.bean.getPropertyCount();
	}
	
	
	
	public Class<?> getPorpertyType(String key) {
		return this.bean.getPorpertyType(key);
	}
	
	
	
	public Type getPorpertyGenericType(String key) {
		return this.bean.getPorpertyGenericType(key);
	}
	
	
	public Set<String> keySet() {
		return this.bean.keySet();
	}
	
	
	public Iterator<Object> valuesIterator() {
		return new PropertyValueIterator(this.bean.getPropertyIterator(), this);
	}
	
	
	
	public Iterator<Entry<String,Object>> entryIterator() {
		if(this.entryMap==null) buildEntryMap();
		return new PropertyEntryIterator(this.bean.getPropertyIterator(), this.entryMap);
	}
	
	
	
	private synchronized void buildEntryMap() {
		if(this.entryMap != null) return ;
		this.entryMap = new HashMap<Property, BeanEntry>();
		Iterator<Property> iterator = this.bean.getPropertyIterator();
		while(iterator.hasNext()) {
			Property property = iterator.next();
			this.entryMap.put(property, new BeanEntry(property, this));
		}
	}
	
	
	public boolean containsKey(String key) {
		return this.bean.containsProperty(key);
	}
	
	
	
	public E newInstance() {
		return this.beanInstance = this.bean.newInstance(); 
	}
	
	
	
	public E getInnerObject() {
		return this.beanInstance;
	}
	
	
	public void copyFrom(Object o) {
		copyFrom(o, false);
	}
	
	
	public void copyFrom(Object o, boolean ignoreNull) {
		if(o == null) return ;
		BMProxy<?> proxy = BMProxy.getInstance(o);
		Iterator<Entry<String, Object>> iter = proxy.entryIterator();
		while(iter.hasNext()) {
			Entry<String, Object> e = iter.next();
			String key = e.getKey();
			if(this.containsKey(key)) {
				Object v = e.getValue();
				if(v==null && ignoreNull) {
					continue;
				}
				this.set(e.getKey(), v);
			}
		}
	}
	
	
	
	public void copyTo(Object o) {
		if(o == null) return ;
		BMProxy.getInstance(o).copyFrom(this.getInnerObject());
	}
	
	
	public void copyTo(Object o, boolean ignoreNull) {
		if(o == null) return ;
		BMProxy.getInstance(o).copyFrom(this.getInnerObject(), ignoreNull);
	}
	
	
}



