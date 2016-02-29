package com.binary.core.io.support;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import com.binary.core.exception.ResourceException;
import com.binary.core.util.BinaryUtils;

public class URLResource extends AbstractResource {
	
	
	private URL url;
	
	
	
	public URLResource(URI uri) {
		if(uri == null) throw new ResourceException(" the uri is NULL argument! ");
		try {
			this.url = uri.toURL();
		} catch (MalformedURLException e) {
			throw new ResourceException(e);
		}
	}
	
	
	public URLResource(URL url) {
		if(url == null) throw new ResourceException(" the url is NULL argument! ");
		this.url = url;
	}
	
	
	public URLResource(String path) {
		if(path == null) throw new ResourceException(" the path is NULL argument! ");
		try {
			this.url = new URL(path);
		} catch (MalformedURLException e) {
			throw new ResourceException(e);
		}
	}
	
	
	public boolean exists() {
		try {
			URLConnection con = url.openConnection();
			useCachesIfNecessary(con);
			HttpURLConnection httpCon =	(con instanceof HttpURLConnection ? (HttpURLConnection) con : null);
			if (httpCon != null) {
				httpCon.setRequestMethod("HEAD");
				int code = httpCon.getResponseCode();
				if (code == HttpURLConnection.HTTP_OK) {
					return true;
				}else if (code == HttpURLConnection.HTTP_NOT_FOUND) {
					return false;
				}
			}
			if (con.getContentLength() >= 0) {
				return true;
			}
			if (httpCon != null) {
				httpCon.disconnect();
				return false;
			}else {
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
		}
		catch (IOException ex) {
			return false;
		}
	}
	
	
	
	public boolean isReadable() {
		return exists();
	}

	
	public String getPath() {
		return this.url.toString();
	}
	
	
	public URL getURL() {
		return this.url;
	}
	
	

	public InputStream getInputStream() {
		try {
			URLConnection con = this.url.openConnection();
			useCachesIfNecessary(con);
			try {
				return con.getInputStream();
			}catch (IOException ex) {
				if (con instanceof HttpURLConnection) {
					((HttpURLConnection) con).disconnect();
				}
				throw ex;
			}
		}catch(Exception e) {
			throw BinaryUtils.transException(e, ResourceException.class);
		}
	}
	
	


}
