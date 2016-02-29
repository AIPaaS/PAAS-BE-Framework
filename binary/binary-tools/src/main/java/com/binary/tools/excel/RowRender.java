package com.binary.tools.excel;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.binary.core.bean.BMProxy;


public interface RowRender {
	
	
	/**
	 * 列数据写入Excel时所调用方法
	 * @param value: 当前表格值
	 * @param rowData: 当前行值
	 */
	public void onRender(BMProxy<?> rowData, int rowIndex, HSSFRow row, HSSFWorkbook hwb);
	
	
	
	
}


