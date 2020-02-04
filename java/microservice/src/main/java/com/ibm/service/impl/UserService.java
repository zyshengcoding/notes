package com.ibm.service.impl;

import com.ibm.entity.User;

import java.util.Map;

public interface UserService {

    void registerUser(User user);

    void loginUser(User user);


    void regisReq(User user, Map<String, String> map);

}
