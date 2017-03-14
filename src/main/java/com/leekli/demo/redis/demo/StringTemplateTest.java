package com.leekli.demo.redis.demo;

import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class StringTemplateTest {

	@Autowired
	StringRedisTemplate stringRedisTemplate;
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	
	private final static String key = "server";
	
	@RequestMapping("/abc")
	public  String abc(){
		ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
		
		zSetOps.add(key, "1.1.1.1", 93);
		zSetOps.add(key, "2.2.2.2", 99);
		zSetOps.add(key, "3.3.3.3", 91);
		
		System.out.println("========从小到大排==========");
		Set<TypedTuple<String>> obj = zSetOps.rangeByScoreWithScores(key, 0, 100);//TODO 需要测试
		if(! obj.isEmpty()){
			 Iterator<TypedTuple<String>> it = obj.iterator();
			 while(it.hasNext()){
				 System.out.println(it.next().getValue());	 
			 }
		}
		System.out.println("========从大到小==========");
		Set<TypedTuple<String>> obj2 = zSetOps.reverseRangeByScoreWithScores(key,0,100);
		if(! obj2.isEmpty()){
			 Iterator<TypedTuple<String>> it = obj2.iterator();
			 while(it.hasNext()){
				 System.out.println(it.next().getValue());	 
			 }
			 
		}
		return "";
	}
	
}

