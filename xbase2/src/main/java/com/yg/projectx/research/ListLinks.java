package com.yg.projectx.research;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Example program to list links from a URL.
 */
public class ListLinks {
	
	public static void getIMG() throws IOException {
		String url = "http://clien.net/cs2/bbs/board.php?bo_table=park";
		url = "http://clien.net/cs2/bbs/board.php?bo_table=image";
		print("Fetching %s...", url);

		Document doc = Jsoup.connect(url).get();
		Elements imgs = doc.select("img[src$=.jpg]");
		
		print("\nIMG: (%d)", imgs.size());
		int i = 0;
		for (Element img : imgs) {
			print(i++ + " * <%s> (%s)", img.attr("abs:src"), img.html());
		}
	}
	
//	public static void 
	
	public static void main(String ... v) throws IOException {
		System.out.println("Start Parsing ..");
		getIMG();
	}
	
	public static void main0(String[] args) throws IOException {
		System.out.println("Start Parse ...");
//		Validate.isTrue(args.length == 1, "usage: supply url to fetch");
		String url = "http://clien.net/cs2/bbs/board.php?bo_table=park";
		url = "http://news.naver.com/";
		print("Fetching %s...", url);

		Document doc = Jsoup.connect(url).get();
		Elements links = doc.select("a[href]");
		Elements media = doc.select("[src]");
		Elements imports = doc.select("link[href]");

		print("\nMedia: (%d)", media.size());
		for (Element src : media) {
			if (src.tagName().equals("img"))
				print(" * %s: <%s> %sx%s (%s)", src.tagName(), src.attr("abs:src"), src.attr("width"),
						src.attr("height"), trim(src.attr("alt"), 20));
			else
				print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
		}

		print("\nImports: (%d)", imports.size());
		for (Element link : imports) {
			print(" * %s <%s> (%s)", link.tagName(), link.attr("abs:href"), link.attr("rel"));
		}

		print("\nLinks: (%d)", links.size());
		for (Element link : links) {
			print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
		}
	}

	private static void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

	private static String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width - 1) + ".";
		else
			return s;
	}
}