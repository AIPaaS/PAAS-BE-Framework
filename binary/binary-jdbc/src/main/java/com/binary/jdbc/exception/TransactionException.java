package com.binary.jdbc.exception;

public class TransactionException extends JdbcException {
	private static final long serialVersionUID = 1L;
	
	
	public TransactionException() {
		super();
	}
	
	public TransactionException(String message) {
		super(message);
	}
	
	public TransactionException(Throwable cause) {
		super(cause);
	}
	
	public TransactionException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	
	
}
