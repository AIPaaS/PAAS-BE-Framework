package com.binary.framework.ibatis.sharding;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.util.Assert;

import com.binary.framework.exception.FrameworkException;
import com.binary.framework.ibatis.IBatisSqlExecutor;
import com.binary.framework.ibatis.IBatisSqlMapClientTemplate;
import com.binary.framework.ibatis.ShardingDataSource;
import com.binary.framework.ibatis.SqlHandler;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.ibatis.sqlmap.client.SqlMapSession;
import com.ibatis.sqlmap.client.event.RowHandler;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;



/**
 *<p>Title: 扩展的IBatisSqlMapClientTemplate, 添加分片处理</p>
 *@author wanwb
 */
@SuppressWarnings("rawtypes")
public class ShardingSqlMapClientTemplate extends IBatisSqlMapClientTemplate {
	
	
	
	/**
	 * @deprecated @see execute(SqlMapClientCallback<T> action, String statementName, Object parameterObject)
	 */
	@Deprecated
	@Override
	public <T> T execute(SqlMapClientCallback<T> action) throws DataAccessException {
		throw new FrameworkException(" not support execute(SqlMapClientCallback<T> action) ");
	}

	
	
	public <T> T execute(String statementName, Object parameterObject, SqlMapClientCallback<T> action) throws DataAccessException {
		Assert.notNull(action, "Callback object must not be null");
		
		SqlMapClient client = getSqlMapClient();
		Assert.notNull(client, "No SqlMapClient specified");

		SqlMapSession session = client.openSession();
		if (logger.isDebugEnabled()) {
			logger.debug("Opened SqlMapSession [" + session + "] for iBATIS operation");
		}
		
		T result = null;
		Connection ibatisConn = null;
		try {
			try {
				DataSource dataSource = getDataSource();
				ibatisConn = session.getCurrentConnection();
				
				//如果为分片数据源
				if(dataSource instanceof ShardingDataSource) {
					ShardingDataSource ds = (ShardingDataSource)dataSource;
					try {
						setShardingDataSource2ShardingSqlHandler(ds);
						
						ds.setShardingParameter(statementName, parameterObject);
						Connection conn = ds.getConnection();
						if(ibatisConn==null || ibatisConn!=conn) {
							session.setUserConnection(conn);
						}
						result = action.doInSqlMapClient(session);
					}finally {
						removeShardingDataSource2ShardingSqlHandler();
						ds.removeShardingParameter();
					}
				}else {
					if (ibatisConn == null) {
						session.setUserConnection(dataSource.getConnection());
					}
					result = action.doInSqlMapClient(session);
				}
			}catch (SQLException ex) {
				throw new CannotGetJdbcConnectionException("Could not get JDBC Connection", ex);
			}
		}finally {
			// Only close SqlMapSession if we know we've actually opened it
			// at the present level.
			if (ibatisConn == null) {
				session.close();
			}
		}
		
		return result;
	}
	
	
	
	/**
	 * 将分片数据源分发给所有ShardingSqlHandler
	 * @param ds
	 */
	private void setShardingDataSource2ShardingSqlHandler(ShardingDataSource ds) {
		SqlExecutor se = getSqlExecutor();
		if(se instanceof IBatisSqlExecutor) {
			IBatisSqlExecutor ise = (IBatisSqlExecutor)se;
			Iterator<SqlHandler> itor = ise.getSqlHandlerIterator();
			while(itor.hasNext()) {
				SqlHandler handler = itor.next();
				if(handler instanceof ShardingSqlHandler) {
					((ShardingSqlHandler)handler).setShardingDataSource(ds);
				}
			}
		}
	}
	
	/**
	 * 去除所有ShardingSqlHandler中的分片数据源
	 * @param ds
	 */
	private void removeShardingDataSource2ShardingSqlHandler() {
		SqlExecutor se = getSqlExecutor();
		if(se instanceof IBatisSqlExecutor) {
			IBatisSqlExecutor ise = (IBatisSqlExecutor)se;
			Iterator<SqlHandler> itor = ise.getSqlHandlerIterator();
			while(itor.hasNext()) {
				SqlHandler handler = itor.next();
				if(handler instanceof ShardingSqlHandler) {
					((ShardingSqlHandler)handler).removeShardingDataSource();
				}
			}
		}
	}
	


