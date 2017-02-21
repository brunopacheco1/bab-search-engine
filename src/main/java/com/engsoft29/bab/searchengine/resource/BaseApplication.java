package com.engsoft29.bab.searchengine.resource;

import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


@ApplicationPath("api")
public class BaseApplication extends Application {
	
	public BaseApplication() {
		BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0");
        beanConfig.setContact("consultoria@bab.com");
        beanConfig.setTitle("BaB Search Engine");
        beanConfig.setDescription("Motor de busca desenvolvido pela BaB Consultoria. Pilha de tecnologia: .Net, Java (JavaEE 7 - JAX-RS, EJB) e Apache Spark.");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("www.babconsultoria.com");
        beanConfig.setBasePath("/bab-search-egine/api");
        beanConfig.setResourcePackage("com.engsoft29.bab.searchengine.resource");
        beanConfig.setScan(true);
	}
}