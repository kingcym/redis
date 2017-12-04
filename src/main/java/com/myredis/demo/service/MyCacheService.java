package com.myredis.demo.service;

import com.myredis.demo.pojo.Person;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/4 14:34
 */
@Service
@CacheConfig(cacheNames = "people")
public class MyCacheService {
    @Cacheable(key = "111")
    public String save(Person person) {
        System.out.println("为id、key为" + person.getId() + "数据查库");
        return "AAAAAAAA" + person.getId();
    }
}
