package com.xm.MyCrawler;

import java.util.Set;
import com.xm.util.MyLinkStringFilter;
import com.xm.util.Url;

public class Spider {

	/**
	 * @param args
	 */
	private CrawlQueue crawlQueue=new CrawlQueue();
	private Downloader downloader = new Downloader();
	private HtmlParser htmlparser = new HtmlParser();
	
	private void init(String[] seeds){
		for(String UrlDomain:seeds){
			Url url = new Url();
			url.setUrlDomain(UrlDomain);
			crawlQueue.EnwaitingQueue(url);
		}
	}
	
	private void crawl(String[] seeds){
		//filter
		MyLinkStringFilter filter = new MyLinkStringFilter(seeds[0]);
		
		//init
		init(seeds);
		
		//BFS
		while(!crawlQueue.visitQueueEmpty()&&crawlQueue.getVisitedNum()<10000){
			Url url = crawlQueue.DewaitingQueue();
			String UrlDomain =url.getUrlDomain();
	 		if(url!=null&&!UrlDomain.equals("")){
				downloader.RetriveWebPage(UrlDomain);
				crawlQueue.UpdatevisitedQueue(UrlDomain);
				String encoding = downloader.getEncoding();
				System.out.println("get: "+encoding);
				if(encoding!=null){
					htmlparser.setEncoding(encoding);
				}
				
				
				Set<String> links = htmlparser.parseLinks(UrlDomain, filter) ; 
				
				for(String link :links){
//					System.out.println(link);
					Url linkurl = new Url();
					linkurl.setUrlDomain(link);
					crawlQueue.EnwaitingQueue(linkurl);
				}
//				System.out.println(crawlQueue.visitQueueEmpty());
			}
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Spider spider = new Spider();
		spider.crawl(new String[]{"http://www.nature.com/nature"});
		//filter
		
		
		
		//init
		

		
	}

}
