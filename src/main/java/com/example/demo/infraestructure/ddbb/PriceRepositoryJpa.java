package com.example.demo.infraestructure.ddbb;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.infraestructure.ddbb.model.PriceEntity;

public interface PriceRepositoryJpa extends JpaRepository<PriceEntity, Long> {
	
}