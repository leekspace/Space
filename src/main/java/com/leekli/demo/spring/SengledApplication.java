package com.leekli.demo.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationContextEvent;


@Configuration
@ConfigurationProperties
public class SengledApplication <T extends ServerContext> implements ApplicationListener<ApplicationContextEvent>{

	@Override
	public void onApplicationEvent(ApplicationContextEvent event) {
			System.out.println("===============================");
	}

}
