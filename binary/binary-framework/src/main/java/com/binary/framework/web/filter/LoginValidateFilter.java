package com.binary.framework.web.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.binary.core.http.URLResolver;
import com.binary.core.lang.StringUtils;
import com.binary.core.util.BinaryUtils;
import com.binary.core.util.WildcardPatternBuilder;
import com.binary.framework.bean.User;
import com.binary.framework.exception.ValidateLoginException;
import com.binary.framework.exception.WebException;
import com.binary.framework.web.ErrorCode;
import com.binary.framework.web.RemoteResult;
import com.binary.framework.web.SessionKey;
import com.binary.json.JSON;



/**
 * 过滤验证优先级：ignoreFilterPattern>mustLoginPattern>ignoreLoginPattern
 */
public class LoginValidateFilter implements Filter {

	
	/**
	 * 不拦截的路径正则表达式, 多个值以分号[;]分隔 , 不需要包含Context
	 */
	private Pattern ignoreFilterPattern;
	
	
	/**
	 * 必须要经过用户登录认证的路径正则表达式, 多个值以分号[;]分隔 , 不需要包含Context
	 */
	private Pattern mustLoginPattern;
	
	
	/**
	 * 不需要验证用户登录的路径正则表达式, 多个值以分号[;]分隔 , 不需要包含Context
	 */
	private Pattern ignoreLoginPattern;
	
	
	/**
	 * 验证不通过时所跳向的页面
	 */
	private String securityPageUrl;
	
	/**
	 * 是否带http全局路径
	 */
	private boolean isSecurityPageHttp;
	
	/**
	 * 如查reqest-header中有以下值, 则不跳转页面，而是在Response中写入RemoteResult对象, 多个值以分号[;]隔开
	 */
	private Map<String, String> resultRequestHeaderMap;
	
	
	
	private String[] splitStringPattern(String strFilterPattern) {
		if(strFilterPattern.indexOf(";") > 0) {
			return strFilterPattern.split("[;]");
		}else {
			return new String[]{strFilterPattern};
		}
	}
	
	
	public void init(FilterConfig filterConfig) throws ServletException {
		String strIgnoreFilterPattern = filterConfig.getInitParameter("ignore-filter-pattern");
        String strIgnoreLoginPattern = filterConfig.getInitParameter("ignore-login-pattern");
        String strMustLoginPattern = filterConfig.getInitParameter("must-login-pattern");
        String securityPageUrl = filterConfig.getInitParameter("security-page-url");
        String strResultRequestHeader = filterConfig.getInitParameter("result-request-header");
		
		if(!BinaryUtils.isEmpty(strIgnoreFilterPattern)) {
			this.ignoreFilterPattern = WildcardPatternBuilder.build(splitStringPattern(strIgnoreFilterPattern.toUpperCase()));
		}
		if(!BinaryUtils.isEmpty(strIgnoreLoginPattern)) {
			this.ignoreLoginPattern = WildcardPatternBuilder.build(splitStringPattern(strIgnoreLoginPattern.toUpperCase()));
		}
		if(!BinaryUtils.isEmpty(strMustLoginPattern)) {
			this.mustLoginPattern = WildcardPatternBuilder.build(splitStringPattern(strMustLoginPattern.toUpperCase()));
		}
		if(!BinaryUtils.isEmpty(strMustLoginPattern)) {
			this.mustLoginPattern = WildcardPatternBuilder.build(splitStringPattern(strMustLoginPattern.toUpperCase()));
		}
		if(securityPageUrl!=null && (securityPageUrl=securityPageUrl.trim()).length()>0) {
			this.securityPageUrl = securityPageUrl;
			this.isSecurityPageHttp = securityPageUrl.matches("[H|h][T|t][T|t][P|p][S|s]?[:][/][/].+");
		}
		if(strResultRequestHeader!=null && (strResultRequestHeader=strResultRequestHeader.trim()).length()>0) {
			String[] arr = splitStringPattern(strResultRequestHeader);
			resultRequestHeaderMap = new HashMap<String, String>();
			for(int i=0; i<arr.length; i++) {
				String item = arr[i].trim().toUpperCase();
				int index = item.indexOf("=");
				if(index<=0 || index>=item.length()-1) continue ;
				String key = item.substring(0, index).trim();
				String value = item.substring(index+1).trim();
				resultRequestHeaderMap.put(key, value);
			}
		}
	}

	
	
	
	protected boolean validLogin(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if(session == null) return false;
		Object obj = session.getAttribute(SessionKey.SYSTEM_USER);
		
		if(obj == null) {
			return false;
		}		
		if(!(obj  instanceof User)) {
			return false;
		}				
		String reload = request.getParameter("reload");	
		
		//只有点击左侧菜单时，才会有reload
		if(StringUtils.isBlank(reload)){
			return true;
		}else{
			User user  = (User) obj;			
			if(reload.equals(user.getUserId())){
				return true;
			}else{
				return false;			
			}	
		}
		
				
				
		
	}
	
