package com.yg.webshow.util;

public class TextUtil {
	
	public static String getFixedLengthId(String id) {
		return String.format("%8s", id);
	}
	
	public static String getFixedLengthText(String val) {
		String strBase = "0000000";
		StringBuffer sb = new StringBuffer(strBase);
		sb.replace(strBase.length() - val.length(), strBase.length(), val);
		
		return sb.toString();
	}
	
	public static void main(String ... v) {
		System.out.println(getFixedLengthText("9"));
		System.out.println(getFixedLengthText("92"));
		System.out.println(getFixedLengthText("9211"));
	}
}
