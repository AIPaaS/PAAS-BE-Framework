package com.binary.tools.excel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.binary.core.bean.BMProxy;
import com.binary.core.lang.Conver;
import com.binary.core.util.BinaryUtils;
import com.binary.tools.excel.ImportErrorException.ErrorType;
import com.binary.tools.exception.ExcelException;

public class ExcelImporter2007 extends AbstractExcel {
	
	
	
	private static class Column {
		String key;
		boolean nullable;
		char type;	//S=文本		N=数值		D=日期		E=枚举
		Class<?> c;
		Map<String,String> enums;
		Column(String code) {
			String[] arr = code.split(",");
			if(arr.length!=3 
					|| (arr[0]=arr[0].trim()).length()==0
					|| (arr[1]=arr[1].trim()).length()!=1
					|| (arr[2]=arr[2].trim()).length()!=1) throw new ExcelException(" is wrong code:'"+code+"'! ");
			key = arr[0];
			char n = arr[1].charAt(0);
			type = arr[2].charAt(0);
			if((n!='T' && n!='F') || (type!='S'&&type!='N'&&type!='D'&&type!='E')) throw new ExcelException(" is wrong code:'"+code+"'! ");
			nullable = n == 'F';
			switch(type) {
				case 'S': c = String.class; break;
				case 'N': c = Double.class; break;
				case 'D': c = java.util.Date.class; break;
				case 'E': c = String.class; break;
			}
		}
		void setEnums(String value) {
			String[] array = value.split(",");
			enums = new HashMap<String,String>();
			for(int i=0; i<array.length; i++) {
				int index = 0;
				if((array[i]=array[i].trim()).length()==0 || (index=array[i].indexOf("="))<=0) throw new ExcelException(" is wrong enums:'"+value+"'! ");
				String k = array[i].substring(0,index).trim();
				String v = array[i].substring(index+1).trim();
				if(k.length()==0 || v.length()==0) throw new ExcelException(" is wrong enums:'"+value+"'! ");
				enums.put(k, v);
			}
		}
		public String toString() {
			return key+","+(nullable?'F':'T')+type;
		}
	}
	
	
	private Object getCellValue(Cell cell, Column col, boolean ismap, int i, int j, ImportListener listener) {
		Object value = getCellValue(cell);
		
		ImportErrorException ex = null;
		if(isEmpty(value, false)) {
			if(!col.nullable) ex = new ImportErrorException(" the "+(i+1)+","+getExcelNumName(j)+" is null! ", ErrorType.NULL);
		}else {
			if(col.type == 'E') {
				value = col.enums.get(Conver.toString(value).trim());
				if(value == null) ex = new ImportErrorException(" the "+(i+1)+","+getExcelNumName(j)+" is not found enum value! ", ErrorType.NotInEnums);
			}else {
				try {
					if(ismap) value = Conver.to(value, col.c );
				}catch(Exception e) {
					ex = new ImportErrorException(" is wrong "+(i+1)+","+getExcelNumName(j)+" value! "+e.getMessage(), ErrorType.WrongValue);
				}
			}
		}
		
		if(listener != null) {
			Object value1 = listener.readCell(value, col.key, col.nullable, col.type, i, j, ex);
			if(ex==null && value!=value1) {
				if(isEmpty(value1, false)) {
					if(!col.nullable) ex = new ImportErrorException(" the "+(i+1)+","+getExcelNumName(j)+" is null! ", ErrorType.NULL);
				}else {
					if(col.type == 'E') {
						value1 = col.enums.get(Conver.toString(value1).trim());
						if(value1 == null) ex = new ImportErrorException(" the "+(i+1)+","+getExcelNumName(j)+" is not found enum value! ", ErrorType.NotInEnums);
					}else {
						try {
							if(ismap) value1 = Conver.to(value1, col.c);
						}catch(Exception e) {
							ex = new ImportErrorException(" is wrong "+(i+1)+","+getExcelNumName(j)+" value! "+e.getMessage(), ErrorType.WrongValue);
						}
					}
				}
				value = value1;
			}
		}
		if(ex!=null && !ex.isIgnored()) throw ex;
		return value;
	}
	
	
	
	
	
	
	
	public List<Map<String,Object>> read(File excel) {
		return read(getFileInputStream(excel), null, null);
	}
	
	
	public <T> List<T> read(File excel, Class<T> c) {
		return read(getFileInputStream(excel), c, null);
	}
	
	
	public List<Map<String,Object>> read(InputStream excel) {
		return read(excel, null, null);
	}
	
	
	
	public <T> List<T> read(InputStream excel, Class<T> c) {
		return read(excel, c, null);
	}
	
	
	public List<Map<String,Object>> read(File excel, ImportListener listener) {
		return read(excel, null, listener);
	}
	
	
	public <T> List<T> read(File excel, Class<T> c, ImportListener listener) {
		return read(excel, c, listener);
	}
	
	
	public List<Map<String,Object>> read(InputStream excel, ImportListener listener) {
		return read(excel, null, listener);
	}
	
	
	public <T> List<T> read(InputStream excel, Class<T> c, ImportListener listener) {
		return read(excel, c, 0, 4, 5, listener);
	}
	
	
	
	
	
