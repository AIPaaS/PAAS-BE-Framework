package com.binary.core.io.support;

import java.io.InputStream;
import java.net.URL;

import com.binary.core.exception.ResourceException;
import com.binary.core.lang.ClassUtils;

public class ClassPathResource extends AbstractResource {
	
	
	private String path;
	private ClassLoader classLoader;
	private Class<?> clazz;
	
	
	public ClassPathResource(String path) {
		this(path, (ClassLoader)null, null);
	}
	
	
	public ClassPathResource(String path, ClassLoader classLoader) {
		this(path, classLoader, null);
	}
	
	
	public ClassPathResource(String path, Class<?> clazz) {
		this(path, null, clazz);
	}
	
	
	protected ClassPathResource(String path, ClassLoader classLoader, Class<?> clazz) {
		if(path == null) throw new ResourceException(" the path is NULL argument! ");
		path = cleanPath(path);
		if(path.length()>0 && path.charAt(0)=='/') {
			path = path.substring(1);
		}
		this.path = path;
		this.clazz = clazz;
		this.classLoader = classLoader;
	}
	
	private ClassLoader getClassLoader() {
		if(this.classLoader == null) {
			return clazz==null ? ClassUtils.getClassLoader() : clazz.getClassLoader();
		}
		return this.classLoader;
	}
	
	private URL getURL(boolean valid) {
		URL url = null;
		if (this.clazz != null) {
			url = this.clazz.getResource(this.path);
		}else {
			url = this.getClassLoader().getResource(this.path);
		}
		if (url==null && valid) {
			throw new ResourceException(" is not found resource:'classpath:"+this.path+"'! ");
		}
		return url;
	}
	
	
	public URL getURL() {
		return getURL(true);
	}
	
	public boolean exists() {
		return getURL(false) != null;
	}
	
	
	public String getPath() {
		return this.path;
	}

	

	public InputStream getInputStream() {
		InputStream is = null;
		if (this.clazz != null) {
			is = this.clazz.getResourceAsStream(this.path);
		}else {
			is = this.getClassLoader().getResourceAsStream(this.path);
		}
		if (is == null) {
			throw new ResourceException(" is not found resource:'classpath:"+this.path+"'! ");
		}
		return is;
	}

}
