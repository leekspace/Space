package com.leekli.demo.controller;

import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class KafkaTestController {
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	@RequestMapping(value="/kafka",method=RequestMethod.GET)
	public String kafka(){
		
		int row = 50;
		
		int pre[] = new int[0];
		for(int n = 1;n <= row;n++){
			StringBuffer sb = new StringBuffer();
			for(int i0=row-n;i0>=0;i0--){
				sb.append(" ");	
			}
			System.out.println("pre.length:"+pre.length);
			if(pre.length ==0){
				pre = new int[1];
				pre[0] = 1;
				sb.append("1");
			}else{
				pre = handle(pre,sb);
			}
			kafkaTemplate.send("lxw1234", "key",sb.toString());	
		}
		
		return "kafka";
	}

	private int[] handle(int[] pre, StringBuffer sb) {
		
		int[] result = new int[pre.length+1];
		if(pre.length == 1){
			result[0] = 1;
			result[1] = 1;
			sb.append("1 1");
			return result;
		}
		
		result[0] = 1;
		sb.append(1+" ");
		for(int i=0;i<pre.length-1;i++){
			sb.append(pre[i]+pre[i+1]+" ");
			result[i+1] = pre[i]+pre[i+1];
		}
		result[result.length-1] = 1;
		sb.append(1+" ");
 
		return result;
	}
	
 
}
