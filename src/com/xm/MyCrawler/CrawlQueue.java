package com.xm.MyCrawler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class CrawlQueue {

	/**
	 * @param args
	 */
	private Set<String> visitedUrl = new HashSet<String>(); 
	private Queue<String> waitingQueue = new LinkedList<String>(); 
	
	//check if the waiting queue emoty
	public boolean visitQueueEmpty(){
		return waitingQueue.isEmpty();
		
	}
	
	//add element to the queue(we should check whether it was visied)
	public void EnwaitingQueue(String url){
		//regex can be used instead
		if(url!=null&&!url.trim().equals("")&&!visitedUrl.contains(url)&&!waitingQueue.contains(url)){
			waitingQueue.add(url);
		}
	}
	
	//get an element
	public String DewaitingQueue(){
		return waitingQueue.poll();
	}
	
	
	//update visited
	public void UpdatevisitedQueue(String url){
		visitedUrl.add(url);
	}
	
	public int getVisitedNum(){
		return visitedUrl.size();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
