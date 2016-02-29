package com.binary.tools.excel;

import java.awt.Font;
import java.io.Serializable;

import org.apache.poi.hssf.util.HSSFColor;


/**
 * @author wanwb
 */
public class Column implements Cloneable,Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final int DefaultWidth = 4600;
	public static final int DefaultAlign = -1;
	public static final short DefalutColor = HSSFColor.BLACK.index;
	public static final Font DefaultFont = new Font("宋体", Font.PLAIN, 10);
	public static final boolean DefaultBreakText = false;
	
	
	private int index;
	private String key = "";
	private String name = "";
	
	private int width = DefaultWidth;
	private int align = DefaultAlign;		//<0:居左  =0:居中  >0居右
	private short color = DefalutColor;
	private short bgcolor = -1;
	private Font font = DefaultFont;
	private boolean breakText = DefaultBreakText;
	
	private short borderTop = 1;
	private short borderLeft = 1;
	private short borderBottom = 1;
	private short borderRight = 1;
	
	private short topBorderColor = -1;
	private short leftBorderColor = -1;
	private short bottomBorderColor = -1;
	private short rightBorderColor = -1;
	
	private ColumnRender columnRender;
	

	public Column() {
	}
	
	public Column(String key, String name) {
		this.key = key;
		this.name = name;
	}
	public Column(String key, String name, int width) {
		this.key = key;
		this.name = name;
		if(width > 0) this.width = width;
	}
	public Column(String key, String name, int width, int align) {
		this.key = key;
		this.name = name;
		if(width > 0) this.width = width;
		this.align = align;
	}
	
	public Column(String key, String name, int width, int align, ColumnRender render) {
		this.key = key;
		this.name = name;
		if(width > 0) this.width = width;
		this.align = align;
		this.columnRender = render;
	}
	
	public Column(String key, String name, int width, int align, short color) {
		this.key = key;
		this.name = name;
		if(width > 0) this.width = width;
		this.align = align;
		if(color > 0) this.color = color;
	}
	public Column(String key, String name, int width, int align, short color, Font font) {
		this.key = key;
		this.name = name;
		if(width > 0) this.width = width;
		this.align = align;
		if(color > 0) this.color = color;
		if(font != null) this.font = font;
	}
	public Column(String key, String name, int width, int align, short color, Font font, ColumnRender render) {
		this.key = key;
		this.name = name;
		if(width > 0) this.width = width;
		this.align = align;
		if(color > 0) this.color = color;
		if(font != null) this.font = font;
		this.columnRender = render;
	}
	public Column(String key, String name, int width, int align, short color, Font font, boolean breakText) {
		this.key = key;
		this.name = name;
		if(width > 0) this.width = width;
		this.align = align;
		if(color > 0) this.color = color;
		if(font != null) this.font = font;
		this.breakText = breakText;
	}
	public Column(String key, String name, int width, int align, short color, Font font, boolean breakText, ColumnRender render) {
		this.key = key;
		this.name = name;
		if(width > 0) this.width = width;
		this.align = align;
		if(color > 0) this.color = color;
		if(font != null) this.font = font;
		this.breakText = breakText;
		this.columnRender = render;
	}
	
	
	
	public int getAlign() {
		return align;
	}
	public void setAlign(int align) {
		this.align = align;
	}
	public short getColor() {
		return color;
	}
	public void setColor(short color) {
		this.color = color;
	}
	public Font getFont() {
		return font;
	}
	public void setFont(Font font) {
		this.font = font;
	}
	public int getIndex() {
		return index;
	}
	void setIndex(int index) {
		this.index = index;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	
	public ColumnRender getColumnRender() {
		return columnRender;
	}

	public void setColumnRender(ColumnRender columnRender) {
		this.columnRender = columnRender;
	}

	public short getBgcolor() {
		return bgcolor;
	}

	public void setBgcolor(short bgcolor) {
		this.bgcolor = bgcolor;
	}

	public boolean isBreakText() {
		return breakText;
	}

	public void setBreakText(boolean breakText) {
		this.breakText = breakText;
	}

	public short getBorderBottom() {
		return borderBottom;
	}

	public void setBorderBottom(short borderBottom) {
		this.borderBottom = borderBottom;
	}

	public short getBorderLeft() {
		return borderLeft;
	}

	public void setBorderLeft(short borderLeft) {
		this.borderLeft = borderLeft;
	}

	public short getBorderRight() {
		return borderRight;
	}

	public void setBorderRight(short borderRight) {
		this.borderRight = borderRight;
	}

	public short getBorderTop() {
		return borderTop;
	}

	public void setBorderTop(short borderTop) {
		this.borderTop = borderTop;
	}

	public short getBottomBorderColor() {
		return bottomBorderColor;
	}

	public void setBottomBorderColor(short bottomBorderColor) {
		this.bottomBorderColor = bottomBorderColor;
	}

	public short getLeftBorderColor() {
		return leftBorderColor;
	}

	public void setLeftBorderColor(short leftBorderColor) {
		this.leftBorderColor = leftBorderColor;
	}

	public short getRightBorderColor() {
		return rightBorderColor;
	}

	public void setRightBorderColor(short rightBorderColor) {
		this.rightBorderColor = rightBorderColor;
	}

	public short getTopBorderColor() {
		return topBorderColor;
	}

	public void setTopBorderColor(short topBorderColor) {
		this.topBorderColor = topBorderColor;
	}

	
	
}
