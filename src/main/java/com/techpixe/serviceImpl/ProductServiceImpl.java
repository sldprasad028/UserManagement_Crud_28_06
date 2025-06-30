package com.techpixe.serviceImpl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.techpixe.dto.ProductRequestDTO;
import com.techpixe.entity.Product;
import com.techpixe.repository.ProductRepository;
import com.techpixe.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService
{
	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product saveProduct(ProductRequestDTO dto) throws IOException 
	{
	    Product product = new Product();

	    product.setProductName(dto.getProductName());
	    
	    product.setProductPrice(dto.getProductPrice());
	    product.setCompanyName(dto.getCompanyName());
	    product.setProductUrl(dto.getFile().getBytes());
	    product.setProductImageName(dto.getFile().getOriginalFilename());
	    product.setContentType(dto.getFile().getContentType());

	    return productRepository.save(product);
	}


}
