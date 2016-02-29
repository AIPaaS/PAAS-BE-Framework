package com.binary.task;


import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;

import com.binary.task.exception.TaskException;




public class TaskJobListener implements SchedulerListener {
	
	
	
	public void jobAdded(JobDetail arg0) {
	}

	public void jobDeleted(String arg0, String arg1) {
	}

	public void jobScheduled(Trigger arg0) {
	}

	public void jobUnscheduled(String arg0, String arg1) {
	}

	public void jobsPaused(String arg0, String arg1) {
	}

	public void jobsResumed(String arg0, String arg1) {
	}

	public void schedulerError(String arg0, SchedulerException arg1) {
	}

	public void schedulerInStandbyMode() {
	}

	public void schedulerShutdown() {
	}

	public void schedulerShuttingdown() {
	}

	public void schedulerStarted() {
	}

	public void triggerFinalized(Trigger trigger) {
		String triggername = trigger.getName();
		String jobId = triggername.substring(triggername.indexOf("|")+1);
		TaskManager mgr = TaskManager.findTaskManager(jobId);
		if(mgr == null) {
			throw new TaskException(" is not found task-manager by job-id '"+jobId+"'!");
		}
		mgr.finalizedTask(jobId);
	}
	
	public void triggersPaused(String arg0, String arg1) {
	}
	
	public void triggersResumed(String arg0, String arg1) {
	}
	
	
	
}
