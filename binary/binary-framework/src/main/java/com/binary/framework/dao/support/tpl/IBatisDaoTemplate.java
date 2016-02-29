package com.binary.framework.dao.support.tpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.binary.core.util.BinaryUtils;
import com.binary.framework.Local;
import com.binary.framework.bean.Condition;
import com.binary.framework.bean.EntityBean;
import com.binary.framework.dao.support.AbstractIBatisDao;
import com.binary.framework.ibatis.IBatisUtils;
import com.binary.framework.util.PrimaryKey;
import com.binary.jdbc.Page;


public abstract class IBatisDaoTemplate<E extends EntityBean, F extends Condition> extends AbstractIBatisDao<E, F> {

	
	/**
	 * 补充数组类型条件
	 * @param cdt
	 * @param map
	 */
	protected void fillCondition(F cdt, Map<String, Object> map) {
		Map<String, String> arrmap = IBatisUtils.extractArrayFields(cdt);
		if(arrmap.size() > 0) {
			map.putAll(arrmap);
		}
	}
	
	
	
	@Override
	public Page<E> selectPage(long pageNum, long pageSize, F cdt, String orders) {
		if(cdt == null) cdt = newCondition();
		setDataStatusValue(cdt, 1);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cdt", cdt);
		fillCondition(cdt, map);
		map.put("orders", orders);
		Page<E> page = IBatisUtils.selectPage(getSqlMapClientTemplate(), getTableName()+".selectList", map, pageNum, pageSize, true);
		return page;
	}

	
	
