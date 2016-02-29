package com.binary.framework.exception;


public class WebException extends FrameworkException {
	private static final long serialVersionUID = 1L;

	public WebException() {
		super();
	}
	
	public WebException(String message) {
		super(message);
	}
	
	public WebException(Throwable cause) {
		super(cause);
	}
	
	public WebException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}


