package com.binary.jdbc.exception;

public class DataSourceException extends JdbcException {
	private static final long serialVersionUID = 1L;
	
	
	public DataSourceException() {
		super();
	}
	
	public DataSourceException(String message) {
		super(message);
	}
	
	public DataSourceException(Throwable cause) {
		super(cause);
	}
	
	public DataSourceException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	
	
}
