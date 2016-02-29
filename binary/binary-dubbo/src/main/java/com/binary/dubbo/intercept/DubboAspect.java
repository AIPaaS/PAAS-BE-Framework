package com.binary.dubbo.intercept;

import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;



/**
 * @author wanwb
 * dubbo拦截切面对象
 */
public interface DubboAspect {
	
	
	
	/**
	 * 获取拦截器类型
	 * @return
	 */
	public DubboAspectType getDubboAspectType();
	
	
	
	
	/**
	 * 切面对象执行前事件
	 * @return 如果Result!=null则中断运行, 将Result返回; 否则将继续运行后面代码
	 */
	public Result before(Invoker<?> invoker, Invocation invocation) throws RpcException;
	
	
	
	
	/**
	 * 切面对象执行后事件
	 * @param invoker
	 * @param invocation
	 * @param result
	 * @return 
	 * @throws RpcException
	 */
	public Result after(Invoker<?> invoker, Invocation invocation, Result result) throws RpcException;
	
	
	
	
	
	/**
	 * 切面对象执行异常事件
	 * @param invoker
	 * @param invocation
	 * @param e
	 * @return 如果Result!=null, 将Result返回; 否则抛出异常
	 * @throws RpcException
	 */
	public Result error(Invoker<?> invoker, Invocation invocation, Throwable t) throws RpcException;
	
	
	
	
	
	/**
	 * 切面执行finally事件
	 * @param invoker
	 * @param invocation
	 */
	public void finalz(Invoker<?> invoker, Invocation invocation);
	
	
	
	
	
	
	

}
