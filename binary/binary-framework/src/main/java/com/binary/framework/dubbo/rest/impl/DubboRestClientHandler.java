package com.binary.framework.dubbo.rest.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import com.binary.core.util.BinaryUtils;
import com.binary.framework.dubbo.rest.DubboRestClient;

public class DubboRestClientHandler implements InvocationHandler {
	
	
	private static final Set<String> overrideMethods = new HashSet<String>();
	
	
	static {
		overrideMethods.add("equals");
		overrideMethods.add("toString");
		overrideMethods.add("hashCode");
	}
	
	
	
	private DubboRestClient restClient;
	private DubboRestClientProxy proxy;
	
	
	
	public DubboRestClientHandler(DubboRestClient restClient, DubboRestClientProxy proxy) {
		BinaryUtils.checkEmpty(restClient, "restClient");
		BinaryUtils.checkEmpty(proxy, "proxy");
		this.restClient = restClient;
		this.proxy = proxy;
	}
	
	
	
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		String methodName = method.getName();
		if(overrideMethods.contains(methodName)) {
			return doLocalMethod(proxy, method, args);
		}else {
			return doRemoteMethod(proxy, method, args);
		}
	}
	
	
	
	
	protected Object doLocalMethod(Object proxy, Method method, Object[] args) {
		String name = method.getName();
		if(name.equals("equals")) {
			if(args==null || args.length==0) {
				return false;
			}else {
				return proxy == args[0];
			}
		}else if(name.equals("toString")) {
			return proxy.getClass().getName() + "@" + Integer.toHexString(hashCode());
		}else if(name.equals("hashCode")) {
			return proxy.getClass().getName().hashCode();
		}
		return null;
	}
	
	
	
	protected Object doRemoteMethod(Object proxy, Method method, Object[] args) throws Throwable {
		String beanName = this.proxy.getBeanName();
		String methodName = this.proxy.getMethodRestName(method.getName());
		return this.restClient.request(beanName, methodName, args, method.getReturnType(), method.getGenericReturnType());
	}
	
	
	
	
	
}
