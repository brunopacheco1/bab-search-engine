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
		System.setProperty("aws.accessKeyId", "AKIAIMA4TRYVHVLOPMAQ");
		System.setProperty("aws.secretKey", "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");

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

		if (StringUtils.isBlank(dto.getDocument()) || StringUtils.isBlank(dto.getUrl())) {
			throw new AppException("Os campos document e url são obrigatórios.");
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
			// Now we need to zero pad it if you actually want the full 32 chars.
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}

			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}