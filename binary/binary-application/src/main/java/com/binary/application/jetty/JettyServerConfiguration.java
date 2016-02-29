package com.binary.application.jetty;

import java.io.Serializable;

import com.binary.core.io.Configuration;


public class JettyServerConfiguration extends Configuration implements Serializable {
	private static final long serialVersionUID = 1L;


	//-- QueuedThreadPool start--------------------------------------------------------------------------------------------------------------------	
	
	/** 否要将线程设置为守护线程 **/
	private Boolean threadDaemon;
	
	/** 表明线程在多少以下,线程池将被视为占用系统低资源 **/
	private Integer threadLowThreads = 20;
	
	
	/** 最大空闲时间，这里应该可以理解为线程池中有空闲的线程的最大的持续时间  **/
	private Integer threadMaxIdleTimeMs;
	
	/** 最大停止时间 **/
	private Integer threadMaxStopTimeMs;
	
	
	
	/** 最大线程数量 **/
	private Integer threadMaxThreads;
	
	/** 最小线程数量 **/
	private Integer threadMinThreads;
	
	
	/** 当任务队列超过当前阀值时创建新的线程 **/
	private Integer threadSpawnOrShrinkAt = 2;
	
	/** 线程优先级 **/
	private Integer threadThreadsPriority;
	
	//-- end -------------------------------------------------------------------------------------------------------------------------
	
	
	
	
	//-- SelectChannelConnector start--------------------------------------------------------------------------------------------------------------------
	
	/** 指定服务器主机连接IP **/
	private String connectorHost;
	
	/** 指定服务器主机开放端口 **/
	private Integer connectorPort;
	
	/** 指定请求接收线程数量 **/
	private Integer connectorAcceptors;
	
	/** 指定请求接收队列最大长度 **/
	private Integer connectorAcceptQueueSize;
	
	/** 最大空闲连接时间 **/
	private Integer connectorMaxIdleTime;
	
	/** 最大连接数，连接数量达到该数值时，系统会认为服务器资源已被耗尽 **/
	private Long connectorLowResourcesConnections;
	
	/** 当资源被耗尽时，连接最大等待时间，时间单位是毫秒  **/
	private Integer connectorLowResourcesMaxIdleTime;
	
	/** 受保护端口 **/
	private Integer connectorConfidentialPort;
	
	/**  是否开启统计功能 **/
	private Boolean connectorStatsOn;
	
	
	//-- end -------------------------------------------------------------------------------------------------------------------------
	
	
	
	

	//-- WebAppContext start--------------------------------------------------------------------------------------------------------------------
	
	/** 以目录形式指定应用入口, 格式：ContextPath=root-path, 指定多个以分号分隔 **/
	private String webAppRoots;
	
	/** 以war形式指定应用入口, 格式：ContextPath=war-path, 指定多个以分号分隔 **/
	private String webAppWars;
	
	/** 指定缺省web.xml位置, 默认为binary/jetty/web-default.xml **/
	private String webAppDefaultDescriptor;
	
	//-- end -------------------------------------------------------------------------------------------------------------------------
	
	
	
	
	
	//-- Server start--------------------------------------------------------------------------------------------------------------------
	
	/** 软关机时最大等待时间 **/
	private Integer serverGracefulShutdown;
	
	/** 是否发送日期头 **/
	private Boolean serverSendDateHeader;
	
	/** 是否发送服务版本 **/
	private Boolean serverSendServerVersion;
	
	/** 服务器关闭时是否停止所有服务 **/
	private Boolean serverStopAtShutdown;
	
	//-- end -------------------------------------------------------------------------------------------------------------------------
	
	
	
	public JettyServerConfiguration() {
		super("jetty");
	}
	
	
	

	public Boolean getThreadDaemon() {
		return threadDaemon;
	}

	public void setThreadDaemon(Boolean threadDaemon) {
		this.threadDaemon = threadDaemon;
	}

	public Integer getThreadLowThreads() {
		return threadLowThreads;
	}

	public void setThreadLowThreads(Integer threadLowThreads) {
		this.threadLowThreads = threadLowThreads;
	}

	public Integer getThreadMaxIdleTimeMs() {
		return threadMaxIdleTimeMs;
	}

	public void setThreadMaxIdleTimeMs(Integer threadMaxIdleTimeMs) {
		this.threadMaxIdleTimeMs = threadMaxIdleTimeMs;
	}

	public Integer getThreadMaxStopTimeMs() {
		return threadMaxStopTimeMs;
	}

	public void setThreadMaxStopTimeMs(Integer threadMaxStopTimeMs) {
		this.threadMaxStopTimeMs = threadMaxStopTimeMs;
	}

