package com.binary.sso.server.token.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.binary.sso.server.exception.SsoTokenException;
import com.binary.sso.server.token.TokenManager;

public class TokenMonitor {
	private static Logger log = LoggerFactory.getLogger(TokenMonitor.class);

	
	/** 扫描token间隔时间, 单位: 分钟 **/
	private int scanIntervalTime;
	
	
	private TokenManager tokenMgr;
	
	private final Object syncobj = new Object();
	
	/** 线程是否起动 **/
	private boolean started = false;
	
	/** 一次线程处理是否完成 **/
	private boolean over = false;
	
	/** 外部是否强行结束当前线程 **/
	private boolean stopped = false;
	
	
	private final Runnable runnable = new Runnable(){
		public void run() {
			runEntity();
		}
	};
	
	
	
	public TokenMonitor(int scanIntervalTime, TokenManager tokenMgr) {
		setScanIntervalTime(scanIntervalTime);
		this.tokenMgr = tokenMgr;
	}
	
	
	
	public void setScanIntervalTime(int scanIntervalTime) {
		if(scanIntervalTime <= 0) throw new SsoTokenException(" the scanIntervalTime is wrong '"+scanIntervalTime+"'! ");
		this.scanIntervalTime = scanIntervalTime;
	}
	
	
	
	private void sleep(int count) {
		try {
			Thread.sleep(this.scanIntervalTime*1000*60);
		}catch(Exception e) {
			if(count >= 10) throw new SsoTokenException(e);
			sleep(count + 1);
		}
	}
	
	

	/**
	 * 起动线程, 只有当线程未启动或已结束时才能被起动, 
	 */
	public boolean start() {
		synchronized (syncobj) {
			if(!this.started || this.over) {
				Thread t = new Thread(runnable);
				t.start();
				this.started = true;
				this.over = false;
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 停止当前线程, 供外部对强行对当前线程直接停止操作
	 */
	public void stop() {
		this.stopped = true;
	}
	
	
	
	/**
	 * 判断当前线程是否结束
	 * @return
	 */
	public boolean isStarted() {
		return this.started;
	}
	
	
	/**
	 * 判断当前线程是否被强行结束
	 * @return
	 */
	public boolean isStoped() {
		return this.stopped;
	}
	
	
	/**
	 * 判断当前线程是否结束
	 * @return
	 */
	public boolean isOver() {
		return this.over;
	}
	
	
	
	private void runEntity() {
		while(true) {
			if(stopped) break;
			try {
				tokenMgr.removeInvalidTokens();
			}catch(Throwable e) {
				log.error(" monitor the effectiveness token error! ", e);
			}
			if(stopped) break;
			sleep(0);
		}
		
		synchronized (syncobj) {
			this.over = true;
		}
	}
	
	
	
	
	
	
	
	
}
