package com.yg.webshow.data;

public class NewsRow implements IRow {
	private String siteId ;
	private String regDate ;
	private String anchorText ;
	private String link ;
	private String imgUrl ;
	private String summary ;
	private String filteredContents ;
	
	public NewsRow() {
		;
	}
	
	public String toString() {
		StringBuffer sbResult = new StringBuffer() ;
		sbResult.append(this.siteId).append("|");
		sbResult.append(this.regDate).append("|");
		sbResult.append(this.anchorText).append("|");
		sbResult.append(this.link).append("|");
		sbResult.append(this.imgUrl).append("|");
		sbResult.append(this.summary).append("|");
		sbResult.append(this.filteredContents).append("|");
		
		return sbResult.toString();
	}
	
	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getAnchorText() {
		return anchorText;
	}

	public void setAnchorText(String anchorText) {
		this.anchorText = anchorText;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getFilteredContents() {
		return filteredContents;
	}

	public void setFilteredContents(String filteredContents) {
		this.filteredContents = filteredContents;
	}
	
}
