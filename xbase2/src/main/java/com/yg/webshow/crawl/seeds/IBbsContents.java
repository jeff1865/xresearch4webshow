package com.yg.webshow.crawl.seeds;

import com.yg.webshow.crawl.webdoc.template.WebDocBbs;

public interface IBbsContents {
	public WebDocBbs getContent(String url);
}
