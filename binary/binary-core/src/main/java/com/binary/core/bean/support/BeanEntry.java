package com.binary.core.bean.support;

import java.util.Map.Entry;

import com.binary.core.bean.Property;

public class BeanEntry implements Entry<String, Object> {
	
	
	private Property property;
	private BeanProxy<?> proxy;
	
	
	public BeanEntry(Property property, BeanProxy<?> proxy) {
		this.property = property;
		this.proxy = proxy;
	}
	
	
	public String getKey() {
		return this.property.getName();
	}
	
	
	public Object getValue() {
		return this.proxy.get(this.property.getName());
	}
	
	
	public Object setValue(Object value) {
		Object oldValue = this.proxy.get(this.property.getName());
		this.proxy.set(this.property.getName(), value);
		return oldValue;
	}
	

}



