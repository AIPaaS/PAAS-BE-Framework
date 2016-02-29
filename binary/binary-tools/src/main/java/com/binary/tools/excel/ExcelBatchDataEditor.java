package com.binary.tools.excel;

import java.util.ArrayList;
import java.util.List;

import com.binary.tools.exception.ExcelException;

public class ExcelBatchDataEditor {
	
	
	
	public static interface DataEditor<E> {
		public List<E> getBatch(int startNum, int pageNum, int pageSize, int totalRows, Object params);
	}
	
	
	/** excel导出最大支持行数 **/
	private int maxSize = 65535;
	
	/** 每批次行数 **/
	private int batchSize = 900;
	
	
	
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	
	
	/**
	 * 迭代分批获取excel导出数据
	 * @param totalRows
	 * @param editor
	 * @return
	 */
	public <T> List<T> iterator(int totalRows, DataEditor<T> editor) {
		return iterator(totalRows, this.batchSize, null, editor);
	}
	
	/**
	 * 迭代分批获取excel导出数据
	 * @param totalRows
	 * @param editor
	 * @return
	 */
	public <T> List<T> iterator(int totalRows, int batchLength, DataEditor<T> editor) {
		return iterator(totalRows, batchLength, null, editor);
	}
	
	
	/**
	 * 迭代分批获取excel导出数据
	 * @param totalRows
	 * @param params
	 * @param editor
	 * @return
	 */
	public <T> List<T> iterator(int totalRows, Object params, DataEditor<T> editor) {
		return iterator(totalRows, this.batchSize, params, editor);
	}
	

	/**
	 * 迭代分批获取excel导出数据
	 * @param totalRows
	 * @param params
	 * @param editor
	 * @return
	 */
	public <T> List<T> iterator(int totalRows, int batchLength, Object params, DataEditor<T> editor) {
		List<T> alllist = new ArrayList<T>();
		if(totalRows <= 0) return alllist;
		
		if(totalRows > this.maxSize) throw new ExcelException("超出当前系统对Excel导出最大支持数:"+totalRows+">"+this.maxSize+"! ");
		
		int totalPages = totalRows%batchLength==0 ? totalRows/batchLength : totalRows/batchLength+1;
		
		for(int i=0; i<totalPages; i++) {
			int pageNum = i + 1;
			int startNum = (pageNum - 1) * batchLength + 1;
			
			List<T> list = editor.getBatch(startNum, pageNum, batchLength, totalRows, params);
			
			alllist.addAll(list);
		}
		
		return alllist;
	}
	
	
	
	
	
	
}
