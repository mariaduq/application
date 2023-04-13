package com.example.demo.infraestructure.ddbb;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.demo.application.ports.PricesPort;
import com.example.demo.model.Price;
import com.example.demo.model.PriceRepository;

@Service
public class PriceAdapter implements PricesPort{
	
	private PriceRepository priceRepository;

	public PriceAdapter (PriceRepository priceRepository) {
		this.priceRepository=priceRepository;
	}

	public List<Price> getPrices(){
		ModelMapper mapper = new ModelMapper();
		
		return priceRepository
				.findAll()
				.stream()
				.map((priceEntity) -> mapper.map(priceEntity, Price.class))
				.collect(Collectors.toList());
	}
	
}
