package com.example.demo.infraestructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.infraestructure.persistence.entities.PriceEntity;

public interface PriceRepositoryJpa extends JpaRepository<PriceEntity, Long> {
}