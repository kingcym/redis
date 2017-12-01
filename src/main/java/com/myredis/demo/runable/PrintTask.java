package com.myredis.demo.runable;
import com.myredis.demo.service.RedissonDistributedLocker;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;


/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/1 13:03
 */
public class PrintTask implements Runnable {
    private int id;
    Redisson redisson;
    public PrintTask(int id, Redisson redisson) {
        this.id = id;
        this.redisson = redisson;
    }

    @Override
    public void run() {
        try {
            RLock lock = redisson.getLock("1111");
            lock.lock();
            System.out.println(id + ":start");
            System.out.println(id + ":end");
            lock.unlock();
            System.out.println("========================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
