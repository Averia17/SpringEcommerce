package by.tabolich.ecommerce.dao;

import by.tabolich.ecommerce.model.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDao
        extends CrudRepository<Cart, Long> {
}