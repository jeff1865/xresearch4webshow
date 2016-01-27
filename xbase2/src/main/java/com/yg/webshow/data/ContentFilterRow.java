package com.yg.webshow.data;

public class ContentFilterRow implements IRow {
	private String value ;
	private long cnt ;
	private String initAt;
	public String getInitAt() {
		return initAt;
	}
	public void setInitAt(String initAt) {
		this.initAt = initAt;
	}
	public String getLatestAt() {
		return latestAt;
	}
	public void setLatestAt(String latestAt) {
		this.latestAt = latestAt;
	}

	private String latestAt;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public long getCnt() {
		return cnt;
	}
	public void setCnt(long cnt) {
		this.cnt = cnt;
	}
	
	public String toString() {
		return this.value + "|" + this.cnt + "|" + this.initAt + "|" + this.latestAt;
	}
}
