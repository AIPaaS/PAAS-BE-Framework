package com.binary.framework.bean;

import java.io.Serializable;



/**
 * 实体类造型
 * @author wanwb
 */
public interface EntityBean extends Serializable {
	
	
	
	
	/**
	 * 获取实体对象ID
	 */
	public Long getId();
	
	
	
	/**
	 * 设置实体对象ID
	 * @param id
	 */
	public void setId(Long id);
	
	
	
	/**
	 * 获取数据创建时间, 格式为yyyyMMddHHmmss
	 * @return
	 */
	public Long getCreateTime();
	
	
	/**
	 * 设置数据创建时间, 格式为yyyyMMddHHmmss
	 * @param createTime
	 */
	public void setCreateTime(Long createTime);
	
	
	
	
	/**
	 * 获取数据更改时间, 格式为yyyyMMddHHmmss
	 * @return
	 */
	public Long getModifyTime();
	
	
	/**
	 * 设置数据更改时间, 格式为yyyyMMddHHmmss
	 * @param modifyTime
	 */
	public void setModifyTime(Long modifyTime);
	
	
	
	
	

}
