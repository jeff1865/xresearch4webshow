package com.yg.webshow.crawl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//TODO Page URL RgularExpression Changer
//TODO Data Scheme to analyze WebPage
public class PageUnitProcessor {
	private static Logger log = Logger.getLogger(PageUnitProcessor.class);
			
	private String id ;
	private String url ;
	private Document doc ;
	
	public class DocPathUnit {
		private String tagName ;
		private int childIndex;
		
		public String getTagName() {
			return tagName;
		}

		public void setTagName(String tagName) {
			this.tagName = tagName;
		}

		public int getChildIndex() {
			return childIndex;
		}

		public void setChildIndex(int childIndex) {
			this.childIndex = childIndex;
		}
				
		public DocPathUnit(String tagNm, int childIdx) {
			tagName = tagNm ;
			childIndex = childIdx ;
		}	
	}
	
	public PageUnitProcessor(String id, String url) {
		this.id = id;
		this.url = url;
	}
	
	public void load() throws IOException {
		this.doc = Jsoup.connect(this.url).get();
	}
	
	public String getContentWithRule(String extRule) {
		return null;
	}
	
	public List<CrawlData> getCrawlData() {
		ArrayList<CrawlData> lstRetData = new ArrayList<CrawlData>();
		
		try {
			Document doc = Jsoup.connect(this.url).get();
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
	
	public Element getElement(List<DocPathUnit> lstUnit) {
		
		for(DocPathUnit docPathUtil : lstUnit) {
			;
		}
				
		return null;
	}
	
	
	public static void main(String ... v) {
		String url = "http://gall.dcinside.com/board/lists/?id=stock_new1";
		PageUnitProcessor test = new PageUnitProcessor(null, url);
		try {
			test.load();
			List<CrawlData> lstData = test.getCrawlData();
			
			for(CrawlData crawlData : lstData) {
				System.out.println("CrawlData >>> "+ crawlData);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
