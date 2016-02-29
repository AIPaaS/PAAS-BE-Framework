package com.binary.core.io.support;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;

public class ByteArrayResource extends AbstractResource {
	
	
	private byte[] data;
	private String name;
	
	
	public ByteArrayResource(byte[] data, String name) {
		this.data = data;
		this.name = name;
	}
	
	

	@Override
	public boolean exists() {
		return this.data != null;
	}
	
	
	@Override
	public String getName() {
		return this.name;
	}

	
	@Override
	public String getPath() {
		return null;
	}

	@Override
	public URL getURL() {
		return null;
	}

	@Override
	public InputStream getInputStream() {
		return new ByteArrayInputStream(this.data);
	}
	
	

}
