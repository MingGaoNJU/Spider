package com.xm.util;
import java.util.BitSet;


public class myBloomFilter {

	/**
	 * @param args
	 */
	private static final int DEFAULT_SIZE = 2<<24; 
	private static final int[] seeds = new int[] {7,11,13,31,37,61,73,79};
	
	//DNA generator
	private Generator[] func = new Generator[seeds.length];
	
	private BitSet bits = new BitSet(DEFAULT_SIZE);
	
	public myBloomFilter(){
		
		for(int i=0;i<seeds.length;i++){
			func[i] = new Generator(DEFAULT_SIZE, seeds[i]);
		}
	}
	
	//add url
	
	public void add(Url url){
		if(url!=null){
			String UrlDomain = url.getUrlDomain();
			add(UrlDomain);
		}
	}
	
	public void add(String UrlDomain){
		for(Generator g : func){
			bits.set(g.generate(UrlDomain));
		}
	}
	
	//check url existence
	public boolean contains(Url url){
		if(url==null){
			return false;
		}else{
			return contains(url.getUrlDomain());
		}
	}
	
	public boolean contains(String UrlDomain){
		if(UrlDomain==null){
			return false;
		}
		
		boolean res = true;
		for(Generator g: func){
			res =res&&bits.get(g.generate(UrlDomain));
		}
		return res;
	}
	
	
	public static class Generator{
		private int capacity;
		private int seed;
		Generator(int capacity,int seed){
			this.capacity=capacity;
			this.seed=seed;
		}
		
		public int generate(String value){
			int result = 0;
			int len = value.length();
			
			for(int i=0;i<len;i++){
				result = seed*result+value.charAt(i);
			}
			
			
			
			return (capacity-1)& result;
		}

	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String value="www.baidu.com";
		myBloomFilter mb= new myBloomFilter();
		System.out.println(mb.contains("www.baidu.com"));
		mb.add(value);
		System.out.println(mb.contains("www.baidu.com"));
	}

}
