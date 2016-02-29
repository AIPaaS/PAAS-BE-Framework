package com.binary.tools.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;

import com.binary.core.lang.Conver;
import com.binary.tools.exception.ExcelException;

public abstract class AbstractExcel {
	
	
	protected boolean isEmpty(Object v, boolean trim) {
		if(v == null) return true;
		if(v instanceof String) {
			String sv = (String) v;
			return trim ? sv.trim().length()==0 : sv.length()==0;
		}else {
			return false;
		}
	}
	
	
	protected static FileInputStream getFileInputStream(File file) {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new ExcelException(e);
		}
	}
	
	protected static FileOutputStream getFileOutputStream(File file) {
		try {
			return new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			throw new ExcelException(e);
		}
	}
	
	
	protected Object getCellValue(Cell cell) {
		if(cell == null) return null;
		switch (cell.getCellType()) { 
			case HSSFCell.CELL_TYPE_STRING: return cell.getStringCellValue(); 
			case HSSFCell.CELL_TYPE_NUMERIC: return Double.valueOf(cell.getNumericCellValue());
			case HSSFCell.CELL_TYPE_BOOLEAN: return Boolean.valueOf(cell.getBooleanCellValue());
			case HSSFCell.CELL_TYPE_BLANK: return "";
			default: {
				try {
					return cell.getDateCellValue();
				}catch(Exception e) {
					return cell.toString();
				}
			}
		}
	}
	
	
	
	protected void setCellValue(HSSFCell cell, Object value) {
		if(value == null) return ;
		if(value instanceof Boolean) {
			cell.setCellValue(((Boolean)value).booleanValue());
		}else if(value instanceof Calendar) {
			cell.setCellValue((Calendar)value);
		}else if(value instanceof java.util.Date) {
			cell.setCellValue((java.util.Date)value);
		}else if(value instanceof Double) {
			cell.setCellValue(((Double)value).doubleValue());
		}else if(value instanceof RichTextString) {
			cell.setCellValue((RichTextString)value);
		}else if(value instanceof String) {
			cell.setCellValue((String)value);
		}else {
			cell.setCellValue(Conver.toString(value));
		}
	}
	
	
	/**
	 * 获取excel单元格名称
	 * @param col
	 * @return
	 */
	protected String getExcelNumName(int col) {
		int a = 65;
		int num = col%26;
		int count = col/26;
		char s = (char)(a+count-1);
		char g = (char)(a+num);
		return count>0 ? s+""+g : g+"";
	}
	

}
