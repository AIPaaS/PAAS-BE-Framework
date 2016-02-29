package com.binary.framework.dubbo.rest;

import java.io.Serializable;
import java.util.List;

public class DubboRestParam implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private String beanName;
	private String methodName;
	private List<String> jsonArguments;
	private String jsonUser;
	
	
	
	
	public String getBeanName() {
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	public String getJsonUser() {
		return jsonUser;
	}
	public void setJsonUser(String jsonUser) {
		this.jsonUser = jsonUser;
	}
	public List<String> getJsonArguments() {
		return jsonArguments;
	}
	public void setJsonArguments(List<String> jsonArguments) {
		this.jsonArguments = jsonArguments;
	}
	
	
	
	
	
	
	
	
	
}
