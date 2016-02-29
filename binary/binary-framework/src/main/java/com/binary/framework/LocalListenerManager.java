package com.binary.framework;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.binary.core.exception.MultipleException;
import com.binary.core.util.SecurityIterator;
import com.binary.framework.critical.CriticalObject;
import com.binary.jdbc.exception.TransactionException;



/**
 * LocalListener管理器
 * @author wanwb
 */
public abstract class LocalListenerManager {
	
	
	/** LocalListener列表 **/
	private static final List<LocalListener> LISTENER_LIST = new ArrayList<LocalListener>();
	
	
	
	/**
	 * 获取Listener个数
	 * @return
	 */
	public static int getListenerCount() {
		return LISTENER_LIST.size();
	}
	
	
	
	/**
	 * 判断Listener是否存在
	 * @param listener
	 * @return
	 */
	public static boolean contains(LocalListener listener) {
		return LISTENER_LIST.contains(listener);
	}
	
	
	
	/**
	 * 添加Listener
	 * @param listener
	 */
	public static boolean addListener(LocalListener listener) {
		return LISTENER_LIST.add(listener);
	}
	
	
	
	/**
	 * 删除Listener
	 * @param listener
	 */
	public static boolean removeListener(LocalListener listener) {
		return LISTENER_LIST.remove(listener);
	}
	
	
	
	/**
	 * 获取所有Listener的遍历迭代器
	 * @return
	 */
	public static Iterator<LocalListener> getListenerIterator() {
		return new SecurityIterator<LocalListener>(LISTENER_LIST.iterator());
	}
	
	
	
	
	
	/**
	 * 环境开启事件
	 * @param criticalObject
	 */
	public static void open(CriticalObject criticalObject) {
		MultipleException me = null;
		for(int i=0; i<LISTENER_LIST.size(); i++) {
			try {
				LocalListener listener = LISTENER_LIST.get(i);
				listener.open(criticalObject);
			}catch(Exception e) {
				me = MultipleException.appendException(me, e);
			}
		}
		if(me!=null && me.size()>0) throw new TransactionException(" the local-listener-manager open error!", me);
	}
	
	
	
	
	/**
	 * 环境事物提交事件
	 */
	public static void commit() {
		MultipleException me = null;
		for(int i=0; i<LISTENER_LIST.size(); i++) {
			try {
				LocalListener listener = LISTENER_LIST.get(i);
				listener.commit();
			}catch(Exception e) {
				me = MultipleException.appendException(me, e);
			}
		}
		if(me!=null && me.size()>0) throw new TransactionException(" the local-listener-manager transaction-commit error!", me);
	}
	
	
	
	
	/**
	 * 环境事物回滚事件
	 */
	public static void rollback() {
		MultipleException me = null;
		for(int i=0; i<LISTENER_LIST.size(); i++) {
			try {
				LocalListener listener = LISTENER_LIST.get(i);
				listener.rollback();
			}catch(Exception e) {
				me = MultipleException.appendException(me, e);
			}
		}
		if(me!=null && me.size()>0) throw new TransactionException(" the local-listener-manager transaction-rollback error!", me);
	}
	
	
	
	
	/**
	 * 环境事物关闭事件
	 */
	public static void close() {
		MultipleException me = null;
		for(int i=0; i<LISTENER_LIST.size(); i++) {
			try {
				LocalListener listener = LISTENER_LIST.get(i);
				listener.close();
			}catch(Exception e) {
				me = MultipleException.appendException(me, e);
			}
		}
		if(me!=null && me.size()>0) throw new TransactionException(" the local-listener-manager close error!", me);
	}
	
	
}
