package com.binary.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;

import com.binary.core.util.BinaryUtils;
import com.binary.task.exception.TaskException;
import com.binary.task.support.TaskJobEntity;
import com.binary.task.support.TaskJobIterator;
import com.binary.task.support.TaskRunner;

public class TaskManager {

	
	/** JOB-ID前缀 **/
	public static final String Prefix_Job = "JOB|";
	
	
	/** TRIGGER-ID前缀 **/
	public static final String Prefix_Trigger = "TRIGGER|";
	
	
	/** 同步锁 **/
	private final Object syncobj = new Object();
	
	
	/** key=TaskJob, value=TaskId **/
	private final Map<TaskJob, String> taskids = new Hashtable<TaskJob, String>();
	
	/** key=TaskId, value=TaskJobEntity **/
	private final Map<String, TaskJobEntity> taskstore = new Hashtable<String, TaskJobEntity>();
	
	
	/** 任务调度器名称 **/
	private String schedulerName;
	
	/** 任务调度器 **/
	private Scheduler scheduler;
	
	
	/** 任务调度器日志 **/
	private TaskLog log;
	
	
	/** 所有TaskManager存储 **/
	private static final List<TaskManager> mgrstore = new ArrayList<TaskManager>();
	
	
	
	public TaskManager() {
		this(null, null, true);
	}
	public TaskManager(TaskLog log) {
		this(log, null, true);
	}
	public TaskManager(TaskLog log, List<TaskJob> jobs) {
		this(log, jobs, true);
	}
	public TaskManager(TaskLog log, List<TaskJob> jobs, boolean startScheduler) {
		this(log, jobs, startScheduler, null);
	}
	public TaskManager(TaskLog log, List<TaskJob> jobs, boolean startScheduler, String schedulerName) {
		mgrstore.add(this);
		
		this.setTaskLog(log);
		this.setSchedulerName(schedulerName);
		
		if(jobs != null) this.pushTasks(jobs.toArray(new TaskJob[0]));
		if(startScheduler) startScheduler();
	}
	
	
	
	
	/**
	 * 跟据jobId查询所属的TaskManager
	 * @param jobId
	 * @return
	 */
	public static TaskManager findTaskManager(String jobId) {
		for(int i=0; i<mgrstore.size(); i++) {
			TaskManager mgr = mgrstore.get(i);
			if(mgr.taskstore.containsKey(jobId)) {
				return mgr;
			}
		}
		return null;
	}
	
	
	
	
	/**
	 * 获取任务日志对象
	 * @return
	 */
	public TaskLog getTaskLog() {
		return this.log;
	}
	
	/**
	 * 设置任务日志对象
	 * @param log
	 */
	public void setTaskLog(TaskLog log) {
		this.log = log;
	}
	
	
	
	/**
	 * 打印日志
	 * @param job
	 * @param type
	 * @param msg
	 */
	public void printInfo(TaskJob job, TaskLogType type, String msg, boolean success) {
		if(this.log != null) {
			this.log.printInfo(this, job, type, msg, success);
		}
	}
	
	/**
	 * 打印异常日志
	 * @param job
	 * @param type
	 * @param msg
	 * @param e
	 */
	public void printError(TaskJob job, TaskLogType type, String msg, Exception e) {
		if(this.log != null) {
			this.log.printError(this, job, type, msg, e);
		}
	}
	
	
	
	
	/**
	 * 获取任务调度器名称
	 * @return
	 */
	public String getSchedulerName() {
		return schedulerName;
	}
	
