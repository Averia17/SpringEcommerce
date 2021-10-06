package by.tabolich.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="product_variant")
public class ProductVariant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("product_variants")
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "size")
    private String size;

    @ManyToMany
    @JsonIgnoreProperties("product_variants")
    @JoinTable(
            name="orders_product_variants_residence",
            joinColumns=@JoinColumn(name="product_variant_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="order_id", referencedColumnName="id"))
    private List<Order> orders;

    @ManyToMany
    @JsonIgnoreProperties("product_variants")
    @JoinTable(
            name="carts_product_variants_residence",
            joinColumns=@JoinColumn(name="product_variant_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="cart_id", referencedColumnName="id"))
    private List<Cart> carts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }
}
