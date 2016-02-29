package com.binary.core.bean;



/**
 * Bean存储器
 * @author wanwb
 *
 */
public interface BeanStore {
	
	
	
	/**
	 * 判断Bean是否存在
	 * @param beanName
	 * @return
	 */
	public boolean containsKey(String beanName);
	
	
	
	/**
	 * 根据beanName获取Bean对象
	 * @param beanName
	 * @return
	 */
	public Bean<?> getBean(String beanName);
	
	
	
	/**
	 * 添加bean对象至bean存储器中
	 * @param beanName
	 * @param bean
	 */
	public void putBean(String beanName, Bean<?> bean);
	
	
	
	
	
}




