package com.yg.webshow.data;

import java.io.IOException;
import java.util.Hashtable;

import org.apache.hadoop.hbase.client.Connection;

import com.yg.webshow.util.DateUtil;
import com.yg.webshow.util.TextUtil;

public class NewsTable extends AbstractTable {
	public static final String TABLE_NAME = "newslist";
	public static final String CF_MAIN = "cr1";
	public static final String CF_SUB1 = "cr2";
	public static final String CQ_ANCHOR = "reg";
	public static final String CQ_LINK = "lnk";
	public static final String CQ_SITE_ID = "sid";
	
	/*
	 * Key = [siteid]_[timestamp] | 
	 */
	
	public NewsTable(Connection conn) {
		super(conn);
	}
	
	public void insertNews(String siteId, String url, String anchor) {
		String fSiteId = TextUtil.getFixedLengthText(siteId);
		String tsKey = DateUtil.getCurrentTsKey();
		String rowKey = fSiteId + "_" + tsKey;
		
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put(CQ_ANCHOR, anchor);
		data.put(CQ_LINK, url);
		data.put(CQ_SITE_ID, siteId);
		
		try {
			this.put(rowKey, CF_MAIN, data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}
	
}
