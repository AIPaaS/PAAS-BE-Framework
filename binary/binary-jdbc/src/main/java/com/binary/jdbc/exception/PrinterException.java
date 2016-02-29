package com.binary.jdbc.exception;

public class PrinterException extends JdbcException {
	private static final long serialVersionUID = 1L;
	
	
	public PrinterException() {
		super();
	}
	
	public PrinterException(String message) {
		super(message);
	}
	
	public PrinterException(Throwable cause) {
		super(cause);
	}
	
	public PrinterException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	
	
}
