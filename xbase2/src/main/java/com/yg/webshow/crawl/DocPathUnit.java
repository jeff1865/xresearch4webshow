package com.yg.webshow.crawl;

public class DocPathUnit {
	private String tagName ;
	private int childIndex;
	
	public DocPathUnit(String tagNm, int childIdx) {
		tagName = tagNm ;
		childIndex = childIdx ;
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