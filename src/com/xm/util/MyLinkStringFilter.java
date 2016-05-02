package com.xm.util;

import org.htmlparser.filters.LinkStringFilter;

public class MyLinkStringFilter extends LinkStringFilter {

	String pattern;
	public MyLinkStringFilter(String pattern) {
		super(pattern);
		this.pattern=pattern;
		// TODO Auto-generated constructor stub
	}
	
	public boolean accept(String url){
		String[] pattern_a = pattern.split("/");
		pattern=pattern_a[0]+"/"+pattern_a[1]+"/"+pattern_a[2];
		if(url.startsWith(pattern)){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyLinkStringFilter ml =new MyLinkStringFilter("http://www.nature.com/news");
		System.out.println(ml.accept("http://www.nature.com/help"));
	}
	

}
