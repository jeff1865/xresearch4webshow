package com.yg.webshow.crawl.webdoc.template;

import java.util.Map;

public class DComment {
	private String author ;
	private String comment ;
	private String strDate ;
	private Map<String, String> etc ;
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getStrDate() {
		return strDate;
	}
	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}
	public Map<String, String> getEtc() {
		return etc;
	}
	public void setEtc(Map<String, String> etc) {
		this.etc = etc;
	}
	
}
