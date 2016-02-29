package com.binary.task.exception;


import com.binary.core.exception.BinaryException;




public class TaskException extends BinaryException {
	private static final long serialVersionUID = 1L;
	

	public TaskException() {
		super();
	}
	
	public TaskException(String message) {
		super(message);
	}
	
	public TaskException(Throwable cause) {
		super(cause);
	}
	
	public TaskException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	
}
