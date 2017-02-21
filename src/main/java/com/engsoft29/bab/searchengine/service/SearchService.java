package com.engsoft29.bab.searchengine.service;

import javax.ejb.Stateless;

import com.engsoft29.bab.searchengine.dto.ResultSearchDTO;
import com.engsoft29.bab.searchengine.exception.AppException;

@Stateless
public class SearchService {
	
    public ResultSearchDTO search(String query) throws AppException {
    	
    	return new ResultSearchDTO();
    }
}