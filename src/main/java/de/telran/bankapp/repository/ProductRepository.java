package de.telran.bankapp.repository;

import de.telran.bankapp.entity.Product;
import de.telran.bankapp.entity.enums.ProductStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByName(String name);

    List<Product> findByStatus(ProductStatus status);

    @Query("update Product c set c.status = :status where c.id = :id")
    @Modifying
    @Transactional
    int updateStatus(Long id, ProductStatus status);

}
