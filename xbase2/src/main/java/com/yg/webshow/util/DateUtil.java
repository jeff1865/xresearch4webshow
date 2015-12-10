package com.yg.webshow.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static final String commonDateFormat = "yyMMdd.HHmmss.SSS";
	
	public static String getCurrent() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd.HHmmss.SSS");
		Date curDate = new Date();
		String strDate = sdf.format(curDate);
		return strDate ;
	}
	
	public static void main(String ... v) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd.HHmmss.SSS");
		Date curDate = new Date();
		
		String strDate = sdf.format(curDate);
		System.out.println("Date >" + strDate);
	}
}
