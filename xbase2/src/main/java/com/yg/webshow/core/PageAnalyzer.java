package com.yg.webshow.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.yg.webshow.crawl.Crawl;
import com.yg.webshow.crawl.CrawlData;
import com.yg.webshow.data.CrawlRow;
import com.yg.webshow.data.CrawlTable;
import com.yg.webshow.data.EnvManager;
import com.yg.webshow.data.NewsTable;

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
	
	public void updateNews() {
		NewsTable newsTable = new NewsTable(this.envManaer.getNewConnection());
		List<CrawlData> newData = this.getNewData();
		log.info("News Crawled :" + newData.size());
		
		for (CrawlData cd : newData) {
			newsTable.insertNews(this.siteId, cd.getUrl(), cd.getAnchorText());
		}
		
		newsTable.close();
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
		
		crawlTable.close();
		return resData;
	}
	
	
	public static void main(String ... v) {
		PageAnalyzer pageAnalyzer = new PageAnalyzer("3", "http://news.naver.com/main/home.nhn");
//		PageAnalyzer pageAnalyzer = new PageAnalyzer("4", "http://clien.net/cs2/bbs/board.php?bo_table=park");
//		List<CrawlData> newData = pageAnalyzer.getNewData();
//		
//		System.out.println("---------- [NEWS] ----------");
//		int i = 0;
//		for(CrawlData crawlData : newData) {
//			System.out.println(i++ + "\t" + crawlData);
//		}
		
		pageAnalyzer.updateNews();
	}
}
