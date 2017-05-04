package com.ygsoft.webshow.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ygsoft.SysConf;
import com.ygsoft.webshow.data.CrawlDataExBo;
import com.ygsoft.webshow.data.RtCrawlTable;

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
		List<CrawlDataExBo> lstNews = this.rtCrawlTable.getLatest(topN);
		model.addAttribute("lstNews", lstNews) ;
		
		return "news" ;
	}
	
}
