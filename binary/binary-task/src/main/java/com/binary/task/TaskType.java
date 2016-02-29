package com.binary.task;

import com.binary.task.exception.TaskException;

public enum TaskType {

	
	Period(1),
	Calendar(2);
	
	
	private final int value;
	
	
    private TaskType(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
    
    public static TaskType valueOf(int v) {
    	switch(v) {
	    	case 1: return Period;
	    	case 2: return Calendar;
    		default : throw new TaskException(" is wrong TaskType:'"+v+"'! ");
    	}
    }
    
    
	
	
}



