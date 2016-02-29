package com.binary.tools.excel;




/**
 * excel导出再填充数据时事件
 * @author wanwb
 *
 */
public interface ExporterFillSheetListener {
	
	
	/**
	 * 开始填充sheet时事件
	 * @return 外部在sheet中添加的行数
	 */
	public int onStartFill(ExporterFillSheetEvent e);
	
	
	/**
	 * 填充Excel表头之前事件 (如果当前表头为空则不激发当前事件)
	 * @param e
	 * @return 外部在sheet中添加的行数
	 */
	public int onBeforeFillTitle(ExporterFillSheetEvent e);
	
	
	
	/**
	 * 填充完Excel表头事件 (如果当前表头为空则不激发当前事件)
	 * @param e
	 * @return 外部在sheet中添加的行数
	 */
	public int onAfterFillTitle(ExporterFillSheetEvent e);
	
	
	
	
	/**
	 * 填充Excel条件之前事件 (如果当前条件为空则不激发当前事件)
	 * @param e
	 * @return 外部在sheet中添加的行数
	 */
	public int onBeforeFillCondition(ExporterFillSheetEvent e);
	
	
	/**
	 * 填充完Excel条件事件 (如果当前条件为空则不激发当前事件)
	 * @param e
	 * @return 外部在sheet中添加的行数
	 */
	public int onAfterFillCondition(ExporterFillSheetEvent e);
	
	
	
	/**
	 * 填充Excel列头之前事件 (如果当前列头为空则不激发当前事件)
	 * @param e
	 * @return 外部在sheet中添加的行数
	 */
	public int onBeforeFillClumnHeader(ExporterFillSheetEvent e);
	
	
	/**
	 * 填充完Excel列头事件 (如果当前列头为空则不激发当前事件)
	 * @param e
	 * @return 外部在sheet中添加的行数
	 */
	public int onAfterFillClumnHeader(ExporterFillSheetEvent e);
	
	
	
	
	/**
	 * 填充Excel数据之前事件 (如果当前数据为空则不激发当前事件)
	 * @param e
	 * @return 外部在sheet中添加的行数
	 */
	public int onBeforeFillData(ExporterFillSheetEvent e);
	
	
	/**
	 * 填充完Excel数据事件 (如果当前数据为空则不激发当前事件)
	 * @param e
	 * @return 外部在sheet中添加的行数
	 */
	public int onAfterFillData(ExporterFillSheetEvent e);
	
	
	
	/**
	 * 填充Excel落款之前事件 (如果当前落款为空则不激发当前事件)
	 * @param e
	 * @return 外部在sheet中添加的行数
	 */
	public int onBeforeFillFloor(ExporterFillSheetEvent e);
	
	
	/**
	 * 填充完Excel落款事件 (如果当前落款为空则不激发当前事件)
	 * @param e
	 * @return 外部在sheet中添加的行数
	 */
	public int onAfterFillFloor(ExporterFillSheetEvent e);
	
	
	
	/**
	 * 填充完Excel事件
	 * @param e
	 * @return 外部在sheet中添加的行数
	 */
	public int onEndFill(ExporterFillSheetEvent e);
	
	
	
	
	
}




