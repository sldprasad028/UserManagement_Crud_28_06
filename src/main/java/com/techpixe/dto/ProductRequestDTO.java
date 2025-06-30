package com.techpixe.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductRequestDTO {

    @NotBlank(message = "Product Name is required")
    @Size(min = 6, max = 50, message = "Product Name must be between 6 and 50 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Product Name must contain only letters and spaces")
    private String productName;

    @NotNull(message = "Product Price is required")
    @DecimalMin(value = "0.01", message = "Product Price must be positive")
    private Double productPrice;

    @NotBlank(message = "Company Name is required")
    @Size(min = 2, max = 50, message = "Company Name must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Company Name must contain only letters and spaces")
    private String companyName;

    @NotNull(message = "Product file is required")
    private MultipartFile file;

   
}
