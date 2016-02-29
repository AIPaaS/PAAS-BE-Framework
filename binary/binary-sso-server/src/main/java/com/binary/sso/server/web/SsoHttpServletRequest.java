package com.binary.sso.server.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.binary.core.util.BinaryUtils;
import com.binary.sso.server.session.support.SsoHttpSessionManager;

public class SsoHttpServletRequest implements HttpServletRequest {

	
	private HttpServletRequest request;
	
	
	
	public SsoHttpServletRequest(HttpServletRequest request) {
		BinaryUtils.checkEmpty(request, "request");
		
		this.request = request;
	}
	
	
	
	@Override
	public Object getAttribute(String name) {
		return this.request.getAttribute(name);
	}

	@Override
	public Enumeration<?> getAttributeNames() {
		return this.request.getAttributeNames();
	}

	@Override
	public String getCharacterEncoding() {
		return this.request.getCharacterEncoding();
	}

	@Override
	public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
		this.request.setCharacterEncoding(env);
	}

	@Override
	public int getContentLength() {
		return this.request.getContentLength();
	}

	@Override
	public String getContentType() {
		return this.request.getContentType();
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return this.request.getInputStream();
	}

	@Override
	public String getParameter(String name) {
		return this.request.getParameter(name);
	}

	@Override
	public Enumeration<?> getParameterNames() {
		return this.request.getParameterNames();
	}

	@Override
	public String[] getParameterValues(String name) {
		return this.request.getParameterValues(name);
	}

	@Override
	public Map<?, ?> getParameterMap() {
		return this.request.getParameterMap();
	}

	@Override
	public String getProtocol() {
		return this.request.getProtocol();
	}

	@Override
	public String getScheme() {
		return this.request.getScheme();
	}

	@Override
	public String getServerName() {
		return this.request.getServerName();
	}

	@Override
	public int getServerPort() {
		return this.request.getServerPort();
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return this.request.getReader();
	}

	@Override
	public String getRemoteAddr() {
		return this.request.getRemoteAddr();
	}

	@Override
	public String getRemoteHost() {
		return this.request.getRemoteHost();
	}

	@Override
	public void setAttribute(String name, Object o) {
		this.request.setAttribute(name, o);
	}

	@Override
	public void removeAttribute(String name) {
		this.request.removeAttribute(name);
	}

	@Override
	public Locale getLocale() {
		return this.request.getLocale();
	}

	@Override
	public Enumeration<?> getLocales() {
		return this.request.getLocales();
	}

	@Override
	public boolean isSecure() {
		return this.request.isSecure();
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String path) {
		return this.request.getRequestDispatcher(path);
	}

	@SuppressWarnings("deprecation")
	@Override
	public String getRealPath(String path) {
		return this.request.getRealPath(path);
	}

	@Override
	public int getRemotePort() {
		return this.request.getRemotePort();
	}

	@Override
	public String getLocalName() {
		return this.request.getLocalName();
	}

	@Override
	public String getLocalAddr() {
		return this.request.getLocalAddr();
	}

	@Override
	public int getLocalPort() {
		return this.request.getLocalPort();
	}

	@Override
	public String getAuthType() {
		return this.request.getAuthType();
	}

	@Override
	public Cookie[] getCookies() {
		return this.request.getCookies();
	}

	@Override
	public long getDateHeader(String name) {
		return this.request.getDateHeader(name);
	}

	@Override
	public String getHeader(String name) {
		return this.request.getHeader(name);
	}

	@Override
	public Enumeration<?> getHeaders(String name) {
		return this.request.getHeaders(name);
	}

	@Override
	public Enumeration<?> getHeaderNames() {
		return this.request.getHeaderNames();
	}

	@Override
	public int getIntHeader(String name) {
		return this.request.getIntHeader(name);
	}

	@Override
	public String getMethod() {
		return this.request.getMethod();
	}

	@Override
	public String getPathInfo() {
		return this.request.getPathInfo();
	}

	@Override
	public String getPathTranslated() {
		return this.request.getPathTranslated();
	}

	@Override
	public String getContextPath() {
		return this.request.getContextPath();
	}

	@Override
	public String getQueryString() {
		return this.request.getQueryString();
	}

	@Override
	public String getRemoteUser() {
		return this.request.getRemoteUser();
	}

	@Override
	public boolean isUserInRole(String role) {
		return this.request.isUserInRole(role);
	}

	@Override
	public Principal getUserPrincipal() {
		return this.request.getUserPrincipal();
	}

	@Override
	public String getRequestedSessionId() {
		return this.request.getRequestedSessionId();
	}

	@Override
	public String getRequestURI() {
		return this.request.getRequestURI();
	}

	@Override
	public StringBuffer getRequestURL() {
		return this.request.getRequestURL();
	}

	@Override
	public String getServletPath() {
		return this.request.getServletPath();
	}

	

	@Override
	public boolean isRequestedSessionIdValid() {
		return this.request.isRequestedSessionIdValid();
	}

	@Override
	public boolean isRequestedSessionIdFromCookie() {
		return this.request.isRequestedSessionIdFromCookie();
	}

	@Override
	public boolean isRequestedSessionIdFromURL() {
		return this.request.isRequestedSessionIdFromURL();
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isRequestedSessionIdFromUrl() {
		return this.request.isRequestedSessionIdFromUrl();
	}
	
	
	
	
	
	
	
	

	private HttpSession replaceSession(HttpSession session) {
		if(session != null) {
			SsoHttpSessionManager mgr = SsoHttpSessionManager.getInstance();
			session = mgr.getSsoHttpSession(session.getId());
		}
		return session;
	}
	
	

	@Override
	public HttpSession getSession(boolean create) {
		HttpSession session = this.request.getSession(create);
		if(session != null) session = replaceSession(session);
		return session;
	}
	
	

	@Override
	public HttpSession getSession() {
		HttpSession session = this.request.getSession();
		if(session != null) session = replaceSession(session);
		return session;
	}
	
	

}
