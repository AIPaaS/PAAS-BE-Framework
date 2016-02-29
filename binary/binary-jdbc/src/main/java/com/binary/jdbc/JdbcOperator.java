package com.binary.jdbc;

import java.util.List;
import java.util.Map;

import com.binary.jdbc.adapter.JdbcAdapter;
import com.binary.jdbc.print.Printer;



/**
 * 数据库操作对象
 * @author wanwb
 */
public interface JdbcOperator {
	
	
	
	/**
	 * 获取当前打印对象
	 * @return
	 */
	public Printer getPrinter();
	
	
	
	
	/**
	 * 获取当前事物对象
	 * @return
	 */
	public Transaction getTransaction();
	
	
	
	/**
	 * 获取JdbcAdapter
	 * @return
	 */
	public JdbcAdapter getJdbcAdapter();
	
	
	

	/**
	 * 基础查询
	 * @param sql
	 * @return List<Map>: 每一元素Map中应一行, Map中的键对应数据库中的字段名, 注意全部大写
	 */
	public List<Map<String,Object>> executeQuery(String sql);
	
	
	/**
	 * 带?参数基础查询
	 * @param sql
	 * @param params
	 * @return List<Map>: 每一元素Map中应一行, Map中的键对应数据库中的字段名, 注意全部大写
	 */
	public List<Map<String,Object>> executeQuery(String sql, Object[] params);
	
	
		
	/**
	 * 带${}参数基础查询
	 * @param sql
	 * @param bean
	 * @return List<Map>: 每一元素Map中应一行, Map中的键对应数据库中的字段名, 注意全部大写
	 */
	public List<Map<String,Object>> executeQuery(String sql, Object paramObj);
	
	
	
	/**
	 * 映射基础查询
	 * @param sql
	 * @return List<Map>: 每一元素Map中应一行, Map中的键对应数据库中的字段名, 注意全部大写
	 */
	public <T> List<T> executeQuery(String sql, Class<T> mapping);
	
	
	
	/**
	 * 映射带?参数基础查询
	 * @param sql
	 * @param params
	 * @return List<Map>: 每一元素Map中应一行, Map中的键对应数据库中的字段名, 注意全部大写
	 */
	public <T> List<T> executeQuery(String sql, Object[] params, Class<T> mapping);
	
		
	/**
	 * 映射带${}参数基础查询
	 * @param sql
	 * @param beanInstance
	 * @return List<Map>: 每一元素Map中应一行, Map中的键对应数据库中的字段名, 注意全部大写
	 */
	public <T> List<T> executeQuery(String sql, Object paramObj, Class<T> mapping);
	
	
	
	/**
	 * 分页基础查询
	 * @param sql
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<Map<String,Object>> executeQuery(long pageNum, long pageSize, String sql, String orderByFields);
	
	
	
	
	/**
	 * 带?参数基础查询
	 * @param sql
	 * @param params
	 * @return List<Map>: 每一元素Map中应一行, Map中的键对应数据库中的字段名, 注意全部大写
	 */
	public Page<Map<String,Object>> executeQuery(long pageNum, long pageSize, String sql, String orderByFields, Object[] params);
	
	
	
	/**
	 * 带${}参数基础查询
	 * @param sql
	 * @param params
	 * @return List<Map>: 每一元素Map中应一行, Map中的键对应数据库中的字段名, 注意全部大写
	 */
	public Page<Map<String,Object>> executeQuery(long pageNum, long pageSize, String sql, String orderByFields, Object paramObj);
	
	
	/**
	 * 分页基础查询
	 * @param sql
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<Map<String,Object>> executeQuery(long pageNum, long pageSize, String sql, String orderByFields, boolean ignoreCount);
	
	
	
	
	/**
	 * 带?参数基础查询
	 * @param sql
	 * @param params
	 * @return List<Map>: 每一元素Map中应一行, Map中的键对应数据库中的字段名, 注意全部大写
	 */
	public Page<Map<String,Object>> executeQuery(long pageNum, long pageSize, String sql, String orderByFields, Object[] params, boolean ignoreCount);
	
	
	
	/**
	 * 带${}参数基础查询
	 * @param sql
	 * @param params
	 * @return List<Map>: 每一元素Map中应一行, Map中的键对应数据库中的字段名, 注意全部大写
	 */
	public Page<Map<String,Object>> executeQuery(long pageNum, long pageSize, String sql, String orderByFields, Object paramObj, boolean ignoreCount);
	
	
	
	/**
	 * 映射分页基础查询
	 * @param sql
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public <T> Page<T> executeQuery(long pageNum, long pageSize, String sql, String orderByFields, Class<T> mapping);
	
	
	
	/**
	 * 映射带?参数基础查询
	 * @param sql
	 * @param params
	 * @return List<Map>: 每一元素Map中应一行, Map中的键对应数据库中的字段名, 注意全部大写
	 */
	public <T> Page<T> executeQuery(long pageNum, long pageSize, String sql, String orderByFields, Object[] params, Class<T> mapping);
	
	
	
	
	/**
	 * 映射带${}参数基础查询
	 * @param sql
	 * @param params
	 * @return List<Map>: 每一元素Map中应一行, Map中的键对应数据库中的字段名, 注意全部大写
	 */
	public <T> Page<T> executeQuery(long pageNum, long pageSize, String sql, String orderByFields, Object paramObj, Class<T> mapping);
	
	
	
