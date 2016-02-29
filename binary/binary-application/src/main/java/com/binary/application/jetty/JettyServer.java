package com.binary.application.jetty;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.binary.application.exception.JettyException;
import com.binary.core.io.Resource;
import com.binary.core.io.ResourceResolver;
import com.binary.core.util.BinaryUtils;

public class JettyServer {
	private static Logger log = LoggerFactory.getLogger(JettyServer.class);
	
	public static final String DEFAULT_WEB = "binary/jetty/web-default.xml";
	
	
	/** 服务器配置信息 **/
	private JettyServerConfiguration configuration;
	
	
	
	public JettyServer(String configuration) {
		JettyServerConfiguration conf = new JettyServerConfiguration();
		
		Resource rs = ResourceResolver.getResource(configuration);
		if(rs!=null && rs.exists()) {
			conf.loadResource(rs);
		}else {
			log.warn(" is not found configuration jetty.properties:'"+configuration+"', the system will run in the default configuration. ");
		}
		
		this.configuration = conf;
	}
	
	public JettyServer(JettyServerConfiguration configuration) {
		BinaryUtils.checkEmpty(configuration, "configuration");
		this.configuration = configuration;
	}
	
	
	
	private QueuedThreadPool getThreadPool() {
	    QueuedThreadPool threadPool = new QueuedThreadPool();
	    
	    if(configuration.getThreadDaemon() != null) {
	    	threadPool.setDaemon(configuration.getThreadDaemon());
	    }
	    if(configuration.getThreadLowThreads() != null) {
	    	threadPool.setLowThreads(configuration.getThreadLowThreads());
	    }
	    if(configuration.getThreadMaxIdleTimeMs() != null) {
	    	threadPool.setMaxIdleTimeMs(configuration.getThreadMaxIdleTimeMs());
	    }
	    if(configuration.getThreadMaxStopTimeMs() != null) {
	    	threadPool.setMaxStopTimeMs(configuration.getThreadMaxStopTimeMs());
	    }
	    if(configuration.getThreadMaxThreads() != null) {
	    	threadPool.setMaxThreads(configuration.getThreadMaxThreads());
	    }
	    if(configuration.getThreadMinThreads() != null) {
	    	threadPool.setMinThreads(configuration.getThreadMinThreads());
	    }
	    if(configuration.getThreadSpawnOrShrinkAt() != null) {
	    	threadPool.setSpawnOrShrinkAt(configuration.getThreadSpawnOrShrinkAt());
	    }
	    if(configuration.getThreadThreadsPriority() != null) {
	    	threadPool.setThreadsPriority(configuration.getThreadThreadsPriority());
	    }
	    
	    return threadPool;
	}
	
	
	
	@SuppressWarnings("deprecation")
	private SelectChannelConnector getConnector() {
		SelectChannelConnector connector = new SelectChannelConnector();
		
	    if(configuration.getConnectorHost() != null) {
	    	connector.setHost(configuration.getConnectorHost());
	    }
	    if(configuration.getConnectorPort() != null) {
	    	connector.setPort(configuration.getConnectorPort());
	    }
	    if(configuration.getConnectorAcceptors() != null) {
	    	connector.setAcceptors(configuration.getConnectorAcceptors());
	    }
	    if(configuration.getConnectorAcceptQueueSize() != null) {
	    	connector.setAcceptQueueSize(configuration.getConnectorAcceptQueueSize());
	    }
	    if(configuration.getConnectorMaxIdleTime() != null) {
	    	connector.setMaxIdleTime(configuration.getConnectorMaxIdleTime());
	    }
	    if(configuration.getConnectorLowResourcesConnections() != null) {
	    	connector.setLowResourcesConnections(configuration.getConnectorLowResourcesConnections());
	    }
	    
	    if(configuration.getConnectorLowResourcesMaxIdleTime() != null) {
	    	connector.setLowResourcesMaxIdleTime(configuration.getConnectorLowResourcesMaxIdleTime());
	    }
	    if(configuration.getConnectorConfidentialPort() != null) {
	    	connector.setConfidentialPort(configuration.getConnectorConfidentialPort());
	    }
	    if(configuration.getConnectorStatsOn() != null) {
	    	connector.setStatsOn(configuration.getConnectorStatsOn());
	    }
	    
	    return connector;
	}
	
	
	
