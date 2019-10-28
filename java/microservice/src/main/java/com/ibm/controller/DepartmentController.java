package com.ibm.controller;

import com.ibm.datado.Department;
import com.ibm.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("dept")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;


    @GetMapping("/singleDept/{id}")
    public Department getdepart(@PathVariable("id") String id) {
        Department getdepart = departmentService.getdepart(id);
        return getdepart;
    }

    @GetMapping("/updateDept")
    public Department updateDepart() {
        Department department = new Department();
        department.setId("1");
        department.setDepartmentName("zhanghan");
        Department department1 = departmentService.updateDepart(department);
        return department1;
    }

    @GetMapping("/deleteDept")
    public String deleteDepart() {
        Department department = new Department();
        department.setId("1");
        departmentService.deleteDepart(department);
        return "good";
    }

}
