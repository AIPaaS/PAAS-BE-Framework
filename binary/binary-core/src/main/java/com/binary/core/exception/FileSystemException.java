package com.binary.core.exception;

public class FileSystemException extends CoreException {
	private static final long serialVersionUID = 1L;

	public FileSystemException() {
		super();
	}
	
	public FileSystemException(String message) {
		super(message);
	}
	
	public FileSystemException(Throwable cause) {
		super(cause);
	}
	
	public FileSystemException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}


