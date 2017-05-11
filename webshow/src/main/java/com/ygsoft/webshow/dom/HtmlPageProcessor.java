package com.ygsoft.webshow.dom;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mortbay.log.Log;

import com.yg.webshow.crawl.seeds.NewClienPark;
import com.yg.webshow.crawl.util.FileDownloader;
import com.yg.webshow.crawl.webdoc.template.WebDocBbs;

public class HtmlPageProcessor {
	
	private final String imageDir = "/Users/1002000/dev/temp20/"; 
	private final int defaultImageWidth = 400;
	
	public HtmlPageProcessor() {
		;
	}
	
	public String convertPage(String siteId, String postNo, String src, List<String> imgUrls) {
		Document doc = Jsoup.parse(src) ;
		
		Elements imgs = doc.select("img[src]") ;
		// Attached IMG
		String attachedImgHtml = "";
		String dnFileName = null ;
		int i = 0;
		if(imgUrls.size() > imgs.size()) {
			dnFileName = this.dnSaveImgFile(imgUrls.get(i), siteId, postNo, i) ;
			attachedImgHtml = "<img width=\"" + this.defaultImageWidth 
					+ "\" src=\"http://localhost:8080/files/" + siteId + "/"+ dnFileName + "/dn\"></img>";
			i++;
		}
		
		Iterator<Element> itr = imgs.iterator();
		while(itr.hasNext()) {
			Element elem = itr.next();
			dnFileName = this.dnSaveImgFile(imgUrls.get(i), siteId, postNo, i) ;
			elem.attr("src", "http://localhost:8080/files/" + siteId + "/" + dnFileName + "/dn");
			elem.attr("width", String.valueOf(this.defaultImageWidth));
			i++;
		}
		
		String converted = doc.select("html > body > *").toString();
		converted = attachedImgHtml + "\n" + converted ;
		
		return converted ;
	}
	
	// Return downloaded Local File Path
	private String dnSaveImgFile(String murl, String siteId, String postNo, int index) {
		try {
			String imgType = ".jpg";
			if(murl.contains(".png") || murl.contains(".PNG")) imgType = ".png";
			else if(murl.contains(".gif") || murl.contains("GIF")) imgType = ".gif";
						
			String filename = postNo + "_" + index + imgType;
			String localAbsPath = this.imageDir + siteId + "/" + filename;
			
			if(new File(localAbsPath).exists()) {
				Log.info("File Aready Exists :" + localAbsPath);
				return filename ;
			}
			
			FileDownloader.downloadFile(murl, localAbsPath);
			Log.info("Download completed .. " + filename);
			return filename ;
		} catch (Exception e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public static void main(String ... v) {
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
		
		Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.ERROR);
		Logger.getLogger("httpclient").setLevel(Level.ERROR);
		
		System.out.println(" .. ");
		String url = "http://clien.net/cs2/bbs/board.php?bo_table=park";
		url = "https://www.clien.net/service/board/park";	// New Clien
		NewClienPark clien = new NewClienPark(url);
				
		WebDocBbs content = clien.getContent("https://www.clien.net/service/board/park/10719290");
		
		System.out.println("contents >" + content);
		
		HtmlPageProcessor test = new HtmlPageProcessor();
//		System.out.println(">>>" + content.getContentsHtml());
//		System.out.println(">>>" + content.getImgUrl());
		
		String convertedPage = test.convertPage("clien.park", "2480", content.getContentsHtml(), content.getImgUrl()) ;
				
		System.out.println("ConvertedPage >>> \n" + convertedPage);
	}
}
