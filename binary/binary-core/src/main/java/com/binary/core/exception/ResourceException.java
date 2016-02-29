package com.binary.core.exception;

public class ResourceException extends CoreException {
	private static final long serialVersionUID = 1L;

	public ResourceException() {
		super();
	}
	
	public ResourceException(String message) {
		super(message);
	}
	
	public ResourceException(Throwable cause) {
		super(cause);
	}
	
	public ResourceException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}


