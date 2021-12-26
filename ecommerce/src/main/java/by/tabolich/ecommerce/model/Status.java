package by.tabolich.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="\"status\"")
public class Status {
    @Id
    private Long id;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "status")
    @JsonIgnoreProperties("status")
    private List<Order> orders;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
