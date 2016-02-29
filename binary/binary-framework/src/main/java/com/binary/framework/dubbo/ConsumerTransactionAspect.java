package com.binary.framework.dubbo;

import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.binary.dubbo.intercept.DubboAspect;
import com.binary.dubbo.intercept.DubboAspectType;
import com.binary.framework.Local;
import com.binary.framework.bean.User;
import com.binary.framework.util.UserUtils;
import com.binary.framework.web.SessionKey;

public class ConsumerTransactionAspect implements DubboAspect {

	
	@Override
	public DubboAspectType getDubboAspectType() {
		return DubboAspectType.CONSUMER;
	}
	
	
	
	
	@Override
	public Result before(Invoker<?> invoker, Invocation invocation) throws RpcException {
		User user = Local.getUser();
		if(user != null) {
			RpcContext.getContext().setAttachment(SessionKey.SYSTEM_USER, UserUtils.toJsonString(user)); 
		}
		return null;
	}

	

	@Override
	public Result after(Invoker<?> invoker, Invocation invocation, Result result) throws RpcException {
		return result;
	}

	

	@Override
	public Result error(Invoker<?> invoker, Invocation invocation, Throwable t) throws RpcException {
		return null;
	}
	


	@Override
	public void finalz(Invoker<?> invoker, Invocation invocation) {
	}



	
	

}
