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
	
	private boolean isLinkedNode(Node node) {
		
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
		Elements elem = doc.getAllElements();
		
		ListIterator<Element> ItlElem = elem.listIterator();
		
		while(ItlElem.hasNext()) {
			Element emt = ItlElem.next();
			
			String data = null;
			List<TextNode> textNodes = emt.textNodes();
			
			for(TextNode tn : textNodes) {
				if(tn.childNodeSize() == 0) { 
					data = tn.text();
					if(data.trim().length() > 0) {
						if(!isLinkedNode(tn)) {
							lstRes.add(tn);
							System.out.println(wrapperUtil.getNodePath(null, tn, -9) + ">>TextNode >" + tn.text() + "---" + elem.indexOf(emt));
						}
					}
				}
			}
		}
		
		return lstRes ;
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
		
		Element tmpElem = elem.get(0);
		for(;i<lstUnit.size();i++) {
			DocPathUnit path = lstUnit.get(i);			
			tmpElem = tmpElem.child(path.getChildIndex());
			
		}
		
		return tmpElem;
	}
	
	public Node getEndNode(String strPath) {
		try {
			Elements elem = this.doc.select("html");
				
			List<DocPathUnit> lstPathUnit = this.wrapperUtil.getPathObject(strPath);
			
			int i = 0;
			for(DocPathUnit path : lstPathUnit) {
				if(path.getTagName() != null && path.getTagName().equals("html")) {
					break ;
				}
				i ++;
			}
			
			Element tmpElem = elem.get(0);
			for(;i<lstPathUnit.size();i++) {
				DocPathUnit path = lstPathUnit.get(i);
				if(path.getChildIndex() >= 0) {
					if(lstPathUnit.size() - 2 == i && lstPathUnit.get(lstPathUnit.size() - 1).getChildIndex() < 0) {
						return tmpElem.childNode(path.getChildIndex());
					} else {
						tmpElem = tmpElem.child(path.getChildIndex());
					}
				} else {
					return null;
				}
			}
		} catch(Exception e) {
//			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String ...v) {
		PageUnitProcessor test = new PageUnitProcessor(null, "http://news.chosun.com/site/data/html_dir/2015/12/27/2015122700455.html");
		try {
			test.load();
			
			Node endNode = test.getEndNode("html:1/body:3/div:2/div:1/article:1/div:190/div:0/#text");
			System.out.println("endNode >" + endNode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main0(String ... v) {
		String url = "http://news.chosun.com/site/data/html_dir/2015/12/27/2015122700455.html";
//		url = "http://www.ppomppu.co.kr/zboard/view.php?id=climb&page=1&divpage=12&search_type=sub_memo&keyword=%B9%E9%B5%CE&no=58896";
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
			System.out.println("---------- Result >" + resElem.text());
			
//			TX:#root:0/html:1/body:0/div:6/div:0/div:12/table:0/tbody:0/tr:0/td:0/table:0/tbody:0/tr:0/td:0/table:0/tbody:0/tr:0/td:-1/
//			천미터급은 하신이 정말 힘들더군요. 지리산은 더하겠지만..:-8/--> 천미터급은 하신이 정말 힘들더군요. 지리산은 더하겠지만..
			
//	TX:#root:-1/html:0/body:1/div:0/div:6/div:0/table:12/tbody:0/tr:0/td:0/table:0/tbody:0/tr:0/td:0/table:0/tbody:0/tr:0/td:0/
//			천미터급은 하신이 정말 힘들더군요. 지리산은 더하겠지만..:-1/--> 천미터급은 하신이 정말 힘들더군요. 지리산은 더하겠지만..
			
			//TX:#root:-1/html:0/body:1/div:3/div:2/article:1/div:1/div:19/제작진은 케
//TX:unknown:-1  /#root:1/html:3/body:2/div:1/div:1/article:19/div:-1/div:-9/-->제작진은 
			
			List<TextNode> utNode = test.getUnlinkedTextNodes();
			System.out.println("-------------------------------------------------");
			
			for(TextNode textNode : utNode) {
				System.out.println("TXa:" + webDocUtil.getNodePath(null, textNode, -9) + "-->" + textNode.text());
//				System.out.println("TXb:" + test.getNodePath(null, textNode) + "-->" + textNode.text());
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
