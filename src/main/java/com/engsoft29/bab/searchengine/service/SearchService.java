package com.engsoft29.bab.searchengine.service;

import java.net.InetAddress;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.engsoft29.bab.searchengine.dto.ResultSearchDTO;
import com.engsoft29.bab.searchengine.dto.SearchDTO;
import com.engsoft29.bab.searchengine.resource.JacksonConfig;

@Stateless
public class SearchService {

	private TransportClient client;

	@SuppressWarnings("resource")
	@PostConstruct
	private void init() {
		try {
			Settings settings = Settings.builder().put("cluster.name", "babSearchEngine").build();
	
			client = new PreBuiltTransportClient(settings)
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PreDestroy
	private void destroy() {
		client.close();
	}

	/*
	public void processar(DocumentDTO dto) throws Exception {
		validar(dto);

		Document document = new Document();
		document.setChildren(dto.getUrls());
		document.setDocument(dto.getDocument());
		document.setUrl(dto.getUrl());
		
		List<String> children = new ArrayList<>();
		for (String url : dto.getUrls()) {
			Document child = new Document();
			child.setId(hash(url));
			child.setUrl(url);
			child.setPagerank(BigDecimal.ZERO);

			children.add(child.getId());

			client.prepareIndex("documents", "document", child.getId())
					.setSource(JacksonConfig.getObjectMapper().writeValueAsString(child)).get();
		}

		document.setChildren(children);
		document.setSummary(dto.getDocument().substring(0, Math.min(dto.getDocument().length(), 150)));
		document.setId(hash(dto.getUrl()));
		document.setPagerank(BigDecimal.ZERO);
		client.prepareIndex("documents", "document", document.getId())
				.setSource(JacksonConfig.getObjectMapper().writeValueAsString(document)).get();
	}

	private String hash(String text) throws NoSuchAlgorithmException {
		MessageDigest m = MessageDigest.getInstance("MD5");

		m.reset();
		m.update(text.getBytes());
		byte[] digest = m.digest();
		BigInteger bigInt = new BigInteger(1, digest);
		String hashtext = bigInt.toString(16);
		// Now we need to zero pad it if you actually want the full 32 chars.
		while (hashtext.length() < 32) {
			hashtext = "0" + hashtext;
		}

		return hashtext;
	}

	private void validar(DocumentDTO dto) throws AppException {
		if (dto == null) {
			throw new AppException("O documento é obrigatório.");
		}

		if (StringUtils.isBlank(dto.getDocument()) || StringUtils.isBlank(dto.getUrl())) {
			throw new AppException("Os campos document e url são obrigatórios.");
		}
	}
*/
	public ResultSearchDTO search(Integer start, Integer limit, String query) throws Exception {
		if(StringUtils.isBlank(query)) {
			return new ResultSearchDTO();
		}
		
		SearchResponse response = client.prepareSearch().setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(QueryBuilders.queryStringQuery(query)).setFrom(start).addSort(SortBuilders.fieldSort("pagerank").order(SortOrder.DESC)).setSize(limit).setExplain(true).get();
		ResultSearchDTO result = new ResultSearchDTO();
		result.setTotalSize(response.getHits().getTotalHits());

		for (SearchHit hit : response.getHits()) {
			result.add(JacksonConfig.getObjectMapper().readValue(hit.sourceAsString(), SearchDTO.class));
		}

		return result;
	}
}