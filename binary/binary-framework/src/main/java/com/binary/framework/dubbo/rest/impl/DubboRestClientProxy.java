package com.binary.framework.dubbo.rest.impl;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.binary.core.util.BinaryUtils;
import com.binary.framework.dubbo.rest.DubboRestClient;
import com.binary.framework.dubbo.rest.annotation.RestName;
import com.binary.framework.exception.DubboException;

public class DubboRestClientProxy {
	
	private static final Set<String> ignoreMethods = new HashSet<String>();
	
	
	static {
		ignoreMethods.add("wait");
		ignoreMethods.add("equals");
		ignoreMethods.add("toString");
		ignoreMethods.add("hashCode");
		ignoreMethods.add("getClass");
		ignoreMethods.add("notify");
		ignoreMethods.add("notifyAll");
	}
	
	
	
	private String beanName;
	private Class<?> proxyClass;
	private Object proxyObject;
	
	/** key=methodName, value=RestName **/
	private final Map<String, String> methodNames = new HashMap<String, String>();
	
	
	public DubboRestClientProxy(Class<?> proxyClass) {
		BinaryUtils.checkEmpty(proxyClass, "proxyClass");
		
		if(!proxyClass.isInterface()) throw new DubboException(" proxy class '"+proxyClass.getName()+"' must be interface ! ");
		this.proxyClass = proxyClass;
		
		parseAnnotation();
	}
	
	
	
	
	private void parseAnnotation() {
		RestName bn = this.proxyClass.getAnnotation(RestName.class);
		if(bn != null) {
			this.beanName = bn.value();
		}
		
		if(this.beanName==null || (this.beanName=this.beanName.trim()).length()==0) {
			this.beanName = this.proxyClass.getSimpleName();
		}
		
		Method[] ms = this.proxyClass.getMethods();
		if(ms!=null && ms.length>0) {
			for(int i=0; i<ms.length; i++) {
				String methodName = ms[i].getName();
				if(ignoreMethods.contains(methodName)) {
					continue ;
				}
				
				RestName rn = ms[i].getAnnotation(RestName.class);
				
				String restName = methodName;
				if(rn != null) {
					String v = rn.value();
					if(v!=null && (v=v.trim()).length()>0) {
						restName = v;
					}
				}
				
				if(methodNames.containsValue(restName)) {
					throw new DubboException(" Repeat the name '"+restName+"' of the method-restName! ");
				}
				
				methodNames.put(methodName, restName);
			}
		}
	}
	
	
	
	
	
	
	protected void setProxyObject(Object proxyObject) {
		this.proxyObject = proxyObject;
	}




	public synchronized void setDubboRestClient(DubboRestClient restClient) {
		BinaryUtils.checkEmpty(restClient, "restClient");
		
		Class<?> type = getProxyClass();
		Object proxyObj = Proxy.newProxyInstance(type.getClassLoader(), new Class[] { type }, new DubboRestClientHandler(restClient, this));
		setProxyObject(proxyObj);
	}
	
	
	
		
	/**
	 * 获取服务名
	 * @return
	 */
	public String getBeanName() {
		return this.beanName;
	}
	
	
	
	/**
	 * 
	 * @param methodName
	 * @return
	 */
	public String getMethodRestName(String methodName) {
		String restName = this.methodNames.get(methodName);
		if(restName == null) throw new DubboException(" The method '"+methodName+"' does not exist or is not permitted to call! ");
		return restName;
	}
	
	
	/**
	 * 获取代理对象
	 * @return
	 */
	public Object getProxyObject() {
		return this.proxyObject;
	}
	
	
	
	/**
	 * 获取代理客户端类名
	 * @return
	 */
	public Class<?> getProxyClass() {
		return this.proxyClass;
	}
	
	

}
