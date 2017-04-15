package com.yg.webshow.crawl.sample;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ClienStone {
	public static void main(String ... v) {
		System.out.println("Start ..");
		String url = "http://clien.net/cs2/bbs/board.php?bo_table=park&wr_id=54644455";
		try {
			Document doc = Jsoup.connect(url).get();
//			Elements elem = doc.select("div[id=zzbang_div]");
			Elements elem = doc.select("div#resContents>div>img");
			System.out.println("Umg->" + elem);
			elem = doc.select("div#resContents span#writeContents");
			
			System.out.println("Result->" + elem.text());;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
