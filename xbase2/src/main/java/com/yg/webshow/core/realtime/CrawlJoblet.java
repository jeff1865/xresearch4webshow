package com.yg.webshow.core.realtime;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.yg.webshow.crawl.Crawl;
import com.yg.webshow.crawl.DCrawlData;
import com.yg.webshow.data.CrawlRow;
import com.yg.webshow.data.CrawlTable;
import com.yg.webshow.data.EnvManager;
import com.yg.webshow.data.NewsTable;

/**
 * Crawl --> Upsert CrawlTable --> Upsert NewsListTable
 * @author jeff
 */
public class CrawlJoblet implements Joblet {
	private static Logger log = Logger.getLogger(CrawlJoblet.class);
	
	private String siteId = null;
	private String seedUrl = null;
	private String mustHaveToken = null;
	private EnvManager envManaer = null;
	private CrawlTable crawlTable = null;
	private NewsTable newsTable = null;
	
	public CrawlJoblet(String seed, String id, String mustHaveToken) {
		this.seedUrl = seed ;
		this.siteId = id ;
		this.mustHaveToken = mustHaveToken;
		this.init() ;
	}
	
	private void init() {
		this.envManaer = new EnvManager();
		this.crawlTable = new CrawlTable(this.envManaer.getNewConnection());
		this.newsTable = new NewsTable(this.envManaer.getNewConnection());
	}
	
	public List<DCrawlData> getNewData() {
		ArrayList<DCrawlData> resData = new ArrayList<DCrawlData>();
				
		Crawl crawl = new Crawl(this.seedUrl);
		List<DCrawlData> innerURL = crawl.getInnerURL();
		
		int i= 0;
		for(DCrawlData crawlData : innerURL) {
			CrawlRow rawData = crawlTable.getRawData(this.siteId, crawlData.getUrl());
			if(rawData == null) {
				if(this.mustHaveToken != null && crawlData.getUrl().contains(this.mustHaveToken))
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
	
	private int initNewsTable(List<DCrawlData> newData) {
		log.info("News Crawled :" + newData.size());
		
		int i = 0;
		for (DCrawlData cd : newData) {
			newsTable.insertNews(this.siteId, cd.getUrl(), cd.getAnchorText());
			i++;
		}
		newsTable.close();
		
		return i;
	}
	
	@Override
	public boolean start() {
		log.info("[Joblet] Crawl & Init News ..");
		List<DCrawlData> newData = this.getNewData();
		log.info("News Count :" + newData.size());
		for(DCrawlData cData : newData) {
			log.info("-->" + cData);
		}
		
		int result = this.initNewsTable(newData);
		log.info("News Table Updated :" + result);
		
		return true;
	}
	
	public static void main(String ... v) {
		CrawlJoblet crawlJob = new CrawlJoblet("http://clien.net/cs2/bbs/board.php?bo_table=park", "5", "park");
		crawlJob.start();
		
//		List<DCrawlData> newData = crawlJob.getNewData();
//		
//		int i = 0;
//		for(DCrawlData cdata : newData) {
//			System.out.println(i++ + " --> " + cdata);
//		}		
	}
	
}
