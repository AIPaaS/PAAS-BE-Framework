package com.binary.framework;

import org.springframework.context.ApplicationContext;

public interface ApplicationListener {
	
	
	
	/**
	 * 系统初始始化之后事件
	 * @param context
	 */
	public void afterInitialization(ApplicationContext context);
	
	

	
	
}
