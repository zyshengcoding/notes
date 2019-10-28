package com.ibm.mapper;

import com.ibm.datado.Department;
import com.ibm.datado.Employee;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MianMapper {

    @Select("select * from department")
     List<Department> getDeptList();

    @Update("update department set departmentName = #{departmentName} where id = #{id}")
    void updateDept(Department department);

    @Select("select * from department where id = #{id}")
    Department getdept(String id);

    @Delete("delete * from department where id = #{id}")
    void delDept(String id);

}
