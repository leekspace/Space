package com.leekli.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.leekli.demo.ApplicationListener.ApplicationContextEventTest;
import com.leekli.demo.ApplicationListener.ApplicationEventTest;
import com.leekli.demo.ApplicationListener.ApplicationReadyEventTest;
import com.leekli.demo.ApplicationListener.ApplicationStartedEventTest;
import com.leekli.demo.ApplicationListener.SpringApplicationEventTest;
import com.leekli.demo.controller.DemoController;
import com.leekli.demo.controller.EventHandleController;
import com.leekli.demo.controller.KafkaTestController;
import com.leekli.demo.kafka.KafkaConfig;
import com.leekli.demo.redis.Redis;
import com.leekli.demo.redis.demo.StringTemplateTest;


@Configuration
@ComponentScan 
public class MainRun {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(new Object[] { Redis.class,
											 KafkaConfig.class,
											 DemoController.class, 
											 EventHandleController.class ,
											 KafkaTestController.class,
											 StringTemplateTest.class,
											 
											 ApplicationEventTest.class,
											 SpringApplicationEventTest.class,
											 
											 ApplicationContextEventTest.class,
											 ApplicationReadyEventTest.class,
											 ApplicationStartedEventTest.class,
											 
											 
											 
											}, args);
		
		
		
	}
}
