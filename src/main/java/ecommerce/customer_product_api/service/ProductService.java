package ecommerce.customer_product_api.service;

import ecommerce.customer_product_api.model.Product;

public interface ProductService {

    Iterable<Product> findAll();

    Product findById(Long id);

    void insert(Product product);

    void update(Long id, Product product);

    void delete(Long id);
}