package com.leekli.demo.kafka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;


public class KafkaConsumerDemo   implements ApplicationListener<ApplicationReadyEvent>{
	
	static{
		System.out.println("KafkaConsumer ...");
	}

    
	/**
	 * Automatic Offset Committing
	 * 简单的自动Offset提交
	 * 也就是说会自动确认消息
	 */
	private void c1(){
		 System.out.println(" run c1");
		 Properties props = new Properties();
	     props.put("bootstrap.servers", "10.100.100.241:9092");
	     props.put("group.id", "test-consumer-group");//消费组
	     props.put("enable.auto.commit", "true");//自动确认消息
	     props.put("auto.commit.interval.ms", "1000");
	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
	     consumer.subscribe(Arrays.asList("test", "lxw1234"));//订阅的topic列表
	     while (true) {
	         ConsumerRecords<String, String> records = consumer.poll(100);
	         System.out.println("消息数量 ："+records.count());
	         for (ConsumerRecord<String, String> record : records)
	             System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
	     }
	}

	/**
	 * Manual Offset Control
	 * 手动控制 ，手动确认消息
	 */
	private void  manualOffsetControl(){
		 Properties props = new Properties();
	     props.put("bootstrap.servers", "10.100.100.241:9092");
	     props.put("group.id", "test-consumer-group");
	     props.put("enable.auto.commit", "false");//手动确认消息
	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
	     consumer.subscribe(Arrays.asList("test", "lxw1234"));//订阅的topic列表
	     final int minBatchSize = 10;
	     List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
	     while (true) {
	         ConsumerRecords<String, String> records = consumer.poll(100);
	         for (ConsumerRecord<String, String> record : records) {
	             buffer.add(record);
	         }
	         if (buffer.size() >= minBatchSize) {
	        	 System.out.println("==============================10一次======"+minBatchSize);
	             System.out.println("insertIntoDb(buffer)");
	             consumer.commitSync();//相当于确认消费
	             buffer.clear();
	         }
	     }
	}
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		System.out.println("onApplicationEvent  " + event);
		//c1();
		//manualOffsetControl();
		
	}
}
