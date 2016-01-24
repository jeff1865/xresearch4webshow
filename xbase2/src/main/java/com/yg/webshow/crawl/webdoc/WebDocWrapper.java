package com.yg.webshow.crawl.webdoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

/**
 * ContentAnalyzer --- (SiteURL Pattern Expression) ---> Hbase
 * @author jeff.yg.kim@gmail.com
 *
 */
public class WebDocWrapper {
	
	private String url = null;
	private Document doc = null;
	private WebDocWrapperUtil wrapperUtil = new WebDocWrapperUtil();
	
	
	public WebDocWrapper(String url) throws IOException {
		this.url = url;
		this.doc = Jsoup.connect(this.url).get();
	}
		
	public String getContentFilterRule() {
		// based on elementPath
		return null;
	}
	
	public DWebDocMeta getDocMeta() {
		
		return null;
	}
	
	public List<TextNode> getCleanedTextNode(List<TextNode> nodes) {
		;	
		
		return null;
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
	
	public static void main(String ... v) {
		WebDocWrapperUtil webDocUtil = new WebDocWrapperUtil();
		WebDocWrapper test1 = null;
		try {
			test1 = new WebDocWrapper("http://news.chosun.com/site/data/html_dir/2015/12/27/2015122700455.html");
			List<TextNode> utNode = test1.getUnlinkedTextNodes();
			System.out.println("-------------------------------------------------");
			
			for(TextNode textNode : utNode) {
				System.out.println("TX--->" + webDocUtil.getNodePathPatternExpression(webDocUtil.getNodePath(null, textNode, -9)) + "-->" + textNode.text());
//				System.out.println("TXb:" + test.getNodePath(null, textNode) + "-->" + textNode.text());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
