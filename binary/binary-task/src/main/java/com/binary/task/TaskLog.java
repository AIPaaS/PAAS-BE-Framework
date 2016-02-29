package com.binary.task;



/**
 * 任务日志
 * @author wanwb
 */
public interface TaskLog {
	
	
	
	
	/**
	 * 打印一般日志信息
	 * @param mgr: 任务管理器
	 * @param job: 任务对象
	 * @param type: 日志类型
	 * @param msg: 日志信息
	 */
	public void printInfo(TaskManager mgr, TaskJob job, TaskLogType type, String msg, boolean success);
	
	
	
	/**
	 * 打印异常日志
	 * @param mgr: 任务管理器
	 * @param job: 任务对象
	 * @param type: 日志类型
	 * @param msg: 日志信息
	 * @param e: 异常对象
	 */
	public void printError(TaskManager mgr, TaskJob job, TaskLogType type, String msg, Exception e);
	
	
	
	
	
	
}
