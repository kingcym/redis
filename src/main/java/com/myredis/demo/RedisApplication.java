package com.myredis.demo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
@EnableCaching   //开启本地缓存
public class RedisApplication {



	@Bean
	public Redisson redissonSentinel() {
		Config config = new Config();
		config.useClusterServers()
				.setScanInterval(2000) // 集群状态扫描间隔时间，单位是毫秒
				//可以用"rediss://"来启用SSL连接
				.addNodeAddress("redis://10.82.0.102:7000")
				.addNodeAddress("redis://10.82.0.102:7001")
				.addNodeAddress("redis://10.82.0.102:7002")
				.addNodeAddress("redis://10.82.0.102:7003")
				.addNodeAddress("redis://10.82.0.102:7004")
				.addNodeAddress("redis://10.82.0.102:7005");
		return (Redisson)Redisson.create(config);
	}

	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}
}