	/**
	 * 设置任务调度器名称
	 * @param schedulerName
	 */
	public void setSchedulerName(String schedulerName) {
		synchronized (syncobj) {
			if(this.scheduler != null) {
				throw new TaskException(" must stop scheduler to set scheduler's name! ");
			}
			this.schedulerName = schedulerName;
			if(this.schedulerName!=null) this.schedulerName = this.schedulerName.trim();
		}
	}
	
	
	/**
	 * 起动Scheduler
	 */
	public boolean startScheduler()  {
		synchronized(syncobj) {
			printInfo(null, TaskLogType.SCHEDULER_START, "begin start scheduler......", true);
			
			boolean success = false;
			try {
				if(this.scheduler == null) {
					String schedulerId = BinaryUtils.getUUID();
					this.scheduler = SchedulerFactory.getScheduler(this.schedulerName);
					this.scheduler.start();
					this.scheduler.addSchedulerListener(new TaskJobListener());
					
					printInfo(null, TaskLogType.SCHEDULER_START, "start scheduler["+schedulerId+"] successful!", true);
					success = true;
				}else {
					printInfo(null, TaskLogType.SCHEDULER_START, "start scheduler failure! the scheduler["+this.scheduler.getSchedulerName()+"] is started!", false);
				}
			}catch(Exception e) {
				String msg = "start scheduler failure!";
				printError(null, TaskLogType.SCHEDULER_START, msg, e);
				throw new TaskException(msg, e);
			}
			
			if(taskstore.size() > 0) {
				Iterator<TaskJobEntity> itor = taskstore.values().iterator();
				while(itor.hasNext()) {
					TaskJobEntity entity = itor.next();
					startTask(entity);
				}
			}
			
			return success;
		}
	}
	
	
	
	
	/**
	 * 关闭任务调度器
	 */
	public boolean stopScheduler() {
		synchronized (syncobj) {
			boolean success = false;
			
			try {
				if(this.scheduler != null) {
					printInfo(null, TaskLogType.SCHEDULER_STOP, "begin stop scheduler["+this.scheduler.getSchedulerName()+"]......", true);
					scheduler.shutdown();
					
					printInfo(null, TaskLogType.SCHEDULER_STOP, "stop scheduler["+this.scheduler.getSchedulerName()+"] successful!", true);
					success = true;
				}else {
					printInfo(null, TaskLogType.SCHEDULER_STOP, "stop scheduler["+this.scheduler.getSchedulerName()+"] failure! the scheduler is not started!", false);
				}
			}catch(SchedulerException e) {
				String msg = "stop scheduler failure!";
				printError(null, TaskLogType.SCHEDULER_STOP, msg, e);
				throw new TaskException(msg, e);
			}
			this.scheduler = null;
			
			return success;
		}
	}
	
	
	/**
	 * 判断调度器是否已经启动
	 * @return
	 */
	public boolean isSchedulerStarted() {
		return this.scheduler != null;
	}
	
	
	/**
	 * 获取任务ID
	 * @param job
	 * @return
	 */
	public String getTaskId(TaskJob job) {
		return taskids.get(job);
	}
	
	
	
	
	/**
	 * 批量添加任务
	 * @param jobs
	 * @return
	 */
	public String[] pushTasks(TaskJob... jobs) {
		if(jobs == null) return null;
		String[] taskids = new String[jobs.length];
		
		printInfo(null, TaskLogType.TASK_PUSH, "start push task batch......", true);
		
		synchronized(syncobj) {
			for(int i=0; i<jobs.length; i++) {
				TaskJob job = jobs[i];
				
				String taskid = getTaskId(job);
				if(taskid == null) {
					TaskJobEntity entity = new TaskJobEntity(job);
					taskid = entity.getId();
					taskstore.put(taskid, entity);
					
					printInfo(job, TaskLogType.TASK_PUSH, "push task["+job.getName()+"] successful!", true);
					
					if(isSchedulerStarted()) {
						startTask(entity);
					}
				}else {
					printInfo(job, TaskLogType.TASK_PUSH, "push task["+job.getName()+"] failure! task is exists!", false);
				}
				taskids[i] = taskid;
			}
			return taskids;
		}
	}
	
	
	
	/**
	 * 添加一个任务
	 * @param job
	 * @return 任务ID
	 */
	public String pushTask(TaskJob job) {
		synchronized(syncobj) {
			String taskid = getTaskId(job);
			if(taskid == null) {
				TaskJobEntity entity = new TaskJobEntity(job);
				taskid = entity.getId();
				taskstore.put(taskid, entity);
				printInfo(job, TaskLogType.TASK_PUSH, "push task["+job.getName()+"] successful!", true);
				
				if(isSchedulerStarted()) {
					startTask(entity);
				}
			}else {
				printInfo(job, TaskLogType.TASK_PUSH, "push task["+job.getName()+"] failure! task is exists!", false);
			}
			return taskid;
		}
	}
	
	
	/**
	 * 卸载任务
	 * @param jobId
	 * @return
	 */
	public boolean popTask(String jobId) {
		synchronized(syncobj) {
			printInfo(null, TaskLogType.TASK_POP, "start pop task["+jobId+"]......", true);
			
			TaskJobEntity entity = taskstore.get(jobId);
			if(entity == null) {
				printInfo(null, TaskLogType.TASK_POP, "pop task["+jobId+"] failure! is not found task["+jobId+"]!", false);
				return false;
			}
			
			String id = entity.getId();
			
			//TaskStatus status = entity.getStatus();
			//if(status == TaskStatus.RUNNING) return false;
			
			TaskJob job = entity.getJob();
			try {
				scheduler.unscheduleJob(Prefix_Trigger+id, Scheduler.DEFAULT_GROUP);
				printInfo(job, TaskLogType.TASK_POP, "pop task["+job.getName()+"] successful!", true);
			}catch(SchedulerException e) {
				String msg = "pop task["+job.getName()+"] failure!";
				printError(job, TaskLogType.TASK_POP, msg, e);
				throw new TaskException(msg, e);
			}
			
			taskstore.remove(id);
			taskids.remove(entity.getJob());
			
			return true;
		}
	}
	
	
	
