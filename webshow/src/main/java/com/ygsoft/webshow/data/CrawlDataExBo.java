package com.ygsoft.webshow.data;

import java.util.Date;

public class CrawlDataExBo extends CrawlDataBo {
	private String filteredContents ;
	private String docTitle ;
	
	public CrawlDataExBo() {
		;
	}
	
	public String getDocTitle() {
		return docTitle;
	}

	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}


	public String getFilteredContents() {
		return filteredContents;
	}


	public void setFilteredContents(String filteredContents) {
		this.filteredContents = filteredContents;
	}
	
	public String getPostDate() {
		return new Date(super.getDocTs()).toString();
	}
}
