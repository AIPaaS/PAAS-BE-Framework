package com.binary.framework.util;

import java.util.HashMap;
import java.util.Map;

import com.binary.core.lang.Conver;
import com.binary.core.util.SecurityMap;
import com.binary.framework.exception.FrameworkException;

public class FrameworkProperties {
	
	private static FrameworkProperties instance;
	
	
	private final Map<String, Object> properties = new HashMap<String, Object>();
	private String charset;
	
	
	
	public FrameworkProperties(Map<String, Object> properties) {
		if(properties!=null && properties.size()>0) {
			this.properties.putAll(properties);
		}
		instance = this;
	}
	
	
	public Object get(String key) {
		return this.properties.get(key);
	}
	
	
	public String getString(String key) {
		Object obj = get(key);
		return obj==null ? null : Conver.to(obj, String.class);
	}
	
	public void set(String key, String value) {
		this.properties.put(key, value);
	}
	
	
	public String getCharset() {
		if(this.charset == null) {
			this.charset = getString("charset");
			if(this.charset==null || (this.charset=this.charset.trim()).length()==0) {
				this.charset = "UTF-8";
			}
		}
		return this.charset;
	}
	
	
	public Map<String, Object> getPropertiesMap() {
		return new SecurityMap<String, Object>(this.properties);
	}
	
	
	
	
	public static FrameworkProperties getInstance() {
		if(instance == null) throw new FrameworkException(" is not initialize FrameworkProperties! ");
		return instance;
	}
	
	
	
	
}
