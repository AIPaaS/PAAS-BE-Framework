package com.binary.framework.spring;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.support.HandlerMethodInvocationException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.NestedServletException;
import org.springframework.web.util.UrlPathHelper;
import org.springframework.web.util.WebUtils;

import com.alibaba.dubbo.remoting.RemotingException;
import com.alibaba.dubbo.rpc.RpcException;
import com.binary.core.exception.BinaryException;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.Application;
import com.binary.framework.Local;
import com.binary.framework.bean.User;
import com.binary.framework.exception.FrameworkException;
import com.binary.framework.exception.ServiceException;
import com.binary.framework.util.ControllerUtils;
import com.binary.framework.web.RemoteResult;
import com.binary.json.JSON;



@SuppressWarnings("serial")
public class BinarySpringServlet extends DispatcherServlet {
	
	
	
	private static final UrlPathHelper urlPathHelper = new UrlPathHelper();
	
	private Map<String, String> resultRequestHeaderMap;
	
	
	public BinarySpringServlet() {
		super();
	}
	public BinarySpringServlet(WebApplicationContext webApplicationContext) {
		super(webApplicationContext);
	}
	
	
	
	@Override
	protected void initFrameworkServlet() throws ServletException {
		super.initFrameworkServlet();
		String strResultRequestHeader = getInitParameter("result-request-header");
		if(strResultRequestHeader!=null && (strResultRequestHeader=strResultRequestHeader.trim()).length()>0) {
			String[] arr = ControllerUtils.splitStringPattern(strResultRequestHeader);
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
		
		try {
			Local.open((User)null);
			Application.afterInitialization(getWebApplicationContext());
			
			try {
				Local.commit();
			}catch(Exception ex) {
				logger.error("Local.commit()出现错误!", ex);
			}
		}catch(Exception e) {
			try {
				Local.rollback();
			}catch(Exception ex) {
				logger.error("Local.rollback()出现错误!", ex);
			}
			
			throw new FrameworkException(e);
		}finally {
			try {
				Local.close();
			}catch(Exception ex) {
				logger.error("Local.close()出现错误!", ex);
			}
		}	
	}
	
	
	
	@SuppressWarnings("unchecked")
	private boolean isJsonResult(HttpServletRequest request) {
		boolean exists = false;
		if(this.resultRequestHeaderMap != null) {
			Enumeration<String> enu = request.getHeaderNames();
			if(enu != null) {
				while(enu.hasMoreElements()) {
					String name = enu.nextElement();
					String value = request.getHeader(name);
					if(name==null || value==null) continue ;
					
					String s = this.resultRequestHeaderMap.get(name.trim().toUpperCase());
					exists = s!=null && s.equalsIgnoreCase(value.trim());
					if(exists) break;
				}
			}
		}
		
		return exists;
	}
	
	
	
	private Throwable getRealThrowable(Throwable t) {
		if(t instanceof HandlerMethodInvocationException) {
			HandlerMethodInvocationException tx = (HandlerMethodInvocationException)t;
			Throwable root = tx.getRootCause();
			if(root != null) {
				return getRealThrowable(root);
			}
		}else if(t instanceof BinaryException) {
			BinaryException tx = (BinaryException)t;
			Throwable top = tx.getOriginalThrowable();
			if(top!=null && tx!=top) {
				return getRealThrowable(top);
			}
		}else if(t instanceof RpcException) {
			RpcException tx = (RpcException)t;
			return getRealThrowable(tx.getCause());
		}else if(t instanceof RemotingException) {
			RemotingException tx = (RemotingException)t;
			String msg = tx.getMessage();
			if(!BinaryUtils.isEmpty(msg)) {
				msg = msg.substring(0, msg.indexOf("\n"));
				msg = msg.substring(msg.indexOf(":")+1).trim();
				return new ServiceException(msg);
			}
		}else {
			Throwable tx = t.getCause();
			if(tx!=null && tx!=t) {
				return getRealThrowable(tx);
			}
		}
		return BinaryUtils.transException(t, ServiceException.class).getOriginalThrowable();
	}
	
	
	@SuppressWarnings("deprecation")
	protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpServletRequest processedRequest = request;
		HandlerExecutionChain mappedHandler = null;
		int interceptorIndex = -1;
		
		try {
			Exception throwexp = null;
			ModelAndView mv = null;
			boolean errorView = false;
			boolean isjson = isJsonResult(request);

			try {
				processedRequest = checkMultipart(request);

				// Determine handler for the current request.
				mappedHandler = getHandler(processedRequest, false);
				if (mappedHandler == null || mappedHandler.getHandler() == null) {
					noHandlerFound(processedRequest, response);
					return;
				}

				// Determine handler adapter for the current request.
				HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());

                // Process last-modified header, if supported by the handler.
				String method = request.getMethod();
				boolean isGet = "GET".equals(method);
				if (isGet || "HEAD".equals(method)) {
					long lastModified = ha.getLastModified(request, mappedHandler.getHandler());
					if (logger.isDebugEnabled()) {
						String requestUri = urlPathHelper.getRequestUri(request);
						logger.debug("Last-Modified value for [" + requestUri + "] is: " + lastModified);
					}
					if (new ServletWebRequest(request, response).checkNotModified(lastModified) && isGet) {
						return;
					}
				}

				// Apply preHandle methods of registered interceptors.
				HandlerInterceptor[] interceptors = mappedHandler.getInterceptors();
				if (interceptors != null) {
					for (int i = 0; i < interceptors.length; i++) {
						HandlerInterceptor interceptor = interceptors[i];
						if (!interceptor.preHandle(processedRequest, response, mappedHandler.getHandler())) {
							triggerAfterCompletion(mappedHandler, interceptorIndex, processedRequest, response, null);
							return;
						}
						interceptorIndex = i;
					}
				}
				
				
				
				// Actually invoke the handler.
				try {
					Local.open(request, response);
					mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
					
					try {
						Local.commit();
					}catch(Exception ex) {
						logger.error("Local.commit()出现错误!", ex);
					}
				}catch(Exception e) {
					try {
						Local.rollback();
					}catch(Exception ex) {
						logger.error("Local.rollback()出现错误!", ex);
					}
					throw e;
				}finally {
					try {
						Local.close();
					}catch(Exception ex) {
						logger.error("Local.close()出现错误!", ex);
					}
				}
				

				// Do we need view name translation?
				if (mv != null && !mv.hasView()) {
					mv.setViewName(getDefaultViewName(request));
				}

				// Apply postHandle methods of registered interceptors.
				if (interceptors != null) {
					for (int i = interceptors.length - 1; i >= 0; i--) {
						HandlerInterceptor interceptor = interceptors[i];
						interceptor.postHandle(processedRequest, response, mappedHandler.getHandler(), mv);
					}
				}
			}
			catch (ModelAndViewDefiningException ex) {
				logger.debug("ModelAndViewDefiningException encountered", ex);
				mv = ex.getModelAndView();
				throwexp = ex;
			}
			catch (Exception ex) {
				if(!isjson) {
					Object handler = (mappedHandler != null ? mappedHandler.getHandler() : null);
					mv = processHandlerException(processedRequest, response, handler, ex);
					errorView = (mv != null);
				}
				throwexp = ex;
			}
			
			if(throwexp != null) {
				logger.error(" mvc process error! ", throwexp);
			}
			
			if(isjson) {
				if(throwexp != null) {
					RemoteResult rs = new RemoteResult(getRealThrowable(throwexp));
					try {
						PrintWriter pw = response.getWriter();
						pw.write(JSON.toString(rs));
						pw.flush();
					}catch(IOException e) {
						throw new ServiceException(e);
					}
				}
			}else {
				// Did the handler return a view to render?
				if (mv != null && !mv.wasCleared()) {
					render(mv, processedRequest, response);
					if (errorView) {
						WebUtils.clearErrorRequestAttributes(request);
					}
				}
				else {
					if (logger.isDebugEnabled()) {
						logger.debug("Null ModelAndView returned to DispatcherServlet with name '" + getServletName() +
								"': assuming HandlerAdapter completed request handling");
					}
				}
			}
			
			// Trigger after-completion for successful outcome.
			triggerAfterCompletion(mappedHandler, interceptorIndex, processedRequest, response, null);
		}

