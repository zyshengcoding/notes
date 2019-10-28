package com.ibm;

import com.ibm.service.MainService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootApplication
@EnableScheduling
@EnableCaching
//@MapperScan("com.ibm.mapper")   有了@MapperScan就不需要写@Mapper注解
public class ApplicationDemo {


    @Autowired
    private MainService mainService;
    @Autowired
    DataSource dataSource;

    public static void main(String[] args) {

        SpringApplication.run(ApplicationDemo.class);

    }


    //@Scheduled(cron = "10 * * * * ?")
    public void scheduleMethod() {
        System.out.println("start task");
        System.out.println(dataSource.getClass());
        mainService.testJdbcMethod();
    }


}
