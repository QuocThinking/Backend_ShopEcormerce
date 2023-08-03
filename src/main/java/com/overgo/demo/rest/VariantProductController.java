package com.overgo.demo.rest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.overgo.demo.DTO.VariantProductDTORequest;
import com.overgo.demo.DTO.VariantProductDTOResponse;
import com.overgo.demo.contains.ResponseCode;
import com.overgo.demo.model.Product;
import com.overgo.demo.model.VariantProduct;
import com.overgo.demo.service.ProductService;
import com.overgo.demo.service.VariantproductService;



@RestController
@RequestMapping("/variantProduct")
public class VariantProductController extends BaseRestController{

	@Autowired
	private VariantproductService variantproductService;
	
	@Autowired
	private ProductService productService;
	
	
	@GetMapping("")
	@Transactional(noRollbackFor = { Exception.class })
	public ResponseEntity<?> getAllvariantProduct(){
		try {
			
			List<VariantProduct> variantProducts = this.variantproductService.getAllvariantProduct();
			List<VariantProductDTOResponse> responses = variantProducts.stream().map(VariantProductDTOResponse::new).toList();
			return super.success(responses);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
	
	
	@PostMapping("")
	public ResponseEntity<?> addVariantProduct(@RequestBody(required = true) Map<String, Object> newVariant){
		
		try {
			if(ObjectUtils.isEmpty(newVariant)) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			VariantProductDTORequest request = new VariantProductDTORequest(newVariant);
			if(ObjectUtils.isEmpty(request.getColor())||ObjectUtils.isEmpty(request.getName())||
					ObjectUtils.isEmpty(request.getProductId())||ObjectUtils.isEmpty(request.getSize())||
					ObjectUtils.isEmpty(request.getModelYear())||ObjectUtils.isEmpty(request.getPrice())) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			VariantProduct variantProduct = this.variantproductService.findByName(newVariant.get("name").toString()).orElse(null);
			if(!ObjectUtils.isEmpty(variantProduct)) {
				return super.error(ResponseCode.DATA_ALREADY_EXISTS.getCode(), ResponseCode.DATA_ALREADY_EXISTS.getMessage());
			}
			Long productId = Long.parseLong(newVariant.get("productId").toString());
			Product foundProduct = this.productService.findProductById(productId);
			if(ObjectUtils.isEmpty(foundProduct)) {
				 return super.error(ResponseCode.PRODUCT_NOT_FOUND.getCode(), ResponseCode.PRODUCT_NOT_FOUND.getMessage());
			}
			VariantProduct insertProduct = this.variantproductService.addVariantProduct(newVariant, foundProduct);
			return super.success(new VariantProductDTOResponse(insertProduct));
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateVariantProduct(@PathVariable Long id, @RequestBody(required = true) Map<String, Object> newVariant){
		try {
			if(ObjectUtils.isEmpty(newVariant)) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			VariantProductDTORequest request = new VariantProductDTORequest(newVariant);
			if(ObjectUtils.isEmpty(request.getColor())||ObjectUtils.isEmpty(request.getSize())||ObjectUtils.isEmpty(request.getPrice())
					||ObjectUtils.isEmpty(request.getName())||ObjectUtils.isEmpty(request.getModelYear())||ObjectUtils.isEmpty(request.getProductId())) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			VariantProduct foundProduct = this.variantproductService.findVariantProductById(id);
			if(ObjectUtils.isEmpty(foundProduct)) {
				 return super.error(ResponseCode.PRODUCT_NOT_FOUND.getCode(), ResponseCode.PRODUCT_NOT_FOUND.getMessage());
			}
			VariantProduct updateVariantProduct = this.variantproductService.updateVariantProduct(newVariant, id);
			
			return super.success(new VariantProductDTOResponse(updateVariantProduct));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
	
}
