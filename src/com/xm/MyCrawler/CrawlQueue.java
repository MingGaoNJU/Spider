package com.xm.MyCrawler;



import com.xm.util.Url;
import com.xm.util.myBloomFilter;


public class CrawlQueue {

	/**
	 * @param args
	 */
//	private Set<String> visitedUrl = new HashSet<String>(); 
//	private Queue<String> waitingQueue = new LinkedList<String>(); 
	private MyFrontier myfrontier = new MyFrontier();
	private myBloomFilter mybloomfilter = new myBloomFilter();
	
	//check if the waiting queue emoty
	public boolean visitQueueEmpty(){
		return myfrontier.isEmpty();
		
	}
	
	//add element to the queue(we should check whether it was visied)
	public boolean EnwaitingQueue(Url url){
		//regex can be used instead
		String UrlDomain="";
		if(url!=null){
			UrlDomain=url.getUrlDomain();
		}else{
			return false;
		}
		
		if(!UrlDomain.trim().equals("")&&!mybloomfilter.contains(url)&&!myfrontier.contains(url)){
			return myfrontier.Enqueue(url);
			
		}else{
			return false;
		}
	}
	
	//get an element
	public Url DewaitingQueue(){
		return myfrontier.Dequeue();
	}
	
	
	//update visited
	public void UpdatevisitedQueue(String url){
		mybloomfilter.add(url);
	}
	
	public int getVisitedNum(){
		return mybloomfilter.size();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
