package com.example.demo.infraestructure.ddbb;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.infraestructure.ddbb.model.PriceEntity;

import java.util.List;
import java.util.Optional;

public interface PriceRepositoryJpa extends JpaRepository<PriceEntity, Long> {
    Optional <List<PriceEntity>> findByProductId(Long productId);
}