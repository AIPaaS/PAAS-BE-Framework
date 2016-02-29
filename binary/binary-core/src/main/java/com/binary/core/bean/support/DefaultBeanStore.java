package com.binary.core.bean.support;

import java.util.HashMap;
import java.util.Map;

import com.binary.core.bean.Bean;
import com.binary.core.bean.BeanStore;

public class DefaultBeanStore implements BeanStore {	
	
	private final Map<String, Bean<?>> beanStore = new HashMap<String, Bean<?>>();
	
	
	public boolean containsKey(String beanName) {
		return beanStore.containsKey(beanName);
	}
	
	

	public Bean<?> getBean(String beanName) {
		return beanStore.get(beanName);
	}

	
	
	public void putBean(String beanName, Bean<?> bean) {
		beanStore.put(beanName, bean);
	}

}
