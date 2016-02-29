package com.binary.tools.exception;

import com.binary.core.exception.BinaryException;




public class BinaryToolsException extends BinaryException {
	private static final long serialVersionUID = 1L;

	
	
	public BinaryToolsException() {
		super();
	}
	
	public BinaryToolsException(String message) {
		super(message);
	}
	
	public BinaryToolsException(Throwable cause) {
		super(cause);
	}
	
	public BinaryToolsException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}


