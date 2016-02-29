package com.binary.dubbo.intercept.impl;

import org.apache.log4j.Logger;

public class DubboConsumerInterceptor extends AbstractDubboInterceptor {
	private static final Logger log = Logger.getLogger(DubboConsumerInterceptor.class);
	
	
	private static DubboConsumerInterceptor instance;
	
	
	
	public DubboConsumerInterceptor() {
		instance = this;
		writeLog(" create instance! ");
	}
	
	
	protected void writeLog(String msg) {
		log.info("Consumer: "+msg);
	}
	
	
	protected void writeErrorLog(String msg, Throwable t) {
		log.error("Consumer: "+msg, t);
	}
	
	
	
	
	/**
	 * 获取实例
	 * @return
	 */
	public static DubboConsumerInterceptor getInstance() {
		return instance;
	}
	
	

}
