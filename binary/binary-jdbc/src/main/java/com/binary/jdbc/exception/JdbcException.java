package com.binary.jdbc.exception;

import com.binary.core.exception.BinaryException;

public class JdbcException extends BinaryException {
	private static final long serialVersionUID = 1L;

	public JdbcException() {
		super();
	}
	
	public JdbcException(String message) {
		super(message);
	}
	
	public JdbcException(Throwable cause) {
		super(cause);
	}
	
	public JdbcException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}


