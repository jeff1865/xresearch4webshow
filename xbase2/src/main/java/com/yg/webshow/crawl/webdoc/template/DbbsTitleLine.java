package com.yg.webshow.crawl.webdoc.template;

public class DbbsTitleLine {
	private int no;
	private String title ;
	private String userName ;
	private String date ;
	private int hit ;
	private String url ;
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(no).append("\t");
		sb.append(title).append("\t");
		sb.append(userName).append("\t");
		sb.append(date).append("\t");
		sb.append(hit).append("\t");
		sb.append(url);
		
		return sb.toString();
	}
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
