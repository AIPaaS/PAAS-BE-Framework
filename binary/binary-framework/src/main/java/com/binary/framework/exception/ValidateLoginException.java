package com.binary.framework.exception;


public class ValidateLoginException extends FrameworkException {
	private static final long serialVersionUID = 1L;

	public ValidateLoginException() {
		super();
	}
	
	public ValidateLoginException(String message) {
		super(message);
	}
	
	public ValidateLoginException(Throwable cause) {
		super(cause);
	}
	
	public ValidateLoginException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}


