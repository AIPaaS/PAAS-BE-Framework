package com.binary.tools.exception;

import com.binary.core.exception.BinaryException;




public class ExcelException extends BinaryException {
	private static final long serialVersionUID = 1L;

	
	
	public ExcelException() {
		super();
	}
	
	public ExcelException(String message) {
		super(message);
	}
	
	public ExcelException(Throwable cause) {
		super(cause);
	}
	
	public ExcelException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}


