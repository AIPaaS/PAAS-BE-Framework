package com.binary.framework.spring;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

public class BinaryRedirectView implements View {
		
	
	private RedirectView view;
	
	
	public BinaryRedirectView(RedirectView view) {
		this.view = view;
	}
	
	
	
	@Override
	public String getContentType() {
		return this.view.getContentType();
	}
	
	
	

	@Override
	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.isRequestedSessionIdFromCookie()) {
			this.view.render(model, request, response);
		}else {
			response.sendRedirect(this.view.getUrl());
		}
	}

}
