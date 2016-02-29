package com.binary.task;

import com.binary.task.exception.TaskException;

public enum TaskStatus {

	
	
	/** 任务处理激活状态, 等待运行 **/
	WAIT(1),
	
	
	/** 任务正在运行当前 **/
	RUNNING(2),
	
	
	/** 任务被停止, 停止中的任务可以再被起动 **/
	STOPPED(3),
	
	
	/** 任务已结束, 结束了的任务不能再被启动 **/
	FINALIZED(8),
	
	
	/** 任务已销毁, 销毁了的任务不能再被启动**/
	DESTROYED(9); 
	
	
	
	
	private final int value;
	
	
    private TaskStatus(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
    
    public static TaskStatus valueOf(int v) {
    	switch(v) {
	    	case 1: return WAIT;
	    	case 2: return RUNNING;
	    	case 3: return STOPPED;
	    	case 8: return FINALIZED;
	    	case 9: return DESTROYED;
    		default : throw new TaskException(" is wrong TaskStatus:'"+v+"'! ");
    	}
    }
    
    
	
	
}



