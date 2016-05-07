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
	private String tempFolder="E:\\temp\\";
	private InputStream in = null;
	private OutputStream out =null;
	CloseableHttpResponse response=null;
	
	private String encoding=null;
	
	public String getEncoding() {
		return this.encoding;
	}

	public void RetriveWebPage(String url){
		
		
		this.encoding=null;
		
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
			 System.out.println(url+": "+sl);
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
					 
					 //update encoding
					 String type = response.getFirstHeader("Content-Type").getValue();
					 System.out.println("Content-Type="+type);
					 String[] type_a =type.split(";");
					 System.out.println("previous encoding: "+encoding);
					 if(type_a.length==2){
						 this.encoding=type_a[1].split("=")[1]; 
					 }
					 
					 System.out.println("change encoding: "+encoding);
					
					 
					 String filepath=getFilename(url,type);
					 out=new FileOutputStream(new File(filepath)); 
					 while((size =in.read(bytes))>0){
						 out.write(bytes,0,size);
//						 System.out.println("read");
//						 System.out.println(new String(bytes));
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
		
		String[] domain = url.split("/");
		
		//是否只有主机名
		if(type.indexOf("html")!=-1){
				//判断资源名里是否有html
				if(domain[domain.length-1].endsWith(".html")){
					name =tempFolder+url.replaceAll("[\\/:*|<>?\"]","_");
				}else{
					name =tempFolder+url.replaceAll("[\\/:*|<>?\"]","_")+".html";
				}
			
			return name;
		}
		else{
			//获得类型
			String[] type_a = type.split(";");
			type=type_a[0].split("/")[1];
//			if(type.equals("vnd.ms-powerpoint")){
//				type = "ppt";
//			}
			switch(type){
			case "vnd.openxmlformats-officedocument.spreadsheetml.sheet":
				type="xlsx";
				break;
			case "vnd.ms-powerpoint":
				type="pptx";
				break;
			}
			//判断是否有文件后缀
			if(domain[domain.length-1].contains("."+type)){
				name = tempFolder+url.replaceAll("[\\/:*|<>\"]","_");
			}
			else{
				name = tempFolder+url.replaceAll("[\\/:*|<>\"]","_")+"."+type;
			}
			
			return name;
		}
		
		
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Downloader d = new Downloader();
		d.RetriveWebPage("http://www.baidu.com");
		
//		String s ="www.baidu.com";
//		String[] s_a=s.split("/");
//		System.out.println(s_a[1]);
	}

}
