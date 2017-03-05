package com.engsoft29.bab.searchengine.service;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.kinesis.producer.KinesisProducer;
import com.amazonaws.services.kinesis.producer.KinesisProducerConfiguration;
import com.engsoft29.bab.searchengine.dto.DocumentDTO;
import com.engsoft29.bab.searchengine.exception.AppException;
import com.engsoft29.bab.searchengine.resource.JacksonConfig;
import com.fasterxml.jackson.core.JsonProcessingException;

@Stateless
public class DocumentService {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	public static final String STREAM_NAME = "bab-search-engine";

	public static final String REGION = "us-east-1";

	private KinesisProducer producer;

	@PostConstruct
	private void init() {
		KinesisProducerConfiguration config = new KinesisProducerConfiguration();

		config.setRegion(REGION);

		config.setCredentialsProvider(new DefaultAWSCredentialsProviderChain());

		config.setMaxConnections(1);

		config.setRequestTimeout(60000);

		config.setRecordMaxBufferedTime(15000);

		producer = new KinesisProducer(config);
	}

	@PreDestroy
	private void destroy() {
		producer.destroy();
	}

	private void validate(DocumentDTO dto) throws AppException {
		if (dto == null) {
			throw new AppException("O documento é obrigatório.");
		}

		if (StringUtils.isBlank(dto.getDocument()) || StringUtils.isBlank(dto.getTitle())
				|| StringUtils.isBlank(dto.getUrl())) {
			throw new AppException("Os campos document, title e url são obrigatórios.");
		}
	}

	public void process(DocumentDTO dto) throws AppException {
		validate(dto);

		try {
			String json = JacksonConfig.getObjectMapper().writeValueAsString(dto);

			producer.addUserRecord(STREAM_NAME, hash(dto.getUrl()), ByteBuffer.wrap(json.getBytes()));

			producer.flush();
		} catch (JsonProcessingException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private String hash(String text) {
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");

			m.reset();
			m.update(text.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1, digest);
			String hashtext = bigInt.toString(16);
			// Now we need to zero pad it if you actually want the full 32
			// chars.
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}

			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}

		return null;
	}

	public void pagerank() throws Exception {
		try {
			Runtime rt = Runtime.getRuntime();
			Process spark = rt.exec("/usr/lib/spark/bin/spark-submit --class com.engsoft29.bab.spark.consumer.PageRanking --deploy-mode cluster --master spark://ip-10-157-128-22:7077 /home/bitnami/document-consumer-1.0-jar-with-dependencies.jar");
			spark.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("O job de pagerank não foi submetido corretamente: " + e.getMessage());
		}
	}
}