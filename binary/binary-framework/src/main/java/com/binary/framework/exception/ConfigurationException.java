package com.binary.framework.exception;


public class ConfigurationException extends FrameworkException {
	private static final long serialVersionUID = 1L;

	public ConfigurationException() {
		super();
	}
	
	public ConfigurationException(String message) {
		super(message);
	}
	
	public ConfigurationException(Throwable cause) {
		super(cause);
	}
	
	public ConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}


