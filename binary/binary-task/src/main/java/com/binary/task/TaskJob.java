package com.binary.task;

import java.util.Date;





/**
 * 任务执行接口
 * @author wanwb
 */
public interface TaskJob {
	
	
	
	/**
	 * 获取任务名称
	 * @return
	 */
	public String getName();
	
	
	
	/**
	 * 获取任务类型(不可为空)
	 * @return
	 */
	public TaskType getTaskType();
	
	
	
	
	/**
	 * 获取任务起始调用时间(不可为空)
	 * @return
	 */
	public Date getStartTime();
	
	
	
	
	/**
	 * 获取任务结束调用时间(为空表示无限)
	 * @return
	 */
	public Date getEndTime();
	
	
	
	
	/**
	 * 获取表达式
	 * TaskType=Period: 周期单位|周期量,运行次数	(周期单位：0||缺省=毫秒, 1=秒, 2=分钟, 3=小时, 4=天, 5=星期)
	 * TaskType=Calendar: cron表达式
	 * @return
	 */
	public String getExpression();
	
	
	
	
	/**
	 * 任务执行出错时，指定重试次数，null=0
	 * @return
	 */
	public Integer getRetryCount();
	
	
	
	
	/**
	 * 将最近一次运行时间保存起来, 主要为了任务调度器在重启时能保证时间区间与停止之前保持一至
	 * @param date
	 */
	public void saveLastRunTime(Date date);
	
	
	
	/**
	 * 获取最近一次运行时间, 任务重启时会以最近启动时间为下一次的启动时间, 为空则以计划启动时间为第一次运行时间
	 * @return
	 */
	public Date getLastRunTime();
	
	
	
	
	/**
	 * 记录任务运行次数
	 */
	public void addRunnedCount();
	
	
	/**
	 * 获取任务已运行次数, 任务重启时, 如查任务指定了运行次数限制, 取此参数会累计之前的运行次数, 为null||<0则表示不累计
	 * @return
	 */
	public Integer getRunnedCount();
	
	
	
	/**
	 * 如果任务类型为Period时, 任务启动和每次运行时会触发此事件, 依赖LastRunTime属性
	 * @param nextTime
	 */
	public void setNextRunTime(Date nextTime);
	
	
	
	/**
	 * 当任务被启动\执行\停止\完成\销毁时, 会触发些事件
	 * @param status
	 */
	public void updateTaskStatus(TaskStatus status);
	
	
	
	/**
	 * 执行任务
	 * @param mgr: 任务管理器
	 * @param jobId: 任务ID
	 * @return
	 */
	public void run(TaskManager mgr, String jobId);
	
	
	
}




