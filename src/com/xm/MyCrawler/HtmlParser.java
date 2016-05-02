package com.xm.MyCrawler;

import java.util.HashSet;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.LinkStringFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.xm.util.MyLinkStringFilter;

public class HtmlParser {

	/**
	 * @param args
	 */
	private String encoding=null;
	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Set<String> parseLinks(String url,MyLinkStringFilter linkfilter){
		Set<String> links = new HashSet<String>();
		try {
			Parser parser = new Parser(url);
//			String encoding  = parser.getEncoding();
//			System.out.println(encoding);

//			System.out.println("seted: "+encoding);
			parser.setEncoding("utf-8");
			
			NodeFilter frameFilter = new NodeFilter() {
				
				@Override
				public boolean accept(Node node) {
					// TODO Auto-generated method stub
					if(node.getText().startsWith("frame src=")){
						 return true;
					}else{
						return false;
					}
					
					//return false;
				}
				
			};
			OrFilter filter =new OrFilter(new NodeClassFilter(LinkTag.class),frameFilter);
			//extract all matched nodes
			NodeList list = parser.extractAllNodesThatMatch(filter);
			
			for(int i=0;i<list.size();i++){
				Node tag = list.elementAt(i);
				if(tag instanceof LinkTag){
					LinkTag link = (LinkTag)tag;
					String linkUrl=link.getLink();
					if(linkfilter.accept(tag)){
						
						links.add(linkUrl);
							
					}
					
				}
				else{
					String frame = tag.getText();
					
					int start = frame.indexOf("src=");
					frame = frame.substring(start);
					
					int end = frame.indexOf("/");
					if(end==-1){
						end=frame.indexOf(">")-1;
					}
					
					String frameUrl = frame.substring(5,end);
					if(linkfilter.accept(frameUrl)){
						links.add(frameUrl);
					}
				}
			}
			
			
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(links.size());
		return links;
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HtmlParser parser =new HtmlParser();
		MyLinkStringFilter filter = new MyLinkStringFilter("http://www.baidu.com");
		Set<String> links = parser.parseLinks("http://www.baidu.com", filter);
		System.out.println(links.size());
		for(String link :links){
			System.out.println(link);
		}
		
	}

}
