package com.binary.framework.dubbo.rest.impl;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import com.binary.core.lang.ClassUtils;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.exception.DubboException;

public class DubboRestClientBeanDefinitionParser implements BeanDefinitionParser {

	
	
	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		String id = element.getAttribute("id");
		String type = element.getAttribute("interface");
		String clientRef = element.getAttribute("client");
		
		if(BinaryUtils.isEmpty(type)) {
			throw new DubboException(" not found attribute 'interface' in element '"+element.getNodeName()+"'! ");
		}
		if(BinaryUtils.isEmpty(clientRef)) {
			throw new DubboException(" not found attribute 'client' in element '"+element.getNodeName()+"'! ");
		}
		
		type = type.trim();
		clientRef = clientRef.trim();
		if(id==null || (id=id.trim()).length()==0) id = type;
		
		Class<?> proxyClass = ClassUtils.forName(type, DubboRestClientBeanDefinitionParser.class.getClassLoader());
		DubboRestClientProxy proxy = new DubboRestClientProxy(proxyClass);
		
		
		RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(DubboRestProxyReferenceBean.class);
        beanDefinition.setLazyInit(false);
        
        beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0, proxy);
        beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(1, new RuntimeBeanReference(clientRef));
        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
		
		return beanDefinition;
	}
	
	
	
	
	
}
