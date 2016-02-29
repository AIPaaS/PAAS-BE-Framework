package com.binary.framework.exception;

import com.binary.core.exception.BinaryException;

public class FrameworkException extends BinaryException {
	private static final long serialVersionUID = 1L;

	public FrameworkException() {
		super();
	}
	
	public FrameworkException(String message) {
		super(message);
	}
	
	public FrameworkException(Throwable cause) {
		super(cause);
	}
	
	public FrameworkException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}