	public List<Map<String,Object>> read(File excel, int fieldrow, int enumsrow, int startrow) {
		InputStream is = null;
		try {
			is = getFileInputStream(excel);
			return read(is, null, fieldrow, enumsrow, startrow, null);
		}finally {
			try {
				if(is != null) is.close();
			}catch(IOException e) {
				throw new ExcelException(e);
			}
		}
	}
	
	public <T> List<T> read(File excel, Class<T> c, int fieldrow, int enumsrow, int startrow) {
		InputStream is = null;
		try {
			is = getFileInputStream(excel);
			return read(is, c, fieldrow, enumsrow, startrow, null);
		}finally {
			try {
				if(is != null) is.close();
			}catch(IOException e) {
				throw new ExcelException(e);
			}
		}
	}
	
	
	public List<Map<String,Object>> read(InputStream excel, int fieldrow, int enumsrow, int startrow) {
		return read(excel, null, fieldrow, enumsrow, startrow, null);
	}
	
	
	public <T> List<T> read(InputStream excel, Class<T> c, int fieldrow, int enumsrow, int startrow) {
		return read(excel, c, fieldrow, enumsrow, startrow, null);
	}
	
	
	public List<Map<String,Object>> read(File excel, int fieldrow, int enumsrow, int startrow, ImportListener listener) {
		InputStream is = null;
		try {
			is = getFileInputStream(excel);
			return read(is, null, fieldrow, enumsrow, startrow, listener);
		}finally {
			try {
				if(is != null) is.close();
			}catch(IOException e) {
				throw new ExcelException(e);
			}
		}
	}
	
	
	public <T> List<T> read(File excel, Class<T> c, int fieldrow, int enumsrow, int startrow, ImportListener listener) {
		InputStream is = null;
		try {
			is = getFileInputStream(excel);
			return read(is, c, fieldrow, enumsrow, startrow, listener);
		}finally {
			try {
				if(is != null) is.close();
			}catch(IOException e) {
				throw new ExcelException(e);
			}
		}
		
	}
	
	
	public List<Map<String,Object>> read(InputStream excel, int fieldrow, int enumsrow, int startrow, ImportListener listener) {
		return read(excel, null, fieldrow, enumsrow, startrow, listener);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> read(InputStream excel, Class<T> c, int fieldrow, int enumsrow, int startrow, ImportListener listener) {
		try {
			if(startrow<=fieldrow || startrow<=enumsrow || fieldrow==enumsrow) throw new ExcelException(" must be FieldRow<StartRow && EnumsRow<StartRow && FieldRow!=EnumsRow: FieldRow='"+fieldrow+"',EnumsRow='"+enumsrow+"',StartRow='"+startrow+"'! ");
			boolean ismap = c == null;
			BMProxy proxy = BMProxy.getInstance(ismap ? Map.class : c);
			
			Workbook wb = WorkbookFactory.create(excel);
			Sheet sheet = wb.getSheetAt(0);
			//Iterator rowiterator = sheet.iterator();
			int rowNum = sheet.getLastRowNum();
			
			Column[] cols = null;
			List dataList = new ArrayList();
			
			for(int i=0; i<=rowNum; i++) {
				Row row = sheet.getRow(i);
				if(row == null) continue ;
				
				if(i == fieldrow) {
					int cellnum = row.getLastCellNum();
					if(cellnum == 0) throw new ExcelException(" is 0 cell number! ");
					cols = new Column[cellnum-1];
					for(int j=1; j<=cols.length; j++) {
						Cell cell = row.getCell(j);
						Object value = getCellValue(cell);
						if(isEmpty(value, true)) throw new ExcelException(" is null code:'"+getExcelNumName(j)+"'! ");
						cols[j-1] = new Column(Conver.toString(value).trim());
					}
				}else if(i == enumsrow) {
					for(int j=1; j<=cols.length; j++) {
						if(cols[j-1].type != 'E') continue ;
						Cell cell = row.getCell(j);
						String value = Conver.toString(getCellValue(cell));
						if(value==null || (value=value.trim()).length()==0) throw new ExcelException(" is null code:'"+getExcelNumName(j)+"'! ");
						cols[j-1].setEnums(value);
					}
				}else if(i >= startrow) {
					Object rowvalue = proxy.newInstance();
					dataList.add(rowvalue);
					
					for(int j=1; j<=cols.length; j++) {
						Cell cell = row.getCell(j);
						proxy.set(cols[j-1].key, getCellValue(cell, cols[j-1], ismap, i, j, listener));
					}
				}
			}
			
			return dataList;
		}catch(Exception e) {
			throw BinaryUtils.transException(e, ExcelException.class);
		}
	}
	
	
}
