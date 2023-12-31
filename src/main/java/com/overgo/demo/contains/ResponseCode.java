package com.overgo.demo.contains;

public enum ResponseCode {

	   SUCCESS(200, "OK"),
	    NOT_FOUND(404, "Not found"),
	    NO_PARAM(6001, "No param"),
	    NO_CONTENT(2004, "No content"),
	    INTERNAL_SERVER_ERROR(5000, "Internal server error"),
	    USER_NOT_FOUND(4005, "User not found"),
	    CATEGORY_NOT_FOUND(4006, "Category not found"),
	    PRODUCT_NOT_FOUND(4007, "Product not found"),
	    VARIANT_PRODUCT_NOT_FOUND(4008, "Variant product not found"),
	    ADDRESS_NOT_FOUND(4009, "Address not found"),
	    CART_NOT_FOUND(4009, "Cart not found"),
	    FAILED_LOGIN(3000, "Failed login"),
	    DATA_ALREADY_EXISTS(2023, "Data already exists");

	    private int code;
	    private String message;

	    public int getCode() {
	        return code;
	    }

	    public String getMessage() {
	        return message;
	    }

	    private ResponseCode(int code, String message) {
	        this.code = code;
	        this.message = message;
	    }
}
