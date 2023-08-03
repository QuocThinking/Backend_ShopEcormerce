package com.overgo.demo.rest;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import com.overgo.demo.DTO.ProductDTOResponse;
import com.overgo.demo.contains.ResponseCode;
import com.overgo.demo.model.Category;
import com.overgo.demo.model.Product;
import com.overgo.demo.service.CategoryService;
import com.overgo.demo.service.ProductService;

@RestController
@RequestMapping(path = "/product")
public class ProductController extends BaseRestController {
	
	@Autowired
	private ProductService productService;
	
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public ResponseEntity<?> getAllProduct(){
		try {
			List<Product> products = this.productService.getAllProduct();
			List<ProductDTOResponse> response = products.stream().map(product -> new ProductDTOResponse(product)).collect(Collectors.toList());
			return super.success(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
	
	@GetMapping("/getProductById")
	public ResponseEntity<?> getProductByid(@RequestParam(name = "id" , required = false, defaultValue = "1") Long id){
		try {
			Product foundProduct = this.productService.findProductById(id);
			if(ObjectUtils.isEmpty(foundProduct)) {
				  return super.error(ResponseCode.PRODUCT_NOT_FOUND.getCode(), ResponseCode.PRODUCT_NOT_FOUND.getMessage());
			}
			return super.success(new ProductDTOResponse(foundProduct));
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
	
	@GetMapping("/getproductbycategoryId")
	public ResponseEntity<?> getAllProductByCategory(@RequestParam(defaultValue = "1") Long categoryId,
													 @RequestParam(defaultValue = "0") Integer offset,
													 @RequestParam(defaultValue = "5") Integer limit,
													 @RequestParam(defaultValue = "") String sortcategoryId){
		try {
			Pageable pageable = PageRequest.of(offset, limit);
			List<Order> orders = new ArrayList<>();
			if(!ObjectUtils.isEmpty(sortcategoryId)) {
				orders.add(new Order(convertDirection(sortcategoryId), "categoryId"));
			}
			if(!orders.isEmpty()) {
				pageable = PageRequest.of(offset, limit, Sort.by(orders));
			}
			Category foundCategory = this.categoryService.findCategoryById(categoryId);
			if(ObjectUtils.isEmpty(foundCategory)) {
				   return super.error(ResponseCode.CATEGORY_NOT_FOUND.getCode(), ResponseCode.CATEGORY_NOT_FOUND.getMessage());
			}
			List<Product> foundproducts = this.productService.findAllProductByCategoryId(categoryId);
			int startIndex = (int) pageable.getOffset();
            int endIndex = Math.min((startIndex + pageable.getPageSize()), foundproducts.size());
            List<Product> products = foundproducts.subList(startIndex, endIndex);
			List<ProductDTOResponse> response = products.stream().map(product -> new ProductDTOResponse(product)).collect(Collectors.toList());
			return super.success(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
	
	private static Direction convertDirection(String direction) {
		return direction.equalsIgnoreCase("ASC") ? Direction.ASC : Direction.DESC;
	}
	
	@PostMapping("")
	public ResponseEntity<?> addProduct(@RequestBody(required = true) Map<String, Object> newProduct, Category category){
		try {
			if(ObjectUtils.isEmpty(newProduct)||ObjectUtils.isEmpty(newProduct.get("name"))||
					ObjectUtils.isEmpty(newProduct.get("categoryId"))) {
				 return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			
			Product foundProduct = this.productService.findByName(newProduct.get("name").toString()).orElse(null);
			if(!ObjectUtils.isEmpty(foundProduct)) {
				 return super.error(ResponseCode.DATA_ALREADY_EXISTS.getCode(),
	                        ResponseCode.DATA_ALREADY_EXISTS.getMessage());
			}
			
			Long categoryId = Long.parseLong(newProduct.get("categoryId").toString());
			Category foundCategory = this.categoryService.findCategoryById(categoryId);
			if(ObjectUtils.isEmpty(foundCategory)) {
				 return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			Product insertProduct = this.productService.addProduct(newProduct, foundCategory);
			return super.success(new ProductDTOResponse(insertProduct));
		} catch (Exception e) {
			e.printStackTrace();
		}
		  return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable Long id,@RequestBody(required = true) Map<String, Object> newProduct){
		try {
			if(ObjectUtils.isEmpty(newProduct)||ObjectUtils.isEmpty(newProduct.get("name"))) {
				 return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			Product product = this.productService.findProductById(id);
			if(ObjectUtils.isEmpty(product)) {
				 return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			Product updateProduct = this.productService.updateProduct(id, newProduct);
			return super.success(new ProductDTOResponse(updateProduct));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
}
