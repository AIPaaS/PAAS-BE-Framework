package com.binary.task;

import java.util.Date;



/**
 * TaskJob减压适配器
 * @author wanwb
 */
public abstract class TaskJobAdapter implements TaskJob {
	
	
	
	public Date getStartTime() {
		return null;
	}
	
	
	public Date getEndTime() {
		return null;
	}
	
	
	
	public void saveLastRunTime(Date date) {
	}
	
	
	public Date getLastRunTime() {
		return null;
	}
	
	
	public void addRunnedCount() {
	}
	
	
	public Integer getRunnedCount() {
		return null;
	}
	
	
	public void setNextRunTime(Date nextTime) {
	}
	
	
	
	public void updateTaskStatus(TaskStatus status) {
	}
	
	
	
	
}



