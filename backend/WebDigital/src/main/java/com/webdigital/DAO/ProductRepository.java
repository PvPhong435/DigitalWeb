package com.webdigital.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdigital.Model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
