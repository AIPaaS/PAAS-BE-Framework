package com.binary.core.bean.support;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.binary.core.bean.BMProxy;
import com.binary.core.exception.BeanException;
import com.binary.core.lang.Conver;
import com.binary.core.util.BinaryUtils;


@SuppressWarnings({ "unchecked", "rawtypes" })
public class MapProxy<E> extends BMProxy<E> {
	
	
	private Class<E> type;
	private Map<String,Object> map;
	
	
	public MapProxy(Class<E> type) {
		this.type = type;
	}
	
	
	public Class<E> getInnerType() {
		return this.type;
	}
	
	
	public void replaceInnerObject(E o) {
		if(!(o instanceof Map)) throw new BeanException(" is not typeof java.util.Map - InstanceType:'"+o.getClass()+"'! ");
		this.map = (Map)o;
	}
	
	
	public void set(String key, Object value) {
		this.map.put(key, value);
	}
	
	
	public Object get(String key) {
		return this.map.get(key);
	}
	
	
	public <T> T get(String key, Class<T> toType) {
		Object obj = get(key);
		return Conver.to(obj, toType);
	}
	
	
	public void set(E instance, String key, Object value) {
		((Map)instance).put(key, value);
	}
	
	
	
	public Object get(E instance, String key) {
		return ((Map)instance).get(key);
	}
	
	
	public <T> T get(E instance, String key, Class<T> toType) {
		Object obj = get(instance, key);
		return Conver.to(obj, toType);
	}
	
	
	public int getPorpertyCount() {
		return this.map.size();
	}
	
	
	
	public Class<?> getPorpertyType(String key) {
		Object value = this.map.get(key);
		return value==null ? null : value.getClass();
	}
	
	
	
	public Type getPorpertyGenericType(String key) {
		return getPorpertyType(key);
	}
	
	
	public Set<String> keySet() {
		return this.map.keySet();
	}
	
	
	public Iterator<Object> valuesIterator() {
		return this.map.values().iterator();
	}
	
	
	
	public Iterator<Entry<String,Object>> entryIterator() {
		return this.map.entrySet().iterator();
	}
	
	
	
	
	public boolean containsKey(String key) {
		return this.map.containsKey(key);
	}
	
	
	
	public E newInstance() {
		return (E)(this.map = (Map)BinaryUtils.getMapInstance(this.type));
	}
	
	
	
	public E getInnerObject() {
		return (E)this.map;
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
			Object v = e.getValue();
			if(v==null && ignoreNull) {
				continue ;
			}
			this.set(e.getKey(), v);
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
