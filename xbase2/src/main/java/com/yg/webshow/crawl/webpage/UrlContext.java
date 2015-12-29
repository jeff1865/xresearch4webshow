package com.yg.webshow.crawl.webpage;

import java.util.LinkedHashMap;
import java.util.Map;

public class UrlContext {
	private String protocol ;
	private String domain ;
	private String resource ;
	private Map<String, String> kvp ;
	
	public UrlContext() {
		this.setKvp(new LinkedHashMap<String, String>());
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
	public Map<String, String> getKvp() {
		return kvp;
	}
	public void setKvp(Map<String, String> kvp) {
		this.kvp = kvp;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.protocol).append("|");
		sb.append(this.domain).append("|");
		sb.append(this.resource).append("|");
		sb.append(this.kvp);
		return sb.toString();
	}
}
