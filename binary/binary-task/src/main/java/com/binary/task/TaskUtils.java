package com.binary.task;


public class TaskUtils {
	
	
	
	
	public static long parsePeriod(TaskPeriodUnit unit, long num) {
		switch(unit) {
			case MILLISECOND: return num;
			case SECOND: return num * 1000;
			case MINUTE: return num * 1000 * 60;
			case HOUR: return num * 1000 * 60 * 60;
			case DAY: return num * 1000 * 60 * 60 * 24;
			case WEEK: return num * 1000 * 60 * 60 * 24 * 7;
			default : return num;
		}
	}
	
	
	
	
	
}
