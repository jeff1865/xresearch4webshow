package com.yg.webshow.crawl.seeds;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.yg.webshow.crawl.webdoc.template.DbbsTitleLine;
import com.yg.webshow.crawl.webdoc.template.WebDocBbs;
import com.yg.webshow.crawl.webdoc.template.WebDocBbsList;

public class DcinsideStock implements IBbsList, IBbsContents {
	
	private String seedUrl = null ;
	
	public DcinsideStock(String seedUrl){
		this.seedUrl = seedUrl ;
	}
	
	@Override
	public WebDocBbsList getList() {
		WebDocBbsList webDocBbsList = new WebDocBbsList();
		List<DbbsTitleLine> titleLines = new ArrayList<DbbsTitleLine>();
		
		try {
			Document doc = Jsoup.connect(this.seedUrl).get();
			
			Elements list = doc.select("tbody[class=list_tbody] > tr:gt(4)");
//			System.out.println(list.toString());;
//			System.out.println("----" + list.text());
			
			Iterator<Element> itrElem = list.iterator();
			
			int i = 0;
			DbbsTitleLine line = null;
			while(itrElem.hasNext()) {
				Element tag = itrElem.next();
				line = new DbbsTitleLine();
								
				Elements title = tag.select("td.t_subject>a[href]:eq(0)");
//				System.out.println("tilte->" + title.text() + "\t URL->" + title.attr("abs:href"));
				
				line.setTitle(title.text());
				line.setUrl(title.attr("abs:href"));
				line.setNo(tag.select("td.t_notice").text());
				line.setAuthor(tag.select("td.t_writer").text());
				line.setDate(tag.select("td.t_date:eq(0)").attr("title"));
				
				titleLines.add(line) ;
			}
			
			webDocBbsList.setTitleLines(titleLines);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return webDocBbsList;
	}
	
	@Override
	public WebDocBbs getContent(String url) {
		WebDocBbs wdb = new WebDocBbs();
		
		try {
			Document doc = Jsoup.connect(url).get();
			wdb.setDocTitle(doc.select("html>head>title").text());
			
			Elements elem = doc.select("div[id=zzbang_div]");
			elem = doc.select("div#zzbang_div ~ table");
			Elements imgs = elem.select("img[src]");
//			System.out.println("Result->" + elem.toString());
			
			wdb.setContentsText(elem.text());
			
//			System.out.println("OnlyText->" + elem.text());
//			System.out.println("IMG->" + imgs);
			ArrayList<String> imgUrls = new ArrayList<String>();
			for(Element img : imgs) {
				String imgUrl = img.attr("abs:src");
				System.out.println("imgURL ->" + imgUrl);
				imgUrls.add(imgUrl);
			}
			wdb.setImgUrl(imgUrls);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return wdb;
	}
	
	public static void main(String ... v) {
		String url = "http://gall.dcinside.com/board/view/?id=stock_new2&no=1372050&page=1";
		
		DcinsideStock test = new DcinsideStock(url) ;
		WebDocBbsList list = test.getList();
		
		int i = 0;
		for(DbbsTitleLine btl :list.getTitleLines()){
			System.out.println(i++ + "\t" + btl);
			if(i < 3) {
				System.out.println("CONT --> " + test.getContent(btl.getUrl())) ;
			}
		}
	}

}
