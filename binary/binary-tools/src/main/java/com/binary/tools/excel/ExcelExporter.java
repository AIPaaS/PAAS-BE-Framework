package com.binary.tools.excel;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.binary.core.bean.BMProxy;
import com.binary.core.util.BinaryUtils;
import com.binary.tools.exception.ExcelException;

public class ExcelExporter extends AbstractExcel {
	
	
	private ExcelStyle style;
	private OutputStream os;
	private HSSFWorkbook book;
	private ExporterFillSheetListener listener;
	
	
	private List<HSSFSheet> sheetList = new ArrayList<HSSFSheet>();
	private Map<HSSFSheet, ExcelCellStyle> cellStyleStore = new HashMap<HSSFSheet, ExcelCellStyle>();
	
	
	public ExcelExporter() {
		this(null, (OutputStream)null);
	}
	
	
	public ExcelExporter(ExcelStyle style, File excelFile) {
		this(style, getFileOutputStream(excelFile));
	}
	
	public ExcelExporter(ExcelStyle style, OutputStream os) {
		this.style = style;
		this.os = os;
		this.book = new HSSFWorkbook();
	}
	
	
	/**
	 * 获取Excel样式
	 * @return
	 */
	public ExcelStyle getStyle() {
		if(this.style == null) {
			throw new ExcelException(" the excel style is NULL! ");
		}
		return style;
	}
	
	
	
	/**
	 * 设置Excel样式
	 * @param style
	 */
	public void setStyle(ExcelStyle style) {
		this.style = style;
	}
	
	
	/**
	 * 设置Excel写入文件
	 * @param excelFile
	 */
	public void setExcelFile(File excelFile) {
		this.os = getFileOutputStream(excelFile);
	}
	
	/**
	 * 设置Excel写入流
	 * @param excelOutputStream
	 */
	public void setExcelOutputStream(OutputStream excelOutputStream) {
		this.os = excelOutputStream;
	}
	
		
	public ExporterFillSheetListener getListener() {
		return listener;
	}
	public void setListener(ExporterFillSheetListener listener) {
		this.listener = listener;
	}


	public HSSFWorkbook getHSSFWorkbook() {
		return this.book;
	}
	
	
	/**
	 * 获取表格样式
	 * @return
	 */
	public synchronized ExcelCellStyle getExcelCellStyle(HSSFSheet sheet) {
		if(sheet == null) throw new ExcelException(" the parameter-sheet is NULL argument! ");
		
		ExcelCellStyle style = cellStyleStore.get(sheet);
		if(style != null) return style;
		
		if(sheet.getWorkbook() != this.book) throw new ExcelException(" the sheet:'"+sheet.getSheetName()+"' is create by this exporter! ");
		
		style = new ExcelCellStyle(this);
		
		cellStyleStore.put(sheet, style);
		
		return style;
	}
	
	
	public <T> void write(List<T> data) {
		write(data, null);
	}
	
