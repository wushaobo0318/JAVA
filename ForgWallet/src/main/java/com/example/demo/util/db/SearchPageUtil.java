package com.example.demo.util.db;




public class SearchPageUtil {
	// 开始行
	private int startRow;
	// 终止行
	private int pageSize;
	// 分页类
	private Page page;

	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.startRow = (page.getPageIndex() - 1) * page.getPageSize();
		this.pageSize = page.getPageSize();
		this.page = page;
	}
}
