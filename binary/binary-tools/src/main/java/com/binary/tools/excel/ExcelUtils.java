package com.binary.tools.excel;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public abstract class ExcelUtils {
	
	
	public static class SheetInfo {
		private List<?> data;
		private String sheetName;
		private ExcelStyle style;
		private ExporterFillSheetListener listener;
		
		public SheetInfo() {
		}
		public SheetInfo(List<?> data, String sheetName, ExcelStyle style) {
			this.data = data;
			this.sheetName = sheetName;
			this.style = style;
		}
		
		public List<?> getData() {
			return data;
		}
		public void setData(List<?> data) {
			this.data = data;
		}
		public String getSheetName() {
			return sheetName;
		}
		public void setSheetName(String sheetName) {
			this.sheetName = sheetName;
		}
		public ExcelStyle getStyle() {
			return style;
		}
		public void setStyle(ExcelStyle style) {
			this.style = style;
		}
		public ExporterFillSheetListener getListener() {
			return listener;
		}
		public void setListener(ExporterFillSheetListener listener) {
			this.listener = listener;
		}
		
	}
	
	
	
	/**
	 * 将数据导出Excel文件
	 * @param data
	 * @param excel
	 * @param style: 指定导出样式
	 */
	public static <T> void export(List<T> data, ExcelStyle style, File excelFile) {
		ExcelExporter exporter = new ExcelExporter(style, excelFile);
		exporter.write(data);
		exporter.flushAndClose();
	}
	
	
	
	/**将数据导出Excel流
	 * 
	 * @param data
	 * @param excel
	 * @param style: 指定导出样式
	 */
	public static <T> void export(List<T> data, ExcelStyle style, OutputStream excelStream) {
		ExcelExporter exporter = new ExcelExporter(style, excelStream);
		exporter.write(data);
		exporter.flush();
	}
	
	
	
	/**
	 * 将数据导出Excel文件
	 * @param data
	 * @param excel
	 * @param style: 指定导出样式
	 */
	public static <T> void export(List<T> data, String sheetName, ExcelStyle style, File excelFile) {
		ExcelExporter exporter = new ExcelExporter(style, excelFile);
		exporter.write(data, sheetName);
		exporter.flushAndClose();
	}
	
	
	
	/**将数据导出Excel流
	 * 
	 * @param data
	 * @param excel
	 * @param style: 指定导出样式
	 */
	public static <T> void export(List<T> data, String sheetName, ExcelStyle style, OutputStream excelStream) {
		ExcelExporter exporter = new ExcelExporter(style, excelStream);
		exporter.write(data, sheetName);
		exporter.flush();
	}
	

	/**
	 * 导出多个sheet
	 * @param sheets
	 * @param excelStream
	 */
	public static void export(List<SheetInfo> sheets, File excelFile) {
		ExcelExporter exporter = null;
		for(int i=0; i<sheets.size(); i++) {
			SheetInfo info = sheets.get(i);
			if(i == 0) {
				exporter = new ExcelExporter(info.getStyle(), excelFile);
			} else {
				exporter.setStyle(info.getStyle());
			}
			exporter.setListener(info.getListener());
			exporter.write(info.getData(), info.getSheetName());
		}
		exporter.flushAndClose();
	}
	
		
	/**
	 * 导出多个sheet
	 * @param sheets
	 * @param excelStream
	 */
	public static void export(List<SheetInfo> sheets, OutputStream excelStream) {
		ExcelExporter exporter = null;
		for(int i=0; i<sheets.size(); i++) {
			SheetInfo info = sheets.get(i);
			if(i == 0) {
				exporter = new ExcelExporter(info.getStyle(), excelStream);
			} else {
				exporter.setStyle(info.getStyle());
			}
			exporter.setListener(info.getListener());
			exporter.write(info.getData(), info.getSheetName());
		}
		exporter.flush();
	}
	
	
	
	
	/**
	 * 读取excel
	 * @see importExcel(InputStream excel, Class<T> c, int fieldrow, int enumsrow, int startrow, ImportListener listener)
	 */
	public static List<Map<String,Object>> importExcel(File excel) {
		ExcelImporter2007 importer = new ExcelImporter2007();
		return importer.read(excel);
	}
	
	
	
	/**
	 * 读取excel
	 * @see importExcel(InputStream excel, Class<T> c, int fieldrow, int enumsrow, int startrow, ImportListener listener)
	 */
	public static <T> List<T> importExcel(File excel, Class<T> c) {
		ExcelImporter2007 importer = new ExcelImporter2007();
		return importer.read(excel, c);
	}
	
	
	
	/**
	 * 读取excel
	 * @see importExcel(InputStream excel, Class<T> c, int fieldrow, int enumsrow, int startrow, ImportListener listener)
	 */
	public static List<Map<String,Object>> importExcel(InputStream excel) {
		ExcelImporter2007 importer = new ExcelImporter2007();
		return importer.read(excel);
	}
	
	
	
	
	/**
	 * 读取excel
	 * @see importExcel(InputStream excel, Class<T> c, int fieldrow, int enumsrow, int startrow, ImportListener listener)
	 */
	public static <T> List<T> importExcel(InputStream excel, Class<T> c) {
		ExcelImporter2007 importer = new ExcelImporter2007();
		return importer.read(excel, c);
	}
	
	
	
	/**
	 * 读取excel
	 * @see importExcel(InputStream excel, Class<T> c, int fieldrow, int enumsrow, int startrow, ImportListener listener)
	 */
	public static List<Map<String,Object>> importExcel(File excel, ImportListener listener) {
		ExcelImporter2007 importer = new ExcelImporter2007();
		return importer.read(excel, listener);
	}
	
	
	
	/**
	 * 读取excel
	 * @see importExcel(InputStream excel, Class<T> c, int fieldrow, int enumsrow, int startrow, ImportListener listener)
	 */
	public static <T> List<T> importExcel(File excel, Class<T> c, ImportListener listener) {
		ExcelImporter2007 importer = new ExcelImporter2007();
		return importer.read(excel, c, listener);
	}
	
	
	
	/**
	 * 读取excel
	 * @see importExcel(InputStream excel, Class<T> c, int fieldrow, int enumsrow, int startrow, ImportListener listener)
	 */
	public static List<Map<String,Object>> importExcel(InputStream excel, ImportListener listener) {
		ExcelImporter2007 importer = new ExcelImporter2007();
		return importer.read(excel, listener);
	}
	
	
	
	/**
	 * 读取excel
	 * @see importExcel(InputStream excel, Class<T> c, int fieldrow, int enumsrow, int startrow, ImportListener listener)
	 */
	public static <T> List<T> importExcel(InputStream excel, Class<T> c, ImportListener listener) {
		ExcelImporter2007 importer = new ExcelImporter2007();
		return importer.read(excel, c, listener);
	}
	
	
	
	
	
	/**
	 * 读取excel
	 * @see importExcel(InputStream excel, Class<T> c, int fieldrow, int enumsrow, int startrow, ImportListener listener)
	 */
	public static List<Map<String,Object>> importExcel(File excel, int fieldrow, int enumsrow, int startrow) {
		ExcelImporter2007 importer = new ExcelImporter2007();
		return importer.read(excel, fieldrow, enumsrow, startrow);
		
	}

	
	/**
	 * 读取excel
	 * @see importExcel(InputStream excel, Class<T> c, int fieldrow, int enumsrow, int startrow, ImportListener listener)
	 */
	public static <T> List<T> importExcel(File excel, Class<T> c, int fieldrow, int enumsrow, int startrow) {
		ExcelImporter2007 importer = new ExcelImporter2007();
		return importer.read(excel, c, fieldrow, enumsrow, startrow);
	}
	
	
	/**
	 * 读取excel
	 * @see importExcel(InputStream excel, Class<T> c, int fieldrow, int enumsrow, int startrow, ImportListener listener)
	 */
	public static List<Map<String,Object>> importExcel(InputStream excel, int fieldrow, int enumsrow, int startrow) {
		ExcelImporter2007 importer = new ExcelImporter2007();
		return importer.read(excel, fieldrow, enumsrow, startrow);
	}
	
	
	/**
	 * 读取excel
	 * @see importExcel(InputStream excel, Class<T> c, int fieldrow, int enumsrow, int startrow, ImportListener listener)
	 */
	public static <T> List<T> importExcel(InputStream excel, Class<T> c, int fieldrow, int enumsrow, int startrow) {
		ExcelImporter2007 importer = new ExcelImporter2007();
		return importer.read(excel, c, fieldrow, enumsrow, startrow);
	}
	
	
	/**
	 * 读取excel
	 * @see importExcel(InputStream excel, Class<T> c, int fieldrow, int enumsrow, int startrow, ImportListener listener)
	 */
	public static List<Map<String,Object>> importExcel(File excel, int fieldrow, int enumsrow, int startrow, ImportListener listener) {
		ExcelImporter2007 importer = new ExcelImporter2007();
		return importer.read(excel, fieldrow, enumsrow, startrow, listener);
	}
	
	
	
	/**
	 * 读取excel
	 * @see importExcel(InputStream excel, Class<T> c, int fieldrow, int enumsrow, int startrow, ImportListener listener)
	 */
	public static <T> List<T> importExcel(File excel, Class<T> c, int fieldrow, int enumsrow, int startrow, ImportListener listener) {
		ExcelImporter2007 importer = new ExcelImporter2007();
		return importer.read(excel, c, fieldrow, enumsrow, startrow, listener);
	}
	
	
	/**
	 * 读取excel
	 * @see importExcel(InputStream excel, Class<T> c, int fieldrow, int enumsrow, int startrow, ImportListener listener)
	 */
	public static List<Map<String,Object>> importExcel(InputStream excel, int fieldrow, int enumsrow, int startrow, ImportListener listener) {
		ExcelImporter2007 importer = new ExcelImporter2007();
		return importer.read(excel, fieldrow, enumsrow, startrow, listener);
	}
	
	
	
	
	
	
	/**
	 * 读取Excel
	 * @param excel: Excel文件
	 * @param c: 映射对象
	 * @param fieldrow: 字段定义所在行号
	 * @param enumsrow: 枚举定义所在行号
	 * @param startrow: 开始导入数据行号
	 * @param listener: 导入事件
	 * @return
	 */
	public static <T> List<T> importExcel(InputStream excel, Class<T> c, int fieldrow, int enumsrow, int startrow, ImportListener listener) {
		ExcelImporter2007 importer = new ExcelImporter2007();
		return importer.read(excel, c, fieldrow, enumsrow, startrow, listener);
	}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
}
