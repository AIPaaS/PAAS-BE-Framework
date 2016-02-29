package com.binary.sso.server.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.RequestMapping;

import com.binary.core.http.URLResolver;
import com.binary.core.io.support.ByteArrayResource;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.bean.User;
import com.binary.framework.util.ControllerUtils;
import com.binary.framework.util.UserUtils;
import com.binary.framework.web.SessionKey;
import com.binary.sso.server.Constant;
import com.binary.sso.server.SsoServerConfiguration;
import com.binary.sso.server.auth.bean.LoginResult;
import com.binary.sso.server.cache.SsoCache;
import com.binary.sso.server.exception.SsoException;
import com.binary.sso.server.exception.SsoVerifyException;
import com.binary.sso.server.session.Session;
import com.binary.sso.server.session.SessionManager;
import com.binary.sso.server.session.support.SsoHttpSessionManager;
import com.binary.sso.server.token.Token;
import com.binary.sso.server.token.TokenManager;
import com.binary.tools.captcha.Captcha;
import com.binary.tools.captcha.CaptchaImage;
import com.binary.tools.captcha.ImageType;



@RequestMapping("/user/auth")
public class UserAuthMvc implements ApplicationContextAware {
	private static Logger logger = LoggerFactory.getLogger(UserAuthMvc.class);

	
	private SsoServerConfiguration configuration;
	
	
	private boolean hasLoginCaptcha;
	private Captcha captcha;
	private SsoCache cache;
	private String loginUrl;
	private String noClientUrl;
	
	private UserLoginListener userLoginListener;
	private TokenManager tokenManager;
	
	private ApplicationContext springContext;
	
	
	
