package com.binary.framework.exception;


public class AuthException extends FrameworkException {
	private static final long serialVersionUID = 1L;

	public AuthException() {
		super();
	}
	
	public AuthException(String message) {
		super(message);
	}
	
	public AuthException(Throwable cause) {
		super(cause);
	}
	
	public AuthException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}