	/**
	 * 获取任务运行实体
	 * @param jobId: 任务ID
	 * @return
	 */
	TaskJobEntity getTask(String jobId) {
		return taskstore.get(jobId);
	}
	
	
	private void updateTaskStatus(TaskJobEntity entity, TaskStatus status) {
		entity.setStatus(status);
		TaskJob job = entity.getJob();
		try {
			job.updateTaskStatus(status);
		}catch(Exception e) {
			printError(entity.getJob(), TaskLogType.TASK_START, "update task["+job.getName()+"] status failure!", e);
		}
		
	}
	
	
	/**
	 * 启动任务
	 * @param jobId
	 * @return
	 */
	public boolean startTask(String jobId) {
		synchronized(syncobj) {
			TaskJobEntity entity = taskstore.get(jobId);
			if(entity == null) {
				printInfo(null, TaskLogType.TASK_START, "start task["+jobId+"] failure! is not found task["+jobId+"]!", false);
				return false;
			}
			return startTask(entity);
		}
	}
	
	
	
	private boolean startTask(TaskJobEntity entity) {
		TaskJob job = entity.getJob();
		printInfo(job, TaskLogType.TASK_START, "beign start task["+job.getName()+"]......", true);
		
		TaskStatus status = entity.getStatus();
		if(status != TaskStatus.STOPPED) {
			printInfo(job, TaskLogType.TASK_START, "start task["+job.getName()+"] failure! the task is not stopped!", false);
			return false;
		}
		
		try {
			String id = entity.getId();
			TaskType type = entity.getTaskType();
			
			JobDetail detail = scheduler.getJobDetail(Prefix_Job+id, Scheduler.DEFAULT_GROUP);
			if(detail != null) {
				printInfo(job, TaskLogType.TASK_START, "start task["+job.getName()+"] failure! the task is started!", false);
				return false;	//任务已起动
			}
			
			Date startTime = job.getStartTime();
			Date endTime = job.getEndTime();
			Date lastRunTime = job.getLastRunTime();
			Date currTime = new Date();
			long currmillis = currTime.getTime();
			
			if(startTime == null) startTime = currTime;
			
			if(endTime!=null && currmillis>endTime.getTime()) return false;
			
			String jobname = Prefix_Job + id;
			String triggername = Prefix_Trigger + id;
			detail = new JobDetail(jobname, Scheduler.DEFAULT_GROUP, TaskRunner.class);
			
			long startTimeMillis = lastRunTime!=null ? lastRunTime.getTime() : startTime.getTime();
			Trigger trigger = null;
			String expression = job.getExpression();
			
			switch(type) {
				case Period: {
						long period = entity.getPeriodMillis();
						int runCount = entity.getPeriodRunCount();
						
						while(startTimeMillis < currmillis) startTimeMillis += period;
						
						int repeatCount = 0;
						if(runCount <= 0) {
							repeatCount = SimpleTrigger.REPEAT_INDEFINITELY;
						}else {
							Integer runnedCount = job.getRunnedCount();
							if(runnedCount==null || runnedCount.intValue()<0) runnedCount = 0;
							repeatCount = runCount - runnedCount.intValue() - 1;
							
							if(repeatCount < 0) {
								printInfo(job, TaskLogType.TASK_START, "start task["+job.getName()+"] failure! the task's runned-count:'"+runCount+"' is over!", false);
								return false;
							}
						}
						
						Date time = new Date(startTimeMillis);
						trigger = new SimpleTrigger(triggername, Scheduler.DEFAULT_GROUP, time, endTime, repeatCount, period);
						job.setNextRunTime(time);
						break;
					}
				case Calendar:
					if(startTimeMillis < currmillis) startTimeMillis = currmillis;
					trigger = new CronTrigger(triggername, Scheduler.DEFAULT_GROUP, jobname, Scheduler.DEFAULT_GROUP, new Date(startTimeMillis), endTime, expression);
					break;
			}
			
			scheduler.scheduleJob(detail, trigger);
			
			entity.setDetail(detail);
			updateTaskStatus(entity, TaskStatus.WAIT);
			
			printInfo(job, TaskLogType.TASK_START, "start task["+job.getName()+"] successful!", true);
		}catch(Exception e) {
			String msg = "start task["+job.getName()+"] failure!";
			printError(null, TaskLogType.TASK_START, msg, e);
			throw BinaryUtils.transException(e, TaskException.class, msg);
		}
		return true;
	}
	
	
	
