package com.binary.core.http;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

import com.binary.core.exception.HttpException;
import com.binary.core.io.FileSystem;
import com.binary.core.util.BinaryUtils;
import com.binary.core.util.SecurityEntryIterator;

public class HttpClient {
	
	
	private String uri;		//http://www.google.com
	private String charset = "UTF-8";
	
	private String requestMethod = "POST";
	private String sessionid;
	
	private Integer connectTimeout;
	private Integer readTimeout;
	
	private final Map<String, String> requestPropertys = new HashMap<String, String>();
	
	private Proxy proxy;

	
	private HttpClient(String uri) {
		this.uri = uri;
	}
	
	/**
	 * 获取实例
	 * @param uri
	 * @return
	 */
	public static HttpClient getInstance(String uri) {
		return new HttpClient(uri);
	}
	
	
	/**
	 * 获取URI
	 * @return
	 */
	public String getURI() {
		return uri;
	}
	
	
	/**
	 * 设置URI
	 * @param uri
	 */
	public void setURI(String uri) {
		this.uri = uri;
	}

	/**
	 * 获取字符集
	 * @return
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * 设置当前字符集
	 * @param charset
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}
	
	
	/**
	 * 设置代理
	 * @param proxyIp
	 * @param proxyPort
	 */
	public void setProxy(String proxyIp, Integer proxyPort) {
		this.proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyIp, proxyPort));  
	}
	
	
	/**
	 * 获取当前SessionID
	 * @return
	 */
	public String getSessionId() {
		return sessionid;
	}
	
	
	/**
	 * 注销当前Session
	 */
	public void invalidate() {
		this.sessionid = null;
	}
	
	
	
	
	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
	

	public Integer getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public Integer getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(Integer readTimeout) {
		this.readTimeout = readTimeout;
	}
	
	
	
	/**
	 * 添加request-property
	 * @param key
	 * @param value
	 */
	public void addRequestProperty(String key, String value) {
		this.requestPropertys.put(key, value);
	}
	public void removeRequestProperty(String key) {
		this.requestPropertys.remove(key);
	}
	public String getRequestProperty(String key) {
		return this.requestPropertys.get(key);
	}
	public void clearRequestProperty() {
		this.requestPropertys.clear();
	}
	public Iterator<Entry<String,String>> getRequestPropertyEntryIterator() {
		return new SecurityEntryIterator<String, String>(this.requestPropertys.entrySet().iterator());
	}
	
	
	public void setUserAgent(String userAgent) {
		this.requestPropertys.put("User-Agent", userAgent);
	}
	public String getUserAgent() {
		return this.requestPropertys.get("User-Agent");
	}
	public void setReferer(String referer) {
		this.requestPropertys.put("Referer", referer);
	}
	public String getReferer() {
		return this.requestPropertys.get("Referer");
	}
	
	public void setCookie(String cookie) {
		this.requestPropertys.put("Cookie", cookie);
	}
	public String getCookie() {
		return this.requestPropertys.get("Cookie");
	}
	
	
	/**
	 * 访问远程服务器
	 * @return
	 */
	public String request() {
		return request((String)null, (Object)null);
	}
	
	/**
	 * 访问远程服务器
	 * @param formbean: 表单对象, 也可以是一个Map
	 * @return
	 */
	public String request(Object formbean) {
		return request((String)null, (Object)formbean);
	}
	
	/**
	 * 访问远程服务器
	 * @param urlparams: 拼在uri后面字符, 构成远程url
	 * @return
	 */
	public String request(String urlparams) {
		return request(urlparams, (Object)null);
	}
	
	/**
	 * 访问远程服务器
	 * @param urlparams: 拼在uri后面字符, 构成远程url
	 * @param formbean: 表单对象, 也可以是一个Map
	 * @return
	 */
	public String request(String urlparams, Object formbean) {
		HttpURLConnection httpConn = null;
		try {
			String url = getHttpURL(urlparams);
			httpConn = HttpUtils.getHttpConnection(url, this.proxy);
			request(httpConn, formbean);
			InputStream is = httpConn.getInputStream();
			
			String encoding = httpConn.getHeaderField("Content-Encoding");
			if(encoding!=null && encoding.equalsIgnoreCase("gzip")) {
				is = new GZIPInputStream(is);
			}
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			FileSystem.copy(is, baos);
			return baos.toString(this.charset);
		}catch(Exception e) {
			throw BinaryUtils.transException(e, HttpException.class);
		}finally {
			if(httpConn!=null) httpConn.disconnect();
		}
	}
	
	
	private String getHttpURL(String urlparams) {
		String url = this.uri;
		if(urlparams != null) url += urlparams;
		return url;
	}
	
	
	
	private void setHttpConnectionPropertys(HttpURLConnection httpConn) {
		try {
			httpConn.setRequestMethod(this.requestMethod);
			
			if(this.connectTimeout != null) httpConn.setConnectTimeout(this.connectTimeout);
			if(this.readTimeout != null) httpConn.setReadTimeout(this.readTimeout);
			
			if(this.requestPropertys.size() > 0) {
				Iterator<Entry<String, String>> itor = this.requestPropertys.entrySet().iterator();
				while(itor.hasNext()) {
					Entry<String, String> e = itor.next();
					httpConn.setRequestProperty(e.getKey(), e.getValue());
				}
			}
		} catch (Exception e) {
			throw BinaryUtils.transException(e, HttpException.class);
		}
	}
	
	
	public void request(HttpURLConnection httpConn, Object formbean) {
		try {
			setHttpConnectionPropertys(httpConn);
			if(this.sessionid != null) httpConn.setRequestProperty("Cookie", "JSESSIONID="+this.sessionid); 
			
			if(formbean != null) HttpUtils.parseHttpConnFormParamenter(httpConn, formbean, this.charset);
			
			int code = httpConn.getResponseCode();
			if(code>=200 && code<300) {
			}else if(code>=400 && code<500) {
				throw new HttpException("ClientError-"+code+"："+httpConn.getResponseMessage());
			}else if(code>=500 && code<600) {
				throw new HttpException("ServerError-"+code+"："+httpConn.getResponseMessage());
			}else {
				throw new HttpException("Can not be analyzed by ResponseCode:'"+code+"'!");
			}
			
			String cookie = httpConn.getHeaderField("Set-Cookie");
			if(cookie!=null && cookie.length()>0) {
				int index = 0;
				if((index=cookie.indexOf(";")) > 0) cookie = cookie.substring(0, index);
				if((index=cookie.indexOf("=")) > 0) {
					String key = cookie.substring(0, index);
					String value = cookie.substring(index+1);
					if(key.equalsIgnoreCase("JSESSIONID") && (value=value.trim()).length()>0) {
						this.sessionid = value;
					}
				}
			}
		}catch(Exception e) {
			throw BinaryUtils.transException(e, HttpException.class);
		}
	}
	
	
	/**
	 * @see download(String urlparams, Object formbean, File output)
	 */
	public void download(File output) {
		download(null, null, output);
	}
	
	
	/**
	 * @see download(String urlparams, Object formbean, OutputStream output)
	 */
	public void download(OutputStream output) {
		download(null, null, output);
	}
	
	
	/**
	 * @see download(String urlparams, Object formbean, File output)
	 */
	public void download(String urlparams, File output) {
		download(urlparams, null, output);
	}
	
	
	/**
	 * @see download(String urlparams, Object formbean, OutputStream output)
	 */
	public void download(String urlparams, OutputStream output) {
		download(urlparams, null, output);
	}
	
	
	/**
	 * @see download(String urlparams, Object formbean, File output)
	 */
	public void download(Object formbean, File output) {
		download(null, formbean, output);
	}
	
	/**
	 * @see download(String urlparams, Object formbean, OutputStream output)
	 */
	public void download(Object formbean, OutputStream output) {
		download(null, formbean, output);
	}
	
	
	/**
	 * 下载远程文件
	 * @param urlparams: 拼在uri后面字符, 构成远程url
	 * @param formbean: 表单对象, 也可以是一个Map
	 * @param output: 指定保存文件位置
	 */
	public void download(String urlparams, Object formbean, File output) {
		FileOutputStream os = null;
		try {
			os = FileSystem.newFileOutputStream(output);
			download(urlparams, formbean, os);
		}finally {
			try {
				if(os != null) os.close();
			} catch (IOException e) {
				throw new HttpException(e);
			}
		}
		
	}
	
	
	/**
	 * 下载远程文件
	 * @param urlparams: 拼在uri后面字符, 构成远程url
	 * @param formbean: 表单对象, 也可以是一个Map
	 * @param output: 指定保存文件流
	 */
	public void download(String urlparams, Object formbean, OutputStream output) {
		HttpURLConnection httpConn = null;
		try {
			String url = getHttpURL(urlparams);
			httpConn = HttpUtils.getHttpConnection(url, this.proxy);
			request(httpConn, formbean);
			InputStream is = httpConn.getInputStream();
			FileSystem.copy(is, output);
		}catch(Exception e) {
			throw BinaryUtils.transException(e, HttpException.class);
		}finally {
			if(httpConn!=null) httpConn.disconnect();
		}
	}
	
	
	
	
}
