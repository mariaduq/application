package com.example.demo.model;

import java.util.List;

import com.example.demo.infraestructure.model.Price;

public interface PriceRepository {
	
	public List<Price> findAll();
}