	@Override
	public Object queryForObject(String statementName) throws DataAccessException {
		throw new FrameworkException(" not support queryForObject(String statementName) ");
	}


	@Override
	public Object queryForObject(final String statementName, final Object parameterObject) throws DataAccessException {
		return execute(statementName, parameterObject, new SqlMapClientCallback<Object>() {
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				return executor.queryForObject(statementName, parameterObject);
			}
		});
	}


	@Override
	public Object queryForObject(final String statementName, final Object parameterObject, final Object resultObject) throws DataAccessException {
		return execute(statementName, parameterObject, new SqlMapClientCallback<Object>() {
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				return executor.queryForObject(statementName, parameterObject, resultObject);
			}
		});
	}


	@Override
	public List queryForList(String statementName) throws DataAccessException {
		throw new FrameworkException(" not support queryForList(String statementName) ");
	}


	@Override
	public List queryForList(final String statementName, final Object parameterObject) throws DataAccessException {
		return execute(statementName, parameterObject, new SqlMapClientCallback<List>() {
			public List doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				return executor.queryForList(statementName, parameterObject);
			}
		});
	}


	@Override
	public List queryForList(String statementName, int skipResults, int maxResults) throws DataAccessException {
		throw new FrameworkException(" not support queryForList(String statementName, int skipResults, int maxResults) ");
	}


	@Override
	public List queryForList(final String statementName, final Object parameterObject, final int skipResults, final int maxResults) throws DataAccessException {
		return execute(statementName, parameterObject, new SqlMapClientCallback<List>() {
			public List doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				return executor.queryForList(statementName, parameterObject, skipResults, maxResults);
			}
		});
	}


	@Override
	public void queryWithRowHandler(String statementName, RowHandler rowHandler) throws DataAccessException {
		throw new FrameworkException(" not support queryWithRowHandler(String statementName, RowHandler rowHandler) ");
	}


	@Override
	public void queryWithRowHandler(final String statementName, final Object parameterObject, final RowHandler rowHandler) throws DataAccessException {
		execute(statementName, parameterObject, new SqlMapClientCallback<Object>() {
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				executor.queryWithRowHandler(statementName, parameterObject, rowHandler);
				return null;
			}
		});
	}


	
	@Override
	public Map queryForMap(final String statementName, final Object parameterObject, final String keyProperty)
			throws DataAccessException {
		return execute(statementName, parameterObject, new SqlMapClientCallback<Map>() {
			public Map doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				return executor.queryForMap(statementName, parameterObject, keyProperty);
			}
		});
	}


	@Override
	public Map queryForMap(final String statementName, final Object parameterObject, final String keyProperty, final String valueProperty) throws DataAccessException {
		return execute(statementName, parameterObject, new SqlMapClientCallback<Map>() {
			public Map doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				return executor.queryForMap(statementName, parameterObject, keyProperty, valueProperty);
			}
		});
	}


	@Override
	public Object insert(String statementName) throws DataAccessException {
		throw new FrameworkException(" not support insert(String statementName) ");
	}


	@Override
	public Object insert(final String statementName, final Object parameterObject) throws DataAccessException {
		return execute(statementName, parameterObject, new SqlMapClientCallback<Object>() {
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				return executor.insert(statementName, parameterObject);
			}
		});
	}


	@Override
	public int update(String statementName) throws DataAccessException {
		throw new FrameworkException(" not support update(String statementName) ");
	}


	@Override
	public int update(final String statementName, final Object parameterObject) throws DataAccessException {
		return execute(statementName, parameterObject, new SqlMapClientCallback<Integer>() {
			public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				return executor.update(statementName, parameterObject);
			}
		});
	}
	


	@Override
	public int delete(String statementName) throws DataAccessException {
		throw new FrameworkException(" not support delete(String statementName) ");
	}


	@Override
	public int delete(final String statementName, final Object parameterObject) throws DataAccessException {
		return execute(statementName, parameterObject, new SqlMapClientCallback<Integer>() {
			public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				return executor.delete(statementName, parameterObject);
			}
		});
	}


	
	
	
	
	
}
