package com.binary.core.os.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.binary.core.os.Computer;
import com.binary.core.os.OperateSystem;
import com.binary.core.os.Performance;


public abstract class AbstractOperateSystem implements OperateSystem {
	
	
	
	/** 操作系统名称 **/
	private String osname;
	
	/** 操作系统架构 **/
	private String osarch;
	
	/** 操作系统版本 **/
	private String osversion;
	
	
	
	protected AbstractOperateSystem() {		
		this.osname = System.getProperty("os.name");
		this.osarch = System.getProperty("os.arch");
		this.osversion = System.getProperty("os.version");
	}


	@Override
	public String getOsname() {
		return osname;
	}


	@Override
	public String getOsarch() {
		return osarch;
	}
	
	
	@Override
	public String getOsversion() {
		return this.osversion;
	}
	
	
	@Override
	public Computer getComputer() {
		Computer c = new Computer();
		c.setCpus(getCpuList());
		c.setMemory(getMemory());
		c.setDisk(getDisk());
		return c;
	}
	
	
	
	
	@Override
	public Performance getPerformance() {
		Performance p = new Performance();
		try {
			p.setCpuRate(getCpuRate());
		}catch(Exception e) {
		}
		
		p.setMemoryRate(getMemoryRate());
		p.setDiskRate(getDiskRate());
		p.setIoRate(getIoRate());
		Long[] arr = getNetRT();
		p.setNetReceive(arr[0]);
		p.setNetTransmit(arr[1]);
		p.setReadTime(new Timestamp(System.currentTimeMillis()));
		return p;
	}
	
	
	
	/**
	 * 以空格拆分字符串
	 * @param s
	 * @return
	 */
	protected String[] splitSpace(String s) {
		List<String> list = new ArrayList<String>();
		int index = -1;
		while((index=(s=s.trim()).indexOf(' ')) > 0) {
			String k = s.substring(0, index);
			list.add(k);
			s = s.substring(index+1);
		}
		if(s.length() > 0) list.add(s);
		return list.toArray(new String[0]);
	}
	
	
	
	
}
