package com.binary.sso.server.exception;



public class SsoTokenException extends SsoException {
	private static final long serialVersionUID = 1L;
	

	public SsoTokenException() {
		super();
	}
	
	public SsoTokenException(String message) {
		super(message);
	}
	
	public SsoTokenException(Throwable cause) {
		super(cause);
	}
	
	public SsoTokenException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	
}
