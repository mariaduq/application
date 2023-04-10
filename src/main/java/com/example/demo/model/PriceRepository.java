package com.example.demo.model;

import java.util.List;

import com.example.demo.infraestructure.model.PriceEntity;

public interface PriceRepository {
	
	public List<PriceEntity> findAll();
}
