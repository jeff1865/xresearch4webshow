package com.yg.webshow.crawl.webpage;

import java.util.LinkedHashMap;

public class UrlContext {
	private String protocol ;
	private String domain ;
	private String resource ;
	private LinkedHashMap<String, String> kvp ;
	
	public UrlContext() {
		this.kvp = new LinkedHashMap<String, String>();
	}
	
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public LinkedHashMap<String, String> getKvp() {
		return kvp;
	}
		
	//http://cafe.naver.com/ArticleRead.nhn?clubid=24513075&page=1&menuid=5&boardtype=L&articleid=21183&referrerAllArticles=false
}
