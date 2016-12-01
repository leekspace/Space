package com.leekli.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class DemoController {

	@Autowired
	private StringRedisTemplate template;


	@RequestMapping("/index/1")
	@ResponseBody
	String home() {
		final String key = "resource:device:DD69960CE1DF6C27EBED2B7889CD8F5A:info";
		Map<String,String> map = template.execute(new RedisCallback<Map<String,String>>() {
			@Override
			public Map<String,String> doInRedis(RedisConnection arg0) throws DataAccessException {
				Map<byte[],byte[]> byteMap = arg0.hGetAll(key.getBytes());
				Map<String,String> result = new HashMap<String,String>(byteMap.size());
				for(Entry<byte[], byte[]> ent : byteMap.entrySet()){
					result.put(new String(ent.getKey()), new String(ent.getValue()));
				}
				return result;
			}
		});
		System.out.println("===ping==="+map);
		return "Hello World 1!";
	}
	


}
