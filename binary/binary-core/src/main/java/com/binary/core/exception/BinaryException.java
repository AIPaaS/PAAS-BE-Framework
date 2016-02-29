package com.binary.core.exception;


public class BinaryException extends RuntimeException implements Nestable {
	private static final long serialVersionUID = 1L;
	
	
	private Throwable cause;
	
	
	public BinaryException() {
		super();
	}
	
	
	public BinaryException(String message) {
		super(message);
	}
	
	
	
	public BinaryException(Throwable cause) {
		super();
		this.cause = cause;
	}
	
	
	
	public BinaryException(String message, Throwable cause) {
		super(message);
		this.cause = cause;
	}
	
	
	
	public String getMessage() {
		String msg = super.getMessage();
		if((msg==null || msg.length()==0) && cause!=null) msg = cause.getMessage();
		return msg;
	}
	
	
	public Throwable getMessageThrowable() {
		String msg = super.getMessage();
		if((msg!=null && msg.length()>0) || cause==null) {
			return this;
		}else {
			return (cause instanceof Nestable) ? ((Nestable)cause).getMessageThrowable() : cause;
		}
	}
	
	
	
	public Throwable getOriginalThrowable() {
		return cause==null ? this : ((cause instanceof Nestable) ? ((Nestable)cause).getOriginalThrowable() : cause);
	}
	
	
	
	
	
	public String getOriginalMessage() {
		Throwable t = getOriginalThrowable();
		return t==null ? null : t.getMessage();
	}
	
	
	public String getFullMessage() {
		String msg = super.getMessage();
		if(msg == null) msg = "";
		if(cause != null) {
			msg += "\n\tThrowable: "+cause.toString();
			StackTraceElement[] stackTrace = cause.getStackTrace();
			if(stackTrace != null) for(int i=0; i<stackTrace.length; i++) msg += "\n\t\tat "+stackTrace[i]+"";
		}else {
			StackTraceElement[] stackTrace = this.getStackTrace();
			if(stackTrace != null) for(int i=0; i<stackTrace.length; i++) msg += "\n\t\tat "+stackTrace[i]+"";
		}
		return msg;
	}
	
	
	public String toString() {
        String s = getClass().getName();
        String message = getFullMessage();
        return (message != null) ? (s + ": " + message) : s;
    }
	
	
	
	
	public Throwable getCause() {
		return this.cause;
	}
	
	
	
}
