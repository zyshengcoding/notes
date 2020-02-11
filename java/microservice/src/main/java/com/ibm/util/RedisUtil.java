package com.ibm.util;

import com.ibm.config.ThreadLocalConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 自定义分布式锁
 */

@Component
public class RedisUtil {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private ThreadLocalConf threadLocalConf = new ThreadLocalConf();

    /**
     * 最终加强分布式锁
     *
     * @param key key值
     * @return 是否获取到
     */
    public boolean getLock(String key) {
        String redisClientID = UUID.randomUUID().toString();
        threadLocalConf.setThreadLocal(redisClientID);
        Boolean aBoolean = stringRedisTemplate.opsForValue().setIfAbsent(key, redisClientID, 10, TimeUnit.SECONDS);
        if (false == aBoolean) {
            System.out.println(Thread.currentThread().getName() + " thread key is " + key + "  抢占失败");
            return false;
        }
        System.out.println(Thread.currentThread().getName() + " thread key is " + key);
        return true;
    }

    /**
     * 解锁操作
     *
     * @param key
     * @return
     */
    public void releaseLock(String key) {

        if (stringRedisTemplate.opsForValue().get(key).equals(threadLocalConf.getThreadLocal())) {
            System.out.println("deletekey sucss this key is " + key + "  current threadname is " + Thread.currentThread().getName()
                    + "  keyvalueis " + threadLocalConf.getThreadLocal() + "  redis value ");
            stringRedisTemplate.delete(key);
        }
    }
}
