package com.ibm;

import com.ibm.entity.User;
import com.ibm.service.MainService;
import com.ibm.service.impl.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.concurrent.*;

@SpringBootApplication
@EnableScheduling
@EnableCaching
//@MapperScan("com.ibm.mapper")   有了@MapperScan就不需要写@Mapper注解
public class ApplicationDemo {


    @Autowired
    private MainService mainService;
    @Autowired
    DataSource dataSource;
    @Autowired
    private UserService userService;

    public static void main(String[] args) {

        SpringApplication.run(ApplicationDemo.class);

    }

/**
 *  模仿100次注册请求请求
 */
    //方式一  线程池手动实现  即在regisReq前面去掉  @Async("taskExecutor")
    //@Scheduled(cron = "10 * * * * ?")
    public void scheduleMethod() {
        ExecutorService executorService = new ThreadPoolExecutor(20, 20, 70,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i <= 30; i++) {
            executorService.execute(new Task());
        }
    }

    private class Task implements Runnable {
        @Override
        public void run() {
            User user = new User();
            user.setAge(String.valueOf((int) (Math.random() * 10)));
            user.setPassword("testPas" + String.valueOf((int) (Math.random() * 10)));
            user.setUsername("testUser" + String.valueOf((int) (Math.random() * 10)));
        }
    }

    //方式二  线程池交给spring异步方法实现  即在regisReq前面加上  @Async("taskExecutor")
    @Scheduled(cron = "10 * * * * ?")
    public void scheduleMethodBak() {
        ExecutorService executorService = new ThreadPoolExecutor(20, 20, 70,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i <= 30; i++) {
            User user = new User();
            user.setAge(String.valueOf((int) (Math.random() * 10)));
            user.setPassword("testPas" + String.valueOf((int) (Math.random() * 10)));
            user.setUsername("testUser" + String.valueOf((int) (Math.random() * 10)));
            userService.regisReq(user);
        }
    }

}
