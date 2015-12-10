package com.yg.webshow.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.yg.webshow.crawl.Crawl;
import com.yg.webshow.crawl.CrawlData;
import com.yg.webshow.data.CrawlRow;
import com.yg.webshow.data.CrawlTable;
import com.yg.webshow.data.EnvManager;

public class PageAnalyzer {
	private static Logger log = Logger.getLogger(PageAnalyzer.class);
	
	private String siteId = null;
	private String seedUrl = null;
	
	public EnvManager envManaer = null;
	
	public PageAnalyzer(String id, String seed) {
		this.siteId = id;
		this.seedUrl = seed;
		this.envManaer = new EnvManager();
	}
	
	public List<CrawlData> getNewData() {
		ArrayList<CrawlData> resData = new ArrayList<CrawlData>();
		
		CrawlTable crawlTable = new CrawlTable(this.envManaer.getNewConnection());
		Crawl crawl = new Crawl(this.seedUrl);
		List<CrawlData> innerURL = crawl.getInnerURL();
		
		int i= 0;
		for(CrawlData crawlData : innerURL) {
			CrawlRow rawData = crawlTable.getRawData(this.siteId, crawlData.getUrl());
			if(rawData == null) {
				resData.add(crawlData);
				crawlTable.insertCrawledUrl(siteId, crawlData.getUrl(), crawlData.getAnchorText());
			} else {
				log.info(i++ + "\tDuplicated :" + crawlData);
			}
//			System.out.println( i++ + ".\t" + crawlData);
		}
				
		return resData;
	}
	
	
	public static void main(String ... v) {
		PageAnalyzer pageAnalyzer = new PageAnalyzer("3", "http://news.naver.com/main/home.nhn");
		List<CrawlData> newData = pageAnalyzer.getNewData();
		
		System.out.println("---------- [NEWS] ----------");
		int i = 0;
		for(CrawlData crawlData : newData) {
			System.out.println(i++ + "\t" + crawlData);
		}
	}
}
