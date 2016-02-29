package com.binary.framework.exception;


public class DubboException extends FrameworkException {
	private static final long serialVersionUID = 1L;

	public DubboException() {
		super();
	}
	
	public DubboException(String message) {
		super(message);
	}
	
	public DubboException(Throwable cause) {
		super(cause);
	}
	
	public DubboException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}