	public <T> void write(List<T> data, String sheetName) {
		try {
			ExcelStyle style = getStyle();
			HSSFSheet s = null;
			if(isEmpty(sheetName, true)) {
				s = this.book.createSheet();
			}else {
				s = this.book.createSheet(sheetName);
			}
			this.sheetList.add(s);
			
			Column[] columns = style.getColumns();
			int colcount = columns!=null&&columns.length>1 ? columns.length : 1;
			int bit = 0;
			
			ExporterFillSheetEvent event = new ExporterFillSheetEvent(this.sheetList.size()-1, sheetName, s, data, this, bit);
			
			if(this.listener != null) {
				int rows = this.listener.onStartFill(event);
				if(rows > 0) bit += rows;
			}
			
			ExcelCellStyle cellstyle = getExcelCellStyle(s);
			
			String title = style.getTitle();
			if(title!=null && (title=title.trim()).length()>0) {
				if(this.listener != null) {
					event.setRowNum(bit);
					int rows = this.listener.onBeforeFillTitle(event);
					if(rows > 0) bit += rows;
				}
				
				HSSFRow row = s.createRow(bit);
				row.setHeight((short)style.getTitleHeight());
				
				HSSFCell cell = row.createCell(0);
				cell.setCellValue(title);
				cell.setCellStyle(cellstyle.getTitleCellStyle());
				s.addMergedRegion(new CellRangeAddress(0,0,0,(colcount-1)));
				bit ++ ;
				
				if(this.listener != null) {
					event.setRowNum(bit);
					int rows = this.listener.onAfterFillTitle(event);
					if(rows > 0) bit += rows;
				}
			}
			
			String condition = style.getCondition();
			if(condition!=null && (condition=condition.trim()).length()>0) {
				if(this.listener != null) {
					event.setRowNum(bit);
					int rows = this.listener.onBeforeFillCondition(event);
					if(rows > 0) bit += rows;
				}
				
				HSSFRow row = s.createRow(bit);
				row.setHeight((short)style.getConditionHeight());
				HSSFCell cell = row.createCell(0);
				cell.setCellValue(condition);
				cell.setCellStyle(cellstyle.getConditionCellStyle());
				s.addMergedRegion(new CellRangeAddress(1,1,0,(colcount-1)));
				bit ++ ;
				
				if(this.listener != null) {
					event.setRowNum(bit);
					int rows = this.listener.onAfterFillCondition(event);
					if(rows > 0) bit += rows;
				}
			}
			if(columns!=null && columns.length>0 && style.isWriteColumnHeader()) {
				if(this.listener != null) {
					event.setRowNum(bit);
					int rows = this.listener.onBeforeFillClumnHeader(event);
					if(rows > 0) bit += rows;
				}
				
				HSSFRow row = s.createRow(bit);
				row.setHeight((short)(style.getColumnTitleHeight()));
				HSSFCellStyle cs = cellstyle.getColumnHeaderCellStyle();
				for(int i=0; i<columns.length; i++) {
					HSSFCell cell = row.createCell(i);
					cell.setCellValue(columns[i].getName());
					s.setColumnWidth(i, columns[i].getWidth());
					cell.setCellStyle(cs);
				}
				bit ++ ;
				
				if(this.listener != null) {
					event.setRowNum(bit);
					int rows = this.listener.onAfterFillClumnHeader(event);
					if(rows > 0) bit += rows;
				}
			}
			
			if(columns!=null && columns.length>0 && data!=null && data.size()>0) {
				if(this.listener != null) {
					event.setRowNum(bit);
					int rows = this.listener.onBeforeFillData(event);
					if(rows > 0) bit += rows;
				}
				
				int rowcount = data.size();
				int contentheight = style.getContentHeight();
				
				//HSSFCellStyle style = hwb.createCellStyle();
				BMProxy<Object> proxy = null;
				for(int i=0; i<rowcount; i++) {
					HSSFRow row = s.createRow(i+bit);
					row.setHeight((short)contentheight);
					
					Object rowvalue = data.get(i);
					if(rowvalue == null) continue ;
					if(proxy == null) {
						proxy = BMProxy.getInstance(rowvalue);
					}else {
						proxy.replaceInnerObject(rowvalue);
					}
										
					for(int j=0; j<columns.length; j++) {
						Column column = columns[j];
						column.setIndex(j);
						
						Object value = proxy.get(columns[j].getKey());
						ColumnRender render = column.getColumnRender();
						
						HSSFCell cell = row.createCell(j);
						HSSFCellStyle cs = cellstyle.getContentCellStyle(j);
						if(render != null) {
							CellStyle cstyle = new CellStyle(column);
							value = render.onRender(value, proxy, i, j, column, cs, cstyle, this.book);
							if(cstyle.isChanged()) cs = cstyle.createHSSFStyle(this.book);
						}
						
						cell.setCellStyle(cs);
						setCellValue(cell, value);
					}
					
					RowRender rowrender = style.getRowRender();
					if(rowrender!=null) rowrender.onRender(proxy, i, row, this.book);
				}
				bit += rowcount;
				
				if(this.listener != null) {
					event.setRowNum(bit);
					int rows = this.listener.onAfterFillData(event);
					if(rows > 0) bit += rows;
				}
			}
			
			String floor = style.getFloor();
			if(floor!=null && (floor=floor.trim()).length()>0) {
				if(this.listener != null) {
					event.setRowNum(bit);
					int rows = this.listener.onBeforeFillFloor(event);
					if(rows > 0) bit += rows;
				}
				
				HSSFRow row = s.createRow(bit);
				row.setHeight((short)style.getFloorHeight());
				HSSFCell cell = row.createCell(0);
				cell.setCellValue(floor);
				cell.setCellStyle(cellstyle.getFloorCellStyle());
				s.addMergedRegion(new CellRangeAddress(bit,bit,0,(colcount-1)));
				bit ++ ;
				
				if(this.listener != null) {
					event.setRowNum(bit);
					int rows = this.listener.onAfterFillFloor(event);
					if(rows > 0) bit += rows;
				}
			}
			
			if(this.listener != null) {
				event.setRowNum(bit);
				int rows = this.listener.onEndFill(event);
				if(rows > 0) bit += rows;
			}
		}catch(Exception e) {
			throw BinaryUtils.transException(e, ExcelException.class);
		}
	}
	
	
	
	public void flush() {
		try {
			this.book.write(this.os);
		} catch (Exception e) {
			throw BinaryUtils.transException(e, ExcelException.class);
		}
	}
	
	
	public void flushAndClose() {
		try {
			flush();
			this.os.close();
		} catch (Exception e) {
			throw BinaryUtils.transException(e, ExcelException.class);
		}
	}
	
	
	
	
	
	
}



