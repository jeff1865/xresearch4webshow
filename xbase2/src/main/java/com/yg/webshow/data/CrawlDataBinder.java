package com.yg.webshow.data;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HTable;

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
		try {
			HTable table = new HTable(this.config, TABLE_NAME);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
