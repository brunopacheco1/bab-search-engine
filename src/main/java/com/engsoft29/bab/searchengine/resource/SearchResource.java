package com.engsoft29.bab.searchengine.resource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.engsoft29.bab.searchengine.dto.ResponseDTO;
import com.engsoft29.bab.searchengine.dto.ResultSearchDTO;
import com.engsoft29.bab.searchengine.service.DocumentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Stateless
@Path("/search")
@Api("servicos")
public class SearchResource {

	@Inject
	private DocumentService service;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Serviço de consulta.")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Resultado da pesquisa.", response = ResultSearchDTO.class),
	    @ApiResponse(code = 500, message = "Erro não esperado.", response = ResponseDTO.class)
	})
	public ResultSearchDTO search(@ApiParam(required=true, value="Query textual de pesquisa.") @QueryParam("query") String query,
			@ApiParam(required=true, value="Index inicial da pesquisa.") @QueryParam("start") @DefaultValue("0") Integer start,
			@ApiParam(required=true, value="Tamanho da página.") @QueryParam("limit") @DefaultValue("20") Integer limit) throws Exception {
		return service.search(start, limit, query);
	}
}