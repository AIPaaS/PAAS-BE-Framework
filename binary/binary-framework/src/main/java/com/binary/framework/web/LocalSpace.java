package com.binary.framework.web;

import java.io.File;
import java.util.Date;

import com.binary.core.http.HttpUtils;
import com.binary.core.lang.Conver;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.exception.FrameSpaceException;
import com.binary.framework.util.FrameworkProperties;

public abstract class LocalSpace {
	
	
	private static String projectLocalspace;
	
	
	private static String getProjectLocalspace() {
		if(projectLocalspace == null) {
			projectLocalspace = FrameworkProperties.getInstance().getString("Project_Local_Space");
			if(projectLocalspace==null || (projectLocalspace=projectLocalspace.trim()).length()==0) throw new FrameSpaceException(" is not found project-localspace! ");
			projectLocalspace = HttpUtils.formatContextPath(projectLocalspace);
			if(projectLocalspace.length() == 0) projectLocalspace = "/";
		}
		return projectLocalspace;
	}
	
	
	private static File getSpace(File parent, String name) {
		File file = parent!=null ? new File(parent, name) : new File(name);
		if(!file.isDirectory()) {
			boolean r = file.mkdirs();
			if(!r) throw new FrameSpaceException(" is can not create space:'"+file.getPath()+"'! ");
		}
		return file;
	}
	
	private static void verifySpace(String path) {
		File dir = new File(path);
		if(!dir.isDirectory() && !dir.mkdirs()) {
			throw new FrameSpaceException(" is can not create space:'"+path+"'! ");
		}
	}
	
	
	
	/**
	 * 获取根目录
	 * @return
	 */
	public static File getRoot() {
		return getSpace(null, getProjectLocalspace());
	}
	
	
	
	/**
	 * 获取根目录路径 (末尾不包括文件分隔符)
	 * @return
	 */
	public static String getRootPath() {
		String path = getProjectLocalspace();
		verifySpace(path);
		return path;
	}
	
	
	
	/**
	 * 获取用户空间目录
	 * @return
	 */
	private static File getUserSpace() {
		return getSpace(getRoot(), "UserSpace");
	}
	private static String getUserSpacePath() {
		String path = getRootPath() + "/" + "UserSpace";
		verifySpace(path);
		return path;
	}
	
	
	
	/**
	 * 获取指定用户空间目录
	 * @param userId
	 * @return
	 */
	public static File getUserSpace(String userId) {
		BinaryUtils.checkEmpty(userId, "userId");
		return getSpace(getUserSpace(), userId);
	}
	
	
	/**
	 * 获取指定用户空间目录 (末尾不包括文件分隔符)
	 * @param userId
	 * @return
	 */
	public static String getUserSpacePath(String userId) {
		BinaryUtils.checkEmpty(userId, "userId");
		String path = getUserSpacePath() + "/" + userId;
		verifySpace(path);
		return path;
	}
	
	
	
	/**
	 * 获取临时目录空间, 目录名安每天存在
	 * @return
	 */
	public static File getTmpSpace() {
		String dirName = getTmpRelativePath();
		return getSpace(getRoot(), dirName);
	}
	
	
	/**
	 * 获取临时目录空间, 目录名安每天存在 (末尾不包括文件分隔符)
	 * @return
	 */
	public static String getTmpSpacePath() {
		String path = getRootPath() + "/" + getTmpRelativePath();
		verifySpace(path);
		return path;
	}
	
	
	/**
	 * 获取临时目录相关目录名	(前后都不包括文件分隔符)
	 * @return
	 */
	public static String getTmpRelativePath() {
		String suffix = Conver.to(new Date(), String.class, "yyyyMMdd");
		return ".tmp/" + suffix;
	}
	
	
	/**
	 * 获取空间目录
	 * @param subdir
	 * @return
	 */
	public static File getSpace(String subdir) {
		if(subdir==null || (subdir=subdir.trim()).length()==0) {
			return getRoot();
		}else {
			return getSpace(getRoot(), subdir);
		}	
	}
	
	
	
	/**
	 * 获取空间目录 (末尾不包括文件分隔符)
	 * @param subdir
	 * @return
	 */
	public static String getSpacePath(String subdir) {
		if(subdir==null || (subdir=subdir.trim()).length()==0) {
			return getRootPath();
		}else {
			subdir = HttpUtils.formatContextPath(subdir).substring(1);
			if(subdir.length() == 0) subdir = "/";
			String path = getRootPath() + "/" + subdir;
			verifySpace(path);
			return path;
		}
	}
	
	
	
}


