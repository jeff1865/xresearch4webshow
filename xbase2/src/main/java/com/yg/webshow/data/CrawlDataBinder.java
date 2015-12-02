package com.yg.webshow.data;

import org.apache.hadoop.conf.Configuration;

public class CrawlDataBinder {
	
	public static final String TABLE_NAME = "CrawlBuffer";
	
	Configuration config = null;
	
	public CrawlDataBinder(Configuration conf) {
		this.config = conf;
	}
	
	public Object getUrlGroup() {
		return null;
	}
	
	public void initTable() {
		// ---
		;
	}
	
	public void addCrawlData(String url, String anchorText, String imgSrc) {
		// ---
		;
	}
}
