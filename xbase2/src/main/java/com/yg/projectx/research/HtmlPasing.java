package com.yg.projectx.research;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
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
	
	private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
	
	private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

	
	public static void main(String ... v) {
		System.out.println("Start System .." + System.currentTimeMillis());
		crawlPage();
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
