package com.example.demo.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.infrastructure.persistence.entities.PriceEntity;

public interface PriceRepositoryJpa extends JpaRepository<PriceEntity, Long> {
}