		catch (Exception ex) {
			// Trigger after-completion for thrown exception.
			triggerAfterCompletion(mappedHandler, interceptorIndex, processedRequest, response, ex);
			throw ex;
		}
		catch (Error err) {
			ServletException ex = new NestedServletException("Handler processing failed", err);
			// Trigger after-completion for thrown exception.
			triggerAfterCompletion(mappedHandler, interceptorIndex, processedRequest, response, ex);
			throw ex;
		}

		finally {
			// Clean up any resources used by a multipart request.
			if (processedRequest != request) {
				cleanupMultipart(processedRequest);
			}
		}
	}
	
	
	
	
	private void triggerAfterCompletion(HandlerExecutionChain mappedHandler,
			int interceptorIndex,
			HttpServletRequest request,
			HttpServletResponse response,
			Exception ex) throws Exception {

		// Apply afterCompletion methods of registered interceptors.
		if (mappedHandler != null) {
			HandlerInterceptor[] interceptors = mappedHandler.getInterceptors();
			if (interceptors != null) {
				for (int i = interceptorIndex; i >= 0; i--) {
					HandlerInterceptor interceptor = interceptors[i];
					try {
						interceptor.afterCompletion(request, response, mappedHandler.getHandler(), ex);
					}
					catch (Throwable ex2) {
						logger.error("HandlerInterceptor.afterCompletion threw exception", ex2);
					}
				}
			}
		}
	}
	

	
	@Override
	protected View resolveViewName(String viewName, Map<String, Object> model, Locale locale, HttpServletRequest request) throws Exception {
		View view = super.resolveViewName(viewName, model, locale, request);
		
		if(view!=null && view instanceof RedirectView) {
			view = new BinaryRedirectView((RedirectView)view);
		}
		return view;
	}
	
	

}
