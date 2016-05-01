package com.xm.MyCrawler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class Downloader {

	/**
	 * @param args
	 */
	public void RetriveWebPage(String url){
		
		InputStream in = null;
		OutputStream out =null;
		
		CloseableHttpResponse response=null;
		//get method
//		RequestConfig config =RequestConfig.custom().setConnectTimeout(1000).setSocketTimeout(1000).build();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpRequestBase httprequest = new HttpGet(url);
		//httprequest.getParams().setParameter(, )
//		httprequest.setConfig(config);
		

		try {
			 response = httpclient.execute(httprequest);
			 //read status line
			 StatusLine sl = response.getStatusLine();
			 System.out.println(sl);
			 //get status code and operate on it
			 int StatusCode = sl.getStatusCode();
			 switch(StatusCode){
			 case HttpStatus.SC_MOVED_TEMPORARILY:
			 	
			 		Header location = response.getFirstHeader("Location");
			 		System.out.println(sl+""+location.getValue());

/*			 		//read headers
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
			 		break;
			 	
			 case HttpStatus.SC_OK:
			 	
					 //read content
					 HttpEntity en = response.getEntity();
					 in=en.getContent();
					 byte[] bytes = new byte[1024];
					 int size=0;
					 String filepath="E:\\temp\\"+getFilename(url,response.getFirstHeader("Content-Type").getValue());
					 out=new FileOutputStream(new File(filepath)); 
					 while((size =in.read(bytes))>0){
						 out.write(bytes);
//						 System.out.println("read");
						 System.out.println(new String(bytes));
					 }
//					 System.out.println(EntityUtils.toString(en));
					 break;
			 	
			 }
			 



		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(in!=null){
					in.close();
				}
				if(response!=null){
					response.close();
				}
				
				if(out!=null){
					out.close();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	private String getFilename(String url, String type) {
		// TODO Auto-generated method stub
		String name=null;
		//delete protacle
		String[] url_a = url.split("://");
		url = url_a[1];
		if(type.indexOf("html")!=-1){
			name =url.replaceAll("[\\?/:*|<>\"'#$%@]","_")+".html";
			return name;
		}
		else{
			name = url.replaceAll("[\\?/:*|<>\"'#$%@]","_")+"."+type.substring(type.lastIndexOf("/")+1);
			return name;
		}
		
		
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Downloader d = new Downloader();
		d.RetriveWebPage("http://www.baidu.com");
	}

}
