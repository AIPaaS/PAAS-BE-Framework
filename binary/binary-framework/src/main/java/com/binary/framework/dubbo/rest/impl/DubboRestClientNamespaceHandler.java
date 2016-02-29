package com.binary.framework.dubbo.rest.impl;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class DubboRestClientNamespaceHandler extends NamespaceHandlerSupport {

	
	
	@Override
	public void init() {
		 registerBeanDefinitionParser("rest", new DubboRestClientBeanDefinitionParser());
	}
	
	

}
