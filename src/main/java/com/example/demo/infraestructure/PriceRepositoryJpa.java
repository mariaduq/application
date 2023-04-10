package com.example.demo.infraestructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.infraestructure.model.PriceEntity;

public interface PriceRepositoryJpa extends JpaRepository<PriceEntity, Long> {
	
}