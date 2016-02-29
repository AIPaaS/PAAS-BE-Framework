package com.binary.framework.exception;


public class PrimaryKeyException extends FrameworkException {
	private static final long serialVersionUID = 1L;

	public PrimaryKeyException() {
		super();
	}
	
	public PrimaryKeyException(String message) {
		super(message);
	}
	
	public PrimaryKeyException(Throwable cause) {
		super(cause);
	}
	
	public PrimaryKeyException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}


