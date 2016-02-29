package com.binary.singleweb.primarykey.dao;

public interface PrimaryKeyDao {

	
	
	/**
	 * 跟据名称获取当前值
	 * @param name: 名称标识
	 * @return 当前值, 为空表示名称不存在
	 */
	public Long selectValue(String name);
	
	
	
	/**
	 * 插入数据
	 * @param name
	 * @param value
	 */
	public void insert(String name, Long value);
	
	
	
	
	/**
	 * 更新名称对应的值
	 * @param name
	 * @param value
	 */
	public void update(String name, Long value);
	
	
	
	
}
