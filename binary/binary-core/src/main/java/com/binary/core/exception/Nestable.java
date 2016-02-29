package com.binary.core.exception;



public interface Nestable {

	
	/**
	 * 获取嵌套Throwable
	 * @return
	 */
	public Throwable getCause();
	
	
	
	/**
	 * 获取异常消息，以最外层向内推，直至含有消息
	 * @return
	 */
	public String getMessage();
	
	
	
	/**
	 * 获取抛出异常消息的异常
	 * @return
	 */
	public Throwable getMessageThrowable() ;
	
	
	/**
	 * 获取所有异常信息
	 * @return
	 */
	public String getFullMessage() ;
	
	
	
	/**
	 * 获取最里层嵌套异常消息
	 * @return
	 */
	public String getOriginalMessage() ;
	
	
	
	
	/**
	 * 获取最里层嵌套异常
	 * @return
	 */
	public Throwable getOriginalThrowable() ;
	
	
	
	
	
	
	
	
	
	
}


