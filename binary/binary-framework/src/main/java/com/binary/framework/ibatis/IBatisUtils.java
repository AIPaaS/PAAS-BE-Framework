package com.binary.framework.ibatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.binary.core.bean.BMProxy;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.bean.Condition;
import com.binary.framework.bean.EntityBean;
import com.binary.framework.exception.DaoException;
import com.binary.jdbc.Page;

public abstract class IBatisUtils {
	
	
	
	public static <T> Page<T> selectPage(SqlMapClientTemplate smct, String statementName, int pageNum, int pageSize) throws DataAccessException {
		return selectPage(smct, statementName, null, pageNum, pageSize, true);
	}
	
	
	public static <T> Page<T> selectPage(SqlMapClientTemplate smct, String statementName, Object parameterObject, long pageNum, long pageSize)	throws DataAccessException {
		return selectPage(smct, statementName, parameterObject, pageNum, pageSize, true);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Page<T> selectPage(SqlMapClientTemplate smct, String statementName, Object parameterObject, long pageNum, long pageSize, boolean appendCount)	throws DataAccessException {
		Page<T> page = new Page<T>();
		page.setPageNum(pageNum);
		page.setPageSize(pageSize);
		
		PagingSqlHandler.setLocalObject(page, smct.getDataSource(), appendCount);
		List<T> list = null;
		try {
			list = smct.queryForList(statementName, parameterObject);
		}finally {
			PagingSqlHandler.removeLocalObject();
		}
		
		if(list == null) list = new ArrayList<T>();
		page.setData(list);
		
		return page;
	}
	
	
	
	/**
	 * 验证实体对象中的值是否都是空, 如果是则抛异常
	 * @param entity
	 */
	public static void validateEntityEmpty(EntityBean entity) {
		if(isEntityEmpty(entity)) {
			throw new DaoException(" the entity object is empty! ");
		}
	}
	
	
	/**
	 * 验证条件对象中的值是否都是空, 如果是则抛异常
	 * @param cdt
	 */
	public static void validateConditionEmpty(Condition cdt) {
		validateConditionEmpty(cdt, true);
	}
	
	
	
	
	/**
	 * 验证条件对象中的值是否都是空, 如果是则抛异常
	 * @param cdt
	 * @param trim
	 */
	public static void validateConditionEmpty(Condition cdt, boolean trim) {
		if(isConditionEmpty(cdt, trim)) {
			throw new DaoException(" the condition object is empty! ");
		}
	}
	
	
	
	
	/**
	 * 验证实体对象中的值是否都是空
	 * @param obj
	 * @return
	 */
	public static boolean isEntityEmpty(EntityBean entity) {
		return isEmpty(entity, false, true);
	}
	
	
	/**
	 * 验证条件对象中的值是否都是空
	 * @param cdt
	 * @return
	 */
	public static boolean isConditionEmpty(Condition cdt) {
		return isEmpty(cdt, true, false);
	}

	
	
	/**
	 * 验证条件对象中的值是否都是空
	 * @param cdt
	 * @param trim
	 * @return
	 */
	public static boolean isConditionEmpty(Condition cdt, boolean trim) {
		return isEmpty(cdt, trim, false);
	}
	
	
	
	private static boolean isEmpty(Object obj, boolean trim, boolean isnull) {
		if(obj == null) return true;
		
		BMProxy<?> proxy = BMProxy.getInstance(obj);
		Iterator<Object> itor = proxy.valuesIterator();
		boolean ba = true;
		
		while(itor.hasNext()) {
			Object v = itor.next();
			if(isnull) {
				if(v != null) {
					ba = false;
					break;
				}
			}else {
				if(!BinaryUtils.isEmpty(v, trim)) {
					ba = false;
					break;
				}
			}
		}
		
		return ba;
	}
	


	/**
	 * 提取条件对象中的数组值
	 * @param cdt
	 * @return
	 */
	public static Map<String, String> extractArrayFields(Condition cdt) {
		Map<String, String> map = new HashMap<String, String>();
		if(cdt != null) {
			BMProxy<?> proxy = BMProxy.getInstance(cdt);
			Iterator<Entry<String, Object>> itor = proxy.entryIterator();
			StringBuilder sb = new StringBuilder();
			while(itor.hasNext()) {
				Entry<String, Object> e = itor.next();
				String key = e.getKey();
				Object value = e.getValue();
				
				if(value==null || !value.getClass().isArray()) continue ;
				
				Object[] array = (Object[])value;
				if(array.length == 0) continue ;
				Class<?> comptype = array.getClass().getComponentType();
				boolean isstring = String.class.isAssignableFrom(comptype);
				
				if(sb.length() > 0) sb.delete(0, sb.length());
				for(int j=0; j<array.length; j++) {
					if(j > 0) sb.append(",");
					if(isstring) sb.append('\'');
					sb.append(array[j]);
					if(isstring) sb.append('\'');
				}
				
				map.put(key, sb.toString());
			}
		}
		return map;
	}
	
	
	



}
