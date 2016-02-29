package com.binary.tools.excel;

import java.awt.Font;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.util.HSSFColor;


public class ExcelStyle implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private String title;
	private Font titleFont = new Font("华文中宋",Font.BOLD, 16);
	private short titleColor = HSSFColor.BLACK.index;
	private short titleBgColor = -1;
	private int titleAlign = 0;		//<0:居左  =0:居中  >0居右
	private int titleHeight = 600;
	
	private String condition;
	private Font conditionFont = new Font("宋体",Font.BOLD, 10);
	private short conditionColor = HSSFColor.BLACK.index;
	private short conditionBgColor = -1;
	private int conditionAlign = -1;		//<0:居左  =0:居中  >0居右
	private int conditionHeight = 460;
	private boolean conditionBreakText = false;	//是否自动换行
	
	private String floor;
	private Font floorFont = new Font("宋体",Font.BOLD, 10);
	private short floorColor = HSSFColor.BLACK.index;
	private short floorBgColor = -1;
	private int floorAlign = 1;				//<0:居左  =0:居中  >0居右
	private int floorHeight = 460;
	private boolean floorBreakText = false;	//是否自动换行
	
	private Font columnTitleFont = new Font("宋体",Font.BOLD, 11);
	private short columnTitleColor = HSSFColor.BLACK.index;
	private short columnTitleBgColor = HSSFColor.LIGHT_CORNFLOWER_BLUE.index;
	private int columnTitleHeight = 400;
	private boolean columnTitleBreakText = true;
	
	private short columnTitleBorderTop = 1;
	private short columnTitleBorderLeft = 1;
	private short columnTitleBorderBottom = 1;
	private short columnTitleBorderRight = 1;
	
	private short columnTitleTopBorderColor = -1;
	private short columnTitleLeftBorderColor = -1;
	private short columnTitleBottomBorderColor = -1;
	private short columnTitleRightBorderColor = -1;
	
	
	
	private Font contentFont = new Font("宋体",Font.PLAIN, 10);
	private short contentColor = HSSFColor.BLACK.index;
	private short contentBgColor = -1;
	private int contentHeight = 280;
	
	
	private Column[] columns;		//列
	
	private RowRender rowRender;
	
	private Map<String,Column> columnmap;
	
	/** 是否写列头 **/
	private boolean writeColumnHeader = true;
	
	
	public ExcelStyle() {
	}
	
	public ExcelStyle(Column[] columns) {
		this(null, null, null, columns);
	}
	
	public ExcelStyle(String title, Column[] columns) {
		this(title, null, null, columns);
	}
	
	public ExcelStyle(String title, String condition, Column[] columns) {
		this(title, condition, null, columns);
	}
	
	public ExcelStyle(String title, String condition, String floor, Column[] columns) {
		this.title = title;
		this.condition = condition;
		this.floor = floor;
		this.columns = columns;
	}
	
	
	public synchronized Column getColumn(String key) {
		if(columns == null) return null;
		if(columnmap==null) {
			columnmap = new HashMap<String,Column>();
			for(int i=0; i<columns.length; i++) {
				columnmap.put(columns[i].getKey(), columns[i]);
			}
		}
		return columnmap.get(key);
	}
	
	
	
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}


	public Column[] getColumns() {
		return columns;
	}
	
	
