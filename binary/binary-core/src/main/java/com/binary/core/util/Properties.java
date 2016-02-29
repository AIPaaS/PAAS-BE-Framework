package com.binary.core.util;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.binary.core.exception.ResourceException;
import com.binary.core.io.Resource;

public class Properties implements Serializable, Map<String, String> {
	private static final long serialVersionUID = 1L;
	
	
	private java.util.Properties pro;
	
	
	
	public Properties(Resource res) {
		BinaryUtils.checkEmpty(res, "resource");
		
		try {
			InputStream is = null;
			try {
				is = res.getInputStream();
				pro = new java.util.Properties();
				pro.load(is);
			}finally {
				if(is != null) is.close();
			}
		}catch(Exception e) {
			throw BinaryUtils.transException(e, ResourceException.class); 
		}
	}



	@Override
	public int size() {
		return this.pro.size();
	}



	@Override
	public boolean isEmpty() {
		return this.pro.isEmpty();
	}



	@Override
	public boolean containsKey(Object key) {
		return this.pro.containsKey(key);
	}



	@Override
	public boolean containsValue(Object value) {
		return this.pro.containsValue(value);
	}



	@Override
	public String get(Object key) {
		return (String)this.pro.get(key);
	}



	@Override
	public String put(String key, String value) {
		return (String)this.pro.put(key, value);
	}



	@Override
	public String remove(Object key) {
		return (String)this.pro.remove(key);
	}



	@Override
	public void putAll(Map<? extends String, ? extends String> m) {
		this.pro.putAll(m);
	}



	@Override
	public void clear() {
		this.pro.clear();
	}



	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Set<String> keySet() {
		return (Set)this.pro.keySet();
	}



	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Collection<String> values() {
		return (Collection)this.pro.values();
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Set<java.util.Map.Entry<String, String>> entrySet() {
		return (Set)this.pro.entrySet();
	}
	
	

}
