package com.binary.core.thread;



import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import com.binary.core.exception.ThreadPoolException;



public class BinaryThreadPool {
	
	
	private final Object syncobj = new Object();
	
	
	/** 线程池大小, 缺省为32 **/
	private int poolSize = 32;
	
	/** 线程创建工厂 **/
	private ThreadFactory threadFactory;
	
	/** 线程池 **/
	private ExecutorService threadPool;
	
	
	
	public BinaryThreadPool() {
	}
	public BinaryThreadPool(int poolSize) {
		this.setPoolSize(poolSize);
	}
	public BinaryThreadPool(ThreadFactory threadFactory) {
		this.threadFactory = threadFactory;
	}
	public BinaryThreadPool(int poolSize, ThreadFactory threadFactory) {
		this.setPoolSize(poolSize);
		this.threadFactory = threadFactory;
	}
	
	
	
	public void setPoolSize(int poolSize) {
		synchronized (syncobj) {
			if(poolSize <= 0) throw new ThreadPoolException(" the thread-poolsize must be greater than zero! ");
			this.poolSize = poolSize;
		}
	}
	
	
	public void setThreadFactory(ThreadFactory threadFactory) {
		synchronized (syncobj) {
			this.threadFactory = threadFactory;
		}
	}
	
	
	public int getPoolSize() {
		return this.poolSize;
	}
	
	
	public ThreadFactory getThreadFactory() {
		return this.threadFactory;
	}
	
	
	
	
	public Future<?> pushTask(Runnable runnable) {
		if(threadPool == null) createThreadPool();
		return threadPool.submit(runnable);
	}
	
	
	
	
	public void shutdown() {
		synchronized (syncobj) {
			if(threadPool != null) {
				threadPool.shutdown();
				threadPool = null;
			}
		}
	}
	
	
	
	
	private void createThreadPool() {
		synchronized (syncobj) {
			if(threadPool == null) {
				if(this.threadFactory == null) {
					this.threadPool = Executors.newFixedThreadPool(this.poolSize);
				}else {
					this.threadPool = Executors.newFixedThreadPool(this.poolSize, this.threadFactory);
				}
			}
		}
	}
	
	
	
}
