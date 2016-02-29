package com.binary.jdbc.exception;

public class SqlParserException extends JdbcException {
	private static final long serialVersionUID = 1L;
	

	public SqlParserException() {
		super();
	}
	
	public SqlParserException(String message) {
		super(message);
	}
	
	public SqlParserException(Throwable cause) {
		super(cause);
	}
	
	public SqlParserException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
