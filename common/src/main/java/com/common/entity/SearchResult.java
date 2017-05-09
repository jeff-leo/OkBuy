package com.common.entity;

import java.util.List;

public class SearchResult {

	private List<Item> itemList;
	//总记录数
	private long resultCount;
	//总页数
	private long pageCount;
	//当前页
	private long curPage;
	
	public List<Item> getItemList() {
		return itemList;
	}
	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
	public long getResultCount() {
		return resultCount;
	}
	public void setResultCount(long resultCount) {
		this.resultCount = resultCount;
	}
	public long getPageCount() {
		return pageCount;
	}
	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}
	public long getCurPage() {
		return curPage;
	}
	public void setCurPage(long curPage) {
		this.curPage = curPage;
	}
	
}
