package com.yg.webshow.crawl.webdoc.template;

import java.util.Date;
import java.util.List;

/**
 * Standard Web Document Template (Tagging Interface)
 */
public class WebDoc {
	protected String docTitle ;
	protected String contTitle ;
	protected String contentsText ;
	protected Date docDate ;
	protected List<String> meta ;
	
	public String getDocTitle() {
		return docTitle;
	}
	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}
	public String getContTitle() {
		return contTitle;
	}
	public void setContTitle(String contTitle) {
		this.contTitle = contTitle;
	}
	public String getContentsText() {
		return contentsText;
	}
	public void setContentsText(String contentsText) {
		this.contentsText = contentsText;
	}
	public Date getDocDate() {
		return docDate;
	}
	public void setDocDate(Date docDate) {
		this.docDate = docDate;
	}
	public List<String> getMeta() {
		return meta;
	}
	public void setMeta(List<String> meta) {
		this.meta = meta;
	}
}
