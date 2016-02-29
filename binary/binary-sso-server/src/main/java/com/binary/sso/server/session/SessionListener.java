package com.binary.sso.server.session;




/**
 * Session监听器
 * @author wanwb
 */
public interface SessionListener {

	
	
	
	/**
	 * Session创建事件
	 */
	public void onCreated(Session session);
	
	
    
    /**
	 * Session失效事件
	 */
    public void onDestroyed(Session session);
	
	
	
	
}
