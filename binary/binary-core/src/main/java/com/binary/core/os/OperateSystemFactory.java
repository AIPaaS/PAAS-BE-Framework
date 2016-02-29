package com.binary.core.os;

import com.binary.core.exception.BinaryException;
import com.binary.core.os.impl.LinuxOperateSystem;
import com.binary.core.os.impl.WindowsOperateSystem;


/**
 * 获取操作系统服务对象工厂
 * @author wanwb
 */
public abstract class OperateSystemFactory {

	
	
	
	private static OperateSystem os;
	
	
	
	public static OperateSystem getOperateSystem() {
		if(os == null) createOperateSystem();
		return os;
	}
	
	
	
	private synchronized static void createOperateSystem() {
		if(os == null) {
			String osname = System.getProperty("os.name").toLowerCase();
			if(osname.indexOf("windows") > -1) {
				os = new WindowsOperateSystem();
			}else if(osname.indexOf("linux") > -1) {
				os = new LinuxOperateSystem();
			}else {
				throw new BinaryException(" is not support operate system '"+osname+"'! ");
			}
		}
	}
	
	
	
	
	
	
}
