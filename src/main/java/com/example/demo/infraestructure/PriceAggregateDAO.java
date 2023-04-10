package com.example.demo.infraestructure;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.example.demo.model.PriceAggregate;
import com.example.demo.model.PriceRepository;

public class PriceAggregateDAO {
	
	private PriceRepository priceRepository;

	public PriceAggregateDAO (PriceRepository priceRepository) {
		this.priceRepository=priceRepository;
	}

	public List<PriceAggregate> findAll(){
		ModelMapper mapper = new ModelMapper();
		
		return priceRepository
				.findAll()
				.stream()
				.map((priceEntity) -> mapper.map(priceEntity, PriceAggregate.class))
				.collect(Collectors.toList());
	}
	
}
