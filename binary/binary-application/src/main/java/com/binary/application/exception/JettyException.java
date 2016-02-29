package com.binary.application.exception;

import com.binary.core.exception.BinaryException;


public class JettyException extends BinaryException {
	private static final long serialVersionUID = 1L;

	public JettyException() {
		super();
	}
	
	public JettyException(String message) {
		super(message);
	}
	
	public JettyException(Throwable cause) {
		super(cause);
	}
	
	public JettyException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}


