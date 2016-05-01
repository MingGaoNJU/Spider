package com.xm.util;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
//import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

//import com.sun.org.apache.xerces.internal.util.URI;

//import org.apache.http.client.HttpClient;

//import sun.net.www.http.HttpClient;
public class RetriveWebPage {

	/**
	 * @param args
	 */
	
	
	
	@SuppressWarnings("deprecation")
	public static boolean downloadWebPage(URI uri){
		InputStream in = null;
		OutputStream out =null;
		//get method
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpRequestBase httprequest = new HttpGet(uri);
		CloseableHttpResponse response=null;
		
		
		
//		//post method
//		CloseableHttpClient httpclient = HttpClients.createDefault();
//		HttpPost httppost = new HttpPost(uri);
//		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
//		formparams.add(new BasicNameValuePair("s", "wd=hello"));
//		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,Consts.UTF_8);
//		httppost.setEntity(entity);
//		CloseableHttpResponse response=null;
//		

		try {
			 response = httpclient.execute(httprequest);
			 
			 
			 //read status line
			 StatusLine sl = response.getStatusLine();
			 System.out.println(sl);
			 //get status code and operate on it
			 int StatusCode = sl.getStatusCode();
			 switch(StatusCode){
			 case HttpStatus.SC_MOVED_TEMPORARILY:
			 	{
			 		Header location = response.getFirstHeader("Location");
			 		System.out.println(location.getValue());
			 		
			 		break;
			 	}
			 }
			 

			 //read content
			 HttpEntity en = response.getEntity();
			 in=en.getContent();
			 byte[] bytes = new byte[1024];
			 int size=0;
			 //System.out.println("ready");
			 while((size =in.read(bytes))>0){
				 
				 System.out.println(new String(bytes));
			 }
//			 System.out.println(EntityUtils.toString(en));
/*			 //read headers
			 Header[] hds=response.getAllHeaders();
			 for(Header hd:hds){
				 System.out.println(hd.toString());
			 }
			 
			 Header[] location_a = response.getHeaders("Location");
			 String s="";
			 for(Header hd:location_a){
				 System.out.println(hd.toString());
				 s=hd.toString();
			 }
*/
/*
			 URI uri_next=null;
			 try {
				 
				 uri_next = new URIBuilder().setPath(s).build();
				 downloadWebPage(uri);
			 } catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
*/			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		httppost.s
		return true;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
/*		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet("http://www.baidu.com");
		CloseableHttpResponse response=null;
		try {
			response = httpclient.execute(httpget);
			
			System.out.println(response.toString());
			System.out.println(response.getEntity());
			HttpEntity entity = response.getEntity();
			//InputStream in=entity.getContent();
			//byte[] b=new byte[1024];
			//in.read(b);
			System.out.println(EntityUtils.toString(entity));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		URI uri=null;
		try {
			uri = new URIBuilder().setScheme("http").setHost("www.baidu.com").setPath("/s").setParameter("wd","hello").build();
			downloadWebPage(uri);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
