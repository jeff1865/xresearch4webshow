package com.yg.webshow.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.yg.webshow.crawl.Crawl;
import com.yg.webshow.crawl.DCrawlData;
import com.yg.webshow.data.CrawlRow;
import com.yg.webshow.data.CrawlTable;
import com.yg.webshow.data.EnvManager;
import com.yg.webshow.data.NewsRow;
import com.yg.webshow.data.NewsTable;

public class CrawlUnitJob {
	private static Logger log = Logger.getLogger(CrawlUnitJob.class);
	
	private String siteId = null;
	private String seedUrl = null;
	
	public EnvManager envManaer = null;
	
	public CrawlUnitJob(String id, String seed) {
		this.siteId = id;
		this.seedUrl = seed;
		this.envManaer = new EnvManager();
	}
	
	public void updateNews() {
		NewsTable newsTable = new NewsTable(this.envManaer.getNewConnection());
		List<DCrawlData> newData = this.getNewData();
		log.info("News Crawled :" + newData.size());
		
		for (DCrawlData cd : newData) {
			newsTable.insertNews(this.siteId, cd.getUrl(), cd.getAnchorText());
		}
		
		newsTable.close();
	}
	
	public List<DCrawlData> getNewData() {
		ArrayList<DCrawlData> resData = new ArrayList<DCrawlData>();
				
		CrawlTable crawlTable = new CrawlTable(this.envManaer.getNewConnection());
		Crawl crawl = new Crawl(this.seedUrl);
		List<DCrawlData> innerURL = crawl.getInnerURL();
		
		int i= 0;
		for(DCrawlData crawlData : innerURL) {
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
	
	@Deprecated
	public void printNews() {
		NewsTable newsTable = new NewsTable(this.envManaer.getNewConnection());
		List<NewsRow> news = newsTable.getNews(10, "0000004", "0000005");
		
		int i = 0;
		for(NewsRow newsRow : news) {
			System.out.println(i++ + "\t" + newsRow);
		}
	}
	
	
	public static void main(String ... v) {
//		PageAnalyzer pageAnalyzer = new PageAnalyzer("3", "http://news.naver.com/main/home.nhn");
		CrawlUnitJob pageAnalyzer = new CrawlUnitJob("4", "http://clien.net/cs2/bbs/board.php?bo_table=park");
//		List<CrawlData> newData = pageAnalyzer.getNewData();
//		
//		System.out.println("---------- [NEWS] ----------");
//		int i = 0;
//		for(CrawlData crawlData : newData) {
//			System.out.println(i++ + "\t" + crawlData);
//		}
		
//		pageAnalyzer.updateNews();
		pageAnalyzer.printNews();
	}
}