	private Server getServer() {
		Server server = new Server();
		
	    if(configuration.getServerGracefulShutdown() != null) {
	    	server.setGracefulShutdown(configuration.getServerGracefulShutdown());
	    }
	    if(configuration.getServerSendDateHeader() != null) {
	    	server.setSendDateHeader(configuration.getServerSendDateHeader());
	    }
	    if(configuration.getServerSendServerVersion() != null) {
	    	server.setSendServerVersion(configuration.getServerSendServerVersion());
	    }
	    if(configuration.getServerStopAtShutdown() != null) {
	    	server.setStopAtShutdown(configuration.getServerStopAtShutdown());
	    }
	    
	    return server;
	}
	
	
	
	
	public void start() {
		log.info("start jetty-server ......");
		
		log.info("begin initialization QueuedThreadPool ......");
		QueuedThreadPool threadPool = getThreadPool();
		
		
	    log.info("begin initialization SelectChannelConnector ......");
	    SelectChannelConnector connector = getConnector();
	    
	    
	    log.info("begin initialization Server ......");
	    Server server = getServer();	    
	    server.setThreadPool(threadPool);
	    server.addConnector(connector);
	    
	    
	    log.info("begin initialization WebAppContext ......");
	    List<WebAppContext> contextList = new ArrayList<WebAppContext>();
	    String webAppRoots = configuration.getWebAppRoots();
	    String webAppWars = configuration.getWebAppWars();
	    
	    String defaultDescriptor = configuration.getWebAppDefaultDescriptor();
	    if(BinaryUtils.isEmpty(defaultDescriptor)) defaultDescriptor = DEFAULT_WEB;
	    Set<String> contextPathSet = new HashSet<String>();
	    	    
	    if(webAppRoots!=null && (webAppRoots=webAppRoots.trim()).length()>0) {
	    	String[] arr = webAppRoots.indexOf(';')>0 ? webAppRoots.split("[;]") : new String[]{webAppRoots};
	    	for(int i=0; i<arr.length; i++) {
	    		String s = arr[i];
	    		log.info(" initialization app-root '"+s+"' ...... ");
	    		
	    		if((s=s.trim()).length() == 0) {
	    			log.warn(" has empty app-root! ");
	    			continue ;
	    		}
	    		
	    		int idx = s.indexOf('=');
	    		if(idx <= 0) {
	    			log.error(new JettyException(" is wrong app-root '"+s+"'! ").toString());
	    			continue ;
	    		}
	    		
	    		String contextPath = s.substring(0, idx).trim();
	    		String appRoot = s.substring(idx+1).trim();
	    		
	    		if(appRoot.length()==0) {
	    			log.error(new JettyException(" is wrong app-root '"+s+"'! ").toString());
	    			continue ;
	    		}
	    		
	    		if(!(new File(appRoot).isDirectory())) {
	    			log.error(new JettyException(" is not found root-directory '"+s+"'! ").toString());
	    			continue ;
	    		}
	    		
	    		contextPath = effectiveContextPath(contextPath);
	    		
	    		WebAppContext context = new WebAppContext(appRoot, contextPath);
	    		context.setDefaultsDescriptor(defaultDescriptor);
	    		
	    		char last = appRoot.charAt(appRoot.length()-1);
				if(last!='/' && last!='\\') appRoot += File.separator;
				String webXml = appRoot + "WEB-INF" + File.separator + "web.xml";
				File file = new File(webXml);
				if(file.isFile()) {
					context.setDescriptor(webXml);
				}
	    		
				contextList.add(context);
				
				String exists = contextPath.toLowerCase();
				if(contextPathSet.contains(exists)) {
					log.warn(" is repeated contextPath '"+contextPath+"'! ");
				}else {
					contextPathSet.add(exists);
				}
	    	}
	    }
	    
	    if(webAppWars!=null && (webAppWars=webAppWars.trim()).length()>0) {
	    	String[] arr = webAppWars.indexOf(';')>0 ? webAppWars.split("[;]") : new String[]{webAppWars};
	    	for(int i=0; i<arr.length; i++) {
	    		String s = arr[i];
	    		log.info(" initialization app-war '"+s+"' ...... ");
	    		
	    		if((s=s.trim()).length() == 0) {
	    			log.warn(" has empty app-war! ");
	    			continue ;
	    		}
	    		
	    		int idx = s.indexOf('=');
	    		if(idx <= 0) {
	    			log.error(new JettyException(" is wrong app-war '"+s+"'! ").toString());
	    			continue ;
	    		}
	    		
	    		String contextPath = s.substring(0, idx).trim();
	    		String appWar = s.substring(idx+1).trim();
	    		
	    		if(appWar.length()==0) {
	    			log.error(new JettyException(" is wrong app-root '"+s+"'! ").toString());
	    			continue ;
	    		}
	    		
	    		if(!(new File(appWar).isFile())) {
	    			log.error(new JettyException(" is not found war '"+s+"'! ").toString());
	    			continue ;
	    		}
	    		
	    		contextPath = effectiveContextPath(contextPath);
	    		
	    		WebAppContext context = new WebAppContext();
	    		context.setContextPath(contextPath);
	    		context.setDefaultsDescriptor(defaultDescriptor);
	    		context.setWar(appWar);
	    		
				contextList.add(context);
				
				String exists = contextPath.toLowerCase();
				if(contextPathSet.contains(exists)) {
					log.warn(" is repeated contextPath '"+contextPath+"'! ");
				}else {
					contextPathSet.add(exists);
				}
	    	}
	    }
	    
	    
	    if(contextList.size() > 0) {
	    	server.setHandlers(contextList.toArray(new WebAppContext[0]));
	    }
	    
	    
	    log.info("begin start server ......");
	    try {
			server.start();
			log.info("start server successful.");
			server.join();
		} catch (Exception e) {
			log.error("start server is failure.", e);
			throw new JettyException(e);
		}
	}
	
	
	
	private String effectiveContextPath(String contextPath) {
		if(contextPath.charAt(0) != '/') contextPath = "/" + contextPath;
    	while(contextPath.charAt(contextPath.length()-1)=='/') contextPath = contextPath.substring(0, contextPath.length()-1);
    	return contextPath;
	}
	
	
	
	
	
}
