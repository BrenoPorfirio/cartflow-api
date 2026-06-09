package ecommerce.customer_product_api.controller;

import ecommerce.customer_product_api.dto.customer.CustomerRequestDTO;
import ecommerce.customer_product_api.dto.customer.CustomerResponseDTO;
import ecommerce.customer_product_api.model.Address;
import ecommerce.customer_product_api.model.Customer;
import ecommerce.customer_product_api.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/customers")
@Tag(name = "Customers", description = "Endpoints for customers management")
public class CustomerRestController {

    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<Iterable<CustomerResponseDTO>> findAll() {
        Iterable<Customer> customers = customerService.findAll();
        List<CustomerResponseDTO> dtos = StreamSupport.stream(customers.spliterator(), false)
                .map(CustomerResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> findById(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        return ResponseEntity.ok(new CustomerResponseDTO(customer));
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> insert(@RequestBody CustomerRequestDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        Address address = new Address();
        address.setZipCode(dto.getZipCode());
        customer.setAddress(address);

        customerService.insert(customer);
        return ResponseEntity.ok(new CustomerResponseDTO(customer));
    }

    @PostMapping("/{id}/products/{productId}")
    public ResponseEntity<CustomerResponseDTO> addProductToCustomer(
            @PathVariable Long id,
            @PathVariable Long productId,
            @RequestParam(defaultValue = "1") Integer quantity) {

        customerService.addProductToCustomer(id, productId, quantity);
        Customer updatedCustomer = customerService.findById(id);
        return ResponseEntity.ok(new CustomerResponseDTO(updatedCustomer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable Long id, @RequestBody CustomerRequestDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        Address address = new Address();
        address.setZipCode(dto.getZipCode());
        address.setStreet(dto.getStreet());
        address.setNeighborhood(dto.getNeighborhood());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setComplement(dto.getComplement());
        customer.setAddress(address);

        customerService.update(id, customer);
        return ResponseEntity.ok(new CustomerResponseDTO(customerService.findById(id)));
    }

    @PutMapping("/{id}/products/{productId}")
    public ResponseEntity<CustomerResponseDTO> updateProductQuantity(
            @PathVariable Long id,
            @PathVariable Long productId,
            @RequestParam Integer quantity) {

        customerService.updateProductQuantity(id, productId, quantity);
        return ResponseEntity.ok(new CustomerResponseDTO(customerService.findById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.ok().build();
    }
}