package by.tabolich.ecommerce.dao;

import by.tabolich.ecommerce.model.ProductVariant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantDao
        extends CrudRepository<ProductVariant, Long> {
}