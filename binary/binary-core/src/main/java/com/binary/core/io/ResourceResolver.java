package com.binary.core.io;

import java.util.ArrayList;
import java.util.List;

import com.binary.core.exception.ResourceException;
import com.binary.core.io.support.ClassPathResource;
import com.binary.core.io.support.FileResource;
import com.binary.core.io.support.URLResource;


public abstract class ResourceResolver {
	
	
	private static final Object syncobj = new Object();
	private static final List<ResourceFinder> finders = new ArrayList<ResourceFinder>();
	
	
	
	/**
	 * 注册查找器
	 * @param finder
	 */
	public static void registerFinder(ResourceFinder finder) {
		if(finder == null) return ;
		synchronized(syncobj) {
			if(!finders.contains(finder)) {
				finders.add(finder);
			}
		}
	}
	
	
	
	/**
	 * 注销查找器
	 * @param finder
	 * @return
	 */
	public static boolean cancelFinder(ResourceFinder finder) {
		synchronized(syncobj) {
			return finders.remove(finder);
		}
	}
	
	
	/**
	 * 获取资源
	 * @param path
	 * @return
	 */
	public static Resource getResource(String path) {
		return getResource(path, null);
	}
	
	
	/**
	 * 获取资源
	 * @param path
	 * @return
	 */
	public static Resource getResource(String path, ClassLoader loader) {
		if(path == null) throw new ResourceException(" the path is NULL argument! ");
		
		Resource rs = null;
		if(finders.size() > 0) {
			for(int i=0; i<finders.size(); i++) {
				rs = finders.get(i).findResource(path, loader);
				if(rs != null) break;
			}
		}
		if(rs != null) return rs;
		
		try {
			int startIndex = path.indexOf(':');
			if(startIndex > 0) {
				String start = path.substring(0, startIndex);
				if(start.equalsIgnoreCase("http")||start.equalsIgnoreCase("ftp")||start.equalsIgnoreCase("file")) {
					rs = new URLResource(path);
				}else if(start.equalsIgnoreCase("classpath")) {
					rs = new ClassPathResource(path.substring(startIndex+1), loader);
				}
			}
			if(rs == null) {
				try {
					rs = new URLResource(path);
				}catch (Exception e1) {
					rs = new FileResource(path);
				}
			}
		}catch(Exception e) {
			throw new ResourceException(" is not found resource:'"+path+"'! ", e);
		}
		return rs;
	}
	
	
	
}
