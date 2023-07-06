package com.example.demo.infraestructure.ddbb;

import com.example.demo.infraestructure.ddbb.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepositoryJpa extends JpaRepository<ProductEntity, Long> {

}
