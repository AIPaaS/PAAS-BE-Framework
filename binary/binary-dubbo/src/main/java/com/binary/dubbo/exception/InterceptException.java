package com.binary.dubbo.exception;

import com.binary.core.exception.BinaryException;


public class InterceptException extends BinaryException {
	private static final long serialVersionUID = 1L;

	public InterceptException() {
		super();
	}
	
	public InterceptException(String message) {
		super(message);
	}
	
	public InterceptException(Throwable cause) {
		super(cause);
	}
	
	public InterceptException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}


