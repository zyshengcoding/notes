package com.ibm.jdbc;

import com.ibm.datado.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class MainJdbc {


    @Autowired
    JdbcTemplate jdbcTemplate;

    public Map<String, Object> getDepart() {
        List<Map<String, Object>> listForResult = jdbcTemplate.queryForList("select * from department");
        if (0 == listForResult.size()) {
            return null;
        } else {
            return listForResult.get(0);
        }

    }

}
