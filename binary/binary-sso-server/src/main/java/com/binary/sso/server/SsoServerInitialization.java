package com.binary.sso.server;

import org.springframework.context.ApplicationContext;

import com.binary.sso.server.exception.SsoException;
import com.binary.sso.server.session.support.SsoHttpSessionManager;
import com.binary.sso.server.web.SsoSessionListener;

public class SsoServerInitialization {

	
	
	public void initialize(ApplicationContext context) {
		//初始化Session监听器
		SsoSessionListener sessionListener = context.getBean(SsoSessionListener.class);
		if(sessionListener == null) throw new SsoException(" is not com.binary.sso.server.web.SsoSessionListener in spring-context! ");
		SsoHttpSessionManager sessionManager = SsoHttpSessionManager.getInstance();
		sessionManager.addSessionListener(sessionListener);
		sessionManager.initialize(context);
	}

	
	
	
	
}
