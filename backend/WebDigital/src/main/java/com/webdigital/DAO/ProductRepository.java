package com.webdigital.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webdigital.Model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByCategory_CategoryID(Long categoryID);
	
    @Query(value = "SELECT * FROM Product ORDER BY RANDOM() LIMIT 5", nativeQuery = true)
    List<Product> findRandomProducts();
}
