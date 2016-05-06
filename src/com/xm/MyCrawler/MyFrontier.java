package com.xm.MyCrawler;

import java.io.File;
import java.util.Map.Entry;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.collections.StoredMap;
import com.sleepycat.je.*;
import com.xm.util.Url;





public class MyFrontier {

	/**
	 * @param args
	 * @return 
	 */
	private Database catalogdatabase=null;
	private Database database=null;
	private Environment env=null;
	private StoredClassCatalog javaCatalog=null;
	private StoredMap<String, Url> DB=null;
	
	private boolean EnvironmentConfig(String homeDirectory){
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setTransactional(true);
		envConfig.setAllowCreate(true);
	

		env = new Environment(new File("E:\\db"), envConfig);
			
		DatabaseConfig dbConfig = new DatabaseConfig();
		dbConfig.setTransactional(true);
		dbConfig.setAllowCreate(true);
			
		catalogdatabase = env.openDatabase(null,"java_class_catalog", dbConfig);
		javaCatalog = new StoredClassCatalog(catalogdatabase);
			
		DatabaseConfig dbConfig1 = new DatabaseConfig();
		dbConfig1.setTransactional(true);
		dbConfig1.setAllowCreate(true);
			
		database = env.openDatabase(null,"url",dbConfig1);
		

		 EntryBinding<String> keyBinding = new SerialBinding<String>(javaCatalog,String.class);
		 EntryBinding<Url> valueBinding = new SerialBinding<Url>(javaCatalog, Url.class);
		 DB = new StoredMap<String, Url>(database, keyBinding, valueBinding, true);
			
		return true;
	}
	
	public MyFrontier(String homeDirectory){
		EnvironmentConfig( homeDirectory);
	}
	
	public MyFrontier(){
		String homeDirectory = "E:\\db";
		//this.MyFrontier(homeDirectory );
		EnvironmentConfig( homeDirectory);
	}
	
	private Entry<String, Url> getNext(){
		return DB.entrySet().iterator().next();
	}
	
	private void delete(String key){
		DB.remove(key);
	}
	
	public Url Dequeue(){
		Url url = null;
		if(!DB.isEmpty()){
			Entry<String, Url> entry=getNext();
			url = entry.getValue();
			delete(entry.getKey());
		}
		return url;
	}
	 
	public boolean Enqueue(Url url){
		if(url!=null){
			//此处仍然需要检查visited
			DB.putIfAbsent(url.getUrlDomain(), url);
		}
		return true;
		
	}
	
	public void close(){
		database.close();
		javaCatalog.close();
		env.close();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyFrontier mf =new MyFrontier();
		Url url0 = new Url();
		url0.setUrlDomain("www.baidu.com");
		Url url1 = new Url();
		url1.setUrlDomain("www.google.com");
		mf.Enqueue(url0);
		mf.Enqueue(url1);
		
		System.out.println(mf.Dequeue().getUrlDomain());
		System.out.println(mf.Dequeue().getUrlDomain());
		

	}

}
