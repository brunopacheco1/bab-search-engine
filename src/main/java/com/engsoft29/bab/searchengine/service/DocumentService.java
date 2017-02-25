package com.engsoft29.bab.searchengine.service;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.engsoft29.bab.searchengine.dto.DocumentDTO;
import com.engsoft29.bab.searchengine.exception.AppException;
import com.engsoft29.bab.searchengine.resource.JacksonConfig;
import com.fasterxml.jackson.core.JsonProcessingException;

@Singleton
public class DocumentService {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	private Producer<String, String> producer;
	
	@PostConstruct
	private void init() {
		Properties props = new Properties();
	    props.put("bootstrap.servers", "localhost:9092");
	    props.put("acks", "all");
	    props.put("retries", 0);
	    props.put("batch.size", 16384);
	    props.put("linger.ms", 1);
	    props.put("buffer.memory", 33554432);
	    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	    producer = new KafkaProducer<>(props);
	}
	
	@PreDestroy
	private void destroy() {
		producer.close();
	}
	
	private void validate(DocumentDTO dto) throws AppException {
		if (dto == null) {
			throw new AppException("O documento é obrigatório.");
		}

		if (StringUtils.isBlank(dto.getDocument()) || StringUtils.isBlank(dto.getUrl())) {
			throw new AppException("Os campos document e url são obrigatórios.");
		}
	}
	
	public void process(DocumentDTO dto) throws AppException {
		validate(dto);
		
		try {
			String json = JacksonConfig.getObjectMapper().writeValueAsString(dto);
			producer.send(new ProducerRecord<String, String>("documents", json));
		} catch (JsonProcessingException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}