package com.binary.core.exception;

public class HttpException extends CoreException {
	private static final long serialVersionUID = 1L;

	public HttpException() {
		super();
	}
	
	public HttpException(String message) {
		super(message);
	}
	
	public HttpException(Throwable cause) {
		super(cause);
	}
	
	public HttpException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}


