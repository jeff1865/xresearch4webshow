package com.yg.webshow.crawl.webdoc;

import java.util.List;

public class WrapperPath {
	private final String VALUE_TYPE_TEXT = "text";
	private final String VALUE_TYPE_TIMESTAMP = "timestamp";
	
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
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.title).append("|").append(this.contents).append("|")
		.append(this.docDate).append("|").append(this.reply);
		
		return sb.toString();
	}
}
