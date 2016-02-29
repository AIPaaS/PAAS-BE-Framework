package com.binary.dubbo.intercept.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.binary.core.exception.MultipleException;
import com.binary.core.util.SecurityIterator;
import com.binary.core.util.BinaryUtils;
import com.binary.dubbo.exception.InterceptException;
import com.binary.dubbo.intercept.DubboAspect;
import com.binary.dubbo.intercept.DubboInterceptor;



public abstract class AbstractDubboInterceptor implements Filter, DubboInterceptor {
	
	
	private final Object syncobj = new Object();
	private final List<DubboAspect> aspects = new ArrayList<DubboAspect>();
	
	
	
	
	public boolean addAspect(DubboAspect aspect) {
		synchronized(syncobj) {
			if(aspects.contains(aspect)) {
				throw new InterceptException(" the aspect '"+aspect.toString()+"' is exists! ");
			}
			return aspects.add(aspect);
		}
	}
	
	
	
	public boolean removeAspect(DubboAspect aspect) {
		synchronized(syncobj) {
			return aspects.remove(aspect);
		}
	}
	
	
	public Iterator<DubboAspect> iterator() {
		return new SecurityIterator<DubboAspect>(aspects.iterator());
	}
	
	
	public int getAspectCount() {
		return this.aspects.size();
	}
	
	
	public boolean contains(DubboAspect aspect) {
		return this.aspects.contains(aspect);
	}
	
	
	
	public Result onBefore(Invoker<?> invoker, Invocation invocation) throws RpcException {
		for(int i=0; i<this.aspects.size(); i++) {
			DubboAspect aspect = this.aspects.get(i);
			Result result = aspect.before(invoker, invocation);
			if(result != null) return result;
		}
		return null;
	}
	
	
	
	public Result onAfter(Invoker<?> invoker, Invocation invocation, Result result) throws RpcException {
		for(int i=0; i<this.aspects.size(); i++) {
			DubboAspect aspect = this.aspects.get(i);
			result = aspect.after(invoker, invocation, result);
		}
		return result;
	}
	
	
	
	
	public Result onError(Invoker<?> invoker, Invocation invocation, Throwable t) throws Throwable {
		for(int i=0; i<this.aspects.size(); i++) {
			DubboAspect aspect = this.aspects.get(i);
			Result result = aspect.error(invoker, invocation, t);
			if(result != null) return result;
		}
		throw t;
	}
	
	
	
	
	public void onFinally(Invoker<?> invoker, Invocation invocation) {
		MultipleException me = new MultipleException();
		
		for(int i=0; i<this.aspects.size(); i++) {
			DubboAspect aspect = this.aspects.get(i);
			
			try {
				aspect.finalz(invoker, invocation);
			}catch(Throwable t) {
				me.add(t);
			}
		}
		
		if(me.size() > 0) throw me;
	}
	
	
	
	/**
	 * 写日志
	 * @param msg
	 */
	protected abstract void writeLog(String msg);
	
	
	/**
	 * 写异常日志
	 * @param msg
	 * @param t
	 */
	protected abstract void writeErrorLog(String msg, Throwable t);
	
	
	
	
	
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		try {
			Result result = onBefore(invoker, invocation);
			if(result != null) {
				writeLog("invoke-before之后被中断!");
				return result;
			}
			
			Throwable tow = null;
			try {
				result = invoker.invoke(invocation);
			}catch(Throwable t) {
				tow = t;
			}
			
			if(tow!=null || (result!=null&&result.hasException())) {
				if(tow == null) tow = result.getException();
				try {
					result = onError(invoker, invocation, tow!=null?tow:result.getException());
				} catch (Throwable e) {
					throw BinaryUtils.transException(e, InterceptException.class);
				}
				if(result != null) {
					writeLog("invoke-异常["+tow.getClass().getName()+": "+tow.getMessage()+"]被转换!");
					return result;
				}
			}
			
			result = onAfter(invoker, invocation, result);
			
			if(result == null) {
				throw new InterceptException(" the dubbo-aspect object after() return value is NULL! ");
			}
			
			return result;
		}finally {
			onFinally(invoker, invocation);
		}
	}
	
	
	
	
	
}


