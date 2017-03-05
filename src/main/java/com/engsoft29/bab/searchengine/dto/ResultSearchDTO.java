package com.engsoft29.bab.searchengine.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ResultSearchDTO implements Serializable {

	private static final long serialVersionUID = 6435974476117737767L;
	
	@ApiModelProperty(value="Configuração da página")
	private SearchPageDTO page;

	@ApiModelProperty(value="Resultado da pesquisa.")
	private List<SearchDTO> result = new ArrayList<>();

	public List<SearchDTO> getResult() {
		return result;
	}

	public void setResult(List<SearchDTO> result) {
		this.result = result;
	}
	
	public void add(SearchDTO dto) {
		result.add(dto);
	}

	public SearchPageDTO getPage() {
		return page;
	}

	public void setPage(SearchPageDTO page) {
		this.page = page;
	}
}