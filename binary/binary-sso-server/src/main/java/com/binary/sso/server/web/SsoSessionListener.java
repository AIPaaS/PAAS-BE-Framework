package com.binary.sso.server.web;

import com.binary.core.util.BinaryUtils;
import com.binary.sso.server.SsoServerConfiguration;
import com.binary.sso.server.cache.SsoCache;
import com.binary.sso.server.session.Session;
import com.binary.sso.server.session.SessionListener;

public class SsoSessionListener implements SessionListener {
	
	
	private SsoCache cache;
	
	
	
	public SsoSessionListener(SsoServerConfiguration configuration) {
		BinaryUtils.checkEmpty(configuration, "configuration");
		SsoCache cache = configuration.getSsoCache();
		BinaryUtils.checkEmpty(cache, "configuration.cache");
		
		this.cache = cache;
	}
	
	

	@Override
	public void onCreated(Session session) {
	}

	
	
	@Override
	public void onDestroyed(Session session) {
		//当Session失败时, 册除缓存中所存放的用户信息
		String sessionId = session.getId();
		this.cache.clearSessionAttribute(sessionId);
	}
	
	
	
	
	
	
}
