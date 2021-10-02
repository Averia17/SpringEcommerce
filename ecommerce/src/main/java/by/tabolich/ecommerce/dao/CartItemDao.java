package by.tabolich.ecommerce.dao;


import by.tabolich.ecommerce.model.CartItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemDao
        extends CrudRepository<CartItem, Long> {
}