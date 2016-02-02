package com.yg.webshow.crawl.webdoc;

import java.util.List;

import org.jsoup.nodes.TextNode;

/**
 * 
 *
 */
public class DWebDocMeta {
	private String title;
	private List<TextNode> contents;
	private List<String> mediaUrls;
	private String contentsPath;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<TextNode> getContents() {
		return contents;
	}
	public void setContents(List<TextNode> contents) {
		this.contents = contents;
	}
	public String getContentsPath() {
		return contentsPath;
	}
	public void setContentsPath(String contentsPath) {
		this.contentsPath = contentsPath;
	}
	public List<String> getMediaUrls() {
		return mediaUrls;
	}
	public void setMediaUrls(List<String> mediaUrls) {
		this.mediaUrls = mediaUrls;
	}
	
}
