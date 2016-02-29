package com.binary.core.exception;

import java.util.ArrayList;
import java.util.List;


public class MultipleException extends CoreException {
	private static final long serialVersionUID = 1L;
	
	
	private List<Throwable> list;
	
	
	public MultipleException() {
		super();
	}
	
	public MultipleException(String message) {
		super(message);
	}
	
	public MultipleException(Throwable cause) {
		super(cause);
	}
	
	public MultipleException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	private synchronized void initList() {
		if(list != null) return ;
		list = new ArrayList<Throwable>();
	}
	
	public void add(Throwable e) {
		if(list == null) initList();
		list.add(e);
	}
	
	
	public String getMessage() {
		String msg = super.getMessage();
		StringBuffer sb = new StringBuffer(msg==null ? "" : msg);
		if(list != null) {
			if(sb.length()>0) sb.append("\n");
			for(int i=0; i<list.size(); i++) {
				if(i > 0) sb.append("\n");
				Throwable t = list.get(i);
				sb.append(t instanceof BinaryException ? ((BinaryException)t).getOriginalMessage() : t.getMessage());
			}
		}
		return sb.toString();
	}

	
	public String getOriginalMessage() {
		return this.getMessage();
	}
	
	
	public int size() {
		return list!=null ? list.size() : 0;
	}
	
	
	public StackTraceElement[] getStackTrace() {
		StackTraceElement[] array = super.getStackTrace(); 
		if(size() > 0) {
			List<StackTraceElement> els = new ArrayList<StackTraceElement>();
			if(array!=null)for(int i=0; i<array.length; i++)els.add(array[i]);
			for(int i=0; i<list.size(); i++) {
				array = list.get(i).getStackTrace();
				if(array!=null)for(int j=0; j<array.length; j++)els.add(array[j]);
			}
			array = new StackTraceElement[els.size()];
			els.toArray(array);
		}
		return array;
	}
	
	
	public String getFullMessage() {
		String msg = super.getMessage();
		if(msg == null) msg = "";
		StringBuilder sb = new StringBuilder(msg);
		if(list!=null && list.size()>0) {
			for(int i=0; i<list.size(); i++) {
				Throwable cause = list.get(i);
				sb.append("\n\tThrowable: ").append(cause.toString());
				StackTraceElement[] stackTrace = cause.getStackTrace();
				if(stackTrace != null) {
					for(int j=0; j<stackTrace.length; j++) {
						sb.append("\n\t\tat ").append(stackTrace[j]);
					}
				}
			}
		}else {
			StackTraceElement[] stackTrace = this.getStackTrace();
			if(stackTrace != null) {
				for(int i=0; i<stackTrace.length; i++) {
					sb.append("\n\t\tat ").append(stackTrace[i]);
				}
			}
		}
		return sb.toString();
	}
	
	
	
	/**
	 * 添加Exception
	 * @param me
	 * @param cause
	 * @return
	 */
	public static MultipleException appendException(MultipleException me, Throwable cause) {
		if(cause != null) {
			if(me == null) me = new MultipleException();
			me.add(cause);
		}
		return me;
	}

}
