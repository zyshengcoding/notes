package com.ibm.controller;


import com.ibm.entity.Department;
import com.ibm.service.MainService;
import com.ibm.vo.Person;
import com.ibm.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/test")
public class MainController {

    @Autowired
    UserVo userVo;

    @Autowired
    Person person;

    @Autowired
    private MainService mainService;

    @ResponseBody
    @RequestMapping("/getString")
    public String getString() {

        System.out.println(userVo);
        System.out.println(person);
        return "Hello";
    }

    /**
     * @return
     * @Cacheable 缓存方法
     * testMapperCache如果是缓存标注的方法，那么debug断电都进不去，相当于里面的逻辑不会执行
     */
    @ResponseBody
    @RequestMapping("/testCache")
    public String testCache() {
        List<Department> departments = mainService.testMapperCache();
        if (null == departments) {
            return "false";
        } else {
            for (Department dept : departments) {
                System.out.println(dept.getId() + ">> : <<" + dept.getDepartmentName());
            }
            return "Hello";
        }
    }

}
