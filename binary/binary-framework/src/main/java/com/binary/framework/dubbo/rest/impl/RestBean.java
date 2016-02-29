package com.binary.framework.dubbo.rest.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.binary.core.util.BinaryUtils;
import com.binary.framework.exception.ServiceException;

public class RestBean {
	
	private static final Set<String> ignoreMethods = new HashSet<String>();
	
	
	static {
		ignoreMethods.add("wait".toUpperCase());
		ignoreMethods.add("equals".toUpperCase());
		ignoreMethods.add("toString".toUpperCase());
		ignoreMethods.add("hashCode".toUpperCase());
		ignoreMethods.add("getClass".toUpperCase());
		ignoreMethods.add("notify".toUpperCase());
		ignoreMethods.add("notifyAll".toUpperCase());
	}
	
	
	private Object bean;
	
	private Map<String, RestMethod> methods = new HashMap<String, RestMethod>();

	
	
	
	
	public RestBean(Object bean) {
		this.bean = bean;
		parseMethod();
	}
	
	
	
	private void parseMethod() {
		Class<?>[] parents = this.bean.getClass().getInterfaces();
		if(parents==null || parents.length==0) {
			throw new ServiceException(" the bean '"+this.bean.getClass().getName()+"' must be implements interface! ");
		}
		
		
		Class<?> beanClass = null;
		for(int i=0; i<parents.length; i++) {
			Class<?> p = parents[i];
			if(p.getName().startsWith("com.aic.paas.")) {
				beanClass = p;
				break;
			}
		}
		
		if(beanClass == null) {
			throw new ServiceException(" the bean '"+this.bean.getClass().getName()+"' not found effective interface! ");
		}
		
		Method[] ms = beanClass.getMethods();
		for(int i=0; i<ms.length; i++) {
			Method mm = ms[i];
			String name = mm.getName().toUpperCase();
			if(ignoreMethods.contains(name)) continue ;
			
			if(methods.containsKey(name)) {
				throw new ServiceException(" is repeated method '"+mm.getName()+"' in bean '"+beanClass.getName()+"'! ");
			}
			
			methods.put(name, new RestMethod(this, mm));
		}
	}
	
	
	
	
	

	public Object getBean() {
		return bean;
	}

	
	
	
	
	public RestMethod getMethod(String methodName) {
		BinaryUtils.checkEmpty(methodName, "methodName");
		
		RestMethod method = methods.get(methodName.trim().toUpperCase());
		if(method == null) {
			throw new ServiceException(" not found method '"+methodName+"'! ");
		}
		
		return method;
	}
	
	
	
	

}
