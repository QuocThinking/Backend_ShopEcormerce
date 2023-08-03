package com.overgo.demo.DTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.util.ObjectUtils;

import com.overgo.demo.model.Category;
import com.overgo.demo.model.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTOResponse {
	private Long id;
	private Long categoryId;
	private String name;
	
	
	public ProductDTOResponse(Product product) {
		this.id = product.getId();
		this.name = product.getName();
	if(!ObjectUtils.isEmpty(product.getCategory())) {
		Category category = product.getCategory();
		this.categoryId = category.getId();
	}
	}
	
}
