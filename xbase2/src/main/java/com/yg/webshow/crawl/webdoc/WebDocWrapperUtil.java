package com.yg.webshow.crawl.webdoc;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.mortbay.log.Log;

import com.yg.webshow.crawl.DUrlContext;
import com.yg.webshow.crawl.DocPathUnit;

public class WebDocWrapperUtil {
		
	public WebDocWrapperUtil() {
		;
	}
	
	//TODO
	public String getNodePathPatternExpression(String nodePath) {
		List<DocPathUnit> pathObj = this.getPathObject(nodePath);
		StringBuffer sb = new StringBuffer();
		
		for(DocPathUnit docPath : pathObj) {
			sb.append(docPath.getTagName()).append(":");
			if(docPath.getChildIndex() >= 0) sb.append("*");
//			else sb.append("-");
			else sb.append("*");
			sb.append("/");
		}
				
		return sb.toString();
	} 
	
	public String getNodePath(Node node) {
		return this.getNodePath(null, node, -9);
	}
	
	private String getNodePath(String base, Node node, int idx) {
		if(base == null) base = "";
		
		Node pNode = node.parentNode();
		
		String tmp = "unknown";
		int elemIndex = -1;
		if(node instanceof Element) {
			Element elem = (Element)node;
			tmp = elem.tagName();
			
			if(elem.parent() != null) {
//				elemIndex = elem.parent().childNodes().indexOf(elem);
				elemIndex = elem.parent().children().indexOf(elem);
			}
			
		} else if(node instanceof DataNode) {
			DataNode dataNode = (DataNode) node;
			tmp = dataNode.toString();
			
			
		} else if(node instanceof TextNode) {
			TextNode textNode = (TextNode) node;
			tmp = textNode.getWholeText();
			
			elemIndex = pNode.childNodes().indexOf(node);
		}
		
		if(base.length() != 0)
			base = node.nodeName() + ":" + idx + "/" + base;
		else
			base = node.nodeName();
		
		
//		//to be removed
//		if(node.nodeName().equals("#text")) {
//			System.out.println("======>" + pNode + "---->" + pNode.parentNode());
//		}
		
		if(node.nodeName().equalsIgnoreCase("html")) return base;
		
		pNode = node.parentNode();
		if(pNode != null) {
			base = getNodePath(base, pNode, elemIndex);
		}
		
		return base;
	}
		
	/**
	 * Sample of path : html:1/body:2/div:3/div:1/div:0/#text
	 * PathText must start with element 'html'
	 * @param path path expression to find matched elements
	 * @return
	 */
	public List<DocPathUnit> getPathObject(String path) {
		ArrayList<DocPathUnit> lstPath = new ArrayList<DocPathUnit>() ;
		
		StringTokenizer stkz = new StringTokenizer(path, "/");
		String token = null;
		while(stkz.hasMoreTokens()) {
			token = stkz.nextToken();
			String[] tk = token.split(":");
			
			if(tk.length == 2) {
				int i = DocPathUnit.ALL_INDEX;
				try {
					i = Integer.parseInt(tk[1]);
				} catch(Exception e) {}
				
				lstPath.add(new DocPathUnit(tk[0], i));
			} else {
				lstPath.add(new DocPathUnit(tk[0], DocPathUnit.INVALID_INDEX)) ;
			}
		}
		
		return lstPath;
	}
	
	public static void main(String ... v) {
		System.out.println("Activate an ejection mode !!");
				
		//Sample URL : http://news.chosun.com/site/data/html_dir/2015/12/27/2015122700214.html
		WebDocWrapperUtil webDocUtil = new WebDocWrapperUtil();
//		webDocUtil.getUrlPatternExpression("http://news.naver.com/main/read.nhn?oid=018&sid1=101&aid=0003435915&mid=shm&cid=428288&mode=LSD&nh=20151227114901");
//		System.out.println("------------------------");
//		webDocUtil.getUrlPatternExpression("http://news.chosun.com/site/data/html_dir/2015/12/27/2015122700214.html");
//		System.out.println("------------------------");
		
		
		
//		ArrayList<DocPathUnit> lstPath = new ArrayList<DocPathUnit>() ;
//		lstPath.add(new DocPathUnit("html", 1));
//		lstPath.add(new DocPathUnit("body", 3));
//		lstPath.add(new DocPathUnit("div", 2));
//		lstPath.add(new DocPathUnit("div", 1));
//		lstPath.add(new DocPathUnit("article", 1));
//		lstPath.add(new DocPathUnit("div", 19));
//		
//		Element resElem = test.getElement(lstPath);
//		
//		System.out.println("Result >" + resElem.text());
		
		List<DocPathUnit> pathObject = webDocUtil.getPathObject("html:1/body:3/div:2/div:1/article:1/div:19");
				
		System.out.println("RES>" + pathObject);
		
		String samplePath = "html:1/body:3/div:2/div:1/article:1/div:8/div:0/#text";
		String nodePathPatternExpression = webDocUtil.getNodePathPatternExpression(samplePath);
		System.out.println("-------->" + nodePathPatternExpression);
	}
	
}
