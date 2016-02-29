package com.binary.task;

import com.binary.task.exception.TaskException;

public enum TaskPeriodUnit {

	
	MILLISECOND(0),
	
	SECOND(1),
	
	MINUTE(2),
	
	HOUR(3),
	
	DAY(4),
	
	WEEK(5);
	
	
	
	private int value;
	
	
    private TaskPeriodUnit(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
    
    public static TaskPeriodUnit valueOf(int v) {
    	switch(v) {
	    	case 0: return MILLISECOND;
	    	case 1: return SECOND;
	    	case 2: return MINUTE;
	    	case 3: return HOUR;
	    	case 4: return DAY;
	    	case 5: return WEEK;
    		default : throw new TaskException(" is wrong TaskPeriodUnit:'"+v+"'! ");
    	}
    }
    
    
	
	
}



