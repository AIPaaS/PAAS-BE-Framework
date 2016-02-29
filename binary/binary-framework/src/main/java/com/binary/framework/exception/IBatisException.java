package com.binary.framework.exception;


public class IBatisException extends FrameworkException {
	private static final long serialVersionUID = 1L;

	public IBatisException() {
		super();
	}
	
	public IBatisException(String message) {
		super(message);
	}
	
	public IBatisException(Throwable cause) {
		super(cause);
	}
	
	public IBatisException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}


