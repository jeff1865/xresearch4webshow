package com.yg.webshow.crawl.webdoc;

import java.util.List;

public class WrapperPath {
	private String title;
	private List<String> contents;
	private String docDate ;
	private List<String> reply ;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<String> getContents() {
		return contents;
	}
	public void setContents(List<String> contents) {
		this.contents = contents;
	}
	public String getDocDate() {
		return docDate;
	}
	public void setDocDate(String docDate) {
		this.docDate = docDate;
	}
	public List<String> getReply() {
		return reply;
	}
	public void setReply(List<String> reply) {
		this.reply = reply;
	}
	
}
