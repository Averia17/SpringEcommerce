package by.tabolich.ecommerce.repository;

import by.tabolich.ecommerce.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleEntityRepository extends JpaRepository<RoleEntity, Long> {
    //RoleEntity findByName(String name);
    RoleEntity getRoleEntityByName(String name);
}