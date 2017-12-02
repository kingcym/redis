package com.myredis.demo;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RedisApplication {


	@Bean
	Redisson redissonSentinel() {
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
