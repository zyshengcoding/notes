package com.ibm.service;

import com.ibm.entity.User;
import com.ibm.mapper.UserMapper;
import com.ibm.service.impl.UserService;
import com.ibm.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public void registerUser(User user) {

        userMapper.addUser(user);
    }

    @Override
    public void loginUser(User user) {

    }


    @Async("taskExecutor")
    @Override
    public void regisReq(User user, Map<String, String> map) {
        String parimaryKey = "parimaryKey";
        boolean accquire = redisUtil.getLock(user.getUsername());
        if (true == accquire) {
            try {
                Long increAge = redisTemplate.opsForValue().increment(parimaryKey,1);
                long lage = increAge.longValue();
                String sAge = String.valueOf(lage);
                user.setAge(sAge);
                map.put("age",user.getAge());
                userMapper.addUser(user);
                redisTemplate.opsForHash().putAll(user.getAge()+":"+user.getUsername(),map);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                redisUtil.releaseLock(user.getUsername());
            }
        }


        // System.out.println(Thread.currentThread().getName()+" ^^^^^ : ^^^^^ "+System.currentTimeMillis());

    }

}
