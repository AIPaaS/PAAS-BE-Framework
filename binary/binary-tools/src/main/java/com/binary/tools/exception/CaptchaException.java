package com.binary.tools.exception;

import com.binary.core.exception.BinaryException;




public class CaptchaException extends BinaryException {
	private static final long serialVersionUID = 1L;

	
	
	public CaptchaException() {
		super();
	}
	
	public CaptchaException(String message) {
		super(message);
	}
	
	public CaptchaException(Throwable cause) {
		super(cause);
	}
	
	public CaptchaException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}


