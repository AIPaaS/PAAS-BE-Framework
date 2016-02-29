package com.binary.sso.server.exception;

import com.binary.core.exception.BinaryException;


public class SsoException extends BinaryException {
	private static final long serialVersionUID = 1L;
	

	public SsoException() {
		super();
	}
	
	public SsoException(String message) {
		super(message);
	}
	
	public SsoException(Throwable cause) {
		super(cause);
	}
	
	public SsoException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	
}
