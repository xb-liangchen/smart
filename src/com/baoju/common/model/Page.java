package com.baoju.common.model;

import java.util.List;

public class Page {

	private int curpage=1;
	
	private int pages;
	
	private int pagesize=10;
	
	private int rows;
	
	private List entityList;

	public int getCurpage() {
		return curpage;
	}

	public void setCurpage(int curpage) {
		this.curpage = curpage;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getPagesize() {
		return pagesize;
	}

	public List getEntityList() {
		return entityList;
	}

	public void setEntityList(List entityList) {
		this.entityList = entityList;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	
}
