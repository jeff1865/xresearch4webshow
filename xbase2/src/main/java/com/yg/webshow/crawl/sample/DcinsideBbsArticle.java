package com.yg.webshow.crawl.sample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.mortbay.log.Log;

import com.yg.webshow.crawl.webdoc.WebDocAnalyzer;
import com.yg.webshow.crawl.webdoc.WebDocWrapper;
import com.yg.webshow.crawl.webdoc.WebDocWrapperUtil;
import com.yg.webshow.crawl.webdoc.template.DbbsTitleLine;
import com.yg.webshow.crawl.webdoc.template.WebDocBbs;

public class DcinsideBbsArticle {
	
	public static void showAllNode() {
		WebDocWrapperUtil webDocUtil = new WebDocWrapperUtil();
//		WebDocAnalyzer test1 = null;
		try {
			String url = "http://gall.dcinside.com/board/view/?id=stock_new2&no=771633&page=1";
//			test1 = new WebDocAnalyzer(url);
//			List<TextNode> utNode = test1.getAllTextNode();
//			System.out.println("---------------------<UnLinked>----------------------------");
//			
//			for(TextNode textNode : utNode) {
//				
//				System.out.println("NODE --> " + textNode.text() + " ==> " + textNode.getTokenIndex() + 
//						"\t\t\t\t\t\t" + webDocUtil.getNodePath(textNode));
//			}
			
			WebDocWrapper<WebDocBbs> wrapper = new WebDocWrapper<WebDocBbs>(url);
			
			List<TextNode> allTextNode = wrapper.getAllTextNode();
			for(TextNode tn : allTextNode) {
				System.out.println("NODE:" + tn.getTokenIndex() + "---" + tn.text() + "\t\t\t" + webDocUtil.getNodePath(tn));
			}
			
			List<Element> imgs = wrapper.getImgs();
			for(Element ei : imgs) {
				System.out.println("IMG:" + ei.getTokenIndex() + "---" + ei.attr("abs:src"));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void bbsList() {
		WebDocWrapperUtil webDocUtil = new WebDocWrapperUtil();
		String url = "http://gall.dcinside.com/board/lists/?id=stock_new2";
		try {
			WebDocWrapper<WebDocBbs> wrapper = new WebDocWrapper<WebDocBbs>(url);
			
			List<TextNode> allTextNode = wrapper.getAllTextNode();
			for(TextNode tn : allTextNode) {
				System.out.println("NODE:" + tn.getTokenIndex() + "---" + tn.text() + "\t\t\t" + webDocUtil.getNodePath(tn));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static WebDocBbs getContents(String url) {
		WebDocBbs webDocBbs = new WebDocBbs();
		try {
			WebDocWrapper<WebDocBbs> wrapper = new WebDocWrapper<WebDocBbs>(url);
			//TITLE
			String pathTitle = "html:1/body:3/div:2/div:1/div:0/div:0/div:0/div:0/dl:1/dd:0/#text";
			Node titleNode = wrapper.getEndNode(pathTitle);
			System.out.println("TITLE> :" + titleNode.toString().trim());
			webDocBbs.setContTitle(titleNode.toString().trim());
			
			//BODY
			String pathBody = "html:1/body:3/div:2/div:1/div:0/div:21/div:0/div:0/div:2/table:0/tbody:0/tr:0/td:5/div:0/#text";
			pathBody = "html:1/body:3/div:2/div:1/div:0/div:21/div:0/div:0/div:2/table:0/tbody:0/tr:0/td:5";
			StringBuffer sb = new StringBuffer();
			List<Node> bodyNode = wrapper.getBlurWrappedNode(pathBody);
			for(Node bNode : bodyNode) {
				sb.append(bNode.toString().trim()).append("\n");
//				System.out.println("BODY> :" + bNode.toString().trim());
			}
			webDocBbs.setContentsText(sb.toString());
			
			
			String pathEndStone = "html:1/body:3/div:2/div:1/div:0/div:23/div:1/div:3/div:0/div:0/h2:0/#text";
			Node endNode = wrapper.getEndNode(pathEndStone);
			System.out.println("EndFixed>" + endNode + " :" + endNode.getTokenIndex());
			String pathStartStone = "html:1/body:3/div:2/div:1/div:0/div:0/div:0/div:1/dl:4/dt:0/#text" ;
			Node startNode = wrapper.getEndNode(pathStartStone);
			int startIndex =  startNode.getTokenIndex();
			int endIndex = endNode.getTokenIndex();
			
			//IMG
			List<Element> imgs = wrapper.getImgs();
			for(Element img : imgs) {
				int idx = img.getTokenIndex();
				if(idx > startIndex && endIndex > idx) {
//					System.out.println("IMG>" + img.getTokenIndex() + ">:" + img.attr("abs:src"));
					webDocBbs.getImgUrl().add(img.attr("abs:src"));
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return webDocBbs;
	}
	
	public static void extContents(String url) {
//		String url = "http://gall.dcinside.com/board/view/?id=stock_new2&no=771633&page=1";
//		url = "http://gall.dcinside.com/board/view/?id=stock_new2&no=773895&page=1";
		
		try {
			WebDocWrapper<WebDocBbs> wrapper = new WebDocWrapper<WebDocBbs>(url);
			String pathTitle = "html:1/body:3/div:2/div:1/div:0/div:0/div:0/div:0/dl:1/dd:0/#text";
			
			Node titleNode = wrapper.getEndNode(pathTitle);
			
			System.out.println("TITLE> :" + titleNode.toString().trim());
			
			String pathBody = "html:1/body:3/div:2/div:1/div:0/div:21/div:0/div:0/div:2/table:0/tbody:0/tr:0/td:5/div:0/#text";
			pathBody = "html:1/body:3/div:2/div:1/div:0/div:21/div:0/div:0/div:2/table:0/tbody:0/tr:0/td:5";
			List<Node> bodyNode = wrapper.getBlurWrappedNode(pathBody);
			for(Node bNode : bodyNode) {
				System.out.println("BODY> :" + bNode.toString().trim());
			}
			
			String pathEndStone = "html:1/body:3/div:2/div:1/div:0/div:23/div:1/div:3/div:0/div:0/h2:0/#text";
			Node endNode = wrapper.getEndNode(pathEndStone);
			System.out.println("EndFixed>" + endNode + " :" + endNode.getTokenIndex());
			String pathStartStone = "html:1/body:3/div:2/div:1/div:0/div:0/div:0/div:1/dl:4/dt:0/#text" ;
			Node startNode = wrapper.getEndNode(pathStartStone);
			int startIndex =  startNode.getTokenIndex();
			int endIndex = endNode.getTokenIndex();
			
			
			List<Element> imgs = wrapper.getImgs();
			
			for(Element img : imgs) {
				int idx = img.getTokenIndex();
				if(idx > startIndex && endIndex > idx) {
					System.out.println("IMG>" + img.getTokenIndex() + ">:" + img.attr("abs:src"));
				}
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public static void extBbsListLines() {
		//html:1/body:3/div:2/div:0/div:0/div:3/div:0/div:3/table:3/tbody
		String url = "http://gall.dcinside.com/board/lists/?id=stock_new2";
		try {
			WebDocWrapper<WebDocBbs> wrapper = new WebDocWrapper<WebDocBbs>(url);
			String path = "html:1/body:3/div:2/div:0/div:0/div:3/div:0/div:3/table:3/tbody:*";

			Element elem  = wrapper.getEndElement(path);
			System.out.println("EndElement -->>" + elem);
			
			int i = 0;
			for(Element chd : elem.getAllElements()) {
				if(chd.tagName().equalsIgnoreCase("tr")) {
					String data = chd.getAllElements().get(2).text();
					System.out.println(i++ + "\t" + data);
				}
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static List<DbbsTitleLine> getBbsTitles() {
		ArrayList<DbbsTitleLine> lstRes = new ArrayList<DbbsTitleLine>();
		String url = "http://gall.dcinside.com/board/lists/?id=stock_new2";
		try {
			WebDocWrapper<WebDocBbs> wrapper = new WebDocWrapper<WebDocBbs>(url);
			String path = "html:1/body:3/div:2/div:0/div:0/div:3/div:0/div:3/table:3/tbody:*";

			Element elem  = wrapper.getEndElement(path);
			
			System.out.println("EndElement -->>" + elem.getAllElements().size());
			
		
			
			DbbsTitleLine dtl = null ;
			int i = 0;
			for(Element chd : elem.children()) {
				if(chd.tagName().equalsIgnoreCase("tr")) {
					try{
					dtl = new DbbsTitleLine() ;
					Elements sub = chd.children();
					
//					System.out.println("links :" + links.size());
					dtl.setNo(Integer.parseInt(sub.get(0).text()));
					dtl.setTitle(sub.get(1).text());
					Elements links = sub.get(1).select("a[href]");
//					System.out.println("lni");
					dtl.setUrl(links.get(0).attr("abs:href"));
					dtl.setUserName(sub.get(2).text());
					dtl.setDate(sub.get(3).text());
					dtl.setHit(Integer.parseInt(sub.get(4).text()));
										
					lstRes.add(dtl);
					
					}catch(Exception e) {
						Log.info("cannot parse condition .." + e.getMessage());
						continue ;
					}
				}
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return lstRes ;
	}
	
	//tr:3/td#text
	
	public static void main(String ... v) {
//		showAllNode();
//		bbsList();
		System.out.println("---------------------------");
//		extBbsListLines();
		List<DbbsTitleLine> bbsTitles = getBbsTitles();
		
		int i = 0;
		for(DbbsTitleLine line : bbsTitles) {
			System.out.println(i++ + " -> " + line);
			if(i<5) {
//				extContents(line.getUrl());
				System.out.println("FILTERED_CONTENT>" + getContents(line.getUrl()));
			}
		}
		
		System.out.println("===========================");
		
//		extContents();
		
	}
}
