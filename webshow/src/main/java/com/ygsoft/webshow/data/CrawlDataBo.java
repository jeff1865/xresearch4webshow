package com.ygsoft.webshow.data;

import java.util.Date;

public class CrawlDataBo {
	private String siteId ;
	private String postId ;
	private String anchorTitle ;
	private String author ;
	private long docTs ;
	private String status ;
	private String url ;
	
	public CrawlDataBo() {
		;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer() ;
		sb.append(this.siteId).append("\t");
		sb.append(this.postId).append("\t");
		sb.append(this.anchorTitle).append("\t");
//		sb.append(this.filteredContents).append("\t");
//		sb.append(this.docTitle).append("\t");
		sb.append(this.author).append("\t");
		sb.append(new Date(this.docTs)).append("\t");
		sb.append(this.status).append("\t");
		sb.append(this.url);
		
		return sb.toString() ;
	}
	
	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getAnchorTitle() {
		return anchorTitle;
	}

	public void setAnchorTitle(String anchorTitle) {
		this.anchorTitle = anchorTitle;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public long getDocTs() {
		return docTs;
	}

	public void setDocTs(long docTs) {
		this.docTs = docTs;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}
	
	
}
