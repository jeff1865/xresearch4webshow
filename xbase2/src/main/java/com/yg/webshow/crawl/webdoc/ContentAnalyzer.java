package com.yg.webshow.crawl.webdoc;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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
public class ContentAnalyzer {
	
	private Document doc ;
	private WebDocWrapperUtil wrapperUtil = new WebDocWrapperUtil();
		
	public ContentAnalyzer(Document doc) {
		this.doc = doc;
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
		;
	}
}
