package com.finley.common.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by 郑远锋 on 2017/3/14.
 */

@Service
public class RedisApplicationCacheImp implements ApplicationCache {

    @Autowired
    private RedisTemplate _redisTemplate;

    /**
     * 静态化实例
     */
    private static RedisTemplate redisTemplate;

    /**
     * 类型完成实例化后，给类型注入静态值
     */
    public
    @PostConstruct  void initTemplate() {
        redisTemplate = this._redisTemplate;
    }

    @Override
    public  <T> void addObject(String key, T obj) throws Exception {
        try {
            ValueOperations<String, T> value = redisTemplate.opsForValue();
            value.set(key, obj);
        } catch (Exception e) {
            throw e;
        }

    }
    @Override
    public <T> void addObject(String key, T obj, long duration) throws Exception {
        try {
            ValueOperations<String, T> value = redisTemplate.opsForValue();
            value.set(key, obj,duration);
        } catch (Exception e) {
            throw e;
        }
    }
    @Override
    public  <T> T getObject(String key) throws Exception {
        try {
            ValueOperations<String, T> value = redisTemplate.opsForValue();
            return value.get(key);
        } catch (Exception e) {
            throw e;
        }

    }
    @Override
    public  void removeObject(String key) throws Exception {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            throw e;
        }
    }

}
