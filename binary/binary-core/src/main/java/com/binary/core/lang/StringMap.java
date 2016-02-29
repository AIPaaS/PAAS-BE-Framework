package com.binary.core.lang;

import java.io.Serializable;
import java.util.Map;

import com.binary.core.bean.BMProxy;
import com.binary.core.exception.BinaryException;

public class StringMap implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private String str;
	private String[] keys;
	private Object[] values;
	
	
	
	public StringMap() {
	}
	
	
	public StringMap(String str, String[] keys) {
		this(str, keys, (Object[]) null);
	}
	
	
	
	
	public StringMap(String str, String[] keys, Map<String,?> params) {
		this(str, keys);
		setValues(params);
	}
	
	
	
	public StringMap(String str, String[] keys, Object paramsInstance) {
		this(str, keys);
		setValues(paramsInstance);
	}
	
	
	public StringMap(String str, String[] keys, Object[] values) {
		this.str = str;
		this.keys = keys;
		setValues(values);
	}
	


	public String getString() {
		return str;
	}




	public String[] getKeys() {
		return keys;
	}
	
	
	
	public void setKeys(String[] keys) {
		this.keys = keys;
	}


	
	public void setString(String str) {
		this.str = str;
	} 
	
	

	public Object[] getValues() {
		return values;
	}
	
	
	public void setValues(Object[] values) {
		if(values!=null && values.length!=keys.length) throw new BinaryException(" StringParser keys.length!=values.length! ");
		this.values = values;
	}
	
	
	public void setValues(Object paramsInstance) {
		this.values = null;
		if(paramsInstance != null) {
			BMProxy<?> proxy = BMProxy.getInstance(paramsInstance);
			
			this.values = new Object[keys.length];
			for(int i=0; i<keys.length; i++) {
				values[i] = proxy.get(keys[i]);
			}
		}
	}
	
	
	
	
	
	
}



