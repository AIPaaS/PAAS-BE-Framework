package com.binary.tools.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.binary.core.bean.BMProxy;


public interface ColumnRender {
	
	
	/**
	 * 列数据写入Excel时所调用方法
	 * @param value: 当前表格值
	 * @param rowData: 当前行值
	 * @param rowIndex: 当前所在行数
	 * @param colIndex: 当前所在列数
	 * @param column: 当前列对象
	 * @param cell: 当前表格对象
	 * @return 显示值
	 */
	public Object onRender(Object value, BMProxy<?> rowData, int rowIndex, int colIndex, Column column, HSSFCellStyle cellstyle, CellStyle cell, HSSFWorkbook hwb) ;
	
	
	
	
}


