package com.binary.framework.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.binary.framework.Local;

public class CharacterEncodingFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String charset = Local.getCharset();
		if(request instanceof HttpServletRequest) {
			((HttpServletRequest)request).setCharacterEncoding(charset);
		}
		if(response instanceof ServletResponse) {
			((ServletResponse)response).setCharacterEncoding(charset);
		}
		chain.doFilter(request, response);
	}

	public void destroy() {
	}

}
