package com.binary.framework.dao;

import com.binary.framework.bean.Condition;
import com.binary.framework.bean.EntityBean;

public interface DaoDefinition<E extends EntityBean, F extends Condition> {
	
	
	/**
	 * 获取映射表名
	 * @return
	 */
	public String getTableName();
	
	
	/**
	 * 获取实体类型
	 * @return
	 */
	public Class<E> getEntityClass();
	
	/**
	 * 获取条件对象类型
	 * @return
	 */
	public Class<F> getConditionClass();
	
	
	
	/**
	 * 是否含有DataStatusField
	 * @return
	 */
	public boolean hasDataStatusField();
	
	
	/**
	 * 对实体对象设置值
	 * @param e
	 * @param value
	 */
	public void setDataStatusValue(E e, int value);
	
	
	/**
	 * 对条件对象设置值
	 * @param e
	 * @param value
	 */
	public void setDataStatusValue(F f, int value);
	
	
		
	
	/**
	 * 设置创建人字段
	 * @param e
	 * @param creator
	 */
	public void setCreatorValue(E e, String creator);
	
	
	/**
	 * 设置修改人字段
	 * @param e
	 * @param modifier
	 */
	public void setModifierValue(E e, String modifier);
	
	
	
	
	
	

}
