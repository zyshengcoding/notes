package com.ibm.controller;

import com.ibm.entity.User;
import com.ibm.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public void userRegister() {

        User user = new User();
        user.setSex("nan");
        user.setAge("26");
        user.setPassword("testPas");
        user.setUsername("testUser");
        //先查询  如果没有进行新增数据库 并且保存到redis
        if (false == queryRedis(user)) {
            userService.registerUser(user);
            Map<String, Object> mapValue = new HashMap<String, Object>();
            mapValue.put("username", user.getUsername());
            mapValue.put("password", user.getPassword());
            mapValue.put("sex", user.getSex());
            mapValue.put("age", user.getAge());
            String hashKey = new StringBuffer().append(user.getUsername()).append(":").append(user.getPassword()).toString();
            redisTemplate.opsForHash().putAll(hashKey, mapValue);
        }
    }

    //采用@pathvariable   请求格式：http://localhost:8089/user/login/zhang  @GetMapping("/login/{username}")与@PathVariable(value = "username")对应
    @GetMapping("/login/{username}")
    public String userLoginBypathvariable(@PathVariable(value = "username") String usr) {

        return usr;

    }


    //采用@requestparam   请求格式http://localhost:8089/user/login?username=sad   username与@RequestParam(value = "username")对应
    @GetMapping("/login")
    public String userLoginbyRequestparam(@RequestParam(value = "username") String usr) {

        return usr;

    }

    @GetMapping("/ssoLogin")
    public void userSsoLogin() {


    }


    private boolean queryRedis(User user) {
        String hashKey = new StringBuffer().append(user.getUsername()).append(":").append(user.getPassword()).toString();
        Object redisName = redisTemplate.opsForHash().get(hashKey, "username");
        if (null == redisName) {
            return false;
        } else {
            return true;
        }
    }
}
