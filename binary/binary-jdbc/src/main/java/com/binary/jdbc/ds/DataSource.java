package com.binary.jdbc.ds;

import java.io.Serializable;
import java.sql.Connection;

import com.binary.jdbc.JdbcType;
import com.binary.jdbc.print.Printer;


/**
 * 数据源
 * @author wanwb
 */
public interface DataSource extends Serializable {
	
	
	
	/**
	 * 获取数据库连接
	 * @return
	 */
	public Connection getConnection();
	
	
	/**
	 * 获取数据库连接
	 * @return
	 */
	public Connection getConnection(Boolean readOnly, TransactionIsolation transactionIsolation);
	
	
	
	/**
	 * 验证链接是否成功
	 */
	public void validate(Printer printer);
	
	
	
	/**
	 * 获取数据源名称
	 * @return
	 */
	public String getName();

	

	/**
	 * 判断数据源是否只读
	 * @return
	 */
	public Boolean getReadOnly();


	/**
	 * 获取数据源事物级别
	 * @return
	 */
	public TransactionIsolation getTransactionIsolation();


	
	/**
	 * 获取数据库类型
	 * @return
	 */
	public JdbcType getJdbcType();
	
	
	
	
	
	
	
	
}