	@Override
	public List<E> selectList(long pageNum, long pageSize, F cdt, String orders) {
		if(cdt == null) cdt = newCondition();
		setDataStatusValue(cdt, 1);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cdt", cdt);
		fillCondition(cdt, map);
		map.put("orders", orders);
		Page<E> page = IBatisUtils.selectPage(getSqlMapClientTemplate(), getTableName()+".selectList", map, pageNum, pageSize, false);
		return page.getData();
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<E> selectList(F cdt, String orders) {
		if(cdt == null) cdt = newCondition();
		setDataStatusValue(cdt, 1);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cdt", cdt);
		fillCondition(cdt, map);
		map.put("orders", orders);
		List<E> list = getSqlMapClientTemplate().queryForList(getTableName()+".selectList", map);
		return list;
	}


	@Override
	public long selectCount(F cdt) {
		if(cdt == null) cdt = newCondition();
		setDataStatusValue(cdt, 1);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cdt", cdt);
		fillCondition(cdt, map);
		long count = (Long)getSqlMapClientTemplate().queryForObject(getTableName()+".selectCount", map);
		return count;
	}


	
	@SuppressWarnings("unchecked")
	@Override
	public E selectById(long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		E record = (E)getSqlMapClientTemplate().queryForObject(getTableName()+".selectById", map);
		return record;
	}
	
	


	@Override
	public long insert(E record) {
		IBatisUtils.validateEntityEmpty(record);
		
		String tableName = getTableName();
		
		Long id = record.getId();
		if(id == null) {
			id = PrimaryKey.getInstance(tableName).next();
			record.setId(id);
		}
		
		long time = BinaryUtils.getNumberDateTime();
		String loguser = Local.getLogUser();
		
		setDataStatusValue(record, 1);
		record.setCreateTime(time);
		record.setModifyTime(time);
		setCreatorValue(record, loguser);
		setModifierValue(record, loguser);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("record", record);
		getSqlMapClientTemplate().insert(tableName+".insert", map);
		
		return id;
	}


	@Override
	public long[] insertBatch(List<E> records) {
		if(records == null) return null;
		if(records.size() == 0) return new long[0];
		
		String tableName = getTableName();
		
		long[] cs = new long[records.size()];
		long time = BinaryUtils.getNumberDateTime();
		String loguser = Local.getLogUser();
		
		PrimaryKey key = PrimaryKey.getInstance(tableName);
		for(int i=0; i<cs.length; i++) {
			E record = records.get(i);
			Long id = record.getId();
			if(id == null) {
				id = key.next();
				record.setId(id);
			}
			
			setDataStatusValue(record, 1);
			record.setCreateTime(time);
			record.setModifyTime(time);
			setCreatorValue(record, loguser);
			setModifierValue(record, loguser);
			
			cs[i] = id;
		}
		
		SqlMapClientTemplate client = getSqlMapClientTemplate();
		Map<String, Object> map = new HashMap<String, Object>();
		for(int i=0; i<cs.length; i++) {
			E record = records.get(i);
			map.put("record", record);
			client.insert(tableName+".insert", map);
		}
		
		return cs;
	}


	@Override
	public int updateById(E record, long id) {
		IBatisUtils.validateEntityEmpty(record);
		
		long time = BinaryUtils.getNumberDateTime();
		record.setModifyTime(time);
		setModifierValue(record, Local.getLogUser());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("record", record);
		map.put("id", id);
		int count = getSqlMapClientTemplate().update(getTableName()+".updateById", map);
		
		return count;
	}


	@Override
	public int updateByCdt(E record, F cdt) {
		IBatisUtils.validateEntityEmpty(record);
		IBatisUtils.validateConditionEmpty(cdt);
		
		long time = BinaryUtils.getNumberDateTime();
		record.setModifyTime(time);
		setModifierValue(record, Local.getLogUser());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("record", record);
		map.put("cdt", cdt);
		fillCondition(cdt, map);
		int count = getSqlMapClientTemplate().update(getTableName()+".updateByCdt", map);
		
		return count;
	}


	@Override
	public int[] updateBatch(List<E> records) {
		if(records == null) return null;
		if(records.size() == 0) return new int[0];
		
		int[] counts = new int[records.size()];
		long time = BinaryUtils.getNumberDateTime();
		String loguser = Local.getLogUser();
		
		SqlMapClientTemplate client = getSqlMapClientTemplate();
		Map<String, Object> map = new HashMap<String, Object>();
		for(int i=0; i<counts.length; i++) {
			E record = records.get(i);
			record.setModifyTime(time);
			setModifierValue(record, loguser);
			
			map.put("record", record);
			map.put("id", record.getId());
			counts[i] = client.update(getTableName()+".updateById", map);
		}
		return counts;
	}


	@Override
	public int deleteById(long id) {
		if(hasDataStatusField()) {
			E record = newEntity();
			setDataStatusValue(record, 0);
			return updateById(record, id);
		}else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			return getSqlMapClientTemplate().delete(getTableName()+".deleteById", map);
		}
	}


	
	
	@Override
	public int deleteByCdt(F cdt) {
		if(hasDataStatusField()) {
			E record = newEntity();
			setDataStatusValue(record, 0);
			return updateByCdt(record, cdt);
		}else {
			IBatisUtils.validateConditionEmpty(cdt);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cdt", cdt);
			fillCondition(cdt, map);
			return getSqlMapClientTemplate().delete(getTableName()+".deleteByCdt", map);
		}
	}
	


	@Override
	public int[] deleteBatch(long[] ids) {
		if(ids == null) return null;
		if(ids.length == 0) return new int[0];
		
		if(hasDataStatusField()) {
			List<E> records = new ArrayList<E>();
			for(int i=0; i<ids.length; i++) {
				E record = newEntity();
				record.setId(ids[i]);
				setDataStatusValue(record, 0);
				records.add(record);
			}
			
			return updateBatch(records);
		}else {
			Map<String, Object> map = new HashMap<String, Object>();
			int[] counts = new int[ids.length];
			for(int i=0; i<ids.length; i++) {
				map.put("id", ids[i]);
				counts[i] = getSqlMapClientTemplate().delete(getTableName()+".deleteById", map);
			}
			return counts;
		}
	}
	
	
	
	
	
	
}