	/**
	 * 映射分页基础查询
	 * @param sql
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public <T> Page<T> executeQuery(long pageNum, long pageSize, String sql, String orderByFields, Class<T> mapping, boolean ignoreCount);
	
	
	
	/**
	 * 映射带?参数基础查询
	 * @param sql
	 * @param params
	 * @return List<Map>: 每一元素Map中应一行, Map中的键对应数据库中的字段名, 注意全部大写
	 */
	public <T> Page<T> executeQuery(long pageNum, long pageSize, String sql, String orderByFields, Object[] params, Class<T> mapping, boolean ignoreCount);
	
	
	
	
	/**
	 * 映射带${}参数基础查询
	 * @param sql
	 * @param params
	 * @return List<Map>: 每一元素Map中应一行, Map中的键对应数据库中的字段名, 注意全部大写
	 */
	public <T> Page<T> executeQuery(long pageNum, long pageSize, String sql, String orderByFields, Object paramObj, Class<T> mapping, boolean ignoreCount);
	
	
	
	
	
	/**
	 * 基础更新操作、支持insert、update、delete三种语句
	 * @param sql
	 * @return 响应行数
	 */
	public int executeUpdate(String sql);
	
	
	
	/**
	 * 带?参数基础更新操作、支持insert、update、delete三种语句
	 * @param sql
	 * @param params
	 * @return 响应行数
	 */
	public int executeUpdate(String sql, Object[] params);
	
	
	
	/**
	 * 带${}参数基础更新操作、支持insert、update、delete三种语句
	 * @param sql
	 * @param params
	 * @return
	 */
	public int executeUpdate(String sql, Object paramObj);
	
	
	
	/**
	 * 批次基础更新操作、支持insert、update、delete三种语句
	 * @param sql
	 * @param paramList: 其中每一元素为Object[]
	 * @return
	 */
	public int[] executeUpdateBatch(String sql, List<Object[]> paramsList);
	
	
	
	
	/**
	 * 批次基础更新操作、支持insert、update、delete三种语句
	 * @param sql
	 * @param beanList: 对应${}参数
	 * @param mapping: 说明paramList中的对象类型
	 * @return
	 */
	public <T> int[] executeUpdateBatch(String sql, List<T> beanList, Class<T> mapping);
	
	
	
	
	
	
	
	
	
	/**
	 * 查询记录数
	 * @param sql
	 * @return List<Map>: 每一元素Map中应一行, Map中的键对应数据库中的字段名, 注意全部大写
	 */
	public long selectCount(String sql);
	
	
	/**
	 * 带?参数查询记录数
	 * @param sql
	 * @param params
	 */
	public long selectCount(String sql, Object[] params);
	
	
		
	/**
	 * 带${}参数查询记录数
	 * @param sql
	 * @param bean
	 */
	public long selectCount(String sql, Object paramObj);
	
	
	
	
	
	/**
	 * 查询第一条记录
	 * @param sql
	 * @return List<Map>: 每一元素Map中应一行, Map中的键对应数据库中的字段名, 注意全部大写
	 */
	public Map<String,Object> selectFirst(String sql);
	
	
	/**
	 * 带?参数查询第一条记录
	 * @param sql
	 * @param params
	 * @return List<Map>: 每一元素Map中应一行, Map中的键对应数据库中的字段名, 注意全部大写
	 */
	public Map<String,Object> selectFirst(String sql, Object[] params);
	
	
		
	/**
	 * 带${}参数查询第一条记录
	 * @param sql
	 * @param bean
	 * @return List<Map>: 每一元素Map中应一行, Map中的键对应数据库中的字段名, 注意全部大写
	 */
	public Map<String,Object> selectFirst(String sql, Object paramObj);
	
	
	
	/**
	 * 映射查询第一条记录
	 * @param sql
	 * @return List<Map>: 每一元素Map中应一行, Map中的键对应数据库中的字段名, 注意全部大写
	 */
	public <T> T selectFirst(String sql, Class<T> mapping);
	
	
	
	/**
	 * 映射带?参数查询第一条记录
	 * @param sql
	 * @param params
	 * @return List<Map>: 每一元素Map中应一行, Map中的键对应数据库中的字段名, 注意全部大写
	 */
	public <T> T selectFirst(String sql, Object[] params, Class<T> mapping);
	
		
	/**
	 * 映射带${}参数查询第一条记录
	 * @param sql
	 * @param beanInstance
	 * @return List<Map>: 每一元素Map中应一行, Map中的键对应数据库中的字段名, 注意全部大写
	 */
	public <T> T selectFirst(String sql, Object paramObj, Class<T> mapping);
	
	
	
	
	
	

}
