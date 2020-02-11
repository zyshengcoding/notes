package ibm.cloud.order_server.fallback;

import ibm.cloud.order_server.service.ProductClient;
import org.springframework.stereotype.Component;

/**
 * 降级的处理  由调用方来控制 发生异常怎么办
 */
@Component
public class ProductClientFallback implements ProductClient {
    @Override
    public String findById(int id) {
        System.out.println("call interface occur exception need 降级");
        return null;
    }
}
