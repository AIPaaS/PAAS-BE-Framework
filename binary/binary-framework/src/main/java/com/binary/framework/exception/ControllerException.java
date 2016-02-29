package com.binary.framework.exception;


public class ControllerException extends FrameworkException {
	private static final long serialVersionUID = 1L;

	public ControllerException() {
		super();
	}
	
	public ControllerException(String message) {
		super(message);
	}
	
	public ControllerException(Throwable cause) {
		super(cause);
	}
	
	public ControllerException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}


