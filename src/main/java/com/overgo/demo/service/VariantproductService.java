package com.overgo.demo.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.overgo.demo.model.Product;
import com.overgo.demo.model.VariantProduct;

@Service
public interface VariantproductService {

	Optional<VariantProduct> findByName(String name);
	
	VariantProduct addVariantProduct(Map<String, Object> newVariant, Product product);
	
	List<VariantProduct> getAllvariantProduct();
	
	VariantProduct updateVariantProduct(Map<String, Object> newVariant, Long Id);
	
	VariantProduct findVariantProductById(Long Id);
}
