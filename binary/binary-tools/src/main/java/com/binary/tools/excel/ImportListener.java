package com.binary.tools.excel;



public interface ImportListener {
	
	
	/**
	 * 
	 * @param value: 当前Cell值
	 * @param key: 当前列所对应的Key
	 * @param nullable: 当前列是否可以为空
	 * @param type: 当前列类型	//S=文本		N=数值		D=日期		E=枚举
	 * @param row: 当前Cell所在行
	 * @param col: 当前Cell所在列
	 * @param error: 当前值如果合法则为null,否则为错误信息
	 * @return
	 */
	public Object readCell(Object value, String key, boolean nullable, char type, int row, int col, ImportErrorException error);
	
	
	
}
