package com.binary.sso.client.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.RequestMapping;

import com.binary.core.http.HttpClient;
import com.binary.core.http.URLResolver;
import com.binary.core.lang.ClassUtils;
import com.binary.core.lang.Conver;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.bean.User;
import com.binary.framework.util.ControllerUtils;
import com.binary.framework.web.SessionKey;
import com.binary.sso.client.SsoClientConfiguration;
import com.binary.sso.client.exception.SsoClientException;



@RequestMapping("/sso/client/auth")
public class SsoClientAuthMvc implements ApplicationContextAware {
	private static Logger logger = LoggerFactory.getLogger(SsoClientAuthMvc.class);
	
	private ApplicationContext springContext;
	
	
	private SsoClientConfiguration configuration;

	
	/** SSO服务端URL根目录, 格式：http://domain:port/contextpath **/
	private String ssoServerRoot;
	
	
	/** SSO客户端(当前集成应用)URL根目录, 格式：http://domain:port/contextpath **/
	private String ssoClientRoot;
	
	/** 如果登录之后没有回调页面, 则指定跳转连接 **/
	private String noBeforeUrl;
	
	
	private Class<User> userType;
	
	/** 当登录成功之后, 从SSO取出的User对象处理对象, 必须实现com.binary.sso.client.auth.SessionUserHandler, 如果没有指定, 则User对象自动存入当前服务器Session当中 **/
	private SessionUserHandler sessionUserHandler;
	private UserLoginListener userLoginListener;
	
	
	@SuppressWarnings("unchecked")
	public SsoClientAuthMvc(SsoClientConfiguration configuration) {
		BinaryUtils.checkEmpty(configuration.getSsoServerRoot(), "com.binary.sso.client.SsoClientConfiguration.ssoServerRoot");
		BinaryUtils.checkEmpty(configuration.getSsoClientRoot(), "com.binary.sso.client.SsoClientConfiguration.ssoClientRoot");
		BinaryUtils.checkEmpty(configuration.getUserClass(), "com.binary.sso.client.SsoClientConfiguration.userClass");
		BinaryUtils.checkEmpty(configuration.getNoBeforeUrl(), "com.binary.sso.client.SsoClientConfiguration.noBeforeUrl");
		
		this.ssoServerRoot = URLResolver.correctContextPath(configuration.getSsoServerRoot());
		this.ssoClientRoot = URLResolver.correctContextPath(configuration.getSsoClientRoot());
		
		this.ssoServerRoot = this.ssoServerRoot.substring(1);
		this.ssoClientRoot = this.ssoClientRoot.substring(1);
		this.noBeforeUrl = configuration.getNoBeforeUrl().trim();
		
		String userClass = configuration.getUserClass().trim();
		this.userType = (Class<User>)ClassUtils.forName(userClass, this.getClass().getClassLoader());
		if(!User.class.isAssignableFrom(this.userType)) {
			throw new SsoClientException(" the userClass '"+userClass+"' is not typeof '"+User.class.getName()+"'! ");
		}
		
		this.configuration = configuration;
	}
	
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.springContext = applicationContext;
		
		String sessionUserHandlerName = configuration.getSessionUserHandler();
		if(!BinaryUtils.isEmpty(sessionUserHandlerName)) {
			Object handler = springContext.getBean(sessionUserHandlerName);
			if(!(handler instanceof SessionUserHandler)) {
				throw new SsoClientException(" the SessionUserHandler '"+handler.getClass().getName()+"' not typeof '"+SessionUserHandler.class.getName()+"'! ");
			}
			this.sessionUserHandler = (SessionUserHandler)handler;
		}
		
