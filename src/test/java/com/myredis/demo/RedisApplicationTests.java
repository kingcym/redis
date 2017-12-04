package com.myredis.demo;

import com.myredis.demo.pojo.Person;
import com.myredis.demo.runable.PrintTask;
import com.myredis.demo.service.MyCacheService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import com.myredis.demo.service.RedissonDistributedLocker;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisApplicationTests {

	@Autowired
	RedisTemplate redisTemplate;
	@Autowired
	Redisson redisson;
	@Autowired
	RedissonDistributedLocker edissonDistributedLocker;
	@Autowired
	MyCacheService cacheService;

	//原理请参考http://www.jianshu.com/p/de5a69622e49
	@Test
	public void contextLoads1() throws InterruptedException {
		for (int i = 0; i < 1; i++) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					String key = "test123";
					RLock lock = redisson.getLock(key);
					lock.lock(1, TimeUnit.SECONDS); //这里阻塞
					System.out.println(Thread.currentThread().getName()+"---------lock.lock111-------"+lock);
					try {
						TimeUnit.SECONDS.sleep(3);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					try {
						lock.unlock();
						System.out.println(Thread.currentThread().getName()+"---------lock.unlock()-------"+lock);

					} catch (Exception e) {
						System.out.println(Thread.currentThread().getName()+"===不能释放====="+lock);
						e.printStackTrace();

					}
				}
			});
			thread.start();
		}
		TimeUnit.SECONDS.sleep(100);

	}

	@Test
	public void contextLoads12() {
		Person person = new Person();
		person.setId(1);
		String save = cacheService.save(person);
		System.out.println(save);

		String save1 = cacheService.save(person);
		System.out.println(save1);

	}



}
