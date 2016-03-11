package com.binary.framework.dubbo.rest;

import java.io.Serializable;
import java.net.InetAddress;

import org.apache.http.HttpHost;

public class RequestConfig implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private Boolean expectContinueEnabled;
    private HttpHost proxy;
    private InetAddress localAddress;
    private Boolean staleConnectionCheckEnabled;
    private String cookieSpec;
    private Boolean redirectsEnabled;
    private Boolean relativeRedirectsAllowed;
    private Boolean circularRedirectsAllowed;
    private Integer maxRedirects;
    private Boolean authenticationEnabled;
    private Integer connectionRequestTimeout;
    private Integer connectTimeout;
    private Integer socketTimeout;
    private Boolean contentCompressionEnabled;
    
    
    
    private Integer timeout;
    
    
	public Boolean getExpectContinueEnabled() {
		return expectContinueEnabled;
	}
	public void setExpectContinueEnabled(Boolean expectContinueEnabled) {
		this.expectContinueEnabled = expectContinueEnabled;
	}
	public HttpHost getProxy() {
		return proxy;
	}
	public void setProxy(HttpHost proxy) {
		this.proxy = proxy;
	}
	public InetAddress getLocalAddress() {
		return localAddress;
	}
	public void setLocalAddress(InetAddress localAddress) {
		this.localAddress = localAddress;
	}
	public Boolean getStaleConnectionCheckEnabled() {
		return staleConnectionCheckEnabled;
	}
	public void setStaleConnectionCheckEnabled(Boolean staleConnectionCheckEnabled) {
		this.staleConnectionCheckEnabled = staleConnectionCheckEnabled;
	}
	public String getCookieSpec() {
		return cookieSpec;
	}
	public void setCookieSpec(String cookieSpec) {
		this.cookieSpec = cookieSpec;
	}
	public Boolean getRedirectsEnabled() {
		return redirectsEnabled;
	}
	public void setRedirectsEnabled(Boolean redirectsEnabled) {
		this.redirectsEnabled = redirectsEnabled;
	}
	public Boolean getRelativeRedirectsAllowed() {
		return relativeRedirectsAllowed;
	}
	public void setRelativeRedirectsAllowed(Boolean relativeRedirectsAllowed) {
		this.relativeRedirectsAllowed = relativeRedirectsAllowed;
	}
	public Boolean getCircularRedirectsAllowed() {
		return circularRedirectsAllowed;
	}
	public void setCircularRedirectsAllowed(Boolean circularRedirectsAllowed) {
		this.circularRedirectsAllowed = circularRedirectsAllowed;
	}
	public Integer getMaxRedirects() {
		return maxRedirects;
	}
	public void setMaxRedirects(Integer maxRedirects) {
		this.maxRedirects = maxRedirects;
	}
	public Boolean getAuthenticationEnabled() {
		return authenticationEnabled;
	}
	public void setAuthenticationEnabled(Boolean authenticationEnabled) {
		this.authenticationEnabled = authenticationEnabled;
	}
	public Integer getConnectionRequestTimeout() {
		return connectionRequestTimeout;
	}
	public void setConnectionRequestTimeout(Integer connectionRequestTimeout) {
		this.connectionRequestTimeout = connectionRequestTimeout;
	}
	public Integer getConnectTimeout() {
		return connectTimeout;
	}
	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	public Integer getSocketTimeout() {
		return socketTimeout;
	}
	public void setSocketTimeout(Integer socketTimeout) {
		this.socketTimeout = socketTimeout;
	}
	public Boolean getContentCompressionEnabled() {
		return contentCompressionEnabled;
	}
	public void setContentCompressionEnabled(Boolean contentCompressionEnabled) {
		this.contentCompressionEnabled = contentCompressionEnabled;
	}
	public Integer getTimeout() {
		return timeout;
	}
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
	
    
    
    

}