		String userLoginListenerName = configuration.getUserLoginListener();
		if(!BinaryUtils.isEmpty(userLoginListenerName)) {
			Object listener = springContext.getBean(userLoginListenerName);
			if(!(listener instanceof UserLoginListener)) {
				throw new SsoClientException(" the UserLoginListener '"+listener.getClass().getName()+"' not typeof '"+UserLoginListener.class.getName()+"'! ");
			}
			this.userLoginListener = (UserLoginListener)listener;
		}
	}
	
	
	
	/**
	 * 验证是否登录
	 * @param request
	 * @param response
	 */
	@RequestMapping("/verifyLogin")
	public String verifyLogin(HttpServletRequest request, HttpServletResponse response) {
			return "forward:/sso/client/auth/notLogin";
	}
	
	
	
	@RequestMapping("/notLogin")
	public String notLogin(HttpServletRequest request, HttpServletResponse response) {
		String beforeUrl = request.getParameter("beforeUrl");
		if(BinaryUtils.isEmpty(beforeUrl)) {
			beforeUrl = Conver.to(request.getAttribute("beforeUrl"), String.class);
		}
		
		if(BinaryUtils.isEmpty(beforeUrl)) {
			beforeUrl = "";
		}else {
			if(beforeUrl.indexOf('/') >= 0) {
				beforeUrl = URLResolver.encode(beforeUrl);
			}
		}
		
		//回调地址
		String callbackUrl = URLResolver.encode(this.ssoClientRoot + "/sso/client/auth/ssoCallback");
		
		String ssourl = this.ssoServerRoot + "/user/auth/verify?beforeUrl="+beforeUrl+"&callbackUrl="+callbackUrl;
		
		return "redirect:" + ssourl;
	}
	
	
	
	
	
	private HttpSession newSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		if(session != null) {
			try {
				session.invalidate();
			}catch(Exception e) {
				logger.error("new-session error!", e);
			}
		}
		
		session = request.getSession(true);
		return session;
	}
	
	
	protected void loginSession(HttpServletRequest request, User user) {
		HttpSession session = newSession(request);
		
		if(this.sessionUserHandler == null) {
			session.setAttribute(SessionKey.SYSTEM_USER, user);
		}else {
			boolean ba = this.sessionUserHandler.handle(session, user);
			if(ba) {
				session.setAttribute(SessionKey.SYSTEM_USER, user);
			}
		}
		
		if(this.userLoginListener != null) {
			this.userLoginListener.onLoginSuccess(request, user, session.getId());
		}
	}
	
	
	
	
	@RequestMapping("/ssoCallback")
	public String ssoCallback(HttpServletRequest request, HttpServletResponse response,
								String token, String beforeUrl) {
		BinaryUtils.checkEmpty(token, "token");
		
		HttpClient client = HttpClient.getInstance(this.ssoServerRoot);
		client.setRequestMethod("GET");
		client.addRequestProperty("REQUEST_HEADER", "binary-http-client-header");
		String userString = client.request("/user/auth/getUserByToken?tokenId="+token);
		
		User user = ControllerUtils.toRemoteJsonObject(userString, this.userType);
		
		if(user == null) {
			throw new SsoClientException(" is not found user by token '"+token+"'! ");
		}
		
		loginSession(request, user);
		
		String forward = null;
		
		//如果没有回跳连接
		if(BinaryUtils.isEmpty(beforeUrl)) {
			forward = "redirect:" + this.noBeforeUrl;
		}
		//如果有回跳连接
		else {
			if(beforeUrl.indexOf('/') < 0) {
				beforeUrl = URLResolver.decode(beforeUrl);
			}
			
			String strmodu = beforeUrl.substring(beforeUrl.lastIndexOf('/')+1,beforeUrl.length());
			if(!BinaryUtils.isEmpty(strmodu) ){
				if(strmodu.contains("?")){
					strmodu = strmodu.substring(0,strmodu.indexOf("?"));
				}
				
				if(beforeUrl.indexOf("/mi/") > 0){
					client = HttpClient.getInstance(this.ssoServerRoot);
					client.setRequestMethod("GET");
					client.addRequestProperty("REQUEST_HEADER", "binary-http-client-header");
					String verifyModuIdRetult = client.request("/external/operation/verifyModuId?opId="+user.getId()+"&moduId="+strmodu);
					if(!"true".equals(verifyModuIdRetult)){
						beforeUrl = this.noBeforeUrl;					
					}
				}
				
				if(beforeUrl.indexOf("/mc/") > 0){
					client = HttpClient.getInstance(this.ssoServerRoot);
					client.setRequestMethod("GET");
					client.addRequestProperty("REQUEST_HEADER", "binary-http-client-header");
					String verifyModuIdRetult = client.request("/external/operation/verifyModuCode?opId="+user.getId()+"&moduCode="+strmodu);
					if(!"true".equals(verifyModuIdRetult)){
						beforeUrl = this.noBeforeUrl;					
					}
				}
				
				
			}		
			
			
			
			forward = "redirect:" + beforeUrl;
		}
		
		return forward;
	}
	
	
	

}
