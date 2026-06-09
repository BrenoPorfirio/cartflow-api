package ecommerce.customer_product_api.dto.product;

import ecommerce.customer_product_api.model.Product;

public class ProductResponseDTO {
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;

    public ProductResponseDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Double getPrice() { return price; }
    public Integer getQuantity() { return quantity; }
}