	@SuppressWarnings("unchecked")
	protected void processErrorResult(HttpServletRequest request, HttpServletResponse response) {
		if(this.resultRequestHeaderMap != null) {
			Enumeration<String> enu = request.getHeaderNames();
			if(enu != null) {
				while(enu.hasMoreElements()) {
					String name = enu.nextElement();
					String value = request.getHeader(name);
					if(name==null || value==null) continue ;
					
					String exit = this.resultRequestHeaderMap.get(name.trim().toUpperCase());
					if(exit!=null && exit.equalsIgnoreCase(value.trim())) {
						RemoteResult rs = new RemoteResult(ErrorCode.NOT_LOGIN, "页面已超时，请重新登录！");
						try {
							PrintWriter pw = response.getWriter();
							pw.write(JSON.toString(rs));
							pw.flush();
						}catch(IOException e) {
							throw new ValidateLoginException(e);
						}
						return ;
					} 
				}
			}
		}
		
		if(this.securityPageUrl != null) {
			String url = this.securityPageUrl;
			if(!this.isSecurityPageHttp) url = request.getContextPath()+url;
			try {
				StringBuffer beforeUrl = request.getRequestURL();
				String qs = request.getQueryString();
				if(!BinaryUtils.isEmpty(qs)) {
					beforeUrl.append("?").append(qs);
				}
				
				url += (url.indexOf('?')<0 ? '?' : '&') + "beforeUrl=" + URLResolver.encode(beforeUrl.toString());
				
				response.sendRedirect(url);
			} catch (IOException e) {
				throw new WebException(e);
			}
		}
	}
	
	
	
	public void doFilter(ServletRequest req, ServletResponse rep, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) rep;
        
        String contextpath = request.getContextPath();
        String url = request.getRequestURI();
        String path = url.substring(contextpath.length()).toUpperCase();
        if(path.length() == 0) path = "/";
        
        //如果输入的地址为根目录, 则跳转至安全页面
        if(path.equals("/")) {
        	if(this.securityPageUrl != null) {
        		url = this.securityPageUrl;
        		if(!this.isSecurityPageHttp) url = request.getContextPath()+url;
        		response.sendRedirect(url);
        	}
        	return ;
        }
        
        
        //如里不需要过滤
        if(this.ignoreFilterPattern!=null && ignoreFilterPattern.matcher(path).matches()) {
        	chain.doFilter(request, response);
        	return ;
        }
        
        //必需要验证
        if(this.mustLoginPattern!=null && this.mustLoginPattern.matcher(path).matches()) {
        	if(validLogin(request, response)) {
        		chain.doFilter(request, response);
        	}else {
        		processErrorResult(request, response);
        	}
        	return ;
        }
        
        //不需要验证登录
        if(this.ignoreLoginPattern!=null && !this.ignoreLoginPattern.matcher(path).matches()) {
        	if(validLogin(request, response)) {
        		chain.doFilter(request, response);
        	}else {
        		processErrorResult(request, response);
        	}
        	return ;
        }
        
        chain.doFilter(request, response);
    }

	
	
    
	public void destroy() {
		if(this.resultRequestHeaderMap!=null) {
			this.resultRequestHeaderMap.clear();
		}
	}
	
	
	
	

}



