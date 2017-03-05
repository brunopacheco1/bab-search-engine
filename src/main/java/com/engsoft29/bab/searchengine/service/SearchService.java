package com.engsoft29.bab.searchengine.service;

import java.net.InetAddress;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.engsoft29.bab.searchengine.dto.ResultSearchDTO;
import com.engsoft29.bab.searchengine.dto.SearchDTO;
import com.engsoft29.bab.searchengine.dto.SearchPageDTO;
import com.engsoft29.bab.searchengine.exception.AppException;
import com.engsoft29.bab.searchengine.resource.JacksonConfig;

@Stateless
public class SearchService {

	private TransportClient client;

	@SuppressWarnings("resource")
	@PostConstruct
	private void init() {
		try {
			Settings settings = Settings.builder().put("cluster.name", "babSearchEngine").build();

			client = new PreBuiltTransportClient(settings).addTransportAddress(new InetSocketTransportAddress(
					InetAddress.getByName("localhost"), 9300));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PreDestroy
	private void destroy() {
		client.close();
	}

	public ResultSearchDTO search(Integer page, Integer size, String query) throws Exception {
		if (StringUtils.isBlank(query) || page == null || size == null || page < 1 || size < 1) {
			throw new AppException("Parâmetros de consulta obrigatórios: query, page e size, seguindo as seguintes restrições: query[String].length > 0; page[Integer] > 0; size[Integer] > 0");
		}

		SearchResponse response = client.prepareSearch().setIndices("documents")
				.setQuery(QueryBuilders.queryStringQuery(query).field("document")).setFrom((page - 1) * size)
				.addSort(SortBuilders.fieldSort("pagerank").order(SortOrder.DESC)).setSize(size).setExplain(true)
				.get();
		
		ResultSearchDTO result = new ResultSearchDTO();
		Long elements = 0l;
		for (SearchHit hit : response.getHits()) {
			SearchDTO dto = JacksonConfig.getObjectMapper().readValue(hit.sourceAsString(), SearchDTO.class);
			result.add(dto);
			elements++;
		}
		
		Long totalPages = response.getHits().getTotalHits() / size;
		
		if(response.getHits().getTotalHits() % size != 0) {
			totalPages++;
		}
		
		SearchPageDTO pageDTO = new SearchPageDTO();
		pageDTO.setPage(page.longValue());
		pageDTO.setElements(elements);
		pageDTO.setTotalElements(response.getHits().getTotalHits());
		pageDTO.setTotalPages(totalPages);
		
		result.setPage(pageDTO);
		return result;
	}
}