package com.ibm.service;

import com.ibm.datado.Department;
import com.ibm.jdbc.MainJdbc;
import com.ibm.mapper.MianMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {


    @Autowired
    private MianMapper mianMapper;

    @Cacheable(cacheNames = "dept",key="#id")
    public Department getdepart(String id) {
        Department getdept = mianMapper.getdept(id);
        return getdept;
    }


    //需要有返回值，负责@Cacheable无法拿到更新的值
    @CachePut(cacheNames = "dept",key = "#result.id")
    public Department updateDepart(Department department) {
         mianMapper.updateDept(department);
         return department;
    }

    @CacheEvict(cacheNames = "dept",key="#department.id")//属性beforeInvocation  为true表示在方法执行之前清除的
    public void deleteDepart(Department department) {
        System.out.println("delete success"+department);
    }

}
