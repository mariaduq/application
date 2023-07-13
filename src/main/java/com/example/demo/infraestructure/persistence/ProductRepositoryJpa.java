package com.example.demo.infraestructure.persistence;

import com.example.demo.infraestructure.persistence.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepositoryJpa extends JpaRepository<ProductEntity, Long> {

}
