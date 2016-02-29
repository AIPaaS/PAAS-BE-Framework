package com.binary.jdbc.ds.support;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import com.binary.core.bean.BMProxy;
import com.binary.jdbc.ds.DataSourceFactory;
import com.binary.jdbc.exception.DataSourceException;

public class DefaultDataSourceFactory implements DataSourceFactory {
	
	
	
	public DataSource getDataSource(Class<?> dataSourceClass, Map<String, String> properties) {
		if(!DataSource.class.isAssignableFrom(dataSourceClass)) {
			throw new DataSourceException(" the dataSourceClass:'"+dataSourceClass.getName()+"' is not implement javax.sql.DataSource! ");
		}
		BMProxy<?> proxy = BMProxy.getInstance(dataSourceClass);
		DataSource ds = (DataSource)proxy.newInstance();
		
		if(properties!=null && properties.size()>0) {
			Iterator<Entry<String,String>> iter = properties.entrySet().iterator();
			while(iter.hasNext()) {
				Entry<String,String> e = iter.next();
				String name = e.getKey();
				String value = e.getValue();
				
				if(proxy.containsKey(name)) {
					proxy.set(name, value);
				}
			}
		}
		
		return ds;
	}

}
