package com.engsoft29.bab.searchengine.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown=true)
public class SearchDTO implements Serializable {

	private static final long serialVersionUID = 6435974476117737767L;
	
	@ApiModelProperty(value="Resumo do corpo da página web capturada.", example="pagina web")
	private String summary;
	
	@ApiModelProperty(value="URL da página capturada.", example="http://www.pagina.com")
	private String url;

	@ApiModelProperty(value="Relevância da página.")
	private BigDecimal pagerank;

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public BigDecimal getPagerank() {
		return pagerank;
	}

	public void setPagerank(BigDecimal pagerank) {
		this.pagerank = pagerank;
	}
}