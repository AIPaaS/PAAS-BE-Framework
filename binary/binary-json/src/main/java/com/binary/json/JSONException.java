package com.binary.json;

import com.binary.core.exception.CoreException;

public class JSONException extends CoreException {
	private static final long serialVersionUID = 1L;

	public JSONException() {
		super();
	}
	
	public JSONException(String message) {
		super(message);
	}
	
	public JSONException(Throwable cause) {
		super(cause);
	}
	
	public JSONException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}


