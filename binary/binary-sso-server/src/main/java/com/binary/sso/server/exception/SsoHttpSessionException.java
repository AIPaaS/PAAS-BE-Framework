package com.binary.sso.server.exception;



public class SsoHttpSessionException extends SsoException {
	private static final long serialVersionUID = 1L;
	

	public SsoHttpSessionException() {
		super();
	}
	
	public SsoHttpSessionException(String message) {
		super(message);
	}
	
	public SsoHttpSessionException(Throwable cause) {
		super(cause);
	}
	
	public SsoHttpSessionException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	
}
