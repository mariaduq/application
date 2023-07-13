package com.example.demo.infraestructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.infraestructure.persistence.model.PriceEntity;

public interface PriceRepositoryJpa extends JpaRepository<PriceEntity, Long> {
}