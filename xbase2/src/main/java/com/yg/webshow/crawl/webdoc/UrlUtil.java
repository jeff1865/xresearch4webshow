package com.yg.webshow.crawl.webdoc;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.mortbay.log.Log;

import com.yg.webshow.crawl.DUrlContext;

public class UrlUtil {
	
	//TODO need to apply that key should be sorted by apla order
	public String getUrlPatternExpression(String url) {
		StringBuffer sb = new StringBuffer();
		
		StringTokenizer stkz = new StringTokenizer(url, "/ & = ? .");
		
		int i = 0;
		String token = null;
		while(stkz.hasMoreTokens()) {
			token = stkz.nextToken();
			System.out.println(i++ + ". Token -> " + token + "==>" + this.getPatternExpression(token));
			sb.append(this.getPatternExpression(token) + ":");
		}
		
		return sb.toString(); 
	}
		
	public DUrlContext getUrlContext(String url) {
		DUrlContext urlContext = new DUrlContext() ;
		
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
		UrlUtil urlUtil = new UrlUtil();
		DUrlContext urlContext = urlUtil.getUrlContext("http://news.naver.com/main/read.nhn?oid=018&sid1=&aid=0003435915&mid=shm&cid=428288&mode=LSD&nh=20151227114901");
		System.out.println("--->>>" + urlContext);
		String urlPattern = urlUtil.getUrlPatternExpression("http://news.naver.com/main/read.nhn?oid=018&sid1=&aid=0003435915&mid=shm&cid=428288&mode=LSD&nh=20151227114901");
		System.out.println("===>>>" + urlPattern);
	}
}
