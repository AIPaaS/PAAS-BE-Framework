package com.binary.jdbc.ds.support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.binary.core.lang.ClassUtils;
import com.binary.jdbc.JdbcType;
import com.binary.jdbc.exception.DataSourceException;

public class SimpleDataSource extends AbstractDataSource {
	private static final long serialVersionUID = -6831307255144410194L;
	
	
	private String driver;
	private String url;
	private String username;
	private String password;
	
	
	public SimpleDataSource(String name, JdbcType jdbcType) {
		super(name, jdbcType);
	}
	
	
	
	protected Connection newConnection() {
		try {
			return DriverManager.getConnection(this.url, this.username, this.password);
		} catch (SQLException e) {
			throw new DataSourceException(e);
		}
	}



	public String getDriver() {
		return driver;
	}



	public void setDriver(String driver) {
		if(driver==null || (driver=driver.trim()).length()==0) throw new DataSourceException(" the driver is empty argument! ");
		ClassUtils.forName(driver);
		this.driver = driver;
	}



	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		if(url==null || (url=url.trim()).length()==0) throw new DataSourceException(" the url is empty argument! ");
		this.url = url;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		if(password==null || (password=password.trim()).length()==0) throw new DataSourceException(" the password is empty argument! ");
		this.password = password;
	}
	
	
	
	
	
	
	
}


