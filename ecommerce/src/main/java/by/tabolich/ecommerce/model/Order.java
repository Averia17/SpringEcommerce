package by.tabolich.ecommerce.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="\"order\"")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name="orders_product_variants_residence",
            joinColumns=@JoinColumn(name="order_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="product_variant_id", referencedColumnName="id"))
    private List<ProductVariant> productVariants;

    public Order(List<ProductVariant> productVariants, User user) {
        this.productVariants = productVariants;
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name="user_id")


    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ProductVariant> getProductVariants() {
        return productVariants;
    }

    public void setProductVariants(List<ProductVariant> productVariants) {
        this.productVariants = productVariants;
    }
}
