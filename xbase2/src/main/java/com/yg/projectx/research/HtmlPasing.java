package com.yg.projectx.research;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlPasing {
	public static void main(String ... v) {
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
