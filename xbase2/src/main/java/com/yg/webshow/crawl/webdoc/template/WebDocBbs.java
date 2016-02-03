package com.yg.webshow.crawl.webdoc.template;

import java.util.List;

public class WebDocBbs extends WebDoc {
	
	private List<DComment> comment ;
	
	public WebDocBbs() {
		;
	}
	
	public List<?> getComment() {
		return comment;
	}

	public void setComment(List<DComment> comment) {
		this.comment = comment;
	}
}
