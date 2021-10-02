package by.tabolich.ecommerce.dao;

import by.tabolich.ecommerce.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserDao
        extends CrudRepository<User, Long> {
}