package com.binary.core.exception;

public class SerializationException extends CoreException {
	private static final long serialVersionUID = 1L;

	public SerializationException() {
		super();
	}
	
	public SerializationException(String message) {
		super(message);
	}
	
	public SerializationException(Throwable cause) {
		super(cause);
	}
	
	public SerializationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}


