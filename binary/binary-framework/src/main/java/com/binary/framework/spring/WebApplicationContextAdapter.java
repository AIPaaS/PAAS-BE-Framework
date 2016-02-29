package com.binary.framework.spring;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.web.context.WebApplicationContext;

public class WebApplicationContextAdapter implements WebApplicationContext {
	
	
	private ApplicationContext springContext;
	
	private ServletContext servletContext;
	
	
	public WebApplicationContextAdapter(ApplicationContext springContext, ServletContext servletContext) {		
		this.springContext = springContext;
		this.servletContext = servletContext;
	}
	
	
	@Override
	public String getId() {
		return springContext.getId();
	}

	@Override
	public String getDisplayName() {
		return springContext.getDisplayName();
	}

	@Override
	public long getStartupDate() {
		return springContext.getStartupDate();
	}

	@Override
	public ApplicationContext getParent() {
		return springContext.getParent();
	}

	@Override
	public AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {
		return springContext.getAutowireCapableBeanFactory();
	}

	@Override
	public Environment getEnvironment() {
		return springContext.getEnvironment();
	}

	
	@Override
	public boolean containsBeanDefinition(String beanName) {
		return springContext.containsBeanDefinition(beanName);
	}
	

	@Override
	public int getBeanDefinitionCount() {
		return springContext.getBeanDefinitionCount();
	}

	@Override
	public String[] getBeanDefinitionNames() {
		return springContext.getBeanDefinitionNames();
	}

	@Override
	public String[] getBeanNamesForType(Class<?> type) {
		return springContext.getBeanNamesForType(type);
	}

	@Override
	public String[] getBeanNamesForType(Class<?> type, boolean includeNonSingletons, boolean allowEagerInit) {
		return springContext.getBeanNamesForType(type, includeNonSingletons, allowEagerInit);
	}

	
	@Override
	public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
		return springContext.getBeansOfType(type);
	}

	@Override
	public <T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit) throws BeansException {
		return springContext.getBeansOfType(type, includeNonSingletons, allowEagerInit);
	}

	@Override
	public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException {
		return springContext.getBeansWithAnnotation(annotationType);
	}

	
	@Override
	public <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType) {
		return springContext.findAnnotationOnBean(beanName, annotationType);
	}

	@Override
	public Object getBean(String name) throws BeansException {
		return springContext.getBean(name);
	}

	@Override
	public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return springContext.getBean(name, requiredType);
	}

	@Override
	public <T> T getBean(Class<T> requiredType) throws BeansException {
		return springContext.getBean(requiredType);
	}

	@Override
	public Object getBean(String name, Object... args) throws BeansException {
		return springContext.getBean(name, args);
	}

	@Override
	public boolean containsBean(String name) {
		return springContext.containsBean(name);
	}

	@Override
	public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return springContext.isSingleton(name);
	}

	@Override
	public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
		return springContext.isPrototype(name);
	}

	@Override
	public boolean isTypeMatch(String name, Class<?> targetType) throws NoSuchBeanDefinitionException {
		return springContext.isTypeMatch(name, targetType);
	}

	@Override
	public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		return springContext.getType(name);
	}

	@Override
	public String[] getAliases(String name) {
		return springContext.getAliases(name);
	}

	@Override
	public BeanFactory getParentBeanFactory() {
		return springContext.getParentBeanFactory();
	}

	@Override
	public boolean containsLocalBean(String name) {
		return springContext.containsBean(name);
	}

	@Override
	public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
		return springContext.getMessage(code, args, defaultMessage, locale);
	}

	@Override
	public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
		return springContext.getMessage(code, args, locale);
	}

	@Override
	public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
		return springContext.getMessage(resolvable, locale);
	}

	@Override
	public void publishEvent(ApplicationEvent event) {
		springContext.publishEvent(event);
	}

	@Override
	public Resource[] getResources(String locationPattern) throws IOException {
		return springContext.getResources(locationPattern);
	}

	@Override
	public Resource getResource(String location) {
		return springContext.getResource(location);
	}

	@Override
	public ClassLoader getClassLoader() {
		return springContext.getClassLoader();
	}

	@Override
	public ServletContext getServletContext() {
		return servletContext;
	}
	
	
	
	

}
