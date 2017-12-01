package com.myredis.demo.service;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/1 11:46
 */
@Service
public class RedissonDistributedLocker {
    @Autowired
    RedissonClient redissonClient;
    private static final String LOCK_TITLE = "redisLock_";
    private static RLock lock;

    public void lock(String lockKey) {
        String key = LOCK_TITLE + lockKey;
        lock = redissonClient.getLock(key);
        try {
            TimeUnit.MICROSECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("lock======================" + lock);
        lock.lock(100, TimeUnit.SECONDS);
    }

    public void unlock(String lockKey) {
        String key = LOCK_TITLE + lockKey;
     //   lock = redissonClient.getLock(key);
        System.out.println("unlock===================" + lock);
        lock.unlock();
    }
}
