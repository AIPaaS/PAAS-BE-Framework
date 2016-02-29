package com.binary.application.java;

import java.io.Serializable;

import com.binary.core.io.Configuration;


public class JavaConfiguration extends Configuration implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	/** 指定入口类 **/
	private String mainClass;
	
	/** 指定入口参数, 多个以分号[;]分隔 **/
	private String mainArgs;
	
	
	public JavaConfiguration() {
		super("java");
	}

	

	public String getMainClass() {
		return mainClass;
	}


	public void setMainClass(String mainClass) {
		this.mainClass = mainClass;
	}



	public String getMainArgs() {
		return mainArgs;
	}



	public void setMainArgs(String mainArgs) {
		this.mainArgs = mainArgs;
	}
	
	
	
	
	

}
