package com.myredis.demo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
public class RedisApplication {
	/**
	 * 注册RedisTemplate的bean
	 * @param factory
	 * @return
	 */
	@Bean
	public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate template = new RedisTemplate();
		template.setConnectionFactory(factory);
		/**Jackson序列化*/
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		/**Jdk序列化*/
//		JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
		/**String序列化*/
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		/**将key value 进行stringRedisSerializer序列化*/
		template.setValueSerializer(stringRedisSerializer);
		template.setKeySerializer(stringRedisSerializer);
		/**将HashKey HashValue 进行序列化*/
		template.setHashKeySerializer(stringRedisSerializer);
		template.setHashValueSerializer(jackson2JsonRedisSerializer);

		template.afterPropertiesSet();
		return template;
	}


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
