package com.yg.webshow.crawl;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.jsoup.nodes.Node;
import org.mortbay.log.Log;

public class WebDocWrapperUtil {
		
	public WebDocWrapperUtil() {
		;
	}
	
	//TODO
	public String getNodePathPatternExpression(Node node) {
		return null;
	} 
	
	//TODO
	public String getNodePath(Node node) {
		return null;
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
		
	public UrlContext getUrlContext(String url) {
		UrlContext urlContext = new UrlContext() ;
		
		try {
			URL uri = new URL(url);
			urlContext.setProtocol(uri.getProtocol());
			urlContext.setDomain(uri.getHost());
			urlContext.setResource(uri.getPath());
			urlContext.setKvp(this.parseQuery(uri.getQuery()));
					
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
			
		return urlContext ;
	}
	
	private String getPatternExpression(String token) {
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
		
	private Map<String, String> parseQuery(String query) {
		LinkedHashMap<String, String> resMap = new LinkedHashMap<String, String>();
				
		StringTokenizer stkz = new StringTokenizer(query, "&");
		String kvp = null;
		while(stkz.hasMoreTokens()) {
			kvp = stkz.nextToken();
			String[] tokens = kvp.split("=");
			
			if(tokens.length == 2) {
				resMap.put(tokens[0], tokens[1]);
			} else if(tokens.length == 1) {
				resMap.put(tokens[0], null);
			} else {
				Log.info("Invalid query string :" + kvp);
			}
		}
			
		return resMap;
	}
	
	
	public static void main(String ... v) {
		System.out.println("Activate an ejection mode !!");
				
		//Sample URL : http://news.chosun.com/site/data/html_dir/2015/12/27/2015122700214.html
		WebDocWrapperUtil webDocUtil = new WebDocWrapperUtil();
//		webDocUtil.getUrlPatternExpression("http://news.naver.com/main/read.nhn?oid=018&sid1=101&aid=0003435915&mid=shm&cid=428288&mode=LSD&nh=20151227114901");
//		System.out.println("------------------------");
//		webDocUtil.getUrlPatternExpression("http://news.chosun.com/site/data/html_dir/2015/12/27/2015122700214.html");
//		System.out.println("------------------------");
		
		UrlContext urlContext = webDocUtil.getUrlContext("http://news.naver.com/main/read.nhn?oid=018&sid1=&aid=0003435915&mid=shm&cid=428288&mode=LSD&nh=20151227114901");
		System.out.println("--->>>" + urlContext);

	}
	
}
