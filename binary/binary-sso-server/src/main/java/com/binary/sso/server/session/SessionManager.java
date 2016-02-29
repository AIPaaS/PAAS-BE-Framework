package com.binary.sso.server.session;

import java.util.Iterator;
import java.util.Map.Entry;

public interface SessionManager {
	
	
	/**
	 * 根据SessionID获取Session, 设有则返回null
	 * @param sessionId
	 * @return
	 */
	public Session getSession(String sessionId);
	
	
	
	/**
	 * 判断Session是否存在
	 * @param sessionId
	 * @return
	 */
	public boolean contains(String sessionId);
	
	
	
	/**
	 * 获取所有的SessionID
	 * @return
	 */
	public Iterator<String> getSessionIds();
	
	
	
	/**
	 * 获取所有的Session
	 * @return
	 */
	public Iterator<Session> getSessions();
	
	
	
	/**
	 * 获取所有的Session
	 * @return
	 */
	public Iterator<Entry<String, Session>> getSessionEntrys();
	
	
	
	
	/**
	 * 加入Session
	 * @param session
	 */
	public void pushSession(Session session);
	
	
	
	/**
	 * 释放Session
	 * @param sessionId
	 * @return
	 */
	public Session popSession(String sessionId);
	
	
	
	/**
	 * 添加Session监听器
	 * @param listener
	 */
	public void addSessionListener(SessionListener listener);
	
	
	
	/**
	 * 删除Session监听器
	 * @param listener
	 */
	public void removeSessionListener(SessionListener listener);
	
	
    
    
}
