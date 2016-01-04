package com.yg.webshow.crawl;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

//TODO Page URL RgularExpression Changer
//TODO Data Scheme to analyze WebPage
public class PageUnitProcessor {
	
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
		this.doc = Jsoup.connect("http://gall.dcinside.com/board/lists/?id=stock_new1").get();
	}
	
	public String getContentWithRule(String extRule) {
		return null;
	}
	
	public String getDocUrls() {
		return null;
	} 
	
	public Element getElement(List<DocPathUnit> lstUnit) {
				
		return null;
	}
	
	
	public static void main(String ... v) {
		;
	}
	
}
