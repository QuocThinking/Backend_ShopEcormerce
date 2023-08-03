package com.overgo.demo.DTO;

import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariantProductDTORequest {

	private String name;
	private int modelYear;
	private Double price;
	private String size;
	private String color;
	private Long productId;
	
	public VariantProductDTORequest(Map<String, Object> newVariant) {
		this.name = newVariant.get("name").toString();
		//this.modelYear = Optional.ofNullable(newVariant.get("modelYear")).map(Object::toString).map(Integer::parseInt).orElse(null);
		this.price = Double.parseDouble(newVariant.get("price").toString());
		this.modelYear = Integer.parseInt(newVariant.get("modelYear").toString());
		if (newVariant.containsKey("modelYear") && newVariant.get("modelYear") != null) {
	        try {
	            this.modelYear = Integer.parseInt(newVariant.get("modelYear").toString());
	        } catch (NumberFormatException e) {
	            // Handle if the value cannot be parsed as an integer
	            this.modelYear = 0; // or any appropriate default value
	        }
	    } else {
	        // Handle the "modelYear" key is missing or with null value
	        this.modelYear = 0; // or any appropriate default value
	    }
		this.color = newVariant.get("color").toString();
		this.size = newVariant.get("size").toString();
		this.productId = Long.parseLong(newVariant.get("productId").toString());
	}
	
	
}
