package com.engsoft29.bab.searchengine.resource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.engsoft29.bab.searchengine.dto.ResponseDTO;
import com.engsoft29.bab.searchengine.dto.ResultSearchDTO;
import com.engsoft29.bab.searchengine.service.SearchService;

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
	private SearchService service;
	
	@GET
	@Path("/{query}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Serviço de consulta.")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Resultado da pesquisa.", response = ResultSearchDTO.class),
	    @ApiResponse(code = 500, message = "Erro não esperado.", response = ResponseDTO.class)
	})
	public ResultSearchDTO search(@ApiParam(required=true, value="Query textual de pesquisa.") @PathParam("query") String query) throws Exception {
		return service.search(query);
	}
}