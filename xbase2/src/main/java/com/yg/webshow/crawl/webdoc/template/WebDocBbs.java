package com.yg.webshow.crawl.webdoc.template;

import java.util.ArrayList;
import java.util.List;

public class WebDocBbs extends WebDoc {
	
	private List<DComment> comment ;
	private List<String> imgUrl;
	
	public WebDocBbs() {
		this.comment = new ArrayList<DComment>();
		this.imgUrl = new ArrayList<String>();
	}
	
	public List<?> getComment() {
		return comment;
	}

	public void setComment(List<DComment> comment) {
		this.comment = comment;
	}

	public List<String> getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(List<String> imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString());
		sb.append("|IMG:" + this.imgUrl.size() + "|");
		for(String url : this.imgUrl) {
			sb.append(url).append(";");
		}
		
		return sb.toString();
	}
}
