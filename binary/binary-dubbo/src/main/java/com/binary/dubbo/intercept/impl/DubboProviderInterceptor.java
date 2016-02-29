package com.binary.dubbo.intercept.impl;

import org.apache.log4j.Logger;

public class DubboProviderInterceptor extends AbstractDubboInterceptor {
	private static final Logger log = Logger.getLogger(DubboProviderInterceptor.class);
	
	
	private static DubboProviderInterceptor instance;
	
	
	public DubboProviderInterceptor() {
		instance = this;
		writeLog(" create instance! ");
	}
	
	
	protected void writeLog(String msg) {
		log.info("Provider: "+msg);
	}
	
	
	protected void writeErrorLog(String msg, Throwable t) {
		log.error("Provider: "+msg, t);
	}
	
	
	
	
	/**
	 * 获取实例
	 * @return
	 */
	public static DubboProviderInterceptor getInstance() {
		return instance;
	}
	
	
}
