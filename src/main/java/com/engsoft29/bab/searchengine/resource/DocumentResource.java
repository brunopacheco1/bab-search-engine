package com.engsoft29.bab.searchengine.resource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.engsoft29.bab.searchengine.dto.DocumentDTO;
import com.engsoft29.bab.searchengine.dto.ResponseDTO;
import com.engsoft29.bab.searchengine.service.DocumentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Stateless
@Path("/document")
@Api("servicos")
public class DocumentResource {

	@Inject
	private DocumentService service;
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Serviço de processamento dos documentos.")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "O documento foi recebido.", response = ResponseDTO.class),
	    @ApiResponse(code = 409, message = "O documento não passou pela validação dos campos.", response = ResponseDTO.class),
	    @ApiResponse(code = 500, message = "Erro não esperado.", response = ResponseDTO.class)
	})
	public ResponseDTO send(@ApiParam(required=true, value="Hashtag que será processada.") DocumentDTO dto) throws Exception {
		service.process(dto);
		
		return new ResponseDTO(true);
	}
}