package com.binary.core.os.impl;

import java.util.List;

import com.binary.core.os.Cpu;
import com.binary.core.os.OperateSystemType;

public class WindowsOperateSystem  extends AbstractOperateSystem {
	
	
		
	
	
	@Override
	public OperateSystemType getType() {
		return OperateSystemType.WINDOWS;
	}
	
	
	

	@Override
	public List<Cpu> getCpuList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getMemory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getDisk() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

	@Override
	public Integer getCpuRate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getMemoryRate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getDiskRate() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public Integer getIoRate() {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public Long[] getNetRT() {
		// TODO Auto-generated method stub
		return new Long[]{0l, 0l};
	}




	@Override
	public String getEnvironmentSeparator() {
		return ";";
	}
	
	

}
