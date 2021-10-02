package by.tabolich.ecommerce.model;

import javax.persistence.*;

import com.sun.istack.NotNull;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="menu")
public class Menu implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", length = 255)
    private String title;

    @ManyToMany
    @JoinTable(
            name="menus_products_residence",
            joinColumns=@JoinColumn(name="menu_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="product_id", referencedColumnName="id"))
    private List<Product> products;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Product> getProduct() {
        return products;
    }

    public void setProduct(List<Product> product) {
        this.products = product;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
