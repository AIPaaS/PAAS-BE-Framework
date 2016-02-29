package com.binary.tools.excel;

import java.awt.Font;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.binary.tools.exception.ExcelException;

public class ExcelCellStyle {
	
	
	/** 标题样式 **/
	private HSSFCellStyle titleCellStyle;
	
	/** 条件样式 **/
	private HSSFCellStyle cdtCellStyle;
	
	/** 落款样式 **/
	private HSSFCellStyle floorCellStyle;
	
	/** 列头样式 **/
	private HSSFCellStyle columnHeaderCellStyle;
	
	/** 列表内容样式 **/
	private HSSFCellStyle[] contentCellStyles;
	
	
	private ExcelExporter exporter;
	private HSSFWorkbook book;
	
	
	public ExcelCellStyle(ExcelExporter exporter) {
		this.exporter = exporter;
		this.book = exporter.getHSSFWorkbook();
		this.contentCellStyles = new HSSFCellStyle[exporter.getStyle().getColumns().length];
	}
	
	
	
	/**
	 * 获取标题样式 
	 * @return
	 */
	public synchronized HSSFCellStyle getTitleCellStyle() {
		if(this.titleCellStyle != null) return this.titleCellStyle ;
		
		ExcelStyle style = this.exporter.getStyle();
		int titlealign = style.getTitleAlign();
		Font titlefont = style.getTitleFont();
		HSSFCellStyle ts = this.book.createCellStyle();
		HSSFFont font = this.book.createFont();
		font.setFontName(titlefont.getFamily());
		font.setColor(style.getTitleColor());
		font.setItalic(titlefont.getStyle() == Font.ITALIC);
		font.setFontHeightInPoints((short)titlefont.getSize());
		ts.setAlignment(titlealign==0 ? HSSFCellStyle.ALIGN_CENTER : (titlealign>0 ? HSSFCellStyle.ALIGN_RIGHT : HSSFCellStyle.ALIGN_LEFT));
		ts.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		ts.setFont(font);
		
		if(style.getTitleBgColor() > 0) {
			ts.setFillForegroundColor(style.getTitleBgColor());
			ts.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		}
		return this.titleCellStyle = ts;
	}
	
	
	/**
	 * 获取条件样式
	 * @return
	 */
	public synchronized HSSFCellStyle getConditionCellStyle() {
		if(this.cdtCellStyle != null) return this.cdtCellStyle ;
		
		ExcelStyle style = this.exporter.getStyle();
		int conditionalign = style.getConditionAlign();
		Font conditionfont = style.getConditionFont();
		HSSFCellStyle cs = this.book.createCellStyle();
		HSSFFont font = this.book.createFont();
		font.setFontName(conditionfont.getFamily());
		font.setColor(style.getConditionColor());
		font.setItalic(conditionfont.getStyle() == Font.ITALIC);
		font.setFontHeightInPoints((short)conditionfont.getSize());
		cs.setAlignment(conditionalign==0 ? HSSFCellStyle.ALIGN_CENTER : (conditionalign>0 ? HSSFCellStyle.ALIGN_RIGHT : HSSFCellStyle.ALIGN_LEFT));
		cs.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cs.setFont(font);
		cs.setWrapText(style.isConditionBreakText());
		if(style.getConditionBgColor() > 0) {
			cs.setFillForegroundColor(style.getConditionBgColor());
			cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		}
		return this.cdtCellStyle = cs;
	}
	
	
	/**
	 * 获取列头样式
	 * @return
	 */
	public synchronized HSSFCellStyle getColumnHeaderCellStyle() {
		if(this.columnHeaderCellStyle != null) return this.columnHeaderCellStyle ;
		
		ExcelStyle style = this.exporter.getStyle();
		Font columntitlefont = style.getColumnTitleFont();
		HSSFCellStyle cs = this.book.createCellStyle();
		HSSFFont font = this.book.createFont();
		font.setFontName(columntitlefont.getFamily());
		font.setColor(style.getColumnTitleColor());
		font.setItalic(columntitlefont.getStyle() == Font.ITALIC);
		font.setFontHeightInPoints((short)columntitlefont.getSize());
		cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cs.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cs.setFont(font);
		if(style.getColumnTitleBgColor() > 0) {
			cs.setFillForegroundColor(style.getColumnTitleBgColor());
			cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		}
		cs.setBorderTop(style.getColumnTitleBorderTop());
		cs.setBorderLeft(style.getColumnTitleBorderLeft());
		cs.setBorderRight(style.getColumnTitleBorderRight());
		cs.setBorderBottom(style.getColumnTitleBorderBottom());
		if(style.getColumnTitleTopBorderColor() > 0) cs.setTopBorderColor(style.getColumnTitleTopBorderColor());
		if(style.getColumnTitleLeftBorderColor() > 0) cs.setTopBorderColor(style.getColumnTitleLeftBorderColor());
		if(style.getColumnTitleBottomBorderColor() > 0) cs.setTopBorderColor(style.getColumnTitleBottomBorderColor());
		if(style.getColumnTitleRightBorderColor() > 0) cs.setTopBorderColor(style.getColumnTitleRightBorderColor());
		
		return this.columnHeaderCellStyle = cs;
	}
	
	
	/**
	 * 获取内容样式
	 * @return
	 */
	public synchronized HSSFCellStyle getContentCellStyle(int index) {
		if(index<0 || index>=this.contentCellStyles.length) throw new ExcelException(" the content-style index is out of arrayside! ");
		if(this.contentCellStyles[index] != null) return this.contentCellStyles[index];
		
		ExcelStyle style = this.exporter.getStyle();
		HSSFCellStyle cs = this.book.createCellStyle();
		Column column = style.getColumns()[index];
		
		cs.setAlignment(column.getAlign()==0 ? HSSFCellStyle.ALIGN_CENTER : (column.getAlign()>0 ? HSSFCellStyle.ALIGN_RIGHT : HSSFCellStyle.ALIGN_LEFT));
		HSSFFont font = this.book.createFont();
		Font cellfont = style.getContentFont();
		font.setFontName(cellfont.getFamily());
		font.setColor(style.getContentColor());
		font.setItalic(cellfont.getStyle() == Font.ITALIC);
		font.setFontHeightInPoints((short)cellfont.getSize());
		cs.setFont(font);
		
		if(style.getContentBgColor() > 0) {
			cs.setFillForegroundColor(style.getContentBgColor());
			cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		}
		
		cs.setBorderTop(column.getBorderTop());
		cs.setBorderLeft(column.getBorderLeft());
		cs.setBorderRight(column.getBorderBottom());
		cs.setBorderBottom(column.getBorderRight());
		if(column.getTopBorderColor() > 0) cs.setTopBorderColor(column.getTopBorderColor());
		if(column.getLeftBorderColor() > 0) cs.setTopBorderColor(column.getLeftBorderColor());
		if(column.getBottomBorderColor() > 0) cs.setTopBorderColor(column.getBottomBorderColor());
		if(column.getRightBorderColor() > 0) cs.setTopBorderColor(column.getRightBorderColor());
		
		return this.contentCellStyles[index] = cs;
	}
	
	
	/**
	 * 获取内容样式
	 * @return
	 */
	public synchronized HSSFCellStyle getFloorCellStyle() {
		if(this.floorCellStyle != null) return this.floorCellStyle ;
		
		ExcelStyle style = this.exporter.getStyle();
		int flooralign = style.getFloorAlign();
		Font floorfont = style.getFloorFont();
		HSSFCellStyle cs = this.book.createCellStyle();
		HSSFFont font = this.book.createFont();
		font.setFontName(floorfont.getFamily());
		font.setColor(style.getFloorColor());
		font.setItalic(floorfont.getStyle() == Font.ITALIC);
		font.setFontHeightInPoints((short)floorfont.getSize());
		cs.setAlignment(flooralign==0 ? HSSFCellStyle.ALIGN_CENTER : (flooralign>0 ? HSSFCellStyle.ALIGN_RIGHT : HSSFCellStyle.ALIGN_LEFT));
		cs.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cs.setFont(font);
		cs.setWrapText(style.isFloorBreakText());
		if(style.getFloorBgColor() > 0) {
			cs.setFillForegroundColor(style.getFloorBgColor());
			cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		}
		return this.floorCellStyle = cs;
	}
	
	
}
