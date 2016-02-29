package com.binary.framework.dao;

import java.util.List;

import com.binary.framework.bean.Condition;
import com.binary.framework.bean.EntityBean;
import com.binary.jdbc.Page;


/**
 * 通用DAO接口
 * @author wanwb
 *
 * @param <E> : 实体类型
 * @param <F> : 条件类型
 */
public interface Dao<E extends EntityBean, F extends Condition> {

	
	
	/**
	 * 获取DAO定义对象
	 * @return
	 */
	public DaoDefinition<E, F> getDaoDefinition();
	
	
	
	
	/**
	 * 分页查询
	 * @param pageNum : 指定页码
	 * @param pageSize : 指定页行数
	 * @param cdt : 条件对象
	 * @param orders : 排序字段
	 * @return 
	 */
	public Page<E> selectPage(long pageNum, long pageSize, F cdt, String orders);
	
	
	/**
	 * 不带count分页查询
	 * @param pageNum : 指定页码
	 * @param pageSize : 指定页行数
	 * @param cdt : 条件对象
	 * @param orders : 排序字段
	 * @return 
	 */
	public List<E> selectList(long pageNum, long pageSize, F cdt, String orders);


	/**
	 * 不分页查询
	 * @param cdt : 条件对象
	 * @param orders : 排序字段
	 * @return 
	 */
	public List<E> selectList(F cdt, String orders);


	/**
	 * 查询数据行数
	 * @param cdt : 条件对象
	 * @return 查询行数
	 */
	public long selectCount(F cdt);


	/**
	 * 跟据主键查询
	 * @param id: 主键值
	 * @return 
	 */
	public E selectById(long id);


	/**
	 * 插入记录
	 * @param record : 点映射对象
	 * @return 新插入记录的主键值
	 */
	public long insert(E record);


	/**
	 * 批量插入记录
	 * @param records : 映射对象列表
	 * @return 新插入记录的主键值列表
	 */
	public long[] insertBatch(List<E> records);


	/**
	 * 跟据主键更新记录
	 * @param record : 更新的映射对象
	 * @param id : 主键值
	 * @return 新插入记录的主键值列表
	 */
	public int updateById(E record, long id);


	/**
	 * 跟据条件更新记录
	 * @param record : 更新的映射对象
	 * @param cdt : 条件对象
	 * @return 更新记录数
	 */
	public int updateByCdt(E record, F cdt);


	/**
	 * 批量更新记录(跟据记录主键值), 如果指定更新字段, 则批量更新, 如果没有指定, 则逐各更新
	 * @param records : 更新的映射对象列表
	 * @param updateFields : 指定需更新字段
	 * @return 更新记录数列表
	 */
	public int[] updateBatch(List<E> records);


	/**
	 * 根据主建删除记录
	 * @param id : 主键值
	 * @return 删除记录数
	 */
	public int deleteById(long id);


	/**
	 * 跟据条件删除记录
	 * @param cdt : 条件对象
	 * @return 删除记录数
	 */
	public int deleteByCdt(F cdt);


	/**
	 * 批量删除记录
	 * @param ids : 主键值列表
	 * @return 删除记录数列表
	 */
	public int[] deleteBatch(long[] ids);


	/**
	 * 保存记录, 跟据record.id是否为empty来判断是做insert or update
	 * @param record : 映射对象
	 * @return 保存记录的主键值
	 */
	public long save(E record);


	/**
	 * 批量保存, 跟据record.id是否为empty来判断是做insert or update
	 * @param records : 映射对象列表
	 * @return 保存记录的主键值列表
	 */
	public long[] saveBatch(List<E> records);


	/**
	 * 批量保存, 跟据指定验证字段到数据库中会查一遍记录是否存在, 从而判断是insert or update
	 * @param records : 映射对象列表
	 * @param verifyField : 验证字段
	 * @return 保存记录的主键值列表
	 */
	public long[] saveBatch(List<E> records, String verifyField);
	
	
	
	
	
	
}



