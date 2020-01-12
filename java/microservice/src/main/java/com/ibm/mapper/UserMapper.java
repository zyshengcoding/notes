package com.ibm.mapper;

import com.ibm.entity.Department;
import com.ibm.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("insert into user (username,password,age,sex) values (#{username},#{password},#{age},#{sex})")
    void addUser(User user);

}
