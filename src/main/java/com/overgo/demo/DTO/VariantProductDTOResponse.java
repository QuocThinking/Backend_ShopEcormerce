package com.overgo.demo.DTO;

import org.springframework.util.ObjectUtils;

import com.overgo.demo.model.Product;
import com.overgo.demo.model.VariantProduct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariantProductDTOResponse {

	private Long id;
	private String name;
	private String color;
	private String size;
	private int modelYear;
	private Long productId;
	private Double price;
	
	public VariantProductDTOResponse(VariantProduct variantProduct) {
		this.id = variantProduct.getId();
		this.name = variantProduct.getName();
		this.color = variantProduct.getColor();
		this.size = variantProduct.getSize();
		this.modelYear = variantProduct.getModelYear();
		this.price = variantProduct.getPrice();
		if(!ObjectUtils.isEmpty(variantProduct.getProduct())) {
			Product product = variantProduct.getProduct();
			this.productId = product.getId();
		}
	}
	
}
