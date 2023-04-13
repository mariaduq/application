package com.example.demo.infraestructure.ddbb;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.demo.application.ports.PricesPort;
import com.example.demo.model.Price;

@Service
public class PriceAdapter implements PricesPort{
	
	private PriceRepositoryJpa priceRepositoryJpa;

	public PriceAdapter (PriceRepositoryJpa priceRepositoryJpa) {
		this.priceRepositoryJpa=priceRepositoryJpa;
	}

	public List<Price> getPrices(){
		ModelMapper mapper = new ModelMapper();
		
		return priceRepositoryJpa
				.findAll()
				.stream()
				.map((priceEntity) -> mapper.map(priceEntity, Price.class))
				.collect(Collectors.toList());
	}
	
}
