package com.engsoft29.bab.searchengine.service;

import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;

import com.engsoft29.bab.searchengine.dto.DocumentDTO;
import com.engsoft29.bab.searchengine.exception.AppException;

@Stateless
public class DocumentService {
	
    public void processar(DocumentDTO dto) throws AppException {
    	validar(dto);
    	
    	//processar
    }
	
	private void validar(DocumentDTO dto) throws AppException {
	    if(dto == null) {
	        throw new AppException("O documento é obrigatório.");
	    }
	    
	    if(StringUtils.isBlank(dto.getDocument()) || StringUtils.isBlank(dto.getUrl())) {
	        throw new AppException("Os campos document e url são obrigatórios.");
		}
	}
}