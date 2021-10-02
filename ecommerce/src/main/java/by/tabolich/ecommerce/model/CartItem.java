package by.tabolich.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="cartitem")
public class CartItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private Cart cart;

    @OneToOne
    @JoinColumn(name = "product_variant_id")
    private ProductVariant product_variant;

    public CartItem(Cart cart, ProductVariant product_variant) {
        this.cart = cart;
        this.product_variant = product_variant;
    }

    public CartItem(Long id, Cart cart, ProductVariant product_variant) {
        this.id = id;
        this.cart = cart;
        this.product_variant = product_variant;
    }

    public CartItem() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public ProductVariant getProduct_variant() {
        return product_variant;
    }

    public void setProduct_variant(ProductVariant product_variant) {
        this.product_variant = product_variant;
    }
}
