package com.example.demo.infraestructure;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.example.demo.model.Price;
import com.example.demo.model.PriceRepository;

public class PriceAdapter {
	
	private PriceRepository priceRepository;

	public PriceAdapter (PriceRepository priceRepository) {
		this.priceRepository=priceRepository;
	}

	public List<Price> findAll(){
		ModelMapper mapper = new ModelMapper();
		
		return priceRepository
				.findAll()
				.stream()
				.map((priceEntity) -> mapper.map(priceEntity, Price.class))
				.collect(Collectors.toList());
	}
	
}
