package com.engsoft29.bab.searchengine.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class DocumentDTO implements Serializable {

	private static final long serialVersionUID = 6435974476117737767L;
	
	@ApiModelProperty(value="Corpo em texto da página web capturada.", example="pagina web 123.")
	private String document;
	
	@ApiModelProperty(value="Título da página web capturada.", example="pagina web 123.")
	private String title;
	
	@ApiModelProperty(value="URL da página capturada.", example="http://www.pagina.com")
	private String url;
	
	@ApiModelProperty(value="Tipo da página capturada.", example="NEWS")
	private String documentType = "NEWS";

	@ApiModelProperty(value="Lista de urls encontrada na página capturada.")
	private List<String> urls = new ArrayList<>();

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

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
}