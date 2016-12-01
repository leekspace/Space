package com.leekli.demo.kafka;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * 线程方式启动consumer 并在10秒后关闭
 * @author liyuying
 *
 */
public class KafkaConsumerThread   implements ApplicationListener<ApplicationReadyEvent> , Runnable{
	
	static{
		System.out.println("KafkaConsumerThread ...");
	}
	
	private final AtomicBoolean closed = new AtomicBoolean(false);
    private  KafkaConsumer<String, String> consumer;

    
	/**
	 * Automatic Offset Committing
	 * 简单的自动Offset提交
	 * 自动确认消息
	 */
	private void automaticOffsetCommitting(){
		 System.out.println(" run automaticOffsetCommitting");
		 Properties props = new Properties();
	     props.put("bootstrap.servers", "10.100.100.241:9092");
	     props.put("group.id", "test-consumer-group");//消费组
	     props.put("enable.auto.commit", "true");//自动确认消息
	     props.put("auto.commit.interval.ms", "1000");
	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     consumer = new KafkaConsumer<>(props);
	     try {
			consumer.subscribe(Arrays.asList("test", "lxw1234"));//订阅的topic列表
			 while (!closed.get()) {
			     ConsumerRecords<String, String> records = consumer.poll(100);
			     //System.out.println("消息数量 ："+records.count());
			     for (ConsumerRecord<String, String> record : records)
			         System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
			 }
		} catch (Exception e) {
			if (!closed.get()) 
				throw e;
		}finally {
            consumer.close();
        }
	}

	
	/**
	 *   实现监听器ApplicationListener接口。在容器启动后，调用
	 */
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		System.out.println("onApplicationEvent  " + event);
		KafkaConsumerThread kc = new KafkaConsumerThread();
		Thread thread = new Thread(kc);
		thread.start();
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		//关闭线程
//		kc.shutdown();
	}


	@Override
	public void run() {
		automaticOffsetCommitting();
	}
	 public void shutdown() {
         closed.set(true);
         consumer.wakeup();//唤醒它，让他退出
     }
}
