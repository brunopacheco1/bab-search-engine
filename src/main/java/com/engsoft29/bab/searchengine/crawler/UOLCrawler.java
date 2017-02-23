package com.engsoft29.bab.searchengine.crawler;

public class UOLCrawler extends BABCrawler {
    
    @Override
    public String getSeed() {
        return "https://noticias.uol.com.br/";
    }
}