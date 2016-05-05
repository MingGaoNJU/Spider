package com.xm.MyCrawler;

import java.util.Set;

import com.xm.util.MyLinkStringFilter;

public class Spider {

	/**
	 * @param args
	 */
	private CrawlQueue crawlQueue=new CrawlQueue();
	private Downloader downloader = new Downloader();
	private HtmlParser htmlparser = new HtmlParser();
	
	private void init(String[] seeds){
		for(String url:seeds){
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
			String url = crawlQueue.DewaitingQueue();
	 		if(!url.equals("")){
				downloader.RetriveWebPage(url);
				crawlQueue.UpdatevisitedQueue(url);
				String encoding = downloader.getEncoding();
				System.out.println("get: "+encoding);
				if(encoding!=null){
					htmlparser.setEncoding(encoding);
				}
				
				
				Set<String> links = htmlparser.parseLinks(url, filter) ; 
				
				for(String link :links){
					System.out.println(link);
					crawlQueue.EnwaitingQueue(link);
				}
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
