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

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;



    @Override
    public void registerUser(User user) {

        userMapper.addUser(user);
    }

    @Override
    public void loginUser(User user) {

    }


    @Async("taskExecutor")
    @Override
    public void regisReq(User user) {
        boolean accquire = redisUtil.getLock(user.getUsername());
        if (true == accquire) {
            try {
                userMapper.addUser(user);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                redisUtil.releaseLock(user.getUsername());
            }
        }


        // System.out.println(Thread.currentThread().getName()+" ^^^^^ : ^^^^^ "+System.currentTimeMillis());

    }

}
