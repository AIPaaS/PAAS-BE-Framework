package com.binary.framework.dubbo.rest.impl;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import com.binary.core.lang.Conver;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.Local;
import com.binary.framework.bean.SimpleUser;
import com.binary.framework.util.ExceptionUtil;
import com.binary.json.JSON;

public class RestMethod {
	
	
	private RestBean bean;
	private Method method;
	
	
	private Type[] parameterTypes;
//	private Type resultType;
	
	
	public RestMethod(RestBean bean, Method method) {
		this.bean = bean;
		this.method = method;
		
		this.parameterTypes = this.method.getGenericParameterTypes();
//		this.resultType = this.method.getGenericReturnType();
		
		if(this.parameterTypes == null) this.parameterTypes = new Type[0];
	}
	
	
	
	public RestBean getBean() {
		return bean;
	}
	public Method getMethod() {
		return method;
	}
	
	
	
	
	
	public String invokeRest(String jsonUser, String[] jsonArgs) throws Throwable  {
		try {
			Object[] args = new Object[this.parameterTypes.length];
			
			if(jsonArgs!=null && jsonArgs.length>0) {
				for(int i=0; i<jsonArgs.length&&i<this.parameterTypes.length; i++) {
					String s = jsonArgs[i];
					if(s==null || (s=s.trim()).length()==0) {
						continue ;
					}
					
					Type type = this.parameterTypes[i];
					Object jsonObj = JSON.toObject(s);
					args[i] = Conver.mapping(type, jsonObj);
				}
			}
			
			if(!BinaryUtils.isEmpty(jsonUser)) {
				SimpleUser user = JSON.toObject(jsonUser, SimpleUser.class);
				Local.getCriticalObject().setUser(user);
			}
			Object result = this.method.invoke(this.bean.getBean(), args);
			if(result == null) return null;
			
			return JSON.toString(result);
		}catch(Throwable t) {
			throw ExceptionUtil.getRealThrowable(t);
		}
	}
	
	
	
	

}
