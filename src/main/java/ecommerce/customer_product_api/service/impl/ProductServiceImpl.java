package ecommerce.customer_product_api.service.impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommerce.customer_product_api.model.Product;
import ecommerce.customer_product_api.model.ProductRepository;
import ecommerce.customer_product_api.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @Override
    public void insert(Product product) {
        productRepository.save(product);
    }

    @Override
    public void update(Long id, Product modifiedProduct) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        existingProduct.setName(modifiedProduct.getName());
        existingProduct.setPrice(modifiedProduct.getPrice());
        existingProduct.setQuantity(modifiedProduct.getQuantity());

        productRepository.save(existingProduct);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}