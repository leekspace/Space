package com.leekli.demo.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;


@Configuration
public class KafkaConfig {

	public  ProducerFactory<String, String> getProducerFactory(){
		Map<String, Object> configs = new HashMap<String, Object>();
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.100.100.241:9092");
		configs.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		configs.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		return new DefaultKafkaProducerFactory<String,String>(configs);
	}
	@Bean
	public KafkaTemplate<String,String> getKafkaTemplate(){
		return  new KafkaTemplate<String,String>(getProducerFactory());
	}
	
	@Bean
	public KafkaConsumerDemo runKafkaConsumer(){
		KafkaConsumerDemo  k = new KafkaConsumerDemo();
		return k;
	}
	@Bean
	public KafkaConsumerThread runKafkaConsumerThread(){
		KafkaConsumerThread  k = new KafkaConsumerThread();
		return k;
	}
}
