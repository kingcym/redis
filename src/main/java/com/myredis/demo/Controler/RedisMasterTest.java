package com.myredis.demo.Controler;

import com.myredis.demo.service.RedissonDistributedLocker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/1 17:27
 */

@RestController
@RequestMapping("/redis")
public class RedisMasterTest {
    @Autowired
    RedissonDistributedLocker redissonDistributedLocker;

    @RequestMapping(value = "/a", method = RequestMethod.GET)
    public String saveHiddenDangerInfo(Integer i) {
        try {
            for (int j=0;j<5;j++){
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String key = "SSS";
                        redissonDistributedLocker.lock(key);
                        System.err.println("===============start==============");
                        try {
                            TimeUnit.SECONDS.sleep(10); //获得锁之后可以进行相应的处理
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.err.println("======获得锁后进行相应的操作======");
                        System.err.println("================end=============");
                        redissonDistributedLocker.unlock(key);
                    }
                });
                thread.start();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "ss" + i;
    }
}
