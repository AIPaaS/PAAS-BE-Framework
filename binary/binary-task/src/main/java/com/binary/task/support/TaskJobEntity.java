package com.binary.task.support;

import org.quartz.JobDetail;

import com.binary.core.util.BinaryUtils;
import com.binary.task.TaskJob;
import com.binary.task.TaskPeriodUnit;
import com.binary.task.TaskStatus;
import com.binary.task.TaskType;
import com.binary.task.TaskUtils;
import com.binary.task.exception.TaskException;

public class TaskJobEntity {
	
	
	private String id;
	private TaskJob job;
	private TaskStatus status;
	private JobDetail detail;
	private long periodMillis;
	private int periodRunCount;
	
	
	
	public TaskJobEntity(TaskJob job) {
		if(job == null) throw new TaskException(" the job is null argument! ");
		
		String jobname = job.getName();
		if(BinaryUtils.isEmpty(jobname)) throw new TaskException(" the job-name is empty argument! ");
		
		TaskType taskType = job.getTaskType();
		if(taskType == null) throw new TaskException(" the job-type is null argument! ");
		
		String expression = job.getExpression();
		if(expression==null || (expression=expression.trim()).length()==0) {
			throw new TaskException("the job-expression is empty argument!");
		}
		
		this.id = BinaryUtils.getUUID();
		this.job = job;
		this.status = TaskStatus.STOPPED;
		
		if(taskType == TaskType.Period) {
			TaskPeriodUnit unit = TaskPeriodUnit.MILLISECOND;
			long num = 0;
			int runCount = -1;
			if(expression.indexOf('|') > 0) {
				unit = TaskPeriodUnit.valueOf(Integer.parseInt(expression.substring(0, expression.indexOf('|'))));
				String exp = expression.substring(expression.indexOf('|')+1);
				if(exp.indexOf(',') > 0) {
					num = Long.parseLong(exp.substring(0, exp.indexOf(',')));
					runCount = Integer.parseInt(exp.substring(exp.indexOf(',')+1));
				}else {
					num = Long.parseLong(exp);
				}
			}else {
				if(expression.indexOf(',') > 0) {
					num = Long.parseLong(expression.substring(0, expression.indexOf(',')));
					runCount = Integer.parseInt(expression.substring(expression.indexOf(',')+1));
				}else {
					unit = TaskPeriodUnit.MILLISECOND;
					num = Long.parseLong(expression);
				}
			}
			
			this.periodMillis = TaskUtils.parsePeriod(unit, num);
			this.periodRunCount = runCount;
		}
	}
	
	
	
	/**
	 * 获取ID
	 * @return
	 */
	public String getId() {
		return this.id;
	}
	
	
	/**
	 * 获取当前任务状态
	 * @return
	 */
	public TaskStatus getStatus() {
		return this.status;
	}
	
	
	
	/**
	 * 设置任务状态
	 * @param status
	 */
	public void setStatus(TaskStatus status) {
		this.status = status;
	}
	
	
	
	
	/**
	 * 获取任务类型(不可为空)
	 * @return
	 */
	public TaskType getTaskType() {
		return this.job.getTaskType();
	}
	
	
	/**
	 * 获取任务
	 * @return
	 */
	public TaskJob getJob() {
		return this.job;
	}



	/**
	 * 获取任务名细
	 * @return
	 */
	public JobDetail getDetail() {
		return detail;
	}



	/**
	 * 设置任务名称
	 * @param detail
	 */
	public void setDetail(JobDetail detail) {
		this.detail = detail;
	}



	/**
	 * 获取周期任务间隔时间
	 * @return
	 */
	public long getPeriodMillis() {
		return periodMillis;
	}


	
	/**
	 * 获取周期任务执行次数
	 * @return
	 */
	public int getPeriodRunCount() {
		return periodRunCount;
	}
	
	
	
	
	
	
	
}
