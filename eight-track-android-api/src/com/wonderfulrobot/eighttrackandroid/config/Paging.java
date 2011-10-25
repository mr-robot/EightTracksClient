package com.wonderfulrobot.eighttrackandroid.config;

public class Paging {
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	private int page = 1;
	private int count = 12;

}
