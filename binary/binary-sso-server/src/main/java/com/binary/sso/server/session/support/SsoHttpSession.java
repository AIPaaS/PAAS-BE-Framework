package com.binary.sso.server.session.support;

import java.util.Enumeration;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import com.binary.core.util.BinaryUtils;
import com.binary.core.util.IteratorEnumeration;
import com.binary.sso.server.cache.SsoCache;

@SuppressWarnings("deprecation")
public class SsoHttpSession extends AbstractSession implements HttpSession {

	
	private HttpSession session;
	private SsoCache ssoCache;
	
	
	public SsoHttpSession(HttpSession session, SsoCache ssoCache) {
		BinaryUtils.checkEmpty(session, "session");
		BinaryUtils.checkEmpty(ssoCache, "ssoCache");
				
		this.session = session;
		this.ssoCache = ssoCache;
	}
	
	
	
	@Override
	public long getCreationTime() {
		return this.session.getCreationTime();
	}

	@Override
	public String getId() {
		return this.session.getId();
	}

	@Override
	public long getLastAccessedTime() {
		return this.session.getLastAccessedTime();
	}

	@Override
	public ServletContext getServletContext() {
		return this.session.getServletContext();
	}

	@Override
	public void setMaxInactiveInterval(int interval) {
		this.session.setMaxInactiveInterval(interval);
	}

	
	@Override
	public int getMaxInactiveInterval() {
		return this.session.getMaxInactiveInterval();
	}

	
	@Override
	public HttpSessionContext getSessionContext() {
		return this.session.getSessionContext();
	}
	
	@Override
	public void invalidate() {
		this.session.invalidate();
	}

	@Override
	public boolean isNew() {
		return this.session.isNew();
	}
	
	@Override
	public Object getValue(String name) {
		return this.getAttribute(name);
	}
	@Override
	public void putValue(String name, Object value) {
		setAttribute(name, value);
	}
	@Override
	public void removeValue(String name) {
		this.session.removeAttribute(name);
	}
	
	
	
	
	
	

	
	
	
	@Override
	public Enumeration<String> getAttributeNames() {
		Set<String> set = this.ssoCache.getSessionAttributeKeySet(getId());
		return new IteratorEnumeration<String>(set.iterator());
	}
	
	@Override
	public String[] getValueNames() {
		Set<String> set = this.ssoCache.getSessionAttributeKeySet(getId());
		return set.toArray(new String[0]);
	}
	
	@Override
	public Object getAttribute(String name) {
		return this.ssoCache.getSessionAttribute(getId(), name);
	}
	
	
	@Override
	public void setAttribute(String name, Object value) {
		this.ssoCache.setSessionAttribute(getId(), name, value);
	}
	
	
	@Override
	public void removeAttribute(String name) {
		this.ssoCache.removeSessionAttribute(getId(), name);
	}


	
	

	

}
