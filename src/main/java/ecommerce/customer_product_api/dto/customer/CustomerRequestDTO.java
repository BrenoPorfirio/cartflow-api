package ecommerce.customer_product_api.dto.customer;

public class CustomerRequestDTO {
    private String name;
    private String zipCode;
    private String street;
    private String neighborhood;
    private String city;
    private String state;
    private String complement;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getNeighborhood() { return neighborhood; }
    public void setNeighborhood(String neighborhood) { this.neighborhood = neighborhood; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getComplement() { return complement; }
    public void setComplement(String complement) { this.complement = complement; }
}