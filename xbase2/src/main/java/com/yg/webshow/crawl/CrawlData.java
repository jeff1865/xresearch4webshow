package com.yg.webshow.crawl;

public class CrawlData {
	
	public String url ;
	public String anchorText ;
	public String imgSrc;
	public String mediaSrc;
	
	public CrawlData(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAnchorText() {
		return anchorText;
	}

	public void setAnchorText(String anchorText) {
		this.anchorText = anchorText;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public String getMediaSrc() {
		return mediaSrc;
	}

	public void setMediaSrc(String mediaSrc) {
		this.mediaSrc = mediaSrc;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.url).append("---");
		sb.append(this.anchorText);
		if(this.imgSrc != null) sb.append("|").append(this.imgSrc);
		return sb.toString();
	}
}
