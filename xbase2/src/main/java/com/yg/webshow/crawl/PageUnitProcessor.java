package com.yg.webshow.crawl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

//TODO Page URL RgularExpression Changer
//TODO Data Scheme to analyze WebPage
public class PageUnitProcessor {
	private static Logger log = Logger.getLogger(PageUnitProcessor.class);
			
	private String id ;
	private String url ;
	private Document doc ;
	
	private WebDocWrapperUtil wrapperUtil = new WebDocWrapperUtil();
		
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
	
	private static boolean isLinkedNode(Node node) {
		
		Node pNode = node.parent();
		if(pNode == null) return false;
		
		if(pNode instanceof Element) {
			Element elem = (Element) pNode;
			if(elem.tagName().trim().equalsIgnoreCase("body")) {
				return false;
			}
			
			if(elem.tagName().trim().equalsIgnoreCase("a")) {
				return true;
			} 
			
			return isLinkedNode(pNode);		
		} else {
			return isLinkedNode(pNode);
		}
	}
	
	public List<TextNode> getUnlinkedTextNodes () {
		ArrayList<TextNode> lstRes = new ArrayList<TextNode>();
//		Document doc = Jsoup.connect(this.url).get();
		doc.getAllElements();
		Elements elem = doc.getAllElements();
		
		ListIterator<Element> ItlElem = elem.listIterator();
		
		while(ItlElem.hasNext()) {
			;
			Element emt = ItlElem.next();
//			System.out.println("Element :" + emt.getClass() + "--->" + emt.tagName() + "--->" + emt.ownText());
			
			String data = null;
			List<TextNode> textNodes = emt.textNodes();
			
			for(TextNode tn : textNodes) {
				if(tn.childNodeSize() == 0) { 
					data = tn.text();
					if(data.trim().length() > 0) {
						if(!isLinkedNode(tn)) {
							lstRes.add(tn);
							System.out.println(getNodePath(null, tn) + ">>TextNode >" + tn.text() + "---" + elem.indexOf(emt));
						}
					}
				}
			}
		}
		
		return lstRes ;
	}
	
	public String getNodePath(String base, Node node) {
		if(base == null) base = "";
		
		Node pNode = node.parentNode();
		
		String tmp = "unknown";
		int elemIndex = -1;
		if(node instanceof Element) {
			Element elem = (Element)node;
			tmp = elem.tagName();
			
			if(elem.parent() != null) {
//				elemIndex = elem.parent().childNodes().indexOf(elem);
				elemIndex = elem.parent().children().indexOf(elem);
			}
			
		} else if(node instanceof DataNode) {
			DataNode dataNode = (DataNode) node;
			tmp = dataNode.toString();
		} else if(node instanceof TextNode) {
			TextNode textNode = (TextNode) node;
			tmp = textNode.getWholeText();
		}
		
//		if(elemIndex == -1) 
//			elemIndex = node.siblingIndex();
		
//		base = tmp + "[" + elemIndex + ":" + node.siblingNodes().size() + "]>" + base;
		base = tmp + "[" + elemIndex + "]>" + base;
		
		pNode = node.parentNode();
		if(pNode != null) {
			base = getNodePath(base, pNode);
		}
		
		return base;
	}
	
	public List<CrawlData> getCrawlDataInDoc() {
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
//		String url = "http://clien.net/cs2/bbs/board.php?bo_table=park&wr_id=43632518";
		PageUnitProcessor test = new PageUnitProcessor(null, url);
		try {
			test.load();
			List<CrawlData> lstData = test.getCrawlDataInDoc();
			
			for(CrawlData crawlData : lstData) {
				System.out.println("CrawlData >>> "+ crawlData);
			}
			
//			ArrayList<DocPathUnit> lstPath = new ArrayList<DocPathUnit>() ;
//			lstPath.add(new DocPathUnit("html", 1));
//			lstPath.add(new DocPathUnit("body", 3));
//			lstPath.add(new DocPathUnit("div", 2));
//			lstPath.add(new DocPathUnit("div", 1));
//			lstPath.add(new DocPathUnit("article", 1));
//			lstPath.add(new DocPathUnit("div", 19));
//			Element resElem = test.getElement(lstPath);
//			System.out.println("Result >" + resElem.text());
			
			WebDocWrapperUtil webDocUtil = new WebDocWrapperUtil();
			List<DocPathUnit> pathObject = webDocUtil.getPathObject("html:1/body:3/div:2/div:1/article:1/div:19");
					
			Element resElem = test.getElement(pathObject);
			System.out.println("Result >" + resElem.text());
			
			List<TextNode> utNode = test.getUnlinkedTextNodes();
			System.out.println("-------------------------------------------------");
			
			for(TextNode textNode : utNode) {
				System.out.println("TX:" + textNode.text());
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
