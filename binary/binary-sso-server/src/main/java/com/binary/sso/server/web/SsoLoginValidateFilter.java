package com.binary.sso.server.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.binary.core.http.URLResolver;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.exception.WebException;
import com.binary.framework.web.SessionKey;
import com.binary.framework.web.filter.LoginValidateFilter;
import com.binary.sso.server.cache.SsoCache;
import com.binary.sso.server.session.support.SsoHttpSessionManager;

public class SsoLoginValidateFilter extends LoginValidateFilter {
	
	
	private SsoCache getCache() {
		return SsoHttpSessionManager.getInstance().getCache();
	}
	
	
	
	@Override
	protected boolean validLogin(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if(session == null) return false;
		String sessionId = session.getId();
		
		SsoCache cache = getCache();
		return cache.containsSessionAttribute(sessionId, SessionKey.SYSTEM_USER);
	}
	
	
	@Override
	protected void processErrorResult(HttpServletRequest request, HttpServletResponse response) {
		String contextpath = request.getContextPath();
        String url = request.getRequestURI();
        String path = url.substring(contextpath.length()).toLowerCase();
        if(path.length() == 0) path = "/";
        
        //如果是注销session操作
        if(path.equals("/user/auth/invalidatesession")) {
        	String callbackUrl = request.getParameter("callbackUrl");
        	if(!BinaryUtils.isEmpty(callbackUrl)) {
        		if(callbackUrl.indexOf('/') < 0) callbackUrl = URLResolver.decode(callbackUrl);
        		try {
					response.sendRedirect(callbackUrl);
					return ;
				} catch (IOException e) {
					throw new WebException(e);
				}
        	}
        }
        
        super.processErrorResult(request, response);
	}
	
	
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse rep, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		SsoHttpServletRequest ssorequest = new SsoHttpServletRequest(request);
		
		//替换掉sso-request
		super.doFilter(ssorequest, rep, chain);
	}
	
	
	
	
}
