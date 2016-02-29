package com.binary.task.support;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.binary.task.TaskManager;
import com.binary.task.exception.TaskException;

public class TaskRunner implements Job {
	
	
	
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String jobname = context.getJobDetail().getName();
		String jobId = jobname.substring(jobname.indexOf("|")+1);
		TaskManager mgr = TaskManager.findTaskManager(jobId);
		if(mgr == null) {
			throw new TaskException(" is not found task-manager by job-id '"+jobId+"'!");
		}
		mgr.runTask(jobId);
	}
	
	
	
	
}
