package com.techpixe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techpixe.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>
{

}
