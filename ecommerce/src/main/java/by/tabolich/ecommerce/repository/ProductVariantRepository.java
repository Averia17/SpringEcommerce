package by.tabolich.ecommerce.repository;

import by.tabolich.ecommerce.model.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void deleteById(Long id);
}