	/**
	 * 停止任务
	 * @param jobId
	 * @return
	 */
	public boolean stopTask(String jobId) {
		synchronized(syncobj) {
			printInfo(null, TaskLogType.TASK_STOP, "start stop task["+jobId+"]......", true);
			TaskJobEntity entity = taskstore.get(jobId);
			if(entity == null) {
				printInfo(null, TaskLogType.TASK_STOP, "stop task["+jobId+"] failure! is not found task["+jobId+"]!", false);
				return false;
			}
			TaskJob job = entity.getJob();
			TaskStatus status = entity.getStatus();
			
			try {
				JobDetail detail = scheduler.getJobDetail(Prefix_Job+jobId, Scheduler.DEFAULT_GROUP);
				if(detail == null) {
					printInfo(job, TaskLogType.TASK_STOP, "stop task["+job.getName()+"] failure! is not found JobDetail["+jobId+"]!", false);
					return false;
				}
				if(status != TaskStatus.WAIT) {
					printInfo(job, TaskLogType.TASK_STOP, "stop task["+job.getName()+"] failure! the task's status is not wait!", false);
					return false;
				}
				
				scheduler.unscheduleJob(Prefix_Trigger+jobId, Scheduler.DEFAULT_GROUP);
				updateTaskStatus(entity, TaskStatus.STOPPED);
				
				printInfo(job, TaskLogType.TASK_STOP, "stop task["+job.getName()+"] successful!", true);
			}catch(Exception e) {
				String msg = "stop task["+job.getName()+"] failure!";
				printError(job, TaskLogType.TASK_STOP, msg, e);
				throw BinaryUtils.transException(e, TaskException.class, msg);
			}
			return true;
		}
	}
	
	
	
	/**
	 * 立即运行任务
	 * @param jobId: 任务ID
	 * @return
	 */
	public boolean runTask(String jobId) {
		printInfo(null, TaskLogType.TASK_RUN, "start run task["+jobId+"]......", true);
		TaskJobEntity entity = taskstore.get(jobId);
		if(entity == null) {
			printInfo(null, TaskLogType.TASK_RUN, "run task["+jobId+"] failure! is not found task["+jobId+"]!", false);
			return false;
		}
		
		TaskJob job = entity.getJob();
		TaskStatus status = entity.getStatus();
		if(status != TaskStatus.WAIT) {
			printInfo(job, TaskLogType.TASK_RUN, "run task["+job.getName()+"] failure! the task's status is not wait!", false);
			return false;
		}
		
		updateTaskStatus(entity, TaskStatus.RUNNING);
		
		Date currtime = new Date();
		
		boolean success = false;
		try {
			int retrycount = 0;
			if(job.getRetryCount() != null) {
				retrycount = job.getRetryCount();
				if(retrycount < 0) retrycount = 0;
			}
			
			for(int i=0; i<=retrycount&&!success; i++) {
				if(i > 0) {
					printInfo(job, TaskLogType.TASK_RUN, "run task["+job.getName()+"] retry "+i+"!", true);
				}
				
				try {
					job.run(this, jobId);
					printInfo(job, TaskLogType.TASK_RUN, "run task["+job.getName()+"] successful!", true);
					
					success = true;
				}catch(Exception e) {
					String msg = "run task["+job.getName()+"] failure!";
					printError(job, TaskLogType.TASK_RUN, msg, e);
				}
			}
			
			try {
				job.saveLastRunTime(currtime);
				printInfo(job, TaskLogType.TASK_RUN, "save task["+job.getName()+"] last run time successful!", true);
			}catch(Exception e) {
				printError(job, TaskLogType.TASK_RUN, "save task["+job.getName()+"] last run time failure!", e);
			}
			
			try {
				job.addRunnedCount();
				printInfo(job, TaskLogType.TASK_RUN, "add task["+job.getName()+"] runned count successful!", true);
			}catch(Exception e) {
				printError(job, TaskLogType.TASK_RUN, "add task["+job.getName()+"] runned count failure!", e);
			}
						
			TaskType taskType = job.getTaskType();
			if(taskType == TaskType.Period) {
				long period = entity.getPeriodMillis();
				try {
					job.setNextRunTime(new Date(currtime.getTime()+period));
				}catch(Exception e) {
					printError(job, TaskLogType.TASK_RUN, "set task["+job.getName()+"] next run time failure!", e);
				}
			}
		}catch(Exception e) {
			String msg = "run task["+job.getName()+"] failure!";
			printError(job, TaskLogType.TASK_RUN, msg, e);
			throw BinaryUtils.transException(e, TaskException.class, msg);
		}finally {
			updateTaskStatus(entity, TaskStatus.WAIT);
		}
		return success;
	}
	

	
	
