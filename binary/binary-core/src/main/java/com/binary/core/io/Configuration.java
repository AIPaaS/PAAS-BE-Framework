package com.binary.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import com.binary.core.bean.BMProxy;
import com.binary.core.exception.ConfigurationException;
import com.binary.core.util.BinaryUtils;

public abstract class Configuration implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private String name;
	
	
	protected Configuration(String name) {
		BinaryUtils.checkEmpty(name, "name");
		this.name = name.trim();
	}
	
	
	
	public void loadResource(Resource rs) {
		if(rs==null || !rs.exists()) throw new ConfigurationException(" the resource '"+rs.getPath()+"' is not exists! ");
		
		InputStream is = null;
		try {
			is = rs.getInputStream();
			Properties pro = new Properties();
			pro.load(is);
			
			String suffix = this.name + ".";
			
			BMProxy<?> proxy = BMProxy.getInstance(this);
			Iterator<Entry<Object, Object>> itor = pro.entrySet().iterator();
			while(itor.hasNext()) {
				Entry<Object, Object> e = itor.next();
				String key = (String)e.getKey();
				Object value = e.getValue();
				
				if(!key.startsWith(suffix) || BinaryUtils.isEmpty(value)) continue ;
				key = key.substring(key.indexOf('.')+1);
				if(key.indexOf('.') > 0) {
					key = key.replaceAll("[.]", "");
				}
				
				if(proxy.containsKey(key)) {
					proxy.set(key, value);
				}
			}
		}catch(Exception e) {
			throw new ConfigurationException(e);
		}finally {
			try {
				if(is != null) is.close();
			} catch (IOException e) {
				throw new ConfigurationException(e);
			}
		}	
	}
	
	
}
