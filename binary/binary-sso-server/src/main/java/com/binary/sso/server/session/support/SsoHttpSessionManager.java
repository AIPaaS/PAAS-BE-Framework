package com.binary.sso.server.session.support;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.binary.framework.exception.SessionException;
import com.binary.sso.server.SsoServerConfiguration;
import com.binary.sso.server.cache.SsoCache;
import com.binary.sso.server.exception.SsoException;
import com.binary.sso.server.exception.SsoHttpSessionException;
import com.binary.sso.server.exception.SsoVerifyException;
import com.binary.sso.server.session.Session;

public class SsoHttpSessionManager extends AbstractSessionManager implements HttpSessionListener {
	
	
	private static SsoHttpSessionManager sessionmgr;
	private SsoCache cache;
	
	
	public SsoHttpSessionManager() {
		sessionmgr = this;
	}
	
	
	/**
	 * 获取HttpSessionManager实例
	 * @return
	 */
	public static SsoHttpSessionManager getInstance() {
		if(sessionmgr == null) throw new SessionException(" is not set http-session-listener in web.xml");
		return sessionmgr;
	}
	
	
	
	
	@Autowired
	protected Session doReplaceSession(HttpSession session) {
		return new SsoHttpSession(session, getCache());
	}
	
	
	
	/**
	 * 初始化, 由外部调用
	 * @param config
	 */
	public void initialize(ApplicationContext springContext) {
		SsoServerConfiguration configuration = springContext.getBean(SsoServerConfiguration.class);
		if(configuration == null) throw new SsoException(" is not setting bean 'com.binary.sso.server.SsoServerConfiguration' in spring-context! ");
		
		SsoCache cache = configuration.getSsoCache();
		if(cache == null) throw new SsoException(" is not setting com.binary.sso.server.cache.SsoCache in SsoServerConfiguration! ");
		this.cache = cache;
	}
	
	
	
	
	/**
	 * 获取Session缓存
	 * @return
	 */
	public SsoCache getCache() {
		if(this.cache == null) throw new SsoVerifyException("ERROR", " is not initialize SsoHttpSessionManager! ");
		return this.cache;
	}
	
	
	
	public SsoHttpSession getSsoHttpSession(String sessionId) {
		Session session = getSession(sessionId);
		if(session != null) {
			if(!(session instanceof SsoHttpSession)) {
				throw new SsoHttpSessionException(" the session '"+session.getClass().getName()+"' not typeof '"+SsoHttpSession.class.getName()+"'! ");
			}
			return (SsoHttpSession)session;
		}
		return null;
	}
	
	
	
	/**
	 * Session创建事件
	 */
	@Override
    public void sessionCreated(HttpSessionEvent se) {
		super.pushSession(doReplaceSession(se.getSession()));
	}
	
	
    
    /**
	 * Session失效事件
	 */
	@Override
    public void sessionDestroyed(HttpSessionEvent se) {
    	HttpSession session = se.getSession();
    	if(session != null) popSession(session.getId());
    }

	
	
}
