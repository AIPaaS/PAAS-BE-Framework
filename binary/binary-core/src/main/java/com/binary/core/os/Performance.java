package com.binary.core.os;

import java.io.Serializable;
import java.sql.Timestamp;

import com.binary.core.lang.Conver;
import com.binary.core.util.BinaryUtils;


/**
 * 系统性能
 * @author wanwb
 */
public class Performance implements Serializable {
	private static final long serialVersionUID = 1L;

	
	/** CPU使用率, 单位：% **/
	private Integer cpuRate;
	
	
	/** 内存使用率, 单位：% **/
	private Integer memoryRate;
	
	
	/** 硬盘使用率, 单位：% **/
	private Integer diskRate;
	
	
	/** IO负载率, 单位：% **/
	private Integer ioRate;
	
	
	/** 网络发送数据量, 单位：Byte **/
	private Long netTransmit;
	
	
	/** 网络接收数据量, 单位：Byte **/
	private Long netReceive;
	
	
	/** 性能信息读取时间 **/
	private Timestamp readTime;
	
	
	
	@Override
	public String toString() {
		return "读取时间:"+Conver.to(readTime, String.class, "yyyy-MM-dd HH:mm:ss")+"; CPU使用率:" + (cpuRate!=null?cpuRate.doubleValue()/100:null) + "%; 内存使用率:" + memoryRate.doubleValue()/100+"%; 磁盘使用率:" + diskRate.doubleValue()/100+"%; IO负载:"+ioRate.doubleValue()/100+"%; 网络下载/上传量:"+BinaryUtils.toByteUnit(netReceive)+"B/"+BinaryUtils.toByteUnit(netTransmit)+"B";
	}
	


	public Integer getCpuRate() {
		return cpuRate;
	}


	public void setCpuRate(Integer cpuRate) {
		this.cpuRate = cpuRate;
	}


	public Integer getMemoryRate() {
		return memoryRate;
	}


	public void setMemoryRate(Integer memoryRate) {
		this.memoryRate = memoryRate;
	}


	public Integer getDiskRate() {
		return diskRate;
	}


	public void setDiskRate(Integer diskRate) {
		this.diskRate = diskRate;
	}


	public Integer getIoRate() {
		return ioRate;
	}


	public void setIoRate(Integer ioRate) {
		this.ioRate = ioRate;
	}



	public Long getNetTransmit() {
		return netTransmit;
	}



	public void setNetTransmit(Long netTransmit) {
		this.netTransmit = netTransmit;
	}



	public Long getNetReceive() {
		return netReceive;
	}



	public void setNetReceive(Long netReceive) {
		this.netReceive = netReceive;
	}



	public Timestamp getReadTime() {
		return readTime;
	}



	public void setReadTime(Timestamp readTime) {
		this.readTime = readTime;
	}

	
	
	
	
	
	
	
	
}
