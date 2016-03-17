package com.yg.webshow.core.realtime;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Node;

import com.yg.webshow.crawl.webdoc.WebDocWrapper;
import com.yg.webshow.crawl.webdoc.template.WebDocBbs;
import com.yg.webshow.data.NewsRow;
import com.yg.webshow.data.NewsTable;

/**
 * Get News from newslist table --> extract news from document --> update newslist
 * @author jeff
 *
 */
public class NewsFilterJoblet implements Joblet {
	private static Logger log = Logger.getLogger(NewsFilterJoblet.class);
	
	private NewsTable newsTable = null;
	private final int defaultMaxCount = 20;
	private int seedId ;
	
	public NewsFilterJoblet(int seedId) {
		this.newsTable = new NewsTable() ;
		this.seedId = seedId ;
	}
	
	public int extractDocMeta() {
		List<NewsRow> latestNews = this.newsTable.getLatestNews(seedId, defaultMaxCount);
		int i = 0;
		for(NewsRow newsRow : latestNews) {
			log.info(i++ + "-->" + newsRow.toString());
			if(newsRow.getLink() != null) {
				try {
					WebDocWrapper<WebDocBbs> wrapper = new WebDocWrapper<WebDocBbs>(newsRow.getLink());
					this.displayContents(wrapper);
					
				} catch (IOException e) {
//					e.printStackTrace();
					log.info("Invalid I/O status :" + e.getMessage());
				}
			}
		}
		return -1;
	}
	
	private void displayContents(WebDocWrapper<WebDocBbs> wrapper) {
		//TODO need to change code (getting filter/wrap rule from Hbase)
		String wPath = "html:1/body:8/div:0/div:0/div:2/div:3/div:2/div:1/div:0/span:*/#text";
		List<Node> wrappedNodes = wrapper.getWrappedNodes(wPath);
		
		log.info("Node Size :" + wrappedNodes.size());
		if(wrappedNodes.size() == 0) {
			wrappedNodes = wrapper.getBlurWrappedNode(wPath);
			log.info("---> Blurred Node Size :" + wrappedNodes.size());
		}
				
		StringBuffer sb = new StringBuffer();
		for(Node node : wrappedNodes) {
//			System.out.println("Filtered :" + node + " ---> " + node.nodeName());
			sb.append(node.toString()).append("\n");
		}
		
		log.info("Contents(Filtered) :" + sb.toString());
	}
	
	@Override
	public boolean start() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static void main(String ... v) {
		NewsFilterJoblet test = new NewsFilterJoblet(5);
		test.extractDocMeta();
	}
	
}
