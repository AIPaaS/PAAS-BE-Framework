package com.binary.framework.exception;


public class JavaException extends FrameworkException {
	private static final long serialVersionUID = 1L;

	public JavaException() {
		super();
	}
	
	public JavaException(String message) {
		super(message);
	}
	
	public JavaException(Throwable cause) {
		super(cause);
	}
	
	public JavaException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}


