package com.binary.task;



/**
 * 任务日志类型
 * @author wanwb
 */
public enum TaskLogType {
	
	
	
	/**
	 * 启动任务调度器
	 */
	SCHEDULER_START,
	
	
	
	/**
	 * 停止任务调度器
	 */
	SCHEDULER_STOP,
	
	
	/**
	 * 添加任务
	 */
	TASK_PUSH,
	
	
	
	/**
	 * 删除任务
	 */
	TASK_POP,
	
	
	
	/**
	 * 启动任务
	 */
	TASK_START,
	
	
	
	/**
	 * 停止任务
	 */
	TASK_STOP,
	
	
	
	/**
	 * 运行任务
	 */
	TASK_RUN,
	
	
	
	/**
	 * 销毁\终止任务
	 */
	TASK_DESTROY,
	
	
	
	/**
	 * 任务结束
	 */
	TASK_FINISH
	
	
	
}




