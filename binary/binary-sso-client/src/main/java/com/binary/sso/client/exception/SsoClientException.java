package com.binary.sso.client.exception;

import com.binary.core.exception.BinaryException;


public class SsoClientException extends BinaryException {
	private static final long serialVersionUID = 1L;
	

	public SsoClientException() {
		super();
	}
	
	public SsoClientException(String message) {
		super(message);
	}
	
	public SsoClientException(Throwable cause) {
		super(cause);
	}
	
	public SsoClientException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	
}
