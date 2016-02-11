package com.yg.webshow.data;

import java.util.List;

public class PageTemplateRow {
	private String patternedUrl ;
	private List<String> contextFilterRule ;
	private long count ;
	private String pageType ;
	
	public String getPatternedUrl() {
		return patternedUrl;
	}
	public void setPatternedUrl(String patternedUrl) {
		this.patternedUrl = patternedUrl;
	}
	public List<String> getContextFilterRule() {
		return contextFilterRule;
	}
	public void setContextFilterRule(List<String> contextFilterRule) {
		this.contextFilterRule = contextFilterRule;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public String getPageType() {
		return pageType;
	}
	public void setPageType(String pageType) {
		this.pageType = pageType;
	}
}
