package com.engsoft29.bab.searchengine.crawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class CrawlerExecutor {
    public static void main(String[] args) throws Exception {
		String crawlStorageFolder = "~/data/crawl/root";
		int numberOfCrawlers = 7;

		CrawlConfig config = new CrawlConfig();
		config.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1");
		config.setCrawlStorageFolder(crawlStorageFolder);
        config.setMaxDepthOfCrawling(10);

		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
        
		controller.addSeed(new G1Crawler().getSeed());

		controller.start(G1Crawler.class, numberOfCrawlers);
	}
}