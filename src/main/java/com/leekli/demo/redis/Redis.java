package com.leekli.demo.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Pool;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.support.collections.DefaultRedisZSet;



@Configuration()
@ConfigurationProperties
//@PropertySource(value="file:/etc/sengled/sengled.properties")
public class Redis  {
	@Value("${REDIS_ADDR}")
	private String host = "localhost";
	
	@Value("${REDIS_PWD}")
	private String password;
	
	@Value("${REDIS_PORT}")
	private int port = 6379;
	
//	@Value("${redis.timeout}")
//	private int timeout = 1000;
//	
//	@Value("${redis.default.db}")
//	private int database = 0;
//	
//	@Value("${redis.maxIdle}")
//	private int maxIdle;
//	
//	@Value("${redis.minIdle}")
//	private int minIdle;
//
//	@Value("${redis.maxActive}")
//	private int maxActive;
//	
//	@Value("${redis.maxWait}")
//	private int maxWait;
	
	//@Value("${redis.testOnBorrow}")
	//private boolean testOnBorrow;

	@Bean(name = "org.springframework.autoconfigure.redis.RedisProperties")
	public RedisProperties getRedisProperties() {
		RedisProperties props = new RedisProperties();
		
		props.setHost(host);
		props.setPassword(password);
		props.setPort(port);
//		props.setTimeout(timeout);
//		props.setDatabase(database);

		Pool pool = new Pool();
//		pool.setMaxActive(maxActive);
//		pool.setMaxIdle(maxIdle);
//		pool.setMaxWait(maxWait);
//		pool.setMinIdle(minIdle);
		props.setPool(pool);
		
		return props;
	}
	

	
//	@Bean(name = "org.springframework.data.redis.connection.jedis.JedisConnectionFactory")
//	public JedisConnectionFactory getJedisConnectionFactory(){
//		JedisPoolConfig poolConfig = new JedisPoolConfig();
//		poolConfig.setMaxIdle(1);
//		JedisConnectionFactory jcf = new JedisConnectionFactory(poolConfig );
//		jcf.setHostName(host);
//		jcf.setPort(port);
//		return jcf;
//	}
//	
//	
//	@Bean(name = "org.springframework.data.redis.core.StringRedisTemplate")
//	public RedisTemplate<String, String> getRedisTemplate(){
//		StringRedisTemplate srt = new StringRedisTemplate(getJedisConnectionFactory());
//		return srt;
//	}
//	
}
