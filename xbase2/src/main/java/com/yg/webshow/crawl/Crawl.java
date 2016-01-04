package com.yg.webshow.crawl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawl {
	private static Logger log = Logger.getLogger(Crawl.class);
	
	private String seedUrl;
	
	public Crawl(String url) {
		this.seedUrl = url;
	}
	
	public List<CrawlData> getInnerURL() {
		ArrayList<CrawlData> lstRetData = new ArrayList<CrawlData>();
		
		try {
			Document doc = Jsoup.connect(this.seedUrl).get();
			Elements elem = doc.getAllElements();
			log.info("Size :" + elem.size());
					
			Elements links = doc.select("a[href]");
			CrawlData crawlData = null;			
	        for (Element link : links) {
//	            log.info(link.attr("abs:href") +"---"+ link.text());
	            
	        	if(link.attr("abs:href") == null || link.attr("abs:href").trim().length() == 0) continue;
	        	
	            crawlData = new CrawlData(link.attr("abs:href"));
	            crawlData.setAnchorText(link.text());
	            
	            if(link.text() == null || link.text().trim().length() == 0) {
	            	Elements img = link.select("img[src]");
	            	crawlData.setImgSrc(img.attr("abs:src"));
//	            	log.info("(IMG)" + img.attr("abs:src"));
	            } 
	            
	            lstRetData.add(crawlData);
	        }
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lstRetData;
	}
	
	
	public static void main(String ... v) {
		Crawl test = new Crawl("http://news.naver.com/main/home.nhn");
		List<CrawlData> innerURL = test.getInnerURL();
		
		int i = 0;
		for(CrawlData crawlData : innerURL) {
			System.out.println( i++ + ".\t" + crawlData);
		}
	} 
}
