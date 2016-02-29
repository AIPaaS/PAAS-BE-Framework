package com.binary.core.http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;

import com.binary.core.exception.HttpException;
import com.binary.core.util.BinaryUtils;



/**
 * URL解析器
 * @author wanwb
 */
public abstract class URLResolver {
	
	
	private static final Set<String> URL_SUFFIXS = new HashSet<String>();
	
	
	static {
		URL_SUFFIXS.add("http://");
		URL_SUFFIXS.add("https://");
		URL_SUFFIXS.add("ftp://");
		URL_SUFFIXS.add("file://");
	}
	
	
	
	
	/**
	 * 将url转换成HTML格式编码, 编码字符集为UTF-8
	 * @param url
	 * @return
	 */
	public static String encode(String url) {
		return encode(url, null);
	}
	
	
	
	/**
	 * 将url转换成HTML格式编码
	 * @param url
	 * @param charset : 指定编辑字符集, 缺省为utf-8
	 * @return
	 */
	public static String encode(String url, String charset) {
		BinaryUtils.checkNull(url, "url");
		if(BinaryUtils.isEmpty(charset)) charset = "UTF-8";
		try {
			return URLEncoder.encode(url, charset);
		} catch (UnsupportedEncodingException e) {
			throw new HttpException(e);
		}
	}
	
	
	
	
	/**
	 * 将HTML格式编码转换成普通字符串, 编码字符集为UTF-8
	 * @param url
	 * @return
	 */
	public static String decode(String url) {
		return decode(url, null);
	}
	
	
	
	/**
	 * 将HTML格式编码转换成普通字符串
	 * @param url
	 * @param charset : 指定编辑字符集, 缺省为utf-8
	 * @return
	 */
	public static String decode(String url, String charset) {
		BinaryUtils.checkNull(url, "url");
		if(BinaryUtils.isEmpty(charset)) charset = "UTF-8";
		try {
			return URLDecoder.decode(url, charset);
		} catch (UnsupportedEncodingException e) {
			throw new HttpException(e);
		}
	}
	
	
	
	
	
	/**
	 * 矫正ContextPath格式
	 * @param contextPath
	 * @return
	 */
	public static String correctContextPath(String contextPath) {
		if(contextPath==null || (contextPath=contextPath.trim()).length()==0) {
			return "/";
		}
		
		if(contextPath.equals("/")) return contextPath;
		
		char c = contextPath.charAt(contextPath.length()-1);
		while(c=='/' || c=='\\') {
			contextPath = contextPath.substring(0, contextPath.length()-1);
			if(contextPath.length()==0) break;
			c = contextPath.charAt(contextPath.length()-1);
		}
		
		if(contextPath.length() == 0) {
			return "/";
		}else {
			c = contextPath.charAt(0);
			if(c != '/') contextPath = '/' + contextPath;
		}
		
		return contextPath;
	}
	
	
	
	
	
	/**
	 * 判断path是否是一个url
	 * @param url
	 * @return
	 */
	public static boolean isURL(String path) {
		if(path==null || (path=path.trim()).length()==0) {
			return false;
		}
		
		int index = path.indexOf('/');
		if(index>0 && index<path.length()-2) {
			String suffix = path.substring(0, index+2).toLowerCase();
			return URL_SUFFIXS.contains(suffix);
		}
		
		return false;
	}
	
	
	
	
	
	

}




