package com.ecom.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.model.Category;
import com.ecom.model.Product;

public interface ProductRepository extends JpaRepository<Product,Integer> {
	
       List<Product> findByCategory(Category categoryId);
}
