package com.example.demo.infraestructure;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.example.demo.model.PriceAggregate;

public class PriceAggregateDAO {
	
	public PriceRepository priceRepository;
	
	public PriceAggregateDAO (PriceRepository priceRepository) {
		this.priceRepository=priceRepository;
	}
	
	public List<PriceAggregate> allPrices(){
		ModelMapper mapper = new ModelMapper();
		
		return priceRepository
				.findAll()
				.stream()
				.map((priceEntity) -> mapper.map(priceEntity, PriceAggregate.class))
				.collect(Collectors.toList());
	}
}
