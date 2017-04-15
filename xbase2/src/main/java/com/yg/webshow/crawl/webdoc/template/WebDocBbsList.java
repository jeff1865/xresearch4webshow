package com.yg.webshow.crawl.webdoc.template;

import java.util.Date;
import java.util.List;

public class WebDocBbsList {
	private Date timestamp ;
	private List<DbbsTitleLine> titleLines ;

	public List<DbbsTitleLine> getTitleLines() {
		return titleLines;
	}

	public void setTitleLines(List<DbbsTitleLine> titleLines) {
		this.titleLines = titleLines;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
