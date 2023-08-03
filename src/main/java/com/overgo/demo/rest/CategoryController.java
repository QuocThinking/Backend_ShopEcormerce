package com.overgo.demo.rest;

import java.util.List;
import java.util.Map;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.overgo.demo.DTO.CategoryDTOResponse;
import com.overgo.demo.contains.ResponseCode;
import com.overgo.demo.model.Category;
import com.overgo.demo.repository.CategoryRepository;
import com.overgo.demo.service.CategoryService;

@RestController
@RequestMapping(path = "/category")
public class CategoryController extends BaseRestController{

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("")
	public ResponseEntity<?> addCategory(@RequestBody(required = true) Map<String, Object> newCategory){
		try {
			if(ObjectUtils.isEmpty(newCategory) || ObjectUtils.isEmpty(newCategory.get("name"))) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			Category category = this.categoryService.findByName(newCategory.get("name").toString()).orElse(null);
			if(!ObjectUtils.isEmpty(category)) {
				 return super.error(ResponseCode.DATA_ALREADY_EXISTS.getCode(),
	                        ResponseCode.DATA_ALREADY_EXISTS.getMessage());
			}
			Category insertCategory = this.categoryService.addCategory(newCategory);
			if(!ObjectUtils.isEmpty(insertCategory)) {
				return super.success(new CategoryDTOResponse(insertCategory));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
	
	@GetMapping("")
	public ResponseEntity<?> getAllCategory(){
		try {
			List<Category> categories = this.categoryService.getAllCategory();
			List<CategoryDTOResponse> response = categories.stream().
					map(category -> new CategoryDTOResponse(category)).collect(Collectors.toList());
			return super.success(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody(required = true) Map<String, Object> newCategory){
		try {
			if(ObjectUtils.isEmpty(newCategory)||ObjectUtils.isEmpty(newCategory.get("name"))) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			Category category = this.categoryService.findCategoryById(id);
			if(ObjectUtils.isEmpty(category)) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			Category insertCategory = this.categoryService.updateCategory(id, newCategory);
			if(ObjectUtils.isEmpty(insertCategory)) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			return super.success(new CategoryDTOResponse(insertCategory));
		} catch (Exception e) {
			e.printStackTrace();
		}
		  return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
}
