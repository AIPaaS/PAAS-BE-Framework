package com.binary.core.exception;

public class CompressionException extends BinaryException {
	private static final long serialVersionUID = 1L;

	public CompressionException() {
		super();
	}
	
	public CompressionException(String message) {
		super(message);
	}
	
	public CompressionException(Throwable cause) {
		super(cause);
	}
	
	public CompressionException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}


