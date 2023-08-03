package com.overgo.demo.DTO;

import com.overgo.demo.model.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTOResponse {

	private Long id;
	private String name;
	
	public CategoryDTOResponse(Category category) {
		this.id = category.getId();
		this.name = category.getName();
	}
}
