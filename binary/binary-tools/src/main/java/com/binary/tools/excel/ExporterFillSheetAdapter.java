package com.binary.tools.excel;


/**
 * excel导出再填充数据时事件适配器(减压适配器)
 * @author wanwb
 *
 */
public abstract class ExporterFillSheetAdapter implements ExporterFillSheetListener {
	
	
	
	
	/**
	 * @see ExporterFillSheetListener.#onStartFill(ExporterFillSheetEvent)
	 */
	public int onStartFill(ExporterFillSheetEvent e) {
		return 0;
	}
	

	/**
	 * @see ExporterFillSheetListener.#onBeforeFillTitle(ExporterFillSheetEvent)
	 */
	public int onBeforeFillTitle(ExporterFillSheetEvent e) {
		return 0;
	}
	
	
	/**
	 * @see ExporterFillSheetListener.#onAfterFillTitle(ExporterFillSheetEvent)
	 */
	public int onAfterFillTitle(ExporterFillSheetEvent e) {
		return 0;
	}
	
	
	/**
	 * @see ExporterFillSheetListener.#onBeforeFillCondition(ExporterFillSheetEvent)
	 */
	public int onBeforeFillCondition(ExporterFillSheetEvent e) {
		return 0;
	}
	
	/**
	 * @see ExporterFillSheetListener.#onAfterFillCondition(ExporterFillSheetEvent)
	 */
	public int onAfterFillCondition(ExporterFillSheetEvent e) {
		return 0;
	}
	
	
	/**
	 * @see ExporterFillSheetListener.#onBeforeFillClumnHeader(ExporterFillSheetEvent)
	 */
	public int onBeforeFillClumnHeader(ExporterFillSheetEvent e) {
		return 0;
	}
	
	
	/**
	 * @see ExporterFillSheetListener.#onAfterFillClumnHeader(ExporterFillSheetEvent)
	 */
	public int onAfterFillClumnHeader(ExporterFillSheetEvent e) {
		return 0;
	}
	
	
	
	/**
	 * @see ExporterFillSheetListener.#onBeforeFillData(ExporterFillSheetEvent)
	 */
	public int onBeforeFillData(ExporterFillSheetEvent e) {
		return 0;
	}
	
	
	/**
	 * @see ExporterFillSheetListener.#onAfterFillData(ExporterFillSheetEvent)
	 */
	public int onAfterFillData(ExporterFillSheetEvent e) {
		return 0;
	}
	
	
	
	/**
	 * @see ExporterFillSheetListener.#onBeforeFillFloor(ExporterFillSheetEvent)
	 */
	public int onBeforeFillFloor(ExporterFillSheetEvent e) {
		return 0;
	}
	
	
	/**
	 * @see ExporterFillSheetListener.#onAfterFillFloor(ExporterFillSheetEvent)
	 */
	public int onAfterFillFloor(ExporterFillSheetEvent e) {
		return 0;
	}
	
	
	
	/**
	 * @see ExporterFillSheetListener.#onEndFill(ExporterFillSheetEvent)
	 */
	public int onEndFill(ExporterFillSheetEvent e) {
		return 0;
	}
	
	
	
}
