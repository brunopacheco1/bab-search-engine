package com.engsoft29.bab.searchengine.crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.engsoft29.bab.searchengine.dto.DocumentDTO;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class UOLCrawler extends BABCrawler {
    
    @Override
    public String getSeed() {
        return "https://noticias.uol.com.br/";
    }
    
    @Override
	public void visit(Page page) {
		String url = page.getWebURL().getURL();

		if (page.getParseData() instanceof HtmlParseData) {
		    HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			
			Document doc = Jsoup.parse(htmlParseData.getHtml());
			
			String document = doc.select("article#conteudo-principal > div#texto > p").text();
			
			String documentType = "NEWS";
			if(StringUtils.isBlank(document)) {
				documentType = "HTML";
				document = doc.select("body").text();
			}
			
			Set<WebURL> links = htmlParseData.getOutgoingUrls();

            List<String> children = new ArrayList<>();
			for(WebURL child : links) {
			    if(shouldVisit(null, child)) {
                    children.add(child.getURL());
                }

			}
			
			DocumentDTO documentDTO = new DocumentDTO();
			documentDTO.setDocument(document);
			documentDTO.setUrls(children);
			documentDTO.setDocumentType(documentType);
			documentDTO.setTitle(htmlParseData.getTitle());
			documentDTO.setUrl(url);
			
			try {
			    postDocument(documentDTO);
			} catch(Exception e) {
			    e.printStackTrace();
			}
		}
	}
}