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
		Elements elem = this.doc.select("html");
			
		int i = 0;
		for(DocPathUnit path : lstUnit) {
			if(path.getTagName() != null && path.getTagName().equals("html")) {
				break ;
			}
			i ++;
		}
		
		System.out.println("i INDEX :" + i);
		Element tmpElem = elem.get(0);
		for(;i<lstUnit.size();i++) {
			DocPathUnit path = lstUnit.get(i);			
			tmpElem = tmpElem.child(path.getChildIndex());
			
		}
		
		return tmpElem;
	}
	
	
	public static void main(String ... v) {
		String url = "http://news.chosun.com/site/data/html_dir/2015/12/27/2015122700455.html";
		PageUnitProcessor test = new PageUnitProcessor(null, url);
		try {
			test.load();
			List<CrawlData> lstData = test.getCrawlData();
			
			for(CrawlData crawlData : lstData) {
				System.out.println("CrawlData >>> "+ crawlData);
			}
			
			ArrayList<DocPathUnit> lstPath = new ArrayList<DocPathUnit>() ;
			lstPath.add(new DocPathUnit("html", 1));
			lstPath.add(new DocPathUnit("body", 3));
			lstPath.add(new DocPathUnit("div", 2));
			lstPath.add(new DocPathUnit("div", 1));
			lstPath.add(new DocPathUnit("article", 1));
			lstPath.add(new DocPathUnit("div", 20));
			
			Element resElem = test.getElement(lstPath);
			
			System.out.println("Result >" + resElem.text());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
