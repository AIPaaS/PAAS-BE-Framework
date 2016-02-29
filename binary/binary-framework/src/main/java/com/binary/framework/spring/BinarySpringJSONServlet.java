package com.binary.framework.spring;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.NestedServletException;
import org.springframework.web.util.UrlPathHelper;

import com.binary.framework.Application;
import com.binary.framework.Local;
import com.binary.framework.bean.User;
import com.binary.framework.exception.FrameworkException;
import com.binary.framework.exception.ServiceException;
import com.binary.framework.web.RemoteResult;
import com.binary.json.JSON;



@SuppressWarnings("serial")
public class BinarySpringJSONServlet extends DispatcherServlet {
	
	
	
	private static final UrlPathHelper urlPathHelper = new UrlPathHelper();
	
	
	
	public BinarySpringJSONServlet() {
		super();
	}
	public BinarySpringJSONServlet(WebApplicationContext webApplicationContext) {
		super(webApplicationContext);
	}
	
	
	
	@Override
	protected void initFrameworkServlet() throws ServletException {
		super.initFrameworkServlet();
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
	
	
	
	@SuppressWarnings("deprecation")
	protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpServletRequest processedRequest = request;
		HandlerExecutionChain mappedHandler = null;
		int interceptorIndex = -1;

		try {
			Exception throwexp = null;
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
					ha.handle(processedRequest, response, mappedHandler.getHandler());
					
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
				

				// Apply postHandle methods of registered interceptors.
				if (interceptors != null) {
					for (int i = interceptors.length - 1; i >= 0; i--) {
						HandlerInterceptor interceptor = interceptors[i];
						interceptor.postHandle(processedRequest, response, mappedHandler.getHandler(), null);
					}
				}
			}
			catch (Exception ex) {
				logger.error("do spring-mvc error! ", ex);
				throwexp = ex;
				//Object handler = (mappedHandler != null ? mappedHandler.getHandler() : null);
				//mv = processHandlerException(processedRequest, response, handler, ex);
				//errorView = (mv != null);
			}
			
			if(throwexp != null) {
				RemoteResult rs = new RemoteResult(throwexp);
				try {
					PrintWriter pw = response.getWriter();
					pw.write(JSON.toString(rs));
					pw.flush();
				}catch(IOException e) {
					throw new ServiceException(e);
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
	


}
