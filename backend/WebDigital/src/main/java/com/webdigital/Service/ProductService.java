package com.webdigital.Service;

import com.webdigital.Model.Product;
import com.webdigital.DAO.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProductsByCategory(Long categoryID) {
        return productRepository.findByCategory_CategoryID(categoryID);
    }
    
    public List<Product> getRandomProducts() {
        return productRepository.findRandomProducts();
    }
}