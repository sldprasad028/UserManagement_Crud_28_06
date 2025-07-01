package com.techpixe.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.techpixe.dto.ProductRequestDTO;
import com.techpixe.entity.Product;
import com.techpixe.exception.ApiResponse;
import com.techpixe.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController 
{
	@Autowired
	private ProductService productService;
	
	
	@PostMapping(value = "/addProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResponse<?>> registerProduct(@Valid @ModelAttribute ProductRequestDTO dto,BindingResult result) throws IOException 
	{
	    if (result.hasErrors())
	    {
	        List<Map<String, String>> errors = result.getFieldErrors().stream().map(error -> {
	            Map<String, String> err = new HashMap<>();
	            err.put("field", error.getField());
	            err.put("message", error.getDefaultMessage());
	            return err;
	        }).toList();

	        return ResponseEntity.badRequest().body(ApiResponse.failure(HttpStatus.BAD_REQUEST.value(), "Validation Failed", errors));
	    }
	    Product saved = productService.saveProduct(dto);
	    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(HttpStatus.CREATED.value(), "Product saved successfully", null));
	}
	



	
	
}
