package ecommerce.customer_product_api.service;

import ecommerce.customer_product_api.model.Customer;
import ecommerce.customer_product_api.model.Product;
import java.util.List;

public interface CustomerService {
    Iterable<Customer> findAll();

    Customer findById(Long id);

    void insert(Customer customer);

    void update(Long id, Customer customerModificado);

    void delete(Long id);

    List<Product> findProductsByCustomerId(Long customerId);

    void addProductToCustomer(Long customerId, Long productId, Integer quantity);

    void updateProductQuantity(Long customerId, Long productId, Integer newQuantity);
}