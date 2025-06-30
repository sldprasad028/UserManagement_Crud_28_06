package com.techpixe.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.techpixe.dto.ProductRequestDTO;
import com.techpixe.entity.Product;

public interface ProductService 
{
	public Product saveProduct(ProductRequestDTO dto) throws IOException;
}
