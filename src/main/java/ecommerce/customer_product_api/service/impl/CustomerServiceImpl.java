package ecommerce.customer_product_api.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommerce.customer_product_api.model.Customer;
import ecommerce.customer_product_api.model.CustomerRepository;
import ecommerce.customer_product_api.model.Address;
import ecommerce.customer_product_api.model.AddressRepository;
import ecommerce.customer_product_api.model.Product;
import ecommerce.customer_product_api.model.ProductRepository;
import ecommerce.customer_product_api.model.CartItem;
import ecommerce.customer_product_api.model.CartItemRepository;
import ecommerce.customer_product_api.service.CustomerService;
import ecommerce.customer_product_api.service.ViaCepService;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ViaCepService viaCepService;

    @Override
    public Iterable<Customer> findAll() { return customerRepository.findAll(); }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }

    @Override
    public void insert(Customer customer) { saveCustomerWithZipCode(customer); }

    @Override
    public void update(Long id, Customer modifiedCustomer) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        existingCustomer.setName(modifiedCustomer.getName());
        customerRepository.save(existingCustomer);
    }

    @Override
    public void delete(Long id) { customerRepository.deleteById(id); }

    @Override
    public List<Product> findProductsByCustomerId(Long customerId) {
        return findById(customerId).getCartItems().stream()
                .map(CartItem::getProduct)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public void addProductToCustomer(Long customerId, Long productId, Integer requestedQuantity) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found."));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found."));

        if (cartItemRepository.findByCustomerIdAndProductId(customerId, productId).isPresent()) {
            throw new RuntimeException("Product already in cart. Use PUT to update quantity.");
        }

        int productStock = product.getQuantity() != null ? product.getQuantity() : 0;

        if (productStock <= 0) {
            throw new RuntimeException("Product '" + product.getName() + "' is out of stock.");
        }
        if (requestedQuantity > productStock) {
            throw new RuntimeException("Insufficient stock! Available in store: " + productStock);
        }

        product.setQuantity(productStock - requestedQuantity);

        CartItem cartItem = new CartItem();
        cartItem.setCustomer(customer);
        cartItem.setProduct(product);
        cartItem.setQuantity(requestedQuantity);

        productRepository.save(product);
        cartItemRepository.save(cartItem);
    }

    @Override
    public void updateProductQuantity(Long customerId, Long productId, Integer newQuantity) {
        CartItem cartItem = cartItemRepository.findByCustomerIdAndProductId(customerId, productId)
                .orElseThrow(() -> new RuntimeException("This product is not linked to this customer."));

        Product product = cartItem.getProduct();

        int currentClientQuantity = cartItem.getQuantity() != null ? cartItem.getQuantity() : 0;
        int currentStoreStock = product.getQuantity() != null ? product.getQuantity() : 0;

        if (newQuantity <= 0) {
            throw new RuntimeException("Quantity must be greater than 0.");
        }

        int totalAvailableStock = currentStoreStock + currentClientQuantity;

        if (newQuantity > totalAvailableStock) {
            throw new RuntimeException("Insufficient stock in store! Product '" + product.getName()
                    + "' only has " + totalAvailableStock + " units available in total.");
        }

        product.setQuantity(totalAvailableStock - newQuantity);

        cartItem.setQuantity(newQuantity);

        productRepository.save(product);
        cartItemRepository.save(cartItem);
    }

    private void saveCustomerWithZipCode(Customer customer) {
        String cep = customer.getAddress().getZipCode();
        Address address = addressRepository.findById(cep).orElseGet(() -> {
            Address newAddress = viaCepService.checkZipCode(cep);
            if (newAddress != null) {
                newAddress.setZipCode(cep);
                return addressRepository.save(newAddress);
            }
            throw new RuntimeException("Could not find address.");
        });
        customer.setAddress(address);
        customerRepository.save(customer);
    }
}