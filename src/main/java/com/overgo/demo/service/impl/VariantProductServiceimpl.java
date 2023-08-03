package com.overgo.demo.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.overgo.demo.model.Product;
import com.overgo.demo.model.VariantProduct;
import com.overgo.demo.repository.ProductRepository;
import com.overgo.demo.repository.VariantProductRepository;
import com.overgo.demo.service.ProductService;
import com.overgo.demo.service.VariantproductService;

@Service
public class VariantProductServiceimpl implements VariantproductService {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private VariantProductRepository variantProductRepository;
	
	@Override
	public Optional<VariantProduct> findByName(String name) {
		return this.variantProductRepository.findByname(name);
	}

	@Override
	public VariantProduct addVariantProduct(Map<String, Object> newVariant, Product product) {
		VariantProduct variantProduct = new VariantProduct();
		variantProduct.setColor(newVariant.get("color").toString());
		variantProduct.setName(newVariant.get("name").toString());
//		variantProduct.setModelYear(Optional.ofNullable(newVariant.get("modelYear")).map(Object::toString)
//				.map(Integer::parseInt).orElse(null));
		variantProduct.setModelYear(Integer.parseInt(newVariant.get("modelYear").toString()));
		variantProduct.setPrice(Double.parseDouble(newVariant.get("price").toString()));
		variantProduct.setProduct(product);
		variantProduct.setSize(newVariant.get("size").toString());
		return this.variantProductRepository.save(variantProduct);
	}

	@Override
	public List<VariantProduct> getAllvariantProduct() {
		return this.variantProductRepository.findAll();
	}
	@Override
	public VariantProduct findVariantProductById(Long Id) {
		return this.variantProductRepository.findById(Id).orElse(null);
	}

	@Override
	public VariantProduct updateVariantProduct(Map<String, Object> newVariant, Long Id) {
		VariantProduct foundVariantProduct = this.findVariantProductById(Id);
		foundVariantProduct.setColor(newVariant.get("color").toString());
		foundVariantProduct.setName(newVariant.get("name").toString());
		foundVariantProduct.setModelYear(Integer.parseInt(newVariant.get("modelYear").toString()));
		foundVariantProduct.setPrice(Double.parseDouble(newVariant.get("price").toString()));
		foundVariantProduct.setSize(newVariant.get("size").toString());
		Long productId = Long.parseLong(newVariant.get("productId").toString());
		Product foundProduct = this.productService.findProductById(productId);
		foundVariantProduct.setProduct(foundProduct);
		return this.variantProductRepository.save(foundVariantProduct);
	}

	
	
}
