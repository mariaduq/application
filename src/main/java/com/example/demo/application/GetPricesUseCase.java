package com.example.demo.application;

import java.sql.Timestamp;
import java.util.Comparator;

import com.example.demo.application.ports.PricesPort;
import com.example.demo.model.Price;

import jakarta.persistence.EntityNotFoundException;

public class GetPricesUseCase {
	
	private PricesPort pricesPort;
	
	public GetPricesUseCase(PricesPort pricesPort){
		this.pricesPort=pricesPort;
	}
	
	public Price getCorrectPrice(Timestamp date, long productId, long brandId){
	
		return pricesPort.getPrices().stream()
				.filter(price -> price.getProductId() == productId)
				.filter(price -> price.getBrandId() == brandId)
				.filter(price -> price.getStartDate().before(date))
				.filter(price -> price.getEndDate().after(date))
				.sorted(Comparator.comparingInt(Price::getPriority).reversed())
				.findFirst()
				.orElseThrow(() -> new EntityNotFoundException());
	}
}
