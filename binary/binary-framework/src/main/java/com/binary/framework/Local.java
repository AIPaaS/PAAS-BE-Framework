package com.binary.framework;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.binary.core.exception.MultipleException;
import com.binary.framework.bean.User;
import com.binary.framework.critical.CriticalObject;
import com.binary.framework.critical.support.HttpCriticalObject;
import com.binary.framework.critical.support.LocalCriticalObject;
import com.binary.framework.exception.CriticalException;
import com.binary.framework.util.FrameworkProperties;
import com.binary.framework.web.LocalSpace;
import com.binary.jdbc.JdbcOperatorFactory;
import com.binary.jdbc.exception.TransactionException;



public abstract class Local {
	private static final Logger logger = LoggerFactory.getLogger(Local.class);
	
	
	
	private static final ThreadLocal<CriticalObject> criticalLocal = new ThreadLocal<CriticalObject>();
	
	
	private Local() {
	}
	
	
	
	/**
	 * 添加Listener
	 * @param listener
	 */
	public static boolean addListener(LocalListener listener) {
		return LocalListenerManager.addListener(listener);
	}
	
	
	
	/**
	 * 删除Listener
	 * @param listener
	 */
	public static boolean removeListener(LocalListener listener) {
		return LocalListenerManager.removeListener(listener);
	}
	
	
	
	/**
	 * 判断当前Local是否打开
	 * @return
	 */
	public static boolean isOpen() {
		return criticalLocal.get() != null;
	}
	
	
	
	public static void open(HttpServletRequest request, HttpServletResponse response) {
		open(new HttpCriticalObject(request, response));
	}
	
	
	
	public static void open(String loginCode) {
		open(new LocalCriticalObject(loginCode));
	}
	
	
	public static void open(User loginUser) {
		open(new LocalCriticalObject(loginUser));
	}
	
	
	private static void open(CriticalObject criticalObject) {
		CriticalObject old = criticalLocal.get();
		if(old != null) throw new CriticalException(" the Critical is openned! ");
		criticalLocal.set(criticalObject);
		LocalListenerManager.open(criticalObject);
	}
	
	
	
	public static void commit() {
		MultipleException me = null;
		try {
			JdbcOperatorFactory factory = JdbcOperatorFactory.getMomentFactory();
			if(factory != null) factory.commit();
		}catch(Exception e) {
			me = MultipleException.appendException(me, e);
		}
		try {
			LocalListenerManager.commit();
		}catch(Exception e) {
			me = MultipleException.appendException(me, e);
		}
		if(me!=null && me.size()>0) throw new TransactionException(" the local commit error!", me);
	}
	
	
	
	public static void rollback() {
		MultipleException me = null;
		try {
			JdbcOperatorFactory factory = JdbcOperatorFactory.getMomentFactory();
			if(factory != null) factory.rollback();
		}catch(Exception e) {
			me = MultipleException.appendException(me, e);
		}
		try {
			LocalListenerManager.rollback();
		}catch(Exception e) {
			me = MultipleException.appendException(me, e);
		}
		if(me!=null && me.size()>0) throw new TransactionException(" the local rollback error!", me);
	}
	
	
	
	
	public static void close() {
		MultipleException me = null;
		try {
			criticalLocal.remove();
			JdbcOperatorFactory factory = JdbcOperatorFactory.getMomentFactory();
			if(factory != null) factory.close();
		}catch(Exception e) {
			me = MultipleException.appendException(me, e);
		}
		try {
			LocalListenerManager.close();
		}catch(Exception e) {
			me = MultipleException.appendException(me, e);
		}
		if(me!=null && me.size()>0) throw new TransactionException(" the local close error!", me);
	}
	
	
	
	/**
	 * 获取当前登录用户
	 * @return
	 */
	public static User getUser() {
		CriticalObject cobj = getCriticalObject();
		return cobj.getUser();
	}
	
	
	
	/**
	 * 获取写日志时所需用户名称
	 * @return
	 */
	public static String getLogUser() {
		User user = getUser();
		if(user != null) {
			return user.getUserName() + " ["+user.getUserCode()+"]";
		}else {
			return null;
		}
	}
	
	
	
	/**
	 * 获取临界对象
	 * @return
	 */
	public static CriticalObject getCriticalObject() {
		CriticalObject criticalObj = criticalLocal.get();
		if(criticalObj == null) throw new CriticalException(" the Critical is not open! ");
		return criticalObj;
	}
	
	
	
	/**
	 * 获取配置字符集
	 * @return
	 */
	public static String getCharset() {
		return FrameworkProperties.getInstance().getCharset();
	}
	
	
	
	/**
	 * 获取日志对象
	 * @return
	 */
	public static Logger getLogger() {
		return logger;
	}
	
	
	
		
	/**
	 * 获取当前登录用户空间
	 * @return
	 */
	public static File getUserSpace() {
		User user = getUser();
		if(user == null) throw new CriticalException(" is not found login-user! ");
		return LocalSpace.getUserSpace(user.getUserId());
	}
	
	
	
	
	/**
	 * 获取临时目录空间
	 * @return
	 */
	public static File getTmpSpace() {
		return LocalSpace.getTmpSpace();
	}
	
	
	
	/**
	 * 获取文件目录空间
	 * @param subdir
	 * @return
	 */
	public static File getSpace(String subdir) {
		return LocalSpace.getSpace(subdir);
	}
	
	
	
	
	
}



