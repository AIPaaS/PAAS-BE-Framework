package com.binary.tools.excel;

import java.awt.Font;
import java.io.Serializable;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


/**
 * 
 * @author wanwb
 */
public class CellStyle implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private boolean changed=false;
	
	private int align;			//<0:居左  =0:居中  >0居右
	private short color;
	private short bgcolor;
	private Font font;
	private boolean breakText;
	
	private short borderTop;
	private short borderLeft;
	private short borderBottom;
	private short borderRight;
	
	private short topBorderColor;
	private short leftBorderColor;
	private short bottomBorderColor;
	private short rightBorderColor;
	

	public CellStyle() {
	}
	
	public CellStyle(Column column) {
		this.align = column.getAlign();
		this.color = column.getColor();
		this.bgcolor = column.getBgcolor();
		this.font = column.getFont();
		this.breakText = column.isBreakText();
		this.borderTop = column.getBorderTop();
		this.borderLeft = column.getBorderLeft();
		this.borderBottom = column.getBorderBottom();
		this.borderRight = column.getBorderRight();
		this.topBorderColor = column.getTopBorderColor();
		this.leftBorderColor = column.getLeftBorderColor();
		this.bottomBorderColor = column.getBottomBorderColor();
		this.rightBorderColor = column.getRightBorderColor();
	}

	public HSSFCellStyle createHSSFStyle(HSSFWorkbook workBook) {
		HSSFCellStyle style = workBook.createCellStyle();
		HSSFFont font = workBook.createFont();
		int align = this.getAlign();
		Font columnfont = this.getFont();
		font.setFontName(columnfont.getFamily());
		font.setColor(this.getColor());
		font.setItalic(columnfont.getStyle() == Font.ITALIC);
		font.setFontHeightInPoints((short)columnfont.getSize());
		style.setAlignment(align==0 ? HSSFCellStyle.ALIGN_CENTER : (align>0 ? HSSFCellStyle.ALIGN_RIGHT : HSSFCellStyle.ALIGN_LEFT));
		//style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(font);
		if(this.getBgcolor() > 0) {
			style.setFillForegroundColor(this.getBgcolor());
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		}
		style.setBorderTop(this.getBorderTop());
		style.setBorderLeft(this.getBorderLeft());
		style.setBorderRight(this.getBorderBottom());
		style.setBorderBottom(this.getBorderRight());
		if(this.getTopBorderColor() > 0) style.setTopBorderColor(this.getTopBorderColor());
		if(this.getLeftBorderColor() > 0) style.setTopBorderColor(this.getLeftBorderColor());
		if(this.getBottomBorderColor() > 0) style.setTopBorderColor(this.getBottomBorderColor());
		if(this.getRightBorderColor() > 0) style.setTopBorderColor(this.getRightBorderColor());
		return style;
	}
	
	public boolean isChanged() {
		return changed;
	}

	public int getAlign() {
		return align;
	}

	public short getColor() {
		return color;
	}

	public short getBgcolor() {
		return bgcolor;
	}

	public Font getFont() {
		return font;
	}

	public boolean isBreakText() {
		return breakText;
	}

	public short getBorderTop() {
		return borderTop;
	}

	public short getBorderLeft() {
		return borderLeft;
	}

	public short getBorderBottom() {
		return borderBottom;
	}

	public short getBorderRight() {
		return borderRight;
	}

	public short getTopBorderColor() {
		return topBorderColor;
	}

	public short getLeftBorderColor() {
		return leftBorderColor;
	}

	public short getBottomBorderColor() {
		return bottomBorderColor;
	}

	public short getRightBorderColor() {
		return rightBorderColor;
	}

	
	
	
	public void setAlign(int align) {
		if(!this.changed && this.align!=align) this.changed = true;
		this.align = align;
	}

	public void setColor(short color) {
		if(!this.changed && this.color!=color) this.changed = true;
		this.color = color;
	}

	public void setBgcolor(short bgcolor) {
		if(!this.changed && this.bgcolor!=bgcolor) this.changed = true;
		this.bgcolor = bgcolor;
	}

	public void setFont(Font font) {
		if(!this.changed && this.font!=font) this.changed = true;
		this.font = font;
	}

	public void setBreakText(boolean breakText) {
		if(!this.changed && this.breakText!=breakText) this.changed = true;
		this.breakText = breakText;
	}

	public void setBorderTop(short borderTop) {
		if(!this.changed && this.borderTop!=borderTop) this.changed = true;
		this.borderTop = borderTop;
	}

	public void setBorderLeft(short borderLeft) {
		if(!this.changed && this.borderLeft!=borderLeft) this.changed = true;
		this.borderLeft = borderLeft;
	}

	public void setBorderBottom(short borderBottom) {
		if(!this.changed && this.borderBottom!=borderBottom) this.changed = true;
		this.borderBottom = borderBottom;
	}

	public void setBorderRight(short borderRight) {
		if(!this.changed && this.borderRight!=borderRight) this.changed = true;
		this.borderRight = borderRight;
	}

	public void setTopBorderColor(short topBorderColor) {
		if(!this.changed && this.topBorderColor!=topBorderColor) this.changed = true;
		this.topBorderColor = topBorderColor;
	}

	public void setLeftBorderColor(short leftBorderColor) {
		if(!this.changed && this.leftBorderColor!=leftBorderColor) this.changed = true;
		this.leftBorderColor = leftBorderColor;
	}

	public void setBottomBorderColor(short bottomBorderColor) {
		if(!this.changed && this.bottomBorderColor!=bottomBorderColor) this.changed = true;
		this.bottomBorderColor = bottomBorderColor;
	}

	public void setRightBorderColor(short rightBorderColor) {
		if(!this.changed && this.rightBorderColor!=rightBorderColor) this.changed = true;
		this.rightBorderColor = rightBorderColor;
	}
	

	
	
	
	
}
