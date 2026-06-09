package ecommerce.customer_product_api.dto.customer;

import ecommerce.customer_product_api.dto.product.ProductResponseDTO;
import ecommerce.customer_product_api.model.Address;
import ecommerce.customer_product_api.model.Customer;
import ecommerce.customer_product_api.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerResponseDTO {
    private Long id;
    private String name;
    private Address address;
    private List<ProductResponseDTO> products = new ArrayList<>();
    private Double productsTotalValue = 0.0;

    public CustomerResponseDTO(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.address = customer.getAddress();

        if (customer.getCartItems() != null) {
            this.products = customer.getCartItems().stream().map(item -> {
                Product p = item.getProduct();
                Product dummy = new Product();
                dummy.setId(p.getId());
                dummy.setName(p.getName());
                dummy.setPrice(p.getPrice());
                dummy.setQuantity(item.getQuantity());
                return new ProductResponseDTO(dummy);
            }).collect(Collectors.toList());

            this.productsTotalValue = customer.getCartItems().stream()
                    .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                    .sum();
        }
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Address getAddress() { return address; }
    public List<ProductResponseDTO> getProducts() { return products; }
    public Double getProductsTotalValue() { return productsTotalValue; }
}