//	public synchronized void setColumns(Column[] columns) {
//		this.columns = columns;
//	}

	public int getConditionAlign() {
		return conditionAlign;
	}

	public void setConditionAlign(int conditionAlign) {
		this.conditionAlign = conditionAlign;
	}

	

	public RowRender getRowRender() {
		return rowRender;
	}

	public void setRowRender(RowRender rowRender) {
		this.rowRender = rowRender;
	}

	public Map<String, Column> getColumnmap() {
		return columnmap;
	}

	public void setColumnmap(Map<String, Column> columnmap) {
		this.columnmap = columnmap;
	}

	public Font getConditionFont() {
		return conditionFont;
	}

	public void setConditionFont(Font conditionFont) {
		this.conditionFont = conditionFont;
	}

	public int getConditionHeight() {
		return conditionHeight;
	}

	public void setConditionHeight(int conditionHeight) {
		this.conditionHeight = conditionHeight;
	}

	public int getContentHeight() {
		return contentHeight;
	}

	public void setContentHeight(int contentHeight) {
		this.contentHeight = contentHeight;
	}

	public int getTitleAlign() {
		return titleAlign;
	}

	public void setTitleAlign(int titleAlign) {
		this.titleAlign = titleAlign;
	}

	

	public short getConditionBgColor() {
		return conditionBgColor;
	}

	public void setConditionBgColor(short conditionBgColor) {
		this.conditionBgColor = conditionBgColor;
	}

	public short getConditionColor() {
		return conditionColor;
	}

	public void setConditionColor(short conditionColor) {
		this.conditionColor = conditionColor;
	}

	public short getTitleBgColor() {
		return titleBgColor;
	}

	public void setTitleBgColor(short titleBgColor) {
		this.titleBgColor = titleBgColor;
	}

	public short getTitleColor() {
		return titleColor;
	}

	public void setTitleColor(short titleColor) {
		this.titleColor = titleColor;
	}

	public Font getTitleFont() {
		return titleFont;
	}

	public void setTitleFont(Font titleFont) {
		this.titleFont = titleFont;
	}

	public int getTitleHeight() {
		return titleHeight;
	}

	public void setTitleHeight(int titleHeight) {
		this.titleHeight = titleHeight;
	}

	public short getColumnTitleBgColor() {
		return columnTitleBgColor;
	}

	public void setColumnTitleBgColor(short columnTitleBgColor) {
		this.columnTitleBgColor = columnTitleBgColor;
	}

	public short getColumnTitleColor() {
		return columnTitleColor;
	}

	public void setColumnTitleColor(short columnTitleColor) {
		this.columnTitleColor = columnTitleColor;
	}

	public Font getColumnTitleFont() {
		return columnTitleFont;
	}

	public void setColumnTitleFont(Font columnTitleFont) {
		this.columnTitleFont = columnTitleFont;
	}

	public int getColumnTitleHeight() {
		return columnTitleHeight;
	}

	public void setColumnTitleHeight(int columnTitleHeight) {
		this.columnTitleHeight = columnTitleHeight;
	}

	public boolean isColumnTitleBreakText() {
		return columnTitleBreakText;
	}

	public void setColumnTitleBreakText(boolean columnTitleBreakText) {
		this.columnTitleBreakText = columnTitleBreakText;
	}

	public boolean isConditionBreakText() {
		return conditionBreakText;
	}

	public void setConditionBreakText(boolean conditionBreakText) {
		this.conditionBreakText = conditionBreakText;
	}

	public short getColumnTitleBorderBottom() {
		return columnTitleBorderBottom;
	}

	public void setColumnTitleBorderBottom(short columnTitleBorderBottom) {
		this.columnTitleBorderBottom = columnTitleBorderBottom;
	}

	public short getColumnTitleBorderLeft() {
		return columnTitleBorderLeft;
	}

	public void setColumnTitleBorderLeft(short columnTitleBorderLeft) {
		this.columnTitleBorderLeft = columnTitleBorderLeft;
	}

	public short getColumnTitleBorderRight() {
		return columnTitleBorderRight;
	}

	public void setColumnTitleBorderRight(short columnTitleBorderRight) {
		this.columnTitleBorderRight = columnTitleBorderRight;
	}

	public short getColumnTitleBorderTop() {
		return columnTitleBorderTop;
	}

	public void setColumnTitleBorderTop(short columnTitleBorderTop) {
		this.columnTitleBorderTop = columnTitleBorderTop;
	}

	public short getColumnTitleBottomBorderColor() {
		return columnTitleBottomBorderColor;
	}

	public void setColumnTitleBottomBorderColor(short columnTitleBottomBorderColor) {
		this.columnTitleBottomBorderColor = columnTitleBottomBorderColor;
	}

	public short getColumnTitleLeftBorderColor() {
		return columnTitleLeftBorderColor;
	}

	public void setColumnTitleLeftBorderColor(short columnTitleLeftBorderColor) {
		this.columnTitleLeftBorderColor = columnTitleLeftBorderColor;
	}

	public short getColumnTitleRightBorderColor() {
		return columnTitleRightBorderColor;
	}

	public void setColumnTitleRightBorderColor(short columnTitleRightBorderColor) {
		this.columnTitleRightBorderColor = columnTitleRightBorderColor;
	}

	public short getColumnTitleTopBorderColor() {
		return columnTitleTopBorderColor;
	}

	public void setColumnTitleTopBorderColor(short columnTitleTopBorderColor) {
		this.columnTitleTopBorderColor = columnTitleTopBorderColor;
	}

	public int getFloorAlign() {
		return floorAlign;
	}

	public void setFloorAlign(int floorAlign) {
		this.floorAlign = floorAlign;
	}

	public short getFloorBgColor() {
		return floorBgColor;
	}

	public void setFloorBgColor(short floorBgColor) {
		this.floorBgColor = floorBgColor;
	}

	public boolean isFloorBreakText() {
		return floorBreakText;
	}

	public void setFloorBreakText(boolean floorBreakText) {
		this.floorBreakText = floorBreakText;
	}

	public short getFloorColor() {
		return floorColor;
	}

	public void setFloorColor(short floorColor) {
		this.floorColor = floorColor;
	}

	public Font getFloorFont() {
		return floorFont;
	}

	public void setFloorFont(Font floorFont) {
		this.floorFont = floorFont;
	}

	public int getFloorHeight() {
		return floorHeight;
	}

	public void setFloorHeight(int floorHeight) {
		this.floorHeight = floorHeight;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Font getContentFont() {
		return contentFont;
	}

	public void setContentFont(Font contentFont) {
		this.contentFont = contentFont;
	}

	public short getContentColor() {
		return contentColor;
	}

	public void setContentColor(short contentColor) {
		this.contentColor = contentColor;
	}

	public short getContentBgColor() {
		return contentBgColor;
	}

	public void setContentBgColor(short contentBgColor) {
		this.contentBgColor = contentBgColor;
	}

	public boolean isWriteColumnHeader() {
		return writeColumnHeader;
	}

	public void setWriteColumnHeader(boolean writeColumnHeader) {
		this.writeColumnHeader = writeColumnHeader;
	}

	
	
	
	
}
