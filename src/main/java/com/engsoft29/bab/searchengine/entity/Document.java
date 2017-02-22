package com.engsoft29.bab.searchengine.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Document implements Serializable {

	private static final long serialVersionUID = -2189751195544716832L;

	private String id;
	
	private String document;
	
	private String url;
	
	private String summary;
	
	private BigDecimal pagerank;

	private List<String> children = new ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public BigDecimal getPagerank() {
		return pagerank;
	}

	public void setPagerank(BigDecimal pagerank) {
		this.pagerank = pagerank;
	}

	public List<String> getChildren() {
		return children;
	}

	public void setChildren(List<String> children) {
		this.children = children;
	}
}