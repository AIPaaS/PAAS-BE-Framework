package com.binary.framework.ibatis;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.Resource;
import org.springframework.util.ObjectUtils;

import com.ibatis.common.xml.NodeletException;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.builder.xml.SqlMapConfigParser;
import com.ibatis.sqlmap.engine.builder.xml.SqlMapParser;
import com.ibatis.sqlmap.engine.builder.xml.XmlParserState;

public class IBatisSqlMapClientFactory implements FactoryBean<SqlMapClient>, InitializingBean {


	private Resource[] configLocations;

	private Resource[] mappingLocations;

	private Properties sqlMapClientProperties;

	private SqlMapClient sqlMapClient;


	public IBatisSqlMapClientFactory() {
	}

	public void setConfigLocation(Resource configLocation) {
		this.configLocations = (configLocation != null ? new Resource[] {configLocation} : null);
	}
	
	
	public void setConfigLocations(Resource[] configLocations) {
		this.configLocations = configLocations;
	}

	public void setMappingLocations(Resource[] mappingLocations) {
		this.mappingLocations = mappingLocations;
	}

	public void setSqlMapClientProperties(Properties sqlMapClientProperties) {
		this.sqlMapClientProperties = sqlMapClientProperties;
	}


	public void afterPropertiesSet() throws Exception {
		this.sqlMapClient = buildSqlMapClient(this.configLocations, this.mappingLocations, this.sqlMapClientProperties);
	}

	protected SqlMapClient buildSqlMapClient(Resource[] configLocations, Resource[] mappingLocations, Properties properties) throws IOException {
		if (ObjectUtils.isEmpty(configLocations)) {
			throw new IllegalArgumentException("At least 1 'configLocation' entry is required");
		}

		SqlMapClient client = null;
		SqlMapConfigParser configParser = new SqlMapConfigParser();
		for (Resource configLocation : configLocations) {
			InputStream is = configLocation.getInputStream();
			try {
				client = configParser.parse(is, properties);
			}
			catch (RuntimeException ex) {
				throw new NestedIOException("Failed to parse config resource: " + configLocation, ex.getCause());
			}
		}

		if (mappingLocations != null) {
			SqlMapParser mapParser = createSqlMapParser(configParser);
			for (Resource mappingLocation : mappingLocations) {
				try {
					mapParser.parse(mappingLocation.getInputStream());
				}catch (NodeletException ex) {
					throw new NestedIOException("Failed to parse mapping resource: " + mappingLocation, ex);
				}
			}
		}

		return client;
	}

	public SqlMapClient getObject() {
		return this.sqlMapClient;
	}

	public Class<? extends SqlMapClient> getObjectType() {
		return (this.sqlMapClient != null ? this.sqlMapClient.getClass() : SqlMapClient.class);
	}

	public boolean isSingleton() {
		return true;
	}


	private static SqlMapParser createSqlMapParser(SqlMapConfigParser configParser) {
		// Ideally: XmlParserState state = configParser.getState();
		// Should raise an enhancement request with iBATIS...
		XmlParserState state = null;
		try {
			Field stateField = SqlMapConfigParser.class.getDeclaredField("state");
			stateField.setAccessible(true);
			state = (XmlParserState) stateField.get(configParser);
		}
		catch (Exception ex) {
			throw new IllegalStateException("iBATIS 2.3.2 'state' field not found in SqlMapConfigParser class - " +
					"please upgrade to IBATIS 2.3.2 or higher in order to use the new 'mappingLocations' feature. " + ex);
		}
		return new SqlMapParser(state);
	}
	
	

}
