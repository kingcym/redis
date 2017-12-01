package com.myredis.demo.service;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/1 11:45
 */
public interface DistributedLocker {

    //获取锁
    void lock(String lockKey, TimeUnit unit , int timeout);

    //释放锁
    void unlock();


}
