package com.binary.framework.util;

import org.springframework.web.bind.annotation.support.HandlerMethodInvocationException;

import com.alibaba.dubbo.remoting.RemotingException;
import com.alibaba.dubbo.rpc.RpcException;
import com.binary.core.exception.BinaryException;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.exception.ServiceException;

public abstract class ExceptionUtil {
	
	
	public static Throwable getRealThrowable(Throwable t) {
		if(t instanceof HandlerMethodInvocationException) {
			HandlerMethodInvocationException tx = (HandlerMethodInvocationException)t;
			Throwable root = tx.getRootCause();
			if(root != null) {
				return getRealThrowable(root);
			}
		}else if(t instanceof BinaryException) {
			BinaryException tx = (BinaryException)t;
			Throwable top = tx.getOriginalThrowable();
			if(top!=null && tx!=top) {
				return getRealThrowable(top);
			}
		}else if(t instanceof RpcException) {
			RpcException tx = (RpcException)t;
			return getRealThrowable(tx.getCause());
		}else if(t instanceof RemotingException) {
			RemotingException tx = (RemotingException)t;
			String msg = tx.getMessage();
			if(!BinaryUtils.isEmpty(msg)) {
				msg = msg.substring(0, msg.indexOf("\n"));
				msg = msg.substring(msg.indexOf(":")+1).trim();
				return new ServiceException(msg);
			}
		}else {
			Throwable tx = t.getCause();
			if(tx!=null && tx!=t) {
				return getRealThrowable(tx);
			}
		}
		return BinaryUtils.transException(t, ServiceException.class).getOriginalThrowable();
	}
	

}
