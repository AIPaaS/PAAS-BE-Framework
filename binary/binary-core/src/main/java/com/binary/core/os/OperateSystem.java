package com.binary.core.os;

import java.util.List;



/**
 * 操作系统本地方法
 * @author wanwb
 */
public interface OperateSystem {
	
	
	
	/**
	 * 获取系统类型
	 * @return
	 */
	public OperateSystemType getType();
	
	
	
	/**
	 * 获取操作系统名称
	 * @return
	 */
	public String getOsname();



	/**
	 * 获取操作系统位数
	 * @return
	 */
	public String getOsarch();
	
	
	
	/**
	 * 获取操作系统版本
	 * @return
	 */
	public String getOsversion();
	

		
	/** 
	 * 获取CPU信息
	 * @return List中每一项为一个CPU
	 **/
	public List<Cpu> getCpuList();
	
	
	
	/**
	 * 获取内存大小, 单位：Byte
	 */
	public Long getMemory();
	
	
	
	
	/**
	 * 获取硬盘大小, 单位：Byte
	 */
	public Long getDisk();
	
	
	
	
	/**
	 * 获取机器硬件配置
	 * @return
	 */
	public Computer getComputer();
	
	
	
	
	
	/**
	 * 获取CPU使用率, 单位：%
	 */
	public Integer getCpuRate();
	
	
	
	/**
	 * 获取内存使用率, 单位：%
	 */
	public Integer getMemoryRate();
	
	
	
	
	/**
	 * 获取硬盘使用率, 单位：%
	 */
	public Integer getDiskRate();
	
	
	
	/**
	 * 获取IO负载率, 单位：%
	 * @return
	 */
	public Integer getIoRate();
	
	
	
	/**
	 * 获取网络上传下载量, 单位：Byte
	 * @return [0]=下载量		[1]=上传量
	 */
	public Long[] getNetRT();
	
	
	
	/**
	 * 获取系统性能
	 * @return
	 */
	public Performance getPerformance();
	
	
	
	
	/**
	 * 获取境变量分隔符
	 * @return
	 */
	public String getEnvironmentSeparator();
	
	
}
