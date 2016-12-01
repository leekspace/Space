package com.leekli.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class EventHandleController {
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;


	/**
	 * 处理算法motion 事件
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/event/motion/{token}",method=RequestMethod.GET)
	@ResponseBody
	String motionEvent(@PathVariable String token){
		
		String ping = stringRedisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.ping();
			}
		});

		return token +" " + ping;
	}
}
