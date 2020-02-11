package ibm.cloud.order_server.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import ibm.cloud.order_server.domain.ProductOrder;
import ibm.cloud.order_server.service.ProductClient;
import ibm.cloud.order_server.service.ProductOrderService;
import ibm.cloud.order_server.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.UUID;

@Service
public class ProductOrderServiceImpl implements ProductOrderService {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProductClient productClient;

    @Override
    public ProductOrder save(int userId, int productId) {

       // Object obj = restTemplate.getForObject("http://product-service/api/v1/product/find?id="+productId, Object.class);

        String response = productClient.findById(productId);

        JsonNode jsonNode = JsonUtils.getJson(response);

        ProductOrder productOrder = new ProductOrder();
        productOrder.setCreateTime(new Date());
        productOrder.setUserId(userId);
        productOrder.setTradeNo(UUID.randomUUID().toString());
        productOrder.setProductName(jsonNode.get("name").toString());
        productOrder.setPrice(Integer.parseInt(jsonNode.get("price").toString()));
        return productOrder;

    }
}
