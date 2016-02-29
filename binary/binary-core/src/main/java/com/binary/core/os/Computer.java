package com.binary.core.os;

import java.io.Serializable;
import java.util.List;

import com.binary.core.util.BinaryUtils;

public class Computer implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	/** CPU信息, List中每一项为一个CPU **/
	private List<Cpu> cpus;
	
	
	/** 内存大小, 单位：Byte **/
	private Long memory;
	
	
	/** 硬盘大小, 单位：Byte **/
	private Long disk;

	
	
	
	public String getCpuDesc() {
		String s = null;
		if(cpus != null) {
			s = cpus.size()+"个";
			int cores = 0;
			for(int i=0; i<cpus.size(); i++) {
				cores += cpus.get(i).getCores();
			}
			s += "," + cores + "核";
		}
		return s;
	}
	
	
	@Override
	public String toString() {
		return "CPU:" + getCpuDesc() + "; 内存:" + BinaryUtils.toByteUnit(memory)+"B" + "; 磁盘:" + BinaryUtils.toByteUnit(disk)+"B";
	}
	

	public List<Cpu> getCpus() {
		return cpus;
	}


	public void setCpus(List<Cpu> cpus) {
		this.cpus = cpus;
	}


	public Long getMemory() {
		return memory;
	}


	public void setMemory(Long memory) {
		this.memory = memory;
	}


	public Long getDisk() {
		return disk;
	}


	public void setDisk(Long disk) {
		this.disk = disk;
	}
	
	
	
	
	
	

}
