package com.binary.core.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map.Entry;

import com.binary.core.bean.BMProxy;
import com.binary.core.exception.HttpException;
import com.binary.core.io.FileSystem;
import com.binary.core.io.Resource;
import com.binary.core.lang.Conver;
import com.binary.core.util.BinaryUtils;

public class HttpUtils {
	
	
	public static final String HP_START = "--";
	public static final String HP_BOUNDARY = "--|BINARYb6d428fe-7aa5-4ba1-9395-0abaf8c552e9BINARY|--";
	public static final String HP_END = "\r\n";
	
	
	private HttpUtils() {
	}
	
	
	/**
	 * 获取远程连接
	 * @param httpUrl
	 * @return
	 */
	public static HttpURLConnection getHttpConnection(String httpUrl, Proxy proxy) {
		try {
			URL url = new URL(httpUrl);
			URLConnection conn = proxy==null ? url.openConnection() : url.openConnection(proxy);
			if(!(conn instanceof HttpURLConnection)) throw new HttpException(" is not HttpUrl:'"+httpUrl+"'! ");
			HttpURLConnection httpConn = (HttpURLConnection)conn;
			return httpConn;
		}catch(Exception e) {
			throw BinaryUtils.transException(e, HttpException.class);
		}
	}
	
	
	
	/**
	 * 验证URL是否有效
	 * @param url
	 * @return
	 */
	public static boolean verifyUrl(String url) {
		return verifyUrl(url, null);
	}
	public static boolean verifyUrl(String url, Proxy proxy) {
		try {
			if(url==null || (url=url.trim()).length()==0) return false;
			
			HttpURLConnection conn = getHttpConnection(url, proxy);
			int code = conn.getResponseCode();
			return code == 200;
		}catch(Exception t) {
			return false;
		}
	}
	
	
	
	/**
	 * 键-值 拼成URL传参数方式:  &key=value
	 * @param key
	 * @param value
	 * @return
	 */
	public static StringBuffer parseURLParameter(String key, Object value) {
		return parseURLParameter(key, value, null);
	}
	
	
	/**
	 * 键-值 拼成URL传参数方式:  &key=value
	 * @param key
	 * @param value
	 * @param sb
	 * @return
	 */
	public static StringBuffer parseURLParameter(String key, Object value, StringBuffer sb) {
		if(sb == null) sb = new StringBuffer();
		if(value == null) return sb;
		Class<?> c = value.getClass();
		if(BinaryUtils.isArray(c)) {
			int length = BinaryUtils.getArrayLength(value);
			for(int i=0; i<length; i++) {
				Object rowv = BinaryUtils.getArrayValue(value, i);
				sb.append("&").append(key).append("=").append(Conver.to(rowv, String.class));
			}
		}else {
			sb.append("&").append(key).append("=").append(Conver.to(value, String.class));
		}
		return sb;
	}
	
	
	
	/**
	 * 将参数写入HttpConnection中
	 * @param httpos
	 * @param key
	 * @param value
	 * @param charset: 为空为utf-8
	 */
	public static void writeHttpConnParamenter(OutputStream httpos, String key, String value, String charset, StringBuffer sb) {
		try {
			if(value==null) value = "";
			if(charset==null || charset.length()==0) charset = "utf-8";
			sb.delete(0, sb.length());
			sb.append(HP_START).append(HP_BOUNDARY).append(HP_END)
				.append("Content-Disposition: form-data; name=\"").append(key).append("\"").append(HP_END)
				.append(HP_END)
				.append(value)
				.append(HP_END);
			httpos.write(sb.toString().getBytes(charset));
			
			
//			httpos.write((HP_START + HP_BOUNDARY + HP_END).getBytes(charset));
//			httpos.write(("Content-Disposition: form-data; name=\""+key+"\""+HP_END).getBytes(charset));   
//			httpos.write(HP_END.getBytes(charset));
//			httpos.write(value.getBytes(charset));
//			httpos.write(HP_END.getBytes(charset));
		}catch(Exception e) {
			throw BinaryUtils.transException(e, HttpException.class);
		}
	}
	
	
	/**
	 * 将参数写入HttpConnection中
	 * @param httpos
	 * @param key
	 * @param file
	 * @param charset: 为空为utf-8
	 */
	public static void writeHttpConnAttachFile(OutputStream httpos, String key, File file, String charset, StringBuffer sb) {
		InputStream is = null;
		try {
			is = FileSystem.newFileInputStream(file);
			writeHttpConnAttachFile(httpos, key, is, file.getName(), charset, sb);
		}finally {
			try {
				if(is != null) is.close();
			}catch(Exception e) {
				throw BinaryUtils.transException(e, HttpException.class);
			}
		}
	}
	
	
	/**
	 * 将参数写入HttpConnection中
	 * @param httpos
	 * @param key
	 * @param file
	 * @param charset: 为空为utf-8
	 */
	public static void writeHttpConnAttachResource(OutputStream httpos, String key, Resource file, String charset, StringBuffer sb) {
		InputStream is = null;
		try {
			is = file.getInputStream();
			writeHttpConnAttachFile(httpos, key, is, file.getName(), charset, sb);
		}finally {
			try {
				if(is != null) is.close();
			}catch(Exception e) {
				throw BinaryUtils.transException(e, HttpException.class);
			}
		}
	}
	
	
	/**
	 * 将参数写入HttpConnection中
	 * @param httpos
	 * @param key
	 * @param fileis
	 * @param filename
	 * @param charset: 为空为utf-8
	 */
	public static void writeHttpConnAttachFile(OutputStream httpos, String key, InputStream fileis, String filename, String charset, StringBuffer sb) {
		try {
			if(!(httpos instanceof BufferedOutputStream)) {
				httpos = new BufferedOutputStream(httpos);
			}
			
			if(charset==null || charset.length()==0) charset = "utf-8";
			sb.delete(0, sb.length());
			sb.append(HP_START).append(HP_BOUNDARY).append(HP_END)
				.append("Content-Disposition: form-data; name=\"").append(key).append("\"; filename=\"").append(filename).append("\"").append(HP_END)
				.append(HP_END);
			httpos.write(sb.toString().getBytes(charset));
			
//			httpos.write((HP_START + HP_BOUNDARY + HP_END).getBytes(charset));
//			httpos.write(("Content-Disposition: form-data; name=\""+key+"\"; filename=\""+filename+"\""+HP_END).getBytes(charset)); 
//			httpos.write(HP_END.getBytes(charset));
//			try {
				if(!(fileis instanceof BufferedInputStream)) {
					fileis = new BufferedInputStream(fileis);
				}
//				fileis = (fileis instanceof BufferedInputStream) ? fileis : new BufferedInputStream(fileis);
				int n = -1;
				while((n=fileis.read()) != -1) httpos.write(n);
//			}finally {
//				fileis.close();
//			}
			httpos.write(HP_END.getBytes(charset));
		}catch(Exception e) {
			throw BinaryUtils.transException(e, HttpException.class);
		}
	}
	
	
	/**
	 * 判断值是否是附件
	 * @param value
	 * @return
	 */
	public static boolean isAttachFile(Object value) {
		return value!=null && (value instanceof File) || (value instanceof InputStream) || (value instanceof Resource);
	}
	
	
	
