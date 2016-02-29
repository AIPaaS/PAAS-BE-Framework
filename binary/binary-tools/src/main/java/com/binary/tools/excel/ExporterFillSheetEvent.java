package com.binary.tools.excel;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;


/**
 * 
 * @author wanwb
 *
 */
public class ExporterFillSheetEvent {
	
	/** 当前填充的sheet的下标位置 **/
	private int sheetIndex;
	
	/** 当前填充的sheet名称 **/
	private String sheetName;
	
	
	/** 当前填充的sheet **/
	private HSSFSheet sheet;
	
	
	/** 当前填充数据 **/
	private List<?> data;
	
	
	/** 当前Excel导出对象 **/
	private ExcelExporter exporter;
	
	
	private int rowNum;
	
	
	
	

	public ExporterFillSheetEvent(int sheetIndex, String sheetName, HSSFSheet sheet, List<?> data, ExcelExporter exporter, int rowNum) {
		this.sheetName = sheetName;
		this.sheet = sheet;
		this.data = data;
		this.exporter = exporter;
		this.rowNum = rowNum;
		this.sheetIndex = sheetIndex;
	}

	

	public String getSheetName() {
		return sheetName;
	}


	public HSSFSheet getSheet() {
		return sheet;
	}


	public List<?> getData() {
		return data;
	}


	public ExcelExporter getExporter() {
		return exporter;
	}



	public int getRowNum() {
		return rowNum;
	}



	void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}



	public int getSheetIndex() {
		return sheetIndex;
	}
	
	
	
	
	
	
	
	
	
}
