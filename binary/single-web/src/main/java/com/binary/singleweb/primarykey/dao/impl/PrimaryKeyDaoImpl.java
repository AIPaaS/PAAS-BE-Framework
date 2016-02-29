package com.binary.singleweb.primarykey.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.binary.core.lang.Conver;
import com.binary.core.util.BinaryUtils;
import com.binary.jdbc.JdbcOperator;
import com.binary.jdbc.JdbcOperatorFactory;
import com.binary.singleweb.primarykey.dao.PrimaryKeyDao;

public class PrimaryKeyDaoImpl extends SqlMapClientDaoSupport implements PrimaryKeyDao {

	
	
	@Override
	public Long selectValue(String name) {
		String sql = " select a.value V from primary_key a where a.name=? ";
		
		JdbcOperator op = JdbcOperatorFactory.getMomentFactory().getJdbcOperator();
		
		List<Map<String,Object>> list = op.executeQuery(sql, new Object[]{name.trim().toUpperCase()});
		if(list.size() == 0) return null;
		
		Object value = list.get(0).get("V");
		
		return Conver.to(value, Long.class);
	}
	
	
	

	@Override
	public void insert(String name, Long value) {
		Long time = BinaryUtils.getNumberDateTime();
		String sql = " insert into primary_key(name,value,CREATE_TIME,MODIFY_TIME) values(?,?,"+time+","+time+") ";
		
		JdbcOperator op = JdbcOperatorFactory.getMomentFactory().getJdbcOperator();
		op.executeUpdate(sql, new Object[]{name.trim().toUpperCase(), value});
	}
	
	
	

	@Override
	public void update(String name, Long value) {
		Long time = BinaryUtils.getNumberDateTime();
		String sql = " update primary_key set value=?,MODIFY_TIME="+time+" where name=? ";
		
		JdbcOperator op = JdbcOperatorFactory.getMomentFactory().getJdbcOperator();
		op.executeUpdate(sql, new Object[]{value, name.trim().toUpperCase()});
	}
	
	
	
	
	
}
