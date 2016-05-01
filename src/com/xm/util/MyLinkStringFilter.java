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
	}
	

}
