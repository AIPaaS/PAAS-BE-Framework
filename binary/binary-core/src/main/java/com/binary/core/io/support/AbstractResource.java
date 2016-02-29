package com.binary.core.io.support;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import com.binary.core.io.Resource;

public abstract class AbstractResource implements Resource {
	
	
	protected String cleanPath(String path) {
		return path.trim().replace('\\', '/');
	}
	
	
	protected void useCachesIfNecessary(URLConnection con) {
		con.setUseCaches(con.getClass().getName().startsWith("JNLP"));
	}
	
	
	public boolean isReadable() {
		if(!exists()) return false;
		
		InputStream is = getInputStream();
		if (is != null) {
			try {
				is.close();
			}catch (IOException ex) {
			}
			return true;
		}else {
			return false;
		}
	}
	
	
	public String getName() {
		String path = getPath();
		if(path!=null && path.length()>0) {
			if(path.indexOf('/') > -1) {
				path = path.substring(path.lastIndexOf('/')+1);
			}else if(path.indexOf('\\') > -1) {
				path = path.substring(path.lastIndexOf('\\')+1);
			}
		}
		return path;
	}
	
	
	
	public String toString() {
		return this.getPath();
	}
	
}
