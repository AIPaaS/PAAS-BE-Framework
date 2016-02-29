package com.binary.framework.exception;


public class TestException extends FrameworkException {
	private static final long serialVersionUID = 1L;

	public TestException() {
		super();
	}
	
	public TestException(String message) {
		super(message);
	}
	
	public TestException(Throwable cause) {
		super(cause);
	}
	
	public TestException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}


