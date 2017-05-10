package com.ygsoft.webshow.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ygsoft.SysConf;
import com.yg.webshow.crawl.data.*;
import com.yg.webshow.crawl.seeds.NewClienPark;
import com.yg.webshow.crawl.webdoc.template.WebDocBbs;

@Controller
public class BbsCrawlViewController {
	private static Logger log = Logger.getLogger(BbsCrawlViewController.class) ;
	private RtCrawlTable rtCrawlTable = null ;
	
	public BbsCrawlViewController() {
		this.rtCrawlTable = new RtCrawlTable(SysConf.createNewHbaseConn());
	}
	
	@RequestMapping("/news")
	public String getCrawlList(Model model, @RequestParam int topN) {
		log.info("Get News ..");
		List<CrawlDataExBo> lstNews = this.rtCrawlTable.getLatest(topN, null);
		model.addAttribute("lstNews", lstNews) ;
		
		return "news" ;
	}
	
	@RequestMapping("/newsContent")
	public String getCrawlContents(Model model, @RequestParam String siteId, 
			@RequestParam int postNo) {
		log.info("Get News ..");
//		List<CrawlDataExBo> lstNews = this.rtCrawlTable.getLatest(topN, null);
		CrawlDataExBo crawlData = this.rtCrawlTable.getCrawlData(siteId, postNo) ;
		model.addAttribute("news", crawlData) ;
		
		return "newsCont" ;
	}
	
	@RequestMapping("/rtContent")
	public String getRtContents(Model model, @RequestParam String siteId, 
			@RequestParam int postNo) {
		log.info("Get RtNews ..");
		
		String url = "http://clien.net/cs2/bbs/board.php?bo_table=park";
		url = "https://www.clien.net/service/board/park";	// New Clien
		NewClienPark clien = new NewClienPark(url);
		
//		List<CrawlDataExBo> lstNews = this.rtCrawlTable.getLatest(topN, null);
		CrawlDataExBo crawlData = this.rtCrawlTable.getCrawlData(siteId, postNo) ;
		WebDocBbs content = clien.getContent("https://www.clien.net/service/board/park/10719290");
				
		model.addAttribute("news", content) ;
		
		return "newsCont" ;
	}
}
