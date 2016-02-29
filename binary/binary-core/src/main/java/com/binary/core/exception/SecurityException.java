package com.binary.core.exception;

public class SecurityException extends CoreException {
	private static final long serialVersionUID = 1L;

	public SecurityException() {
		super();
	}
	
	public SecurityException(String message) {
		super(message);
	}
	
	public SecurityException(Throwable cause) {
		super(cause);
	}
	
	public SecurityException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}


