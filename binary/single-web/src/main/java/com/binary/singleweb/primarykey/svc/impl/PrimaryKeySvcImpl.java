package com.binary.singleweb.primarykey.svc.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.binary.framework.exception.ServiceException;
import com.binary.singleweb.primarykey.dao.PrimaryKeyDao;
import com.binary.singleweb.primarykey.svc.PrimaryKeySvc;

public class PrimaryKeySvcImpl implements PrimaryKeySvc {

	private static final Object sync = new Object();
	
	
	@Autowired
	private PrimaryKeyDao dao;
	
	
	
	public String getKey(String name, Integer batch) {
		if(name==null || (name=name.trim()).length()==0) throw new ServiceException(" the PrimaryKey-name is empty! ");
		if(batch == null) throw new ServiceException(" the PrimaryKey-batch is null! ");
		if(batch.intValue() <= 0) throw new ServiceException(" the PrimaryKey-batch is wrong '"+batch+"'! ");
		
		name = name.toUpperCase();
		String result = null;
		
		synchronized(sync) {
			Long value = dao.selectValue(name);
			if(value == null) {
				value = 1l;
				Long last = batch.longValue();
				result = value + "-" + last;
				dao.insert(name, last);
			}else {
				Long last = value + batch;
				value ++ ;
				result = value + "-" + last;
				dao.update(name, last);
			}
		}
		
		return result;
	}
	
	
	
}
