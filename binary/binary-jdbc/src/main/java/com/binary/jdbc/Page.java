package com.binary.jdbc;

import java.io.Serializable;
import java.util.List;

public class Page<E> implements Serializable {
	private static final long serialVersionUID = 1068473581659173339L;
	
	
	private long pageNum;
	private long pageSize;
	
	private long totalRows;
	private long totalPages;
	
	
	private List<E> data;
	
	
	
	public Page() {
	}



	public Page(long pageNum, long pageSize, long totalRows, long totalPages, List<E> data) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.totalRows = totalRows;
		this.totalPages = totalPages;
		this.data = data;
	}



	public long getPageNum() {
		return pageNum;
	}



	public void setPageNum(long pageNum) {
		this.pageNum = pageNum;
	}



	public long getPageSize() {
		return pageSize;
	}



	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}



	public long getTotalRows() {
		return totalRows;
	}



	public void setTotalRows(long totalRows) {
		this.totalRows = totalRows;
	}



	public long getTotalPages() {
		return totalPages;
	}



	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}



	public List<E> getData() {
		return data;
	}



	public void setData(List<E> data) {
		this.data = data;
	}


	
	
	
}