	public UserAuthMvc(SsoServerConfiguration configuration) {
		BinaryUtils.checkEmpty(configuration, "configuration");
		BinaryUtils.checkEmpty(configuration.getUserLoginListenerRef(), "configuration.userLoginListenerRef");
		this.cache = configuration.getSsoCache();
		BinaryUtils.checkEmpty(this.cache, "configuration.cache");
		BinaryUtils.checkEmpty(configuration.getLoginUrl(), "configuration.loginUrl");
		BinaryUtils.checkEmpty(configuration.getNoClientUrl(), "configuration.noClientUrl");
		
		this.configuration = configuration;
		this.hasLoginCaptcha = configuration.isLoginCaptcha();
		this.loginUrl = configuration.getLoginUrl();
		this.noClientUrl = configuration.getNoClientUrl();
	}
	
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.springContext = applicationContext;
	}
	
	
	
	@RequestMapping("/verify")
	public String verify(HttpServletRequest request, HttpServletResponse response,
							String beforeUrl, String callbackUrl) {
		if(!BinaryUtils.isEmpty(beforeUrl)) {
			if(beforeUrl.indexOf('/') < 0) beforeUrl = URLResolver.decode(beforeUrl);
		}
		if(!BinaryUtils.isEmpty(callbackUrl)) {
			if(callbackUrl.indexOf('/') < 0) callbackUrl = URLResolver.decode(callbackUrl);
		}
		
		Session session = getSession(request, true);
		String sessionId = session.getId();
		
		User user = (User)session.getAttribute(SessionKey.SYSTEM_USER);
		
		String forward = null;
		
		
		//如果用户已经登录
		if(user != null) {
			//如果没有回调地址
			if(BinaryUtils.isEmpty(callbackUrl)) {
				forward = "redirect:" + this.noClientUrl;
			}else {
				String token = this.tokenManager.newToken(user.getId(), sessionId).getId();
				String url = callbackUrl.trim();
				if(url.indexOf("?") < 0) url += "?1=1";
				url += "&token="+token+"&beforeUrl="+URLResolver.encode(beforeUrl);
				forward = "redirect:" + url;
			}
		}
		//如果用户没有登录, 则进入登录页面
		else {
			request.setAttribute(Constant.SSO_BEFORE_URL_KEY, beforeUrl);
			request.setAttribute(Constant.SSO_CALLBACK_URL_KEY, callbackUrl);
			request.setAttribute(Constant.SSO_NOCLIENT_URL_KEY, this.noClientUrl);
			forward = "forward:" + this.loginUrl;
		}
		
		return forward;
	}
	
	
	public void setCaptcha(Captcha captcha) {
		this.captcha = captcha;
	}
	public void setTokenManager(TokenManager tokenManager) {
		this.tokenManager = tokenManager;
	}


	private UserLoginListener getUserLoginListener() {
		if(userLoginListener == null) buildVerification();
		return userLoginListener;
	}
	
	private synchronized void buildVerification() {
		if(this.userLoginListener == null) {
			String ref = configuration.getUserLoginListenerRef().trim();
			this.userLoginListener = (UserLoginListener)this.springContext.getBean(ref);
			if(this.userLoginListener == null) {
				throw new SsoVerifyException("ERROR", " create "+UserLoginListener.class.getName()+" instance error! ");
			}
		}
	}
	
	
	@RequestMapping("/getCaptchaImage")
	public void getCaptchaImage(HttpServletRequest request, HttpServletResponse response) {
		if(this.captcha == null) throw new SsoException(" the captcha build tool is not setting! ");
		
		CaptchaImage image = this.captcha.drawImage();
		
		String code = image.getCode();
		byte[] data = image.getData();
		ImageType type = image.getType();
		
		HttpSession session = request.getSession(true);
		session.setAttribute(SessionKey.LOGIN_CAPTCHA, code);
		
		String imageType = type.getContentType();
		ControllerUtils.returnResource(request, response, new ByteArrayResource(data, null), imageType);
	}
	
	
	
	@RequestMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response,
								String loginCode, String password, String captchaCode) {
		LoginResult result = validLogin(request, response, loginCode, password, captchaCode);
		ControllerUtils.returnJson(request, response, result);
	}
	
	
	
	private LoginResult validLogin(HttpServletRequest request, HttpServletResponse response,
								String loginCode, String password, String captchaCode) {
		LoginResult result = new LoginResult();
		UserLoginListener listener = getUserLoginListener();
		
		try {
			if(loginCode==null || (loginCode=loginCode.trim()).length()==0) {
				result.setSuccess(false);
				result.setCode("LOGIN_LOGINCODE_EMPTY");
				result.setMessage("登录代码为空");
				return result;
			}
			if(BinaryUtils.isEmpty(password)) {
				result.setSuccess(false);
				result.setCode("LOGIN_PASSWORD_EMPTY");
				result.setMessage("登录密码为空");
				return result;
			}
			
			if(hasLoginCaptcha) {
				if(captchaCode==null || (captchaCode=captchaCode.trim()).length()==0) {
					result.setSuccess(false);
					result.setCode("LOGIN_CAPTCHA_CODE_EMPTY");
					result.setMessage("验证码为空");
					return result;
				}
				
				HttpSession session = request.getSession();
				String sessioncode = (String)session.getAttribute(SessionKey.LOGIN_CAPTCHA);
				if(sessioncode==null || (sessioncode=sessioncode.trim()).length()==0) {
					result.setSuccess(false);
					result.setCode("LOGIN_CAPTCHA_IMG_EMPTY");
					result.setMessage("验证码图片为空");
					return result;
				}
				String errorCode = null;
				if(!captchaCode.equalsIgnoreCase(sessioncode)) errorCode = "LOGIN_CAPTCHA_ERROR";
				session.removeAttribute(SessionKey.LOGIN_CAPTCHA);
				if(errorCode != null) {
					result.setSuccess(false);
					result.setCode(errorCode);
					result.setMessage("验证码输入错误");
					return result;
				}
			}
			
			User user = null;
			try {
				user = listener.verify(loginCode, password);
			}catch(SsoVerifyException e) {
				result.setSuccess(false);
				result.setCode(e.getErrorCode());
				result.setMessage(e.getMessage());
				return result;
			}
			
			if(user == null) {
				throw new SsoVerifyException("ERROR", " the verification '"+listener.getClass().getName()+"' verify user '"+loginCode+"' return value is empty! ");
			}
			
			//重置Session, 并将用户信息放入缓存当中
			Session session = newSession(request);
			String sid = session.getId();
			session.setAttribute(SessionKey.SYSTEM_USER, user);
			
			result.setSuccess(true);
			result.setCode("SUCCESS");
			result.setMessage("登录成功");
			
			listener.onLoginSuccess(request, response, user, session.getId());
			
			Token token = this.tokenManager.newToken(user.getId(), sid);
			result.setToken(token.getId());
		}catch(Exception e) {
			logger.error(" valid login error! ", e);
			result.setSuccess(false);
			result.setCode("ERROR");
			result.setMessage(e.getMessage());
			
			listener.onLoginFailed(request, response, loginCode, password, e);
		}
		
		return result;
	}

	
	
	
	private Session getSession(HttpServletRequest request, boolean create) {
		Session resession = null;
		
		HttpSession session = request.getSession(create);
		if(session != null) {
			SessionManager mgr = SsoHttpSessionManager.getInstance();
			resession = mgr.getSession(session.getId());
		}
		
		return resession;
	}
	
	
	
	private Session newSession(HttpServletRequest request) {
		Session session = getSession(request, false);
		
		if(session != null) {
			try {
				session.invalidate();
			}catch(Exception e) {
				logger.error("new-session error!", e);
			}
		}
		
		session = getSession(request, true);
		return session;
	}
	
	
	@RequestMapping("/getUserByToken")
	public void getUserByToken(HttpServletRequest request, HttpServletResponse response,
										String tokenId) {
		Token token = this.tokenManager.getToken(tokenId);
		if(token == null) throw new SsoException(" token is invalidate '"+tokenId+"'! ");
		
		String sessionId = token.getSessionId();
		User user = (User)cache.getSessionAttribute(sessionId, SessionKey.SYSTEM_USER);
		if(user == null) throw new SsoException(" is not found user by token '"+tokenId+"'! ");
		
		//SessionUser sessionUser = new SessionUser(sessionId, user);
		
		//user = UserUtils.toSimpleUser(user, true);
		
		ControllerUtils.returnJson(request, response, user);
	}



	
	
	
	@RequestMapping("/getLoginUser")
	public void getLoginUser(HttpServletRequest request, HttpServletResponse response) {
		User user = null;
		
		HttpSession session = request.getSession(false);
		if(session != null) {
			user = (User)session.getAttribute(SessionKey.SYSTEM_USER);
		}
		
		if(user != null) {
			user = UserUtils.toSimpleUser(user, true);
		}
		
		ControllerUtils.returnJson(request, response, user);
	}




	
	@RequestMapping("/invalidateSession")
	public String invalidateSession(HttpServletRequest request, HttpServletResponse response, String callbackUrl) {
		Session session = getSession(request, false);
		
		if(session != null) {
			try {
				String sessionId = session.getId();
				User user = (User)session.getAttribute(SessionKey.SYSTEM_USER);
				session.invalidate();
				
				if(user != null) {
					UserLoginListener listener = getUserLoginListener();
					listener.onLogout(request, response, user, sessionId);
				}
				
			}catch(Exception e) {
				logger.error("new-session error!", e);
			}
		}
		
		if(BinaryUtils.isEmpty(callbackUrl)) {
			request.setAttribute(Constant.SSO_NOCLIENT_URL_KEY, this.noClientUrl);
			return "redirect:" + this.loginUrl;
		}else {
			if(!BinaryUtils.isEmpty(callbackUrl)) {
				if(callbackUrl.indexOf('/') < 0) callbackUrl = URLResolver.decode(callbackUrl);
			}
			return "redirect:" + callbackUrl;
		}
	}
	
	
	
	
	
	
}



