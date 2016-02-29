package com.binary.singleweb.primarykey.svc;



public interface PrimaryKeySvc {

	
	
	/**
	 * 获取Key
	 * @param name: 标识
	 * @param batch: 批量数
	 * @return
	 */
	public String getKey(String name, Integer batch);
	
	
	
	
}
