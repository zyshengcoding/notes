package ibm.cloud.order_server.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import ibm.cloud.order_server.service.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {


    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping("save")
    //熔断后要处理的方法
    @HystrixCommand(fallbackMethod = "saveFail")
    public Object save(@RequestParam("user_id") int userId, @RequestParam("product_id") int productId, HttpServletRequest hsr) {

        return productOrderService.save(userId, productId);
    }


    /**
     * 熔断的处理，由于下游系统调用不通
     * <p>
     * 方法参数需要与上面的调用方法的参数保持一致
     *
     * @param uId
     * @param pId
     * @return
     */
    public Object saveFail(int uId, int pId, HttpServletRequest hsr) {

        String redisKey = "saveFail";
        String saveValue = null;
        try {
            saveValue = redisTemplate.opsForValue().get(redisKey);
        } catch (Exception e) {
            System.out.println(e);
        }
        String remoteAddr = hsr.getRemoteAddr();
        /**
         * new 线程的方式预警
         */
        String finalSaveValue = saveValue;
//        new Thread(() -> {
//            if (null == finalSaveValue) {
//                System.out.println("警報，警报，服务挂掉, id地址为："+remoteAddr);
//                redisTemplate.opsForValue().set(redisKey, "save-order-exception", 55, TimeUnit.SECONDS);
//            } else {
//                System.out.println("已经发送过警报  50S之后重试, id地址为："+remoteAddr);
//            }
//        }).start();

        /**
         * 线程池方式预警
         */
        ExecutorService executorService = new ThreadPoolExecutor(3, 5, 5, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(50), new ThreadPoolExecutor.AbortPolicy());

        executorService.execute(() -> {
                    if (finalSaveValue == null) {
                        System.out.println("警報，警报，服务挂掉, id地址为："+remoteAddr);
                        redisTemplate.opsForValue().set(redisKey, "save-order-exception", 55, TimeUnit.SECONDS);
                    } else {
                        System.out.println("已经发送过警报  55S之后重试, id地址为："+remoteAddr);
                    }
                }
        );
        executorService.shutdown();

        Map<String, Object> map = new HashMap<>();
        map.put("code", -1);
        map.put("msg", "server down");
        return map;
    }

}
