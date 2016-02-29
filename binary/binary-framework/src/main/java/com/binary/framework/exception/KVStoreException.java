package com.binary.framework.exception;


public class KVStoreException extends FrameworkException {
	private static final long serialVersionUID = 1L;

	public KVStoreException() {
		super();
	}
	
	public KVStoreException(String message) {
		super(message);
	}
	
	public KVStoreException(Throwable cause) {
		super(cause);
	}
	
	public KVStoreException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}


