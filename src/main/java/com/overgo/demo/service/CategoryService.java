package com.overgo.demo.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.overgo.demo.model.Category;

@Service
public interface CategoryService {

	Optional<Category> findByName(String name);
	
	Category addCategory(Map<String, Object> newCategory);
	
	List<Category> getAllCategory();
	
	Category findCategoryById(Long id);
	
	Category updateCategory(Long id,Map<String , Object> newCategory);
}
