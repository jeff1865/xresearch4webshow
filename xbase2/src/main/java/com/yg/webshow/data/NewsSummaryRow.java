package com.yg.webshow.data;

import java.util.Map;

public class NewsSummaryRow {
	private String seedId ;
	private String docNo;
	private long timestamp ;
	private String anchorText ;
	private String docTitle ;
	private String contents ;
	private Map<String, String> extra ;
	
	public String toString() {
		StringBuilder sbRet = new StringBuilder() ;
		sbRet.append(this.seedId).append(":");
		sbRet.append(this.anchorText).append("|");
		sbRet.append(this.docTitle).append("|");
		sbRet.append(this.contents).append("|");
		sbRet.append(this.extra);
		
		return sbRet.toString() ;
	}
	
	public String getSeedId() {
		return seedId;
	}
	public void setSeedId(String seedId) {
		this.seedId = seedId;
	}
	public String getAnchorText() {
		return anchorText;
	}
	public void setAnchorText(String anchorText) {
		this.anchorText = anchorText;
	}
	public String getDocTitle() {
		return docTitle;
	}
	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Map<String, String> getExtra() {
		return extra;
	}
	public void setExtra(Map<String, String> extra) {
		this.extra = extra;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getDocNo() {
		return docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}
}
