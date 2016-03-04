package com.yg.webshow.core.realtime;

import java.util.List;

import org.apache.log4j.Logger;

import com.yg.webshow.data.NewsRow;
import com.yg.webshow.data.NewsTable;

/**
 * Get News from newslist table --> extract news from document --> update newslist
 * @author jeff
 *
 */
public class NewsFilterJoblet implements Joblet {
	private static Logger log = Logger.getLogger(NewsFilterJoblet.class);
	
	private NewsTable newsTable = null;
	private final int defaultMaxCount = 20;
	private int seedId ;
	
	public NewsFilterJoblet(int seedId) {
		this.newsTable = new NewsTable() ;
		this.seedId = seedId ;
	}
	
	public int extractDocMeta() {
		List<NewsRow> latestNews = this.newsTable.getLatestNews(seedId, defaultMaxCount);
		int i = 0;
		for(NewsRow newsRow : latestNews) {
			log.info(i++ + "-->" + newsRow.toString());
		}
		return -1;
	}
	
	@Override
	public boolean start() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static void main(String ... v) {
		NewsFilterJoblet test = new NewsFilterJoblet(5);
		test.extractDocMeta();
	}
	
}
