package com.binary.jdbc;

import java.io.Serializable;
import java.util.List;

import com.binary.jdbc.ds.DataSource;
import com.binary.jdbc.print.PrinterType;
import com.binary.jdbc.print.PrinterWriterType;

public class JdbcOperatorConfig implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	
	/** 打印类型 **/
	private PrinterType printerType;
	
	
	/** 打印输出类型 **/
	private PrinterWriterType printerWriterType;
	
	
	/** printerWriterType如果指定为PrinterWriterType.FILE类型时, 需指定文件存文位置 **/
	private String printerWritePath;
	
	
	
	/** 指定缺省的数据源 **/
	private String defaultDataSource;
	
	
	/** 指定数据源资源池 **/
	private List<DataSource> dataSourceStore;
	
	
	
	


	public String getDefaultDataSource() {
		return defaultDataSource;
	}


	public void setDefaultDataSource(String defaultDataSource) {
		this.defaultDataSource = defaultDataSource;
	}


	public List<DataSource> getDataSourceStore() {
		return dataSourceStore;
	}


	public void setDataSourceStore(List<DataSource> dataSourceStore) {
		this.dataSourceStore = dataSourceStore;
	}


	public PrinterType getPrinterType() {
		return printerType;
	}


	public void setPrinterType(PrinterType printerType) {
		this.printerType = printerType;
	}


	public PrinterWriterType getPrinterWriterType() {
		return printerWriterType;
	}


	public void setPrinterWriterType(PrinterWriterType printerWriterType) {
		this.printerWriterType = printerWriterType;
	}


	public String getPrinterWritePath() {
		return printerWritePath;
	}


	public void setPrinterWritePath(String printerWritePath) {
		this.printerWritePath = printerWritePath;
	}

	
	
	
	
	
	
}
