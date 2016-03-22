package com.binary.framework.dubbo.rest.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.binary.core.lang.ArrayUtils;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.Local;
import com.binary.framework.dubbo.rest.DubboRestParam;
import com.binary.framework.dubbo.rest.DubboRestResult;
import com.binary.framework.dubbo.rest.DubboRestServerHandler;
import com.binary.framework.exception.ServiceException;
import com.binary.framework.util.ExceptionUtil;
import com.binary.json.JSON;

public class SpringDubboRestServerHandler implements DubboRestServerHandler, ApplicationContextAware {
	private static final Logger logger = LoggerFactory.getLogger(SpringDubboRestServerHandler.class);

	
	private final Map<String, RestBean> beans = new HashMap<String, RestBean>();
	
	private ApplicationContext springContext;
	
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.springContext = applicationContext;
	}


	
	
	@Override
	public String post(String jsonParam)  throws Throwable  {
		DubboRestResult result = null;
		try {
			BinaryUtils.checkEmpty(jsonParam, "jsonParam");
			
			DubboRestParam param = JSON.toObject(jsonParam, DubboRestParam.class); 
			
			String beanName = param.getBeanName();
			String methodName = param.getMethodName();
			List<String> jsonArgumentList = param.getJsonArguments();
			String jsonUser = param.getJsonUser();
			
			BinaryUtils.checkEmpty(beanName, "beanName");
			BinaryUtils.checkEmpty(methodName, "methodName");
			
			beanName = beanName.trim();
			methodName = methodName.trim();
			RestBean bean = beans.get(beanName.toUpperCase());
			if(bean == null) {
				bean = parseRestBean(beanName);
			}
			
			String[] jsonArguments = null;
			if(jsonArgumentList != null) jsonArguments = ArrayUtils.toArray(jsonArgumentList, String.class);
			
			RestMethod method = bean.getMethod(methodName);
			String rs = method.invokeRest(jsonUser, jsonArguments);
			
			result = new DubboRestResult(rs);
		}catch(Throwable t) {
			Local.rollback();
			logger.error(" call '"+jsonParam+"' error! ", t);
			result = new DubboRestResult(ExceptionUtil.getRealThrowable(t));
		}
		
		return JSON.toString(result);
	}
	
	
	
	
	private synchronized RestBean parseRestBean(String beanName) {
		RestBean bean = beans.get(beanName.toUpperCase());
		if(bean == null) {
			Object obj = springContext.getBean(beanName);
			if(obj == null) {
				throw new ServiceException(" not found bean '"+beanName+"'! ");
			}
			bean = new RestBean(obj);
			beans.put(beanName.toUpperCase(), bean);
		}
		return bean;
	}

	
	
	
	
	
}
