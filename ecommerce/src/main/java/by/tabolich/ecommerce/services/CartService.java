package by.tabolich.ecommerce.services;

import by.tabolich.ecommerce.model.Cart;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartService {
    public Cart findOneByUser(int user_id);
}
