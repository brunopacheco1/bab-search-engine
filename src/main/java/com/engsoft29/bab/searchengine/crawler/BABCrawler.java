package com.engsoft29.bab.searchengine.crawler;

import java.security.MessageDigest;
import java.util.Set;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public abstract class BABCrawler extends WebCrawler {

	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg|png|mp3|mp3|zip|gz))$");
	
	protected static String seed;

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();
		return !FILTERS.matcher(href).matches() && href.startsWith(seed);
	}

	@Override
	public void visit(Page page) {
		String url = page.getWebURL().getURL();

		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			Set<WebURL> links = htmlParseData.getOutgoingUrls();

			for(WebURL child : links) {
				String hash1 = hash(url);
				String hash2 = hash(child.getURL());
				if(!hash1.equals(hash2)) {
					System.out.println(hash1 + " " + hash2);
				}
			}
		}
	}
	
	public static String getSeed() {
	    return seed;
	}

	private String hash(String text) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			String hex = (new HexBinaryAdapter()).marshal(md5.digest(text.getBytes()));
			
			return hex;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
