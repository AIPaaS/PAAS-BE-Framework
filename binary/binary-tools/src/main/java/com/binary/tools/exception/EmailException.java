package com.binary.tools.exception;

import com.binary.core.exception.BinaryException;




public class EmailException extends BinaryException {
	private static final long serialVersionUID = 1L;

	
	
	public EmailException() {
		super();
	}
	
	public EmailException(String message) {
		super(message);
	}
	
	public EmailException(Throwable cause) {
		super(cause);
	}
	
	public EmailException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}


