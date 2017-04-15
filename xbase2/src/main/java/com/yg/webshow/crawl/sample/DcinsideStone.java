package com.yg.webshow.crawl.sample;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class DcinsideStone {
	
	public static void main(String ... v) {
		System.out.println("Start ..");
		String url = "http://gall.dcinside.com/board/view/?id=stock_new2&no=1372050&page=1";
		try {
			Document doc = Jsoup.connect(url).get();
			Elements elem = doc.select("div[id=zzbang_div]");
			elem = doc.select("div#zzbang_div ~ table");
			Elements imgs = elem.select("img[src]");
			System.out.println("Result->" + elem.toString());
			System.out.println("OnlyText->" + elem.text());
			System.out.println("IMG->" + imgs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
