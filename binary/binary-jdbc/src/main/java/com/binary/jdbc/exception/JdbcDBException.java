package com.binary.jdbc.exception;

public class JdbcDBException extends JdbcException {
	private static final long serialVersionUID = 1L;
	
	
	public JdbcDBException() {
		super();
	}
	
	public JdbcDBException(String message) {
		super(message);
	}
	
	public JdbcDBException(Throwable cause) {
		super(cause);
	}
	
	public JdbcDBException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	
	
}
