package com.binary.framework.exception;


public class SessionException extends FrameworkException {
	private static final long serialVersionUID = 1L;

	public SessionException() {
		super();
	}
	
	public SessionException(String message) {
		super(message);
	}
	
	public SessionException(Throwable cause) {
		super(cause);
	}
	
	public SessionException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}