	public Integer getThreadMaxThreads() {
		return threadMaxThreads;
	}

	public void setThreadMaxThreads(Integer threadMaxThreads) {
		this.threadMaxThreads = threadMaxThreads;
	}

	public Integer getThreadMinThreads() {
		return threadMinThreads;
	}

	public void setThreadMinThreads(Integer threadMinThreads) {
		this.threadMinThreads = threadMinThreads;
	}

	public Integer getThreadSpawnOrShrinkAt() {
		return threadSpawnOrShrinkAt;
	}

	public void setThreadSpawnOrShrinkAt(Integer threadSpawnOrShrinkAt) {
		this.threadSpawnOrShrinkAt = threadSpawnOrShrinkAt;
	}

	public Integer getThreadThreadsPriority() {
		return threadThreadsPriority;
	}

	public void setThreadThreadsPriority(Integer threadThreadsPriority) {
		this.threadThreadsPriority = threadThreadsPriority;
	}

	public String getConnectorHost() {
		return connectorHost;
	}

	public void setConnectorHost(String connectorHost) {
		this.connectorHost = connectorHost;
	}

	public Integer getConnectorPort() {
		return connectorPort;
	}

	public void setConnectorPort(Integer connectorPort) {
		this.connectorPort = connectorPort;
	}

	public Integer getConnectorAcceptors() {
		return connectorAcceptors;
	}

	public void setConnectorAcceptors(Integer connectorAcceptors) {
		this.connectorAcceptors = connectorAcceptors;
	}

	public Integer getConnectorAcceptQueueSize() {
		return connectorAcceptQueueSize;
	}

	public void setConnectorAcceptQueueSize(Integer connectorAcceptQueueSize) {
		this.connectorAcceptQueueSize = connectorAcceptQueueSize;
	}

	public Integer getConnectorMaxIdleTime() {
		return connectorMaxIdleTime;
	}

	public void setConnectorMaxIdleTime(Integer connectorMaxIdleTime) {
		this.connectorMaxIdleTime = connectorMaxIdleTime;
	}

	public Long getConnectorLowResourcesConnections() {
		return connectorLowResourcesConnections;
	}

	public void setConnectorLowResourcesConnections(
			Long connectorLowResourcesConnections) {
		this.connectorLowResourcesConnections = connectorLowResourcesConnections;
	}

	public Integer getConnectorLowResourcesMaxIdleTime() {
		return connectorLowResourcesMaxIdleTime;
	}

	public void setConnectorLowResourcesMaxIdleTime(
			Integer connectorLowResourcesMaxIdleTime) {
		this.connectorLowResourcesMaxIdleTime = connectorLowResourcesMaxIdleTime;
	}

	public Integer getConnectorConfidentialPort() {
		return connectorConfidentialPort;
	}

	public void setConnectorConfidentialPort(Integer connectorConfidentialPort) {
		this.connectorConfidentialPort = connectorConfidentialPort;
	}

	public Boolean getConnectorStatsOn() {
		return connectorStatsOn;
	}

	public void setConnectorStatsOn(Boolean connectorStatsOn) {
		this.connectorStatsOn = connectorStatsOn;
	}

	

	public String getWebAppWars() {
		return webAppWars;
	}

	public void setWebAppWars(String webAppWars) {
		this.webAppWars = webAppWars;
	}


	public String getWebAppRoots() {
		return webAppRoots;
	}

	public void setWebAppRoots(String webAppRoots) {
		this.webAppRoots = webAppRoots;
	}

	public Integer getServerGracefulShutdown() {
		return serverGracefulShutdown;
	}

	public void setServerGracefulShutdown(Integer serverGracefulShutdown) {
		this.serverGracefulShutdown = serverGracefulShutdown;
	}

	public Boolean getServerSendDateHeader() {
		return serverSendDateHeader;
	}

	public void setServerSendDateHeader(Boolean serverSendDateHeader) {
		this.serverSendDateHeader = serverSendDateHeader;
	}

	public Boolean getServerSendServerVersion() {
		return serverSendServerVersion;
	}

	public void setServerSendServerVersion(Boolean serverSendServerVersion) {
		this.serverSendServerVersion = serverSendServerVersion;
	}

	public Boolean getServerStopAtShutdown() {
		return serverStopAtShutdown;
	}

	public void setServerStopAtShutdown(Boolean serverStopAtShutdown) {
		this.serverStopAtShutdown = serverStopAtShutdown;
	}




	public String getWebAppDefaultDescriptor() {
		return webAppDefaultDescriptor;
	}




	public void setWebAppDefaultDescriptor(String webAppDefaultDescriptor) {
		this.webAppDefaultDescriptor = webAppDefaultDescriptor;
	}
	
	
	

	
	
	
	

}
