package com.overgo.demo.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.overgo.demo.model.Category;
import com.overgo.demo.model.Product;

@Service
public interface ProductService {

	Optional<Product> findByName(String Name);
	
    List<Product> findAllProductByCategoryId(Long categoryId);
	
	Product addProduct(Map<String, Object> newProduct, Category category);
	
	List<Product> getAllProduct();
	
	Product updateProduct(Long id, Map<String, Object> newProduct);
	
	Product findProductById(Long id);
	
}
