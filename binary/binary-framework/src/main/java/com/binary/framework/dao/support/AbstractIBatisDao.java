package com.binary.framework.dao.support;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.util.Assert;

import com.binary.framework.bean.Condition;
import com.binary.framework.bean.EntityBean;
import com.binary.jdbc.JdbcOperator;
import com.binary.jdbc.JdbcOperatorFactory;
import com.ibatis.sqlmap.client.SqlMapClient;


public abstract class AbstractIBatisDao<E extends EntityBean, F extends Condition> extends AbstractDao<E, F> implements InitializingBean {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	
	private SqlMapClientTemplate sqlMapClientTemplate = new SqlMapClientTemplate();
	private boolean externalTemplate = false;


	public final void afterPropertiesSet() throws IllegalArgumentException, BeanInitializationException {
		// Let abstract subclasses check their configuration.
		checkDaoConfig();

		// Let concrete implementations initialize themselves.
		try {
			initDao();
		}
		catch (Exception ex) {
			throw new BeanInitializationException("Initialization of DAO failed", ex);
		}
	}

	/**
	 * Concrete subclasses can override this for custom initialization behavior.
	 * Gets called after population of this instance's bean properties.
	 * @throws Exception if DAO initialization fails
	 * (will be rethrown as a BeanInitializationException)
	 * @see org.springframework.beans.factory.BeanInitializationException
	 */
	protected void initDao() throws Exception {
	}
	
	


	/**
	 * Set the JDBC DataSource to be used by this DAO.
	 * Not required: The SqlMapClient might carry a shared DataSource.
	 * @see #setSqlMapClient
	 */
	public final void setDataSource(DataSource dataSource) {
		if (!this.externalTemplate) {
	  	this.sqlMapClientTemplate.setDataSource(dataSource);
		}
	}

	/**
	 * Return the JDBC DataSource used by this DAO.
	 */
	public final DataSource getDataSource() {
		return this.sqlMapClientTemplate.getDataSource();
	}

	/**
	 * Set the iBATIS Database Layer SqlMapClient to work with.
	 * Either this or a "sqlMapClientTemplate" is required.
	 * @see #setSqlMapClientTemplate
	 */
	public final void setSqlMapClient(SqlMapClient sqlMapClient) {
		if (!this.externalTemplate) {
			this.sqlMapClientTemplate.setSqlMapClient(sqlMapClient);
		}
	}

	/**
	 * Return the iBATIS Database Layer SqlMapClient that this template works with.
	 */
	public final SqlMapClient getSqlMapClient() {
		return this.sqlMapClientTemplate.getSqlMapClient();
	}

	/**
	 * Set the SqlMapClientTemplate for this DAO explicitly,
	 * as an alternative to specifying a SqlMapClient.
	 * @see #setSqlMapClient
	 */
	public final void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		Assert.notNull(sqlMapClientTemplate, "SqlMapClientTemplate must not be null");
		this.sqlMapClientTemplate = sqlMapClientTemplate;
		this.externalTemplate = true;
	}

	/**
	 * Return the SqlMapClientTemplate for this DAO,
	 * pre-initialized with the SqlMapClient or set explicitly.
	 */
	public final SqlMapClientTemplate getSqlMapClientTemplate() {
	  return this.sqlMapClientTemplate;
	}

	
	
	protected final void checkDaoConfig() {
		if (!this.externalTemplate) {
			this.sqlMapClientTemplate.afterPropertiesSet();
		}
	}
	
	
	
	
	
	protected JdbcOperator getJdbcOperator() {
		JdbcOperatorFactory factory = JdbcOperatorFactory.getMomentFactory();
		JdbcOperator jo = null;
		DataSource ds = getDataSource();
		if(ds instanceof com.binary.jdbc.ds.DataSource) {
			String dsname = ((com.binary.jdbc.ds.DataSource)ds).getName();
			jo = factory.getJdbcOperator(dsname);
		}else {
			jo = factory.getJdbcOperator();
		}
		return jo;
	}
	
	
	
	protected JdbcOperator getJdbcOperator(String dsName) {
		JdbcOperatorFactory factory = JdbcOperatorFactory.getMomentFactory();
		return factory.getJdbcOperator(dsName);
	}
	
	
	
	
}
