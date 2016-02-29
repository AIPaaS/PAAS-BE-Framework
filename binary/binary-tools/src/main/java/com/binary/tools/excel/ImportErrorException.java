package com.binary.tools.excel;

import com.binary.tools.exception.ExcelException;




public class ImportErrorException extends ExcelException {
	private static final long serialVersionUID = 1L;
	
	public static enum ErrorType {
		NULL(1),
		NotInEnums(2),
		WrongValue(3);
		
		private final int value;
		
		
	    private ErrorType(int value) {
	        this.value = value;
	    }
	    
	    public int getValue() {
	        return value;
	    }
	    
	    public String getRemark() {
	    	switch(this.value) {
	    		case 1: return "数据不可以为空!";
	    		case 2: return "数据不在枚举范围之内!"; 
	    		case 3: return "错误的数据!"; 
	    		default : return null;
	    	}
	    }
	    
	    public static ErrorType valueOf(int v) {
	    	switch(v) {
	    		case 1: return NULL;
	    		case 2: return NotInEnums; 
	    		case 3: return WrongValue; 
	    		default : throw new ExcelException(" is wrong ErrorType:'"+v+"'! ");
	    	}
	    }
	    
	}
	
	private ErrorType errortype;
	private boolean ignored=false;
	
	
	public ImportErrorException(ErrorType type) {
		super();
		this.errortype = type;
		if(this.errortype == null) throw new ExcelException(" is null ErrorType argument! ");
	}
	
	public ImportErrorException(String message, ErrorType type) {
		super(message);
		this.errortype = type;
		if(this.errortype == null) throw new ExcelException(" is null ErrorType argument! ");
	}
	
	public ImportErrorException(Throwable cause, ErrorType type) {
		super(cause);
		this.errortype = type;
		if(this.errortype == null) throw new ExcelException(" is null ErrorType argument! ");
	}
	
	public ImportErrorException(String message, Throwable cause, ErrorType type) {
		super(message, cause);
		this.errortype = type;
		if(this.errortype == null) throw new ExcelException(" is null ErrorType argument! ");
	}
	
	
	public ErrorType getErrorType() {
		return errortype;
	}
	
	
	/**
	 * 忽略错误
	 */
	public void ignoreError() {
		this.ignored = true;
	}
	
	/**
	 * 判断是否被忽略
	 * @return
	 */
	public boolean isIgnored() {
		return this.ignored;
	}
	
	

	

}
