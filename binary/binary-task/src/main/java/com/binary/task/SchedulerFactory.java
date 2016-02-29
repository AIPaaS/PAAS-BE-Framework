package com.binary.task;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import com.binary.core.io.Resource;
import com.binary.core.io.ResourceResolver;

public class SchedulerFactory {
	
	
	
	private static Properties getDefaultProperties() {
		Properties pro = new Properties();
		pro.put("org.quartz.scheduler.instanceName", "DefaultQuartzScheduler");
		pro.put("org.quartz.scheduler.rmi.export", "false");
		pro.put("org.quartz.scheduler.rmi.proxy", "false");
		pro.put("org.quartz.scheduler.wrapJobExecutionInUserTransaction", "false");
		pro.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
		pro.put("org.quartz.threadPool.threadCount", "10");
		pro.put("org.quartz.threadPool.threadPriority", "5");
		pro.put("org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread", "true");
		pro.put("org.quartz.jobStore.misfireThreshold", "60000");
		pro.put("org.quartz.jobStore.class", "org.quartz.simpl.RAMJobStore");
		return pro;
	}
	
	
	
	private static Properties getQuartzProperties() throws IOException {
		Resource rs = ResourceResolver.getResource("classpath:quartz.properties");
		if(rs==null || !rs.exists()) rs = ResourceResolver.getResource("classpath:org/quartz/quartz.properties");
		
		Properties pro = null;
		
		if(rs!=null && rs.exists()) {
			pro = new Properties();
			InputStream is = rs.getInputStream();
			pro.load(is);
			is.close();
		}else {
			pro = getDefaultProperties();
		}
		
		return pro;
	}
	
	
	
	
	
	/**
	 * 创建缺省Scheduler实例
	 * @param log
	 * @return
	 */
	public static Scheduler getScheduler() throws IOException,SchedulerException {
		return getScheduler(null);
	}
	
	
	
	
	/**
	 * 创建Scheduler实例
	 * @param schedulerName: 指定scheduler名称
	 * @return
	 */
	public static Scheduler getScheduler(String schedulerName) throws IOException,SchedulerException {
		Properties pro = getQuartzProperties();
		
		if(schedulerName!=null && (schedulerName=schedulerName.trim()).length()>0) {
			pro.setProperty("org.quartz.scheduler.instanceName", schedulerName);
		}else {
			String name = pro.getProperty("org.quartz.scheduler.instanceName");
			if(name==null || name.length()==0) {
				pro.put("org.quartz.scheduler.instanceName", "DefaultQuartzScheduler");
			}
		}
		
		StdSchedulerFactory fact = new StdSchedulerFactory(pro);
		
		return fact.getScheduler();
	}
	
	
	
	
	
	
}
