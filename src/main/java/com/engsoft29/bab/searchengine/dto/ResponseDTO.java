package com.engsoft29.bab.searchengine.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ResponseDTO {

	@ApiModelProperty(value="Se a requisição foi processada com sucesso ou não.", example="false")
	private Boolean success = false;
	
	@ApiModelProperty(value="Mensagem de resposta, caso seja uma excepção", example="Campo não preenchido corretamente.")
	private String message;

	public ResponseDTO(Boolean success, String message) {
		super();
		
		this.success = success;
		this.message = message;
	}
	
	public ResponseDTO(Boolean success) {
		super();
		
		this.success = success;
	}
	
	public ResponseDTO(String message) {
		super();
		
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}
}