package com.yg.projectx.research;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class HtmlPasing {
	
	public static void crawlPage() {
		File input = new File("res/NewFile.html");
		try {
			Document doc = Jsoup.parse(input, "UTF-8", "");
			Elements elem = doc.getAllElements();
			System.out.println("Size :" + elem.size());
					
			
			Elements links = doc.select("a[href]");
			
			print("\nLinks: (%d)", links.size());
	        for (Element link : links) {
	            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
	        }
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void crawlPageFromURL() {
		try {
			Document doc = Jsoup.connect("http://gall.dcinside.com/board/lists/?id=stock_new1").get();
			Elements elem = doc.getAllElements();
			System.out.println("Size :" + elem.size());
					
			
			Elements links = doc.select("a[href]");
			
			print("\nLinks: (%d)", links.size());
	        for (Element link : links) {
	            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
	            
	            if(link.text() == null || link.text().trim().length() == 0) {
	            	Elements img = link.select("img[src]");
	            	print(" -- [IMG] --> (%s)", img.attr("abs:src"));
	            }
	            
	        }
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String ... v) {
		System.out.println("Start System .." + System.currentTimeMillis());
//		crawlPage();
//		unLinkedElement();
//		crawlPageFromURL();
				
		extractContexts();
	}
	
	public static void extractContexts() {
		try {
			Document doc = Jsoup.connect("http://clien.net/cs2/bbs/board.php?bo_table=park&wr_id=43201344").get();
//			Elements elem = doc.getAllElements();
//			
//			System.out.println("Element >" + elem);
			
			System.out.println("doc >" + doc.title());
			
			System.out.println("body >" + doc.body().text());
			
			Element body = doc.body();
			for(DataNode dn :body.dataNodes()) {
				System.out.println(">>>>" + dn);
			}
			
			Elements allElem = doc.body().getAllElements();
			
			System.out.println("Size >" + allElem.size());;
			
			ListIterator<Element> listIterator = allElem.listIterator();
			
			while(listIterator.hasNext()) 
			{
				System.out.println(">>" + listIterator.next().tagName());
			}
			
//			int i = 0;
//			for(TextNode tn : doc.body().textNodes()) {
//				
//				
//				System.out.println(i++ + "\t" + tn.text());
//			}
			
			System.out.println("Text >" + doc.body().text());;
						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void unLinkedElement() {
		File input = new File("res/NewFile.html");
		try {
			Document doc = Jsoup.parse(input, "UTF-8", "");
//			System.out.println(doc.ownText());
			System.out.println("-----------------------");
		
			
			
			List<TextNode> textNodes = doc.textNodes();
			
			for(TextNode tn : textNodes) {
//				System.out.println("Parent :" + tn.parent() + "--->" + tn.text());
				System.out.println("--->" + tn.text());
			}
			
			
			
			
//			Elements elem = doc.getAllElements();
//			System.out.println("Size :" + elem.size());
//					
//			
//			Elements links = doc.select("a[href]");
//			
//			print("\nLinks: (%d)", links.size());
//	        for (Element link : links) {
//	            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
//	        }
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
	
	private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
	
	private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }
	
	public static void researchWithFile() {
		File input = new File("res/NewFile.html");
		try {
			Document doc = Jsoup.parse(input, "UTF-8", "");
			Elements elem = doc.getAllElements();
			System.out.println("Size :" + elem.size());
					
			
			doc.select("a");
			
			Element element = elem.get(5);
			List<Node> childNodes = element.childNodes();
			for(Node node : childNodes) {
				System.out.println("Node Name >" + node.nodeName());
				
				if(node instanceof TextNode) {
					TextNode tn = (TextNode)node;
					
					System.out.println("---->" + tn.getWholeText().trim());
				} else {
					Node pn = node.parentNode();
					System.out.println(">>> par ---> " + pn.nodeName());
					if(node instanceof Element) {
						Element em = (Element)node;
						System.out.println(">>> parent is Emenent >>>" + em.nodeName());
					}
				}
			}
			
			System.out.println("Text>" + doc.body().text());
			
//			System.out.println("-->" + element.nodeName());
//			
//			
//			System.out.println(">>>" + element.toString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public static void researchWithURL() {
		try {
			Document doc = Jsoup.connect("http://clien.net/cs2/bbs/board.php?bo_table=park").get();
//			Elements elem = doc.getAllElements();
//			
//			System.out.println("Element >" + elem);
			
			System.out.println("doc >" + doc.title());
			
			Element link = doc.select("a").first();
			System.out.println("first >" + link);
			
			System.out.println("href :" + link.attr("href"));
			System.out.println("href :" + link.attr("abs:href"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
