package com.ygsoft.webshow.dom;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.mortbay.log.Log;

import com.yg.webshow.crawl.seeds.NewClienPark;
import com.yg.webshow.crawl.util.FileDownloader;
import com.yg.webshow.crawl.webdoc.template.WebDocBbs;

public class HtmlPageProcessor {
	private final String imageDir = "/Users/1002000/dev/temp20/"; 
	
	public HtmlPageProcessor() {
		;
	}
	
	public String convertPage(String siteId, int postId, String src, List<String> imgUrls) {
		Document doc = Jsoup.parse(src) ;
		
		Elements imgs = doc.select("img[src]") ;
		String attachedImgHtml = "";
		int i = 0;
		if(imgUrls.size() > imgs.size()) {
//			attachedImgHtml = "<img src=\""+ postId + "_0." +   +"\"></img>";
			i++;
		}
//		
//		Iterator<Element> itr = imgs.iterator();
//		
//		
//		while(itr.hasNext()) {
//			Element elem = itr.next();
//			
//			
//		}
//		Elements elem = doc.select("html > body *");
//		elem.append("<img src=\""+ "IMG" + System.currentTimeMillis() +"\"></img>") ;
		String converted = doc.select("html > body *").toString() + "XXX";
		
		return converted ;
	}
	
	// Return downloaded Local File Path
	public String dnSaveImgFile(String murl, String postNo, int index) {
		try {
			String imgType = ".jpg";
			if(murl.contains(".png") || murl.contains(".PNG")) imgType = ".png";
			else if(murl.contains(".gif") || murl.contains("GIF")) imgType = ".gif";
			
			String filename = postNo + "_" + index + imgType;
			FileDownloader.downloadFile(murl, this.imageDir + filename);
			Log.info("Download completed .. " + filename);
			return filename ;
		} catch (Exception e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public static void main(String ... v) {
		System.out.println(" .. ");
		String url = "http://clien.net/cs2/bbs/board.php?bo_table=park";
		url = "https://www.clien.net/service/board/park";	// New Clien
		NewClienPark clien = new NewClienPark(url);
				
		WebDocBbs content = clien.getContent("https://www.clien.net/service/board/park/10719290");
		
		System.out.println("contents >" + content);
		
		HtmlPageProcessor test = new HtmlPageProcessor();
		System.out.println(">>>" + content.getContentsHtml());
		System.out.println(">>>" + content.getImgUrl());
		
		String convertedPage = test.convertPage("aaa", 0, content.getContentsHtml(), content.getImgUrl()) ;
		
		;
		
		System.out.println("ConvertedPage >>> \n" + convertedPage);
	}
}
