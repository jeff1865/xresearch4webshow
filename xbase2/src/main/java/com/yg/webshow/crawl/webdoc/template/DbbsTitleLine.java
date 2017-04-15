package com.yg.webshow.crawl.webdoc.template;

public class DbbsTitleLine {
	private String no;
	private String title ;
	private String author ;
	private String date ;
	private int hit ;
	private String url ;
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(no).append("\t");
		sb.append(title).append("\t");
		sb.append(author).append("\t");
		sb.append(date).append("\t");
		sb.append(hit).append("\t");
		sb.append(url);
		
		return sb.toString();
	}
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
