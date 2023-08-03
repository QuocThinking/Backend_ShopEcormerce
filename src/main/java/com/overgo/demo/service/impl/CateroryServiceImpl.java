package com.overgo.demo.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.overgo.demo.model.Category;
import com.overgo.demo.repository.CategoryRepository;
import com.overgo.demo.service.CategoryService;

@Service
public class CateroryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepository;
	
	
	@Override
	public Optional<Category> findByName(String name){
		return this.categoryRepository.findByName(name);
	}
	

	@Override
	public Category addCategory(Map<String , Object> newCategory) {
		Category category = new Category();
		category.setName(newCategory.get("name").toString());
		return this.categoryRepository.save(category);
	}
	
	@Override
	public List<Category> getAllCategory(){
		return this.categoryRepository.findAll();
	}
	
	@Override
	public Category findCategoryById(Long id) {
		return this.categoryRepository.findById(id).orElse(null);
	}
	
	@Override
	public Category updateCategory(Long id , Map<String , Object> newCategory) {
		Category category = this.findCategoryById(id);
		category.setName(newCategory.get("name").toString());
		return this.categoryRepository.save(category);
	}
	
}
