package com.binary.framework.ibatis.sharding;

import java.sql.Connection;
import java.sql.SQLException;

import com.binary.core.util.BinaryUtils;
import com.binary.framework.exception.FrameworkException;
import com.binary.framework.ibatis.IBatisSqlExecutor;
import com.binary.framework.ibatis.ShardingDataSource;
import com.binary.framework.ibatis.SqlHandler;
import com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback;
import com.ibatis.sqlmap.engine.scope.StatementScope;


/**
 * 替换分表标识
 * @author wanwb
 */
public class ShardingSqlHandler implements SqlHandler {

	
	private String leftLimit = "<";
	private String shardingKey = "SHARDING_KEY";
	private String rightLimit = ">";
	
	private String shardingReplaceRegex;
	
	
	private final ThreadLocal<ShardingDataSource> shardingDataSourceLocal = new ThreadLocal<ShardingDataSource>();
	
	
	
	public ShardingSqlHandler() {
		resetShardingRegex();
	}
	
	
	public void setShardingDataSource(ShardingDataSource shardingDataSource) {
		shardingDataSourceLocal.set(shardingDataSource);
	}
	public void removeShardingDataSource() {
		shardingDataSourceLocal.remove();
	}
	private ShardingDataSource getShardingDataSource() {
		ShardingDataSource ds = shardingDataSourceLocal.get();
		if(ds == null) throw new FrameworkException(" not setting ShardingDataSource! ");
		return ds;
	}
	
	
	
	public String getLeftLimit() {
		return leftLimit;
	}
	public void setLeftLimit(String leftLimit) {
		BinaryUtils.checkEmpty(leftLimit, "leftLimit");
		this.leftLimit = leftLimit.trim();
		resetShardingRegex();
	}
	public String getShardingKey() {
		return shardingKey;
	}
	public void setShardingKey(String shardingKey) {
		BinaryUtils.checkEmpty(shardingKey, "shardingKey");
		this.shardingKey = shardingKey.trim();
		resetShardingRegex();
	}
	public String getRightLimit() {
		return rightLimit;
	}
	public void setRightLimit(String rightLimit) {
		BinaryUtils.checkEmpty(rightLimit, "rightLimit");
		this.rightLimit = rightLimit.trim();
		resetShardingRegex();
	}
	
	
	
	private void resetShardingRegex() {
		this.shardingReplaceRegex = this.leftLimit + "[\\s]*" + this.shardingKey + "[\\s]*" + this.rightLimit;
	}
	
	
	
	
	
	@Override
	public String parseQuerySql(StatementScope statementScope, Connection conn,
									String sql, Object[] parameters, int skipResults, int maxResults,
									RowHandlerCallback callback, IBatisSqlExecutor sqlExecutor) throws SQLException {
		ShardingDataSource ds = getShardingDataSource();
		String tableName = ds.getShardingTableName();
		return sql.replaceAll(this.shardingReplaceRegex, tableName);
	}

	
	
	
	
	
	@Override
	public String parseUpdateSql(StatementScope statementScope,
									Connection conn, String sql, Object[] parameters,
									IBatisSqlExecutor sqlExecutor) throws SQLException {
		ShardingDataSource ds = getShardingDataSource();
		String tableName = ds.getShardingTableName();
		return sql.replaceAll(this.shardingReplaceRegex, tableName);
	}
	
	
	

}
