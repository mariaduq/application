package com.example.demo.application.usecases.price;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.application.ports.PricesPort;
import com.example.demo.model.Price;

public class GetAllPricesUseCase {
	
	private PricesPort pricesPort;
	
	public GetAllPricesUseCase(PricesPort pricesPort){
		this.pricesPort=pricesPort;
	}
	
	public List<Price> findAll(){		
		return pricesPort
				.getPrices()
				.stream()
				.collect(Collectors.toList());
	}
}
