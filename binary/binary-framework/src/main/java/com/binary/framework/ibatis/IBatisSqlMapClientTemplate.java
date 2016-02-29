package com.binary.framework.ibatis;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.util.Assert;

import com.binary.framework.exception.IBatisException;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapSession;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;
import com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate;



/**
 *<p>Title: 扩展的SqlMapClientTemplate</p>
 *@author wanwb
 */
public class IBatisSqlMapClientTemplate extends SqlMapClientTemplate {
	
	
	/** 替换ibatis中原生的SqlExecutor **/
	private SqlExecutor sqlExecutor;
	
	
	
	public SqlExecutor getSqlExecutor() {
		return sqlExecutor;
	}
	
	
	
	
	@Override
	public <T> T execute(SqlMapClientCallback<T> action) throws DataAccessException {
		Assert.notNull(action, "Callback object must not be null");
		
		SqlMapClient client = getSqlMapClient();
		Assert.notNull(client, "No SqlMapClient specified");

		SqlMapSession session = client.openSession();
		if (logger.isDebugEnabled()) {
			logger.debug("Opened SqlMapSession [" + session + "] for iBATIS operation");
		}
		
		Connection ibatisConn = null;
		try {
			try {
				DataSource dataSource = getDataSource();
				ibatisConn = session.getCurrentConnection();
				
				if (ibatisConn == null) {
					session.setUserConnection(dataSource.getConnection());
					if (logger.isDebugEnabled()) {
						logger.debug("Obtained JDBC Connection [" + ibatisConn + "] for iBATIS operation");
					}
				}else {
					if (logger.isDebugEnabled()) {
						logger.debug("Reusing JDBC Connection [" + ibatisConn + "] for iBATIS operation");
					}
				}
			}catch (SQLException ex) {
				throw new CannotGetJdbcConnectionException("Could not get JDBC Connection", ex);
			}
			
			try {
				return action.doInSqlMapClient(session);
			} catch (SQLException e) {
				throw new IBatisException(e);
			}
		}
		finally {
			// Only close SqlMapSession if we know we've actually opened it
			// at the present level.
			if (ibatisConn == null) {
				session.close();
			}
		}
	}
	
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setSqlExecutor(SqlExecutor sqlExecutor) throws Exception {
		SqlMapExecutorDelegate delegate = ((SqlMapClientImpl)getSqlMapClient()).getDelegate();
    	Class delegateclass = delegate.getClass();
    	try {
    		Method m = delegateclass.getDeclaredMethod("setSqlExecutor", SqlExecutor.class);
        	if(!Modifier.isPublic(m.getModifiers())) {
        		 m.setAccessible(true);
        	}
        	m.invoke(delegate, sqlExecutor);
    	}catch(Exception e) {
    		Field field = delegateclass.getDeclaredField("sqlExecutor");   
    		if (!Modifier.isPublic(field.getModifiers())) {   
    			field.setAccessible(true);   
    		}
    		field.set(delegate, sqlExecutor);   
    	}
		
		this.sqlExecutor = sqlExecutor;
	}
	
	
	
	
	
	
}
