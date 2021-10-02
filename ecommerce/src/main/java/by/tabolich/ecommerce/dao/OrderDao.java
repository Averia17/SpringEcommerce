package by.tabolich.ecommerce.dao;

import by.tabolich.ecommerce.model.Order;
import by.tabolich.ecommerce.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderDao
        extends CrudRepository<Order, Long> {
}