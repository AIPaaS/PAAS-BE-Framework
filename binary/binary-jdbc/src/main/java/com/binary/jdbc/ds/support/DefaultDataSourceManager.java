package com.binary.jdbc.ds.support;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.binary.core.util.SecurityIterator;
import com.binary.jdbc.ds.DataSource;
import com.binary.jdbc.ds.DataSourceManager;
import com.binary.jdbc.exception.DataSourceException;



public class DefaultDataSourceManager implements DataSourceManager {
	
	
	private final Object syncobj = new Object();
	private DataSource defaultDataSource;
	private final Map<String,DataSource> dsstore = new HashMap<String, DataSource>();
	
	
	public void setDefaultDataSource(String defaultDataSourceName) {
		this.defaultDataSource = getDataSource(defaultDataSourceName);
		if(this.defaultDataSource == null) {
			throw new DataSourceException(" is not found datasource:'"+defaultDataSourceName+"'! ");
		}
	}
	
	
	public DataSource getDefaultDataSource() {
		return this.defaultDataSource;
	}
	
	
	public boolean isDefaultDataSource(DataSource ds) {
		return this.defaultDataSource == ds;
	}
	
	
	public boolean isDefaultDataSource(String dsname) {
		return this.defaultDataSource.getName().equalsIgnoreCase(dsname);
	}
	
	
	public void addDataSource(DataSource ds) {
		synchronized(syncobj) {
			String name = ds.getName().trim().toUpperCase();
			if(dsstore.containsKey(name)) throw new DataSourceException(" is exists datasource-name:'"+ds.getName()+"'! ");
			dsstore.put(name, ds);
		}
	}
	
	
	public DataSource removeDataSource(String dsname) {
		if(dsname==null || (dsname=dsname.trim()).length()==0) throw new DataSourceException(" the dsname is empty argument! ");
		synchronized(syncobj) {
			return dsstore.remove(dsname.toUpperCase());
		}
	}
	
	
	public boolean containsName(String dsname) {
		if(dsname==null || (dsname=dsname.trim()).length()==0) throw new DataSourceException(" the dsname is empty argument! ");
		return dsstore.containsKey(dsname.toUpperCase());
	}
	
	
	public DataSource getDataSource(String dsname) {
		if(dsname==null || (dsname=dsname.trim()).length()==0) throw new DataSourceException(" the dsname is empty argument! ");
		return dsstore.get(dsname.toUpperCase());
	}
	
	
	
	public Iterator<DataSource> getDataSourceIterator() {
		return new SecurityIterator<DataSource>(dsstore.values().iterator());
	}
	
	
	
	
}
