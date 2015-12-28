package com.yg.webshow.crawl.webpage;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.jsoup.nodes.Node;

public class WebDocUtil {
		
	public WebDocUtil() {
		;
	}
	
	public String getUrlPatternExpression(String url) {
		StringTokenizer stkz = new StringTokenizer(url, "/ & = ? .");
		
		int i = 0;
		String token = null;
		while(stkz.hasMoreTokens()) {
			token = stkz.nextToken();
			System.out.println(i++ + ". Token -> " + token + "==>" + this.getPatternExpression(token));
		}
		
		return null;
	}
	
	public String getNodePath(Node noed) {
		
		return null;
	}
	
	public UrlContext getUrlContext(String url) {
		UrlContext urlContext = new UrlContext() ;
		
		return urlContext ;
	}
	
	public String getPatternExpression(String token) {
		String digitReg = "[0-9]+";
		if(token.matches(digitReg)) {
			return "%d[" + token.length() + "]";
		} else if(token.matches("\\w")) {
			return "CX";
		} else if(token.matches("\\W")) {
			return "UX";
		}
		
		return token;
	}
	
	public Map<String, String> parseQuery(String query) {
		LinkedHashMap<String, String> resMap = new LinkedHashMap<String, String>();
				
		StringTokenizer stkz = new StringTokenizer(query, "&");
		String kvp = null;
		while(stkz.hasMoreTokens()) {
			kvp = stkz.nextToken();
			String[] tokens = kvp.split("=");
			
			if(tokens.length == 2) {
				//TODO
			} else {
				//TODO
			}
		}
			
		return resMap;
	}
	
	
	public static void main(String ... v) {
		System.out.println("Activate an ejection mode !!");
				
		//Sample URL : http://news.chosun.com/site/data/html_dir/2015/12/27/2015122700214.html
		WebDocUtil webDocUtil = new WebDocUtil();
		webDocUtil.getUrlPatternExpression("http://news.naver.com/main/read.nhn?oid=018&sid1=101&aid=0003435915&mid=shm&cid=428288&mode=LSD&nh=20151227114901");
		System.out.println("------------------------");
		webDocUtil.getUrlPatternExpression("http://news.chosun.com/site/data/html_dir/2015/12/27/2015122700214.html");
		System.out.println("------------------------");
		
		try {
			URL uri = new URL("http://news.naver.com/main/read.nhn?oid=018&sid1=101&aid=0003435915&mid=shm&cid=428288&mode=LSD&nh=20151227114901");
//			uri = new URI("http://news.chosun.com/site/data/html_dir/2015/12/27/2015122700214.html");
			System.out.println(uri.getQuery());;
			System.out.println(uri.getPath());
			System.out.println(uri.getHost());
			System.out.println(uri.getProtocol());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
