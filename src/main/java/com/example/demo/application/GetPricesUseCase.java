package com.example.demo.application;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.application.ports.PricesPort;
import com.example.demo.model.Price;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GetPricesUseCase {
	
	@Autowired
	private PricesPort pricesPort;
	
	public GetPricesUseCase(PricesPort pricesPort){
		this.pricesPort=pricesPort;
	}

	private boolean validProductId(long productId) {
		List<Price> prices = pricesPort.getPrices().stream()
				.filter(price -> price.getProductId() == productId)
				.collect(Collectors.toList());
		
		if(prices.size() >= 1) return true;

		return false;
	}
	
	private boolean validBrandId(long brandId) {
		List<Price> prices = pricesPort.getPrices().stream()
				.filter(price -> price.getBrandId() == brandId)
				.collect(Collectors.toList());
		
		if(prices.size() >= 1) return true;
		
		return false;
	}
	
	private List<Price> validPrices(Timestamp date){
	
		List<Price> validPrices = pricesPort.getPrices().stream()
				.filter(price -> price.getStartDate().before(date))
				.filter(price -> price.getEndDate().after(date))
				.collect(Collectors.toList());
		
		return validPrices;
	}

	public Price getCorrectPrice(Timestamp date, long productId, long brandId) {
		
		if (!validProductId(productId)) throw new EntityNotFoundException();
		
		if (!validBrandId(brandId)) throw new EntityNotFoundException();
		
		Price price = validPrices(date).stream()
				.sorted(Comparator.comparingInt(Price::getPriority).reversed())
				.findFirst()
				.get();
			
		return price;
	}
	
}
