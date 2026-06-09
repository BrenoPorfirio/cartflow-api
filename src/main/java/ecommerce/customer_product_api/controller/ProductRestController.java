package ecommerce.customer_product_api.controller;

import ecommerce.customer_product_api.dto.product.ProductRequestDTO;
import ecommerce.customer_product_api.dto.product.ProductResponseDTO;
import ecommerce.customer_product_api.model.Product;
import ecommerce.customer_product_api.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@RestController
@RequestMapping("/products")
@Tag(name = "Product", description = "Endpoints for products management")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Iterable<ProductResponseDTO>> findAll() {
        Iterable<Product> products = productService.findAll();
        List<ProductResponseDTO> dtos = StreamSupport.stream(products.spliterator(), false)
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(new ProductResponseDTO(productService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> insert(@RequestBody ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());

        product.setQuantity(dto.getQuantity() != null ? dto.getQuantity() : 0);

        productService.insert(product);
        return ResponseEntity.ok(new ProductResponseDTO(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id, @RequestBody ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());

        productService.update(id, product);

        return ResponseEntity.ok(new ProductResponseDTO(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}