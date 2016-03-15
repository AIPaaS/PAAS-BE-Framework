package com.binary.framework.dao.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.binary.core.bean.BMProxy;
import com.binary.core.lang.ArrayUtils;
import com.binary.core.lang.ClassUtils;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.bean.Condition;
import com.binary.framework.bean.EntityBean;
import com.binary.framework.dao.Dao;
import com.binary.framework.dao.DaoDefinition;
import com.binary.framework.exception.DaoException;
import com.binary.framework.ibatis.IBatisUtils;


/**
 * 
 * @author vosnow
 *
 * @param <E>
 * @param <F>
 */
public abstract class AbstractDao<E extends EntityBean, F extends Condition> implements Dao<E, F> {

	
	private DaoDefinition<E, F> daoDefinition;
	
	
	public void setDaoDefinition(DaoDefinition<E, F> daoDefinition) {
		BinaryUtils.checkEmpty(daoDefinition, "daoDefinition");
		this.daoDefinition = daoDefinition;
	}
	
	
	/**
	 * 获取DAO定义对象
	 * @return
	 */
	public DaoDefinition<E, F> getDaoDefinition() {
		if(this.daoDefinition == null) {
			throw new DaoException(" not setting 'com.binary.framework.dao.DaoDefinition' in '"+getClass().getName()+"'! ");
		}
		return this.daoDefinition;
	}
	
		
	
	/**
	 * 获取映射表名
	 * @return
	 */
	protected String getTableName() {
		return getDaoDefinition().getTableName();
	}
	
	
	/**
	 * 获取实体类型
	 * @return
	 */
	protected Class<E> getEntityClass() {
		return getDaoDefinition().getEntityClass();
	}
	
	/**
	 * 获取条件对象类型
	 * @return
	 */
	protected Class<F> getConditionClass() {
		return getDaoDefinition().getConditionClass();
	}
	
	
	
	/**
	 * 创建实体实例
	 * @return
	 */
	protected E newEntity() {
		return BMProxy.getInstance(getEntityClass()).newInstance();
	}
	
	
	/**
	 * 创建条件对象实例
	 * @return
	 */
	protected F newCondition() {
		return BMProxy.getInstance(getConditionClass()).newInstance();
	}
	
	
	
	/**
	 * 是否含有DataStatusField
	 * @return
	 */
	protected boolean hasDataStatusField() {
		return getDaoDefinition().hasDataStatusField();
	}
	
	
	/**
	 * 对实体对象设置值
	 * @param e
	 * @param value
	 */
	protected void setDataStatusValue(E e, int value) {
		getDaoDefinition().setDataStatusValue(e, value);
	}
	
	
	/**
	 * 对条件对象设置值
	 * @param e
	 * @param value
	 */
	protected void setDataStatusValue(F f, int value) {
		DaoDefinition<E, F> def = getDaoDefinition();
		if(def.hasDataStatusField()) {
			BMProxy<F> proxy = BMProxy.getInstance(f);
			if(proxy.get("dataStatus") == null) {
				def.setDataStatusValue(f, value);
			}
		}
	}
	
		
	/**
	 * 设置创建人字段
	 * @param e
	 * @param creator
	 */
	protected void setCreatorValue(E e, String creator) {
		getDaoDefinition().setCreatorValue(e, creator);
	}
	
	
	/**
	 * 设置修改人字段
	 * @param e
	 * @param modifier
	 */
	protected void setModifierValue(E e, String modifier) {
		getDaoDefinition().setModifierValue(e, modifier);
	}
	
	
	
	
	@Override
	public long save(E record) {
		IBatisUtils.validateEntityEmpty(record);
		
		Long id = record.getId();
		if(BinaryUtils.isEmpty(id)) {
			id = insert(record);
		}else {
			updateById(record, id);
		}
		
		return id;
	}
	
	
	
	
	@Override
	public long[] saveBatch(List<E> records) {
		if(records == null) return null;
		if(records.size() == 0) return new long[0];
		
		long[] ids = new long[records.size()];
		List<E> inserts = new ArrayList<E>();
		List<E> updates = new ArrayList<E>();
		List<Integer> indxs = new ArrayList<Integer>();
		
		for(int i=0; i<records.size(); i++) {
			E record = records.get(i);
			
			Long id = record.getId();
			if(BinaryUtils.isEmpty(id)) {
				inserts.add(record);
				indxs.add(i);
			}else {
				updates.add(record);
				ids[i] = id;
			}
		}
		
		if(inserts.size() > 0) {
			long[] newids = insertBatch(inserts);
			for(int i=0; i<newids.length; i++) {
				ids[indxs.get(i)] = newids[i];
			}
		}
		
		if(updates.size() > 0) {
			updateBatch(updates);
		}
		
		return ids;
	}
		


	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public long[] saveBatch(List<E> records, String verifyField) {
		BinaryUtils.checkEmpty(verifyField, "verifyField");
		verifyField = verifyField.trim();
		
		if(records == null) return null;
		if(records.size() == 0) return new long[0];
		
		Class<E> entityClass = getEntityClass();
		Class<F> conditionClass = getConditionClass();
		
		BMProxy<E> proxy = BMProxy.getInstance(entityClass);
		if(!proxy.containsKey(verifyField)) {
			throw new DaoException(" is not found property '"+verifyField+"' in bean-class '"+entityClass.getName()+"'! ");
		}
		
		F cdt = ClassUtils.newInstance(conditionClass);
		BMProxy<F> cdtproxy = BMProxy.getInstance(cdt);
		if(!cdtproxy.containsKey(verifyField+"s")) {
			throw new DaoException(" is not found property '"+verifyField+"s"+"' in cdt-class '"+conditionClass.getName()+"'! ");
		}
		
		if(verifyField.equalsIgnoreCase("id")) {
			throw new DaoException(" verifyField can not be 'id', if there is 'id' please call method saveBatch(List<"+entityClass.getSimpleName()+"> records)! ");
		}
		
		List<Object> fields = new ArrayList<Object>(records.size());
		Map<Object, E> map = new HashMap<Object, E>(records.size());
		
		for(int i=0; i<records.size(); i++) {
			E record = records.get(i);
			record.setId(null);
			proxy.replaceInnerObject(record);
			
			Object value = proxy.get(verifyField);
			if(BinaryUtils.isEmpty(value)) {
				throw new DaoException(" the records["+i+"]'s verifyField '"+verifyField+"' is empty! ");
			}
			if(map.containsKey(value)) {
				throw new DaoException(" the records["+i+"]'s verifyField '"+verifyField+"' is repeated! ");
			}
			
			fields.add(value);
			map.put(value, record);
		}
		
		Object[] array = ArrayUtils.toArray(fields, (Class)proxy.getPorpertyType(verifyField));
		cdtproxy.set(verifyField+"s", array);
		
		List<E> ls = selectList(cdt, null);
		for(int i=0; i<ls.size(); i++) {
			E idrecord = ls.get(i);
			Long id = idrecord.getId();
			
			proxy.replaceInnerObject(idrecord);
			Object value = proxy.get(verifyField);
			
			E record = map.get(value);
			record.setId(id);
		}
		
		return saveBatch(records);
	}
	
	
	
	
}
