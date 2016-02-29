package com.binary.dubbo.intercept;

import java.util.Iterator;

import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;




/**
 * dubbo拦载器
 * @author wanwb
 */
public interface DubboInterceptor {

	
	
	
	
	/**
	 * 添加切面对象
	 * @param aspect
	 */
	public boolean addAspect(DubboAspect aspect);
	
	
	/**
	 * 删除切面对象
	 * @param aspect
	 * @return
	 */
	public boolean removeAspect(DubboAspect aspect);
	
	
	
	/**
	 * 获取切面对象跃代器
	 * @return
	 */
	public Iterator<DubboAspect> iterator();
	
	
	
	/**
	 * 获取切面对象个数
	 * @return
	 */
	public int getAspectCount();
	
	
	
	/**
	 * 判断切面对象是否存在
	 * @param aspect
	 * @return
	 */
	public boolean contains(DubboAspect aspect);
	
	
	
	
	/**
	 * 切面对象执行前事件
	 * @return 如果Result!=null则中断运行, 将Result返回; 否则将继续运行后面代码
	 */
	public Result onBefore(Invoker<?> invoker, Invocation invocation) throws RpcException;
	
	
	
	
	/**
	 * 切面对象执行后事件
	 * @param invoker
	 * @param invocation
	 * @param result
	 * @return 
	 * @throws RpcException
	 */
	public Result onAfter(Invoker<?> invoker, Invocation invocation, Result result) throws RpcException;
	
	
	
	
	
	/**
	 * 切面对象执行异常事件
	 * @param invoker
	 * @param invocation
	 * @param e
	 * @return 如果Result!=null, 将Result返回; 否则抛出异常
	 * @throws RpcException
	 */
	public Result onError(Invoker<?> invoker, Invocation invocation, Throwable t) throws Throwable;
	
	
	
	
	
	/**
	 * 切面对象执行finally事件
	 * @param invoker
	 * @param invocation
	 */
	public void onFinally(Invoker<?> invoker, Invocation invocation);
	
	
	
	
	
}



