package com.engsoft29.bab.searchengine.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown=true)
public class SearchPageDTO implements Serializable {

	private static final long serialVersionUID = -1157973215474527143L;

	@ApiModelProperty(value="Número de elementos da página.")
	private Long elements = 0l;
	
	@ApiModelProperty(value="Número total de elementos.")
	private Long totalElements = 0l;
	
	@ApiModelProperty(value="Número total de páginas.")
	private Long totalPages = 0l;
	
	@ApiModelProperty(value="Número da página corrente.")
	private Long page = 0l;

	public Long getElements() {
		return elements;
	}

	public void setElements(Long elements) {
		this.elements = elements;
	}

	public Long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Long totalElements) {
		this.totalElements = totalElements;
	}

	public Long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Long totalPages) {
		this.totalPages = totalPages;
	}

	public Long getPage() {
		return page;
	}

	public void setPage(Long page) {
		this.page = page;
	}
	
}