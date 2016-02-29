package com.binary.sso.server.session.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.binary.core.util.SecurityIterator;
import com.binary.sso.server.session.Session;
import com.binary.sso.server.session.SessionListener;
import com.binary.sso.server.session.SessionManager;

public abstract class AbstractSessionManager implements SessionManager {

	
	private final Object sync = new Object();
	
	private final Map<String, Session> store = new HashMap<String, Session>();
	private final List<SessionListener> listeners = new ArrayList<SessionListener>();
	
	
	
	@Override
	public Session getSession(String sessionId) {
		return this.store.get(sessionId);
	}
	
	
	@Override
	public boolean contains(String sessionId) {
		return store.containsKey(sessionId);
	}
	
	
	@Override
	public Iterator<String> getSessionIds() {
		return new SecurityIterator<String>(this.store.keySet().iterator());
	}
	
	
	
	@Override
	public Iterator<Session> getSessions() {
		return new SecurityIterator<Session>(this.store.values().iterator());
	}
	
	
	
	@Override
	public Iterator<Entry<String, Session>> getSessionEntrys() {
		return new SecurityIterator<Map.Entry<String,Session>>(this.store.entrySet().iterator());
	}
	
	
	
	@Override
	public void pushSession(Session session) {
		if(session == null) return ;
		synchronized(sync) {
			if(!this.store.containsKey(session.getId())) {
				this.store.put(session.getId(), session);
				onSessionCreated(session);
			}
		}
	}
	
	
	@Override
	public Session popSession(String sessionId) {
		synchronized(sync) {
			Session session = this.store.remove(sessionId);
			if(session != null) {
				onSessionDestroyed(session);
			}
			return session;
		}
	}
	
	
	
	
	
	public void addSessionListener(SessionListener listener) {
		synchronized (sync) {
			this.listeners.add(listener);
		}
	}
	
	
	public void removeSessionListener(SessionListener listener) {
		synchronized (sync) {
			this.listeners.remove(listener);
		}
	}
	
    
	
	
	/**
	 * Session创建事件
	 */
	protected void onSessionCreated(Session session) {
		for(int i=0; i<this.listeners.size(); i++) {
			this.listeners.get(i).onCreated(session);
		}
	}
	
	
    
    /**
	 * Session失效事件
	 */
	protected void onSessionDestroyed(Session session) {
		for(int i=0; i<this.listeners.size(); i++) {
			this.listeners.get(i).onDestroyed(session);
		}
	}
	
	
	
    
}
