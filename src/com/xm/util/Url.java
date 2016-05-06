package com.xm.util;

import java.io.Serializable;

public class Url implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6056692382085072537L;
	/**
	 * @param args
	 */
	
	
	private String urlDomain;
	private String urlIP;
	

	

	public Url() {
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public String getUrlDomain() {
		return urlDomain;
	}

	public void setUrlDomain(String urlDomain) {
		this.urlDomain = urlDomain;
	}

	public String getUrlIP() {
		return urlIP;
	}

	public void setUrlIP(String urlIP) {
		this.urlIP = urlIP;
	}

}