	/**
	 * 注销\销毁\终止任务
	 * @param jobId
	 */
	public boolean destroyTask(String jobId) {
		printInfo(null, TaskLogType.TASK_DESTROY, "start destroy task["+jobId+"]......", true);
		TaskJobEntity entity = taskstore.get(jobId);
		if(entity == null) {
			printInfo(null, TaskLogType.TASK_DESTROY, "destroy task["+jobId+"] failure! is not found task["+jobId+"]!", false);
			return false;
		}
		
		TaskJob job = entity.getJob();
		
		boolean success = false;
		
		synchronized (syncobj) {
			TaskStatus status = entity.getStatus();
			if(status!=TaskStatus.WAIT && status!=TaskStatus.STOPPED) {
				printInfo(job, TaskLogType.TASK_DESTROY, "destroy task["+job.getName()+"] failure! the task's status is not wait or stopped!", false);
				return false;
			}
			
			try {
				JobDetail detail = scheduler.getJobDetail(Prefix_Job+jobId, Scheduler.DEFAULT_GROUP);
				if(detail == null) {
					printInfo(job, TaskLogType.TASK_DESTROY, "destroy task["+job.getName()+"] failure! is not found JobDetail["+jobId+"]!", false);
					return false;
				}
				
				scheduler.unscheduleJob(Prefix_Trigger+jobId, Scheduler.DEFAULT_GROUP);
				
				printInfo(job, TaskLogType.TASK_DESTROY, "destroy task["+job.getName()+"] successful!", true);
				
				updateTaskStatus(entity, TaskStatus.DESTROYED);
				
				success = true;
			}catch(Exception e) {
				String msg = "destroy task["+job.getName()+"] failure!";
				printError(job, TaskLogType.TASK_DESTROY, msg, e);
				throw BinaryUtils.transException(e, TaskException.class, msg);
			}
		}
		
		return success;
	}
	
	
	
	/**
	 * 结束任务
	 * @param jobId
	 */
	void finalizedTask(String jobId) {
		printInfo(null, TaskLogType.TASK_FINISH, "start finish task["+jobId+"]......", true);
		TaskJobEntity entity = taskstore.get(jobId);
		if(entity == null) {
			printInfo(null, TaskLogType.TASK_FINISH, "finish task["+jobId+"] failure! is not found task["+jobId+"]!", false);
			return ;
		}
		
		TaskJob job = entity.getJob();
		
		synchronized(syncobj) {
			try {
				updateTaskStatus(entity, TaskStatus.FINALIZED);
				printInfo(job, TaskLogType.TASK_FINISH, "finish task["+job.getName()+"] successful!", true);
			}catch(Exception e) {
				String msg = "finish task["+job.getName()+"] failure!";
				printError(job, TaskLogType.TASK_FINISH, msg, e);
				throw BinaryUtils.transException(e, TaskException.class, msg);
			}
		}
	}
	
	
	
	
	/**
	 * 跟据JobID获取TaskJob对象
	 * @param jobId
	 * @return
	 */
	public TaskJob getTaskJob(String jobId) {
		BinaryUtils.checkEmpty(jobId, "jobId");
		TaskJobEntity entity = getTask(jobId);
		if(entity == null) throw new TaskException(" is not found task-job '"+jobId+"'! ");
		return entity.getJob();
	}
	
	
	
	
	/**
	 * 获取所有任务
	 * @return
	 */
	public Iterator<TaskJob> getTaskJobIterator() {
		return new TaskJobIterator(taskstore.values().iterator());
	}
	
	
	
}






