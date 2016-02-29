package com.binary.framework.dubbo.rest.impl;

import org.springframework.beans.factory.FactoryBean;

import com.binary.core.util.BinaryUtils;
import com.binary.framework.dubbo.rest.DubboRestClient;

public class DubboRestProxyReferenceBean implements FactoryBean<Object> {

	
	private DubboRestClientProxy proxy;
	
	
	public DubboRestProxyReferenceBean(DubboRestClientProxy proxy, DubboRestClient restClient) {
		BinaryUtils.checkEmpty(proxy, "proxy");
		this.proxy = proxy;
		this.proxy.setDubboRestClient(restClient);
	}
	
	
	
	
	@Override
	public Object getObject() throws Exception {
		return this.proxy.getProxyObject();
	}

	@Override
	public Class<?> getObjectType() {
		return this.proxy.getProxyClass();
	}
	
	

	@Override
	public boolean isSingleton() {
		return true;
	}

}
