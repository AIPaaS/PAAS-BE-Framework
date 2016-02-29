package com.binary.core.io.support;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.binary.core.exception.ResourceException;
import com.binary.core.io.FileSystem;

public class FileResource extends AbstractResource {
	
	private File file;
	private String path;
	
	
	public FileResource(File file) {
		if(file == null) throw new ResourceException(" the file is NULL argument! ");
		this.file = file;
		this.path = cleanPath(file.getPath());
	}
	
	
	
	public FileResource(String path) {
		if(path == null) throw new ResourceException(" the path is NULL argument! ");
		this.file = new File(path);
		this.path = cleanPath(path);
		
		if(!this.exists() && this.path.length()>0) {
			char start = this.path.charAt(0);
			if (start=='/' || start=='\\') {
				String repath = this.path.substring(1);
				File file = new File(repath);
				if(file.exists()) {
					this.path = repath;
					this.file = file;
				}
			}
		}
	}
	
	
	
	public boolean exists() {
		return this.file.exists();
	}
	
	
	public String getName() {
		return this.file.getName();
	}
	
	
	public String getPath() {
		return this.path;
	}
	
	
	public URL getURL() {
		try {
			return this.file.toURI().toURL();
		} catch (MalformedURLException e) {
			throw new ResourceException(e);
		}
	}
	
	
	public boolean isReadable() {
		return (this.file.exists() && this.file.canRead() && !this.file.isDirectory());
	}

	
	
	public InputStream getInputStream() {
		return FileSystem.newFileInputStream(this.file);
	}
	
	

}
