package com.overgo.demo.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.overgo.demo.model.Category;
import com.overgo.demo.model.Product;
import com.overgo.demo.repository.ProductRepository;
import com.overgo.demo.service.ProductService;

@Service
public class ProductServiceimpl implements ProductService{

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public List<Product> findAllProductByCategoryId(Long categoryId){
		return this.productRepository.findProductByCategoryId(categoryId);
	}
	
	@Override
	public Optional<Product> findByName(String name){
		return this.productRepository.findByName(name);
	}
	
	public Product findProductById(Long id) {
		return this.productRepository.findById(id).orElse(null);
	}
	
	@Override
	public List<Product> getAllProduct(){
		return this.productRepository.findAll();
	}
	
	@Override
	public Product addProduct(Map<String, Object> newProduct, Category category){
		Product product = new Product();
		product.setName(newProduct.get("name").toString());
		product.setCategory(category);
		return this.productRepository.save(product);
	}
	
	@Override
	public Product updateProduct(Long id,Map<String, Object> newProduct) {
		Product product = this.findProductById(id);
		product.setName(newProduct.get("name").toString());
		return this.productRepository.save(product);
	}
	
}
