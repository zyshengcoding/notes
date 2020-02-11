package ibm.cloud.provider_server.service;


import ibm.cloud.provider_server.domain.Product;

import java.util.List;

public interface ProductService {

    List<Product> listProduct();

    Product findById(int id);


}
