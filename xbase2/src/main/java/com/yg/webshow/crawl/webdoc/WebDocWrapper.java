package com.yg.webshow.crawl.webdoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.mortbay.log.Log;

import com.yg.webshow.crawl.webdoc.template.WebDoc;
import com.yg.webshow.crawl.webdoc.template.WebDocBbs;
import com.yg.webshow.data.ContentFilterTable;

/**
 * ContentAnalyzer --- (SiteURL Pattern Expression) ---> Hbase
 * @author jeff.yg.kim@gmail.com
 *
 */
public class WebDocWrapper<T extends WebDoc> {
	
	private String url = null;
	private Document doc = null;
	private WebDocWrapperUtil wrapperUtil = new WebDocWrapperUtil();
	private ContentFilterTable cfTbl = new ContentFilterTable();
	private UrlUtil urlUtil = new UrlUtil();
	
	public WebDocWrapper(String url) throws IOException {
		this.url = url;
		this.doc = Jsoup.connect(this.url).get();
	}
	
	public WebDocWrapper(Document doc) {
		this.doc = doc;
	}
	
	public WebDoc getDocumentMeta() {
		return null;
	}
	
	public T getDocumentMeta(WrapperPath wPath) {
		List<String> lstContPath = wPath.getContents();
		
		for(String path : lstContPath) {
			;
		}
		
		return null;
	}
	
	public static void main(String ... v) {
		String url = "http://clien.net/cs2/bbs/board.php?bo_table=park&wr_id=44170480";
		try {
			WebDocWrapper<WebDocBbs> wrapper = new WebDocWrapper<WebDocBbs>(url);
			WrapperPath wPath = new WrapperPath();
			WebDocBbs docMeta = wrapper.getDocumentMeta(wPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
