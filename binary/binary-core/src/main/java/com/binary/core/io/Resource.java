package com.binary.core.io;

import java.io.InputStream;
import java.net.URL;

public interface Resource {
	
	
	/**
	 * 判断资源是否存在
	 * @return
	 */
	public boolean exists();
	
	
	
	/**
	 * 获取资源名称
	 * @return
	 */
	public String getName();
	
	
	
	/**
	 * 获取资源地址
	 * @return
	 */
	public String getPath();
	
	
	/**
	 * 获取连接地址
	 * @return
	 */
	public URL getURL();
	
	
	/**
	 * 是否可读
	 * @return
	 */
	public boolean isReadable();
	
	
	
	/**
	 * 获取读入流
	 * @return
	 */
	public InputStream getInputStream();
	
	
	
}


