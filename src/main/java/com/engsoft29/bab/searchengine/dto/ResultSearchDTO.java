package com.engsoft29.bab.searchengine.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ResultSearchDTO implements Serializable {

	private static final long serialVersionUID = 6435974476117737767L;
	
	@ApiModelProperty(value="Tamanho da página de pesquisa.", example="0")
	private Long resultSize = 0l;
	
	@ApiModelProperty(value="Tamanho total da pesquisa.", example="0")
	private Long totalSize = 0l;

	@ApiModelProperty(value="Resultado da pesquisa.")
	private List<SearchDTO> result = new ArrayList<>();

	public Long getResultSize() {
		return resultSize;
	}

	public void setResultSize(Long resultSize) {
		this.resultSize = resultSize;
	}

	public Long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Long totalSize) {
		this.totalSize = totalSize;
	}

	public List<SearchDTO> getResult() {
		return result;
	}

	public void setResult(List<SearchDTO> result) {
		this.result = result;
	}
}