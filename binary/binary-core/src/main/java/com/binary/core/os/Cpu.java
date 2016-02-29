package com.binary.core.os;

import java.io.Serializable;

import com.binary.core.util.BinaryUtils;

public class Cpu implements Serializable {
	private static final long serialVersionUID = 1L;

	
	/** 核数 **/
	private Integer cores;
	
	
	/** 频率, 单位：Hz **/
	private Long frequency;
	
	
	/** 型号 **/
	private String model;
	
	
	
	@Override
	public String toString() {
		return cores+"核," + BinaryUtils.toByteUnit(this.frequency) + "Hz";
	}
	
	
	



	public Integer getCores() {
		return cores;
	}



	public void setCores(Integer cores) {
		this.cores = cores;
	}



	public Long getFrequency() {
		return frequency;
	}



	public void setFrequency(Long frequency) {
		this.frequency = frequency;
	}



	public String getModel() {
		return model;
	}



	public void setModel(String model) {
		this.model = model;
	}

	
	
	
	
	
	
}
