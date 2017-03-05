package com.engsoft29.bab.searchengine.crawler;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import com.engsoft29.bab.searchengine.dto.DocumentDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public abstract class BABCrawler extends WebCrawler {

	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg|png|mp3|mp3|zip|gz))$");
	
	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();
		return !FILTERS.matcher(href).matches() && href.startsWith(getSeed());
	}

	@Override
	public void visit(Page page) {
		String url = page.getWebURL().getURL();

		if (page.getParseData() instanceof HtmlParseData) {
		    HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			Set<WebURL> links = htmlParseData.getOutgoingUrls();

            List<String> children = new ArrayList<>();
			for(WebURL child : links) {
			    if(shouldVisit(null, child)) {
                    children.add(child.getURL());
                }

			}
			
			DocumentDTO document = new DocumentDTO();
			document.setDocument(htmlParseData.getText());
			document.setUrls(children);
			document.setUrl(url);
			
			try {
			    postDocument(document);
			} catch(Exception e) {
			    e.printStackTrace();
			}
		}
	}
	
	private void postDocument(DocumentDTO document) throws Exception {
	    String link = "http://50.19.226.130/bab-search-engine/api/document";
//		String link = "http://localhost:8082/bab-search-engine/api/document";
		URL object=new URL(link);

		HttpURLConnection con = (HttpURLConnection) object.openConnection();
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestMethod("POST");
		
		ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
        
		OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
		wr.write(objectMapper.writeValueAsString(document));
		wr.flush();
		
		int httpResult = con.getResponseCode();
		
		if (httpResult == HttpURLConnection.HTTP_OK) {
			System.out.println("Status[" + httpResult + "]");
		}
	}
	
	public abstract String getSeed();
}