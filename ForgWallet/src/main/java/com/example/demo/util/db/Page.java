package com.example.demo.util.db;



public class Page {
	// 第几页
	private int startRow;
	// 每页大小
	private int pageSize;
	// 总数
	private int rowTotal;
	// 总共多少页
	private int pageTotal;
	public Page(int pageIndex, int pageSize, int rowTotal) {
		this.startRow = pageIndex;
		this.pageSize = pageSize;
		this.rowTotal = rowTotal;
		/*if (rowTotal % pageSize == 0) {
			pageTotal = this.rowTotal / this.pageSize;
		} else {*/
			pageTotal = (rowTotal + pageSize - 1) / pageSize;    //(startRow-1) / pageSize
		/*}*/
	}
	public int getRowTotal() {
		return rowTotal;
	}
	public void setRowTotal(int rowTotal) {
		this.rowTotal = rowTotal;
	}
	public int getPageIndex() {
		return startRow;
	}
	public void setPageIndex(int pageIndex) {
		this.startRow = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageTotal() {
		return pageTotal;
	}
	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}
}
