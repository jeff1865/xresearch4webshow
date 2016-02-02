package com.yg.webshow.crawl.webdoc.template;

import java.util.Date;
import java.util.List;

/**
 * Standard Web Document Template (Tagging Interface)
 */
public abstract class AbstractWebDoc {
	protected String docTitle ;
	protected String contTitle ;
	protected String contentsText ;
	protected Date docDate;
	protected List<String> metas;
	
}
