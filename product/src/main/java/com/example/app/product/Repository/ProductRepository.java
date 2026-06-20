package com.example.app.product.Repository;

import com.example.app.product.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("""
    SELECT pr FROM Product_Table pr
    WHERE pr.active = true
    AND (
        LOWER(pr.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR
        LOWER(pr.category) LIKE LOWER(CONCAT('%', :keyword, '%'))
    )

    """)
    List<Product> searchProducts( String keyword);
}