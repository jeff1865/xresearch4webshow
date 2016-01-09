package com.yg.webshow.crawl;

public class DocPathUnit {
	public static final int ALL_INDEX = -1;
	public static final int INVALID_INDEX = -2;
	
	private String tagName ;
	private int childIndex;
	
	public DocPathUnit(String tagNm, int childIdx) {
		tagName = tagNm ;
		childIndex = childIdx ;
	}
	
	public String toString() {
		return this.tagName + "[" + this.childIndex + "]";
	}
	
	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public int getChildIndex() {
		return childIndex;
	}

	public void setChildIndex(int childIndex) {
		this.childIndex = childIndex;
	}
}