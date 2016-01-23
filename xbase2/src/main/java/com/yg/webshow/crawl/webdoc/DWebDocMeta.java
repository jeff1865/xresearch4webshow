package com.yg.webshow.crawl.webdoc;

import java.util.List;

import org.jsoup.nodes.TextNode;

public class DWebDocMeta {
	private String title;
	private List<TextNode> contents;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<TextNode> getContents() {
		return contents;
	}
	public void setContents(List<TextNode> contents) {
		this.contents = contents;
	}
	
}
