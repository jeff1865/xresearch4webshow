package com.yg.webshow.crawl.webdoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.mortbay.log.Log;

import com.yg.webshow.crawl.DocPathUnit;
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
//	private ContentFilterTable cfTbl = new ContentFilterTable();
	private UrlUtil urlUtil = new UrlUtil();
	
	public WebDocWrapper(String url) throws IOException {
		this.url = url;
		this.doc = Jsoup.connect(this.url).get();
	}
		
	public WebDocWrapper(Document doc) {
		this.doc = doc;
	}
		
	private Node getEndNode(List<DocPathUnit> lstPathUnit) {
		try {
			Elements elem = this.doc.select("html");
			int i = 0;
			for(DocPathUnit path : lstPathUnit) {
				if(path.getTagName() != null && path.getTagName().equals("html")) {
					break ;
				}
				i ++;
			}
			
			Element tmpElem = elem.get(0);
			for(;i<lstPathUnit.size();i++) {
				DocPathUnit path = lstPathUnit.get(i);
				System.out.println("PU >>> " + path);
				if(path.getChildIndex() >= 0) {
					if(lstPathUnit.size() - 2 == i && lstPathUnit.get(lstPathUnit.size() - 1).getChildIndex() < 0) {
						return tmpElem.childNode(path.getChildIndex());
					} else {
						tmpElem = tmpElem.child(path.getChildIndex());
					}
				} else {
					System.out.println("LPU>>" + path);
					return null;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Element getEndElement(String strPath) {
		List<DocPathUnit> lstPathUnit = this.wrapperUtil.getPathObject(strPath);
		Element tmpElem = null ;
		try {
			Elements elem = this.doc.select("html");
			int i = 0;
			for(DocPathUnit path : lstPathUnit) {
				if(path.getTagName() != null && path.getTagName().equals("html")) {
					break ;
				}
				i ++;
			}
			
			tmpElem = elem.get(0);
			for(;i<lstPathUnit.size()-1;i++) {
				DocPathUnit path = lstPathUnit.get(i);
				
				tmpElem = tmpElem.child(path.getChildIndex());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
				
		return tmpElem;
	}
	
	//TODO need to remove the duplicated code
	public Node getEndNode(String strPath) {
		try {
			Elements elem = this.doc.select("html");
				
			List<DocPathUnit> lstPathUnit = this.wrapperUtil.getPathObject(strPath);
			
			int i = 0;
			for(DocPathUnit path : lstPathUnit) {
				if(path.getTagName() != null && path.getTagName().equals("html")) {
					break ;
				}
				i ++;
			}
			
			Element tmpElem = elem.get(0);
			for(;i<lstPathUnit.size();i++) {
				DocPathUnit path = lstPathUnit.get(i);
				if(path.getChildIndex() >= 0) {
					if(lstPathUnit.size() - 2 == i && lstPathUnit.get(lstPathUnit.size() - 1).getChildIndex() < 0) {
						return tmpElem.childNode(path.getChildIndex());
					} else {
						tmpElem = tmpElem.child(path.getChildIndex());
					}
				} else {
					return null;
				}
			}
		} catch(Exception e) {
//			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<Node> getGroupWrappedNode(int grpInedx, List<String> lstWrapPath) {
		
		return null;
	}
	
	public List<Node> getBlurWrappedNode(String wrapPath) {
		ArrayList<Node> resNodes = new ArrayList<Node>();
		
//		System.out.println("Patterned >" + this.wrapperUtil.getNodePathPatternExpression(wrapPath));
		String qryPath = this.wrapperUtil.getNodePathPatternExpression(wrapPath);
		if(qryPath.endsWith("#text:*/")) {
			qryPath = qryPath.substring(0, qryPath.length() - "#text:*/".length());
		}
//		System.out.println("Revised Query :" + qryPath);
		
		WebDocAnalyzer wda = new WebDocAnalyzer(this.doc) ;
		List<TextNode> utNodes = wda.getUnlinkedTextNodes();
		
		String nodePath = null, pPath = null;
		for(TextNode node : utNodes) {
			nodePath = this.wrapperUtil.getNodePath(node);
//			System.out.println("Unlinked Node Path >>> " + nodePath);
			pPath = this.wrapperUtil.getNodePathPatternExpression(nodePath);
//			System.out.println("Patterned Node Path >>> " + pPath + "---" + qryPath);
			if(pPath.contains(qryPath)) {
//				System.out.println("Matched Node :" + nodePath);
				resNodes.add(node);
			}
		}
				
		return resNodes;
	}
	
	public List<Node> getAllElements(String wrapPath) {
		ArrayList<Node> lstNode = new ArrayList<Node>();
		try {
//			Elements elem = this.doc.select("html");
			List<DocPathUnit> lstPathUnit = this.wrapperUtil.getPathObject(wrapPath);
			
//			int wrapIdx = -1;
//			for(DocPathUnit dPath : lstPathUnit) {
//				System.out.println("-->" + dPath);
//				if(dPath.getChildIndex() == DocPathUnit.ALL_INDEX) {
//					wrapIdx = lstPathUnit.indexOf(dPath);
//					break;
//				}
//			}
			DocPathUnit endPathUnit = lstPathUnit.get(lstPathUnit.size() -1);
			
			lstPathUnit.remove(lstPathUnit.size() - 1);
			
			for(DocPathUnit pu : lstPathUnit) {
				System.out.println("--->" + pu);
			}
			
			Node endNode = this.getEndNode(lstPathUnit);
			System.out.println("ParentNode:" + endNode);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return lstNode;
	}
	
	public List<Node> getWrappedNodes(String wrapPath) {
		ArrayList<Node> lstNode = new ArrayList<Node>();
		try {
//			Elements elem = this.doc.select("html");
			List<DocPathUnit> lstPathUnit = this.wrapperUtil.getPathObject(wrapPath);
			
			int wrapIdx = -1;
			for(DocPathUnit dPath : lstPathUnit) {
				System.out.println("-->" + dPath);
				if(dPath.getChildIndex() == DocPathUnit.ALL_INDEX) {
					wrapIdx = lstPathUnit.indexOf(dPath);
					break;
				}
			}
			
			System.out.println("Path last Index :" + wrapIdx);
			
			for(int i=0;i<1000;i++) {
				lstPathUnit.get(wrapIdx).setChildIndex(i);
				Node endNode = this.getEndNode(lstPathUnit);
				if(endNode == null) {
					break ;
				}
				
				String strNd = endNode.toString().trim();
				if(!strNd.startsWith("<")) {
					if(strNd.length() > 0) lstNode.add(endNode);
				} else {
//					System.out.println("Invalid -->" + endNode);
					while(endNode.childNodeSize() > 0) {
//						lstNode.add(endNode.childNode(0));
						endNode = endNode.childNode(0);
					}
					
					if(endNode instanceof TextNode)
						lstNode.add(endNode);
//					else 
//						Log.info("Invalid Node :" + endNode);
					
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return lstNode;
	}
	
	
	public T getDocumentMeta(WrapperPath wPath) {
		List<String> lstContPath = wPath.getContents();
		
		for(String path : lstContPath) {
			System.out.println("Path >" + path);
			Node endNode = this.getEndNode(path);
			System.out.println("EndNode >"  + endNode.nodeName() + "-->" + endNode);
		}
		
		return null;
	}
	
	public List<Element> getImgs() {
		ArrayList<Element> lstElement = new ArrayList<Element>();
		
		Elements media = doc.select("[src]");
		
		for (Element src : media) {
            if (src.tagName().equals("img")){
            	lstElement.add(src);
            } 
        }
		
		return lstElement ;
	}
	
	public List<TextNode> getAllTextNode() {
		ArrayList<TextNode> lstTextNode = new ArrayList<TextNode>() ;
		
		Elements allElem = this.doc.getAllElements();
		Iterator<Element> itlElem = allElem.iterator();
		
		while(itlElem.hasNext()) {
			Element emt = itlElem.next();
			
			String data = null;
			List<TextNode> textNodes = emt.textNodes();
			
			for(TextNode tn : textNodes) {
				if(tn.childNodeSize() == 0) { 
					data = tn.text();
					if(data.trim().length() > 0) {
						lstTextNode.add(tn);
					}
				}
			}
		}
		
		return lstTextNode ;
	}
	
	public static void main(String ... v) {
		String url = "http://clien.net/cs2/bbs/board.php?bo_table=park&wr_id=44170480";
		url = "http://clien.net/cs2/bbs/board.php?bo_table=park&wr_id=44713594";
//		url = "http://clien.net/cs2/bbs/board.php?bo_table=park&wr_id=53458800";
		
//		url = "http://news.chosun.com/site/data/html_dir/2015/12/27/2015122700455.html";
		
		try {
			WebDocWrapper<WebDocBbs> wrapper = new WebDocWrapper<WebDocBbs>(url);
			
//			WrapperPath wPath = new WrapperPath();
//			List<String> contents = new ArrayList<String>();
//			contents.add("html:1/body:8/div:0/div:0/div:2/div:3/div:2/div:1/div:0/span:1/#text");
////			contents.add("html:1/body:3/div:2/div:1/article:1/div:19/div:0/#text");
//			wPath.setContents(contents);
//			
//			WebDocBbs docMeta = wrapper.getDocumentMeta(wPath);
//			System.out.println("Wrapper Result >" + docMeta);
			

			String wPath = "html:1/body:8/div:0/div:0/div:2/div:3/div:2/div:1/div:0/span:*/#text";
//			List<Node> wrappedNodes = wrapper.getWrappedNodes(wPath);
//			
//			System.out.println("Result :" + wrappedNodes.size());
//			for(Node node : wrappedNodes) {
//				System.out.println("Filtered :" + node + " ---> " + node.nodeName());
//			}
			String wPathTitle = "html:1/body:8/div:0/div:0/div:2/div:3/div:1/div:0/div:0/h4:0/span:1/#text";
			
//			List<Node> titleNode = wrapper.getWrappedNodes(wPathTitle);
//			System.out.println("Title .....");
//			for(Node node : titleNode) {
//				System.out.println("Data -->" + node.toString());
//			}
			System.out.println("title --> " + wrapper.getEndNode(wPathTitle));
			
			
			List<Node> blurWrappedNode = wrapper.getBlurWrappedNode(wPath);
			System.out.println("----------- Result :" + blurWrappedNode.size());
			for(Node node : blurWrappedNode) {
				System.out.println("Filtered :" + node + " ---> " + node.nodeName() + " ==> " + node.getTokenIndex());
			}
			
			List<Element> imgs = wrapper.getImgs();
			
			System.out.println("Result IMGS ..");
			for(Element elemImg : imgs) {
				System.out.println("[IMG] " + elemImg.attr("abs:src") + " ==> " + elemImg.getTokenIndex());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