	public static void parseHttpConnFormParamenter(HttpURLConnection httpConn, Object params, String charset) {
		if(params==null) return ;
		if(charset==null || charset.length()==0) charset = "utf-8";
		try {
			httpConn.setDoOutput(true);
			httpConn.setUseCaches(false);
			httpConn.setRequestProperty("Charsert", charset);
			BMProxy<?> proxy = BMProxy.getInstance(params);
			int length = proxy.getPorpertyCount();
			String[] keys = new String[length];
			Object[] values = new Object[length];
			boolean hasattach = false;
			Iterator<Entry<String,Object>> iterator = proxy.entryIterator();
			for(int i=0; iterator.hasNext(); i++) {
				Entry<String,Object> e = iterator.next();
				keys[i] = e.getKey();
				values[i] = e.getValue();
				if(!hasattach && isAttachFile(values[i])) hasattach=true;
			}
			
			OutputStream os = null;
			try {
				StringBuffer sb = new StringBuffer();
				if(hasattach) {
					httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + HP_BOUNDARY); 
					os = new BufferedOutputStream(httpConn.getOutputStream());
					
					for(int i=0; i<length; i++) {
						if(values[i] == null) continue ;
						if(isAttachFile(values[i])) {
							if(values[i] instanceof File) {
								writeHttpConnAttachFile(os, keys[i], (File)values[i], charset, sb);
							}else if(values[i] instanceof InputStream) {
								writeHttpConnAttachFile(os, keys[i], (InputStream)values[i], "isfile", charset, sb);
							}else if(values[i] instanceof Resource) {
								writeHttpConnAttachResource(os, keys[i], (Resource)values[i], charset, sb);
							}
						}else {
							boolean isarray = BinaryUtils.isArray(values[i].getClass());
							if(isarray) {
								int arrlen = BinaryUtils.getArrayLength(values[i]);
								for(int j=0; j<arrlen; j++) {
									Object v = BinaryUtils.getArrayValue(values[i], j);
									writeHttpConnParamenter(os, keys[i], Conver.to(v, String.class), charset, sb);
								}
							}else {
								writeHttpConnParamenter(os, keys[i], Conver.to(values[i], String.class), charset, sb);
							}	
						}
					}
					
					os.write((HP_START + HP_BOUNDARY + HP_START + HP_END).getBytes(charset));  
				}else {
					httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset="+charset);
					os = new BufferedOutputStream(httpConn.getOutputStream());
					for(int i=0; i<length; i++) {
						if(values[i] == null) continue ;
						parseURLParameter(keys[i], values[i], sb);
					}
					if(sb.length()>0) sb.deleteCharAt(0);
					os.write(sb.toString().getBytes(charset));
				}
			}finally {
				if(os != null) os.close();
			}
		}catch(Exception e) {
			throw BinaryUtils.transException(e, HttpException.class);
		}
	}
	
	
	
	
	/**
	 * 格式化ContextPath
	 * @param contextPath
	 * @return
	 */
	public static String formatContextPath(String contextPath) {
		if(contextPath!=null && (contextPath=contextPath.trim()).length()>0) {
			char c = contextPath.charAt(0);
			if(c != '/') contextPath = "/" + contextPath;
			while(contextPath.length()>1 && contextPath.charAt(contextPath.length()-1)=='/') contextPath = contextPath.substring(0, contextPath.length()-1);
		}else {
			contextPath = "";
		}
		return contextPath;
	}
	
	

	
	
}
