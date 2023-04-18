package com.example.demo.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
