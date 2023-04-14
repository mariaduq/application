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

	public boolean validProductId(long product_id) {
		List<Price> prices = pricesPort.getPrices().stream()
				.filter(price -> price.getProduct_id() == product_id)
				.collect(Collectors.toList());
		
		if(prices.size() >= 1) return true;

		return false;
	}
	
	public boolean validBrandId(long brand_id) {
		List<Price> prices = pricesPort.getPrices().stream()
				.filter(price -> price.getBrand_id() == brand_id)
				.collect(Collectors.toList());
		
		if(prices.size() >= 1) return true;
		
		return false;
	}
	
	public List<Price> validPrices(Timestamp date){
	
		List<Price> validPrices = pricesPort.getPrices().stream()
				.filter(price -> price.getStart_date().before(date))
				.filter(price -> price.getEnd_date().after(date))
				.collect(Collectors.toList());
		
		return validPrices;
	}

	public Price getCorrectPrice(Timestamp date, long product_id, long brand_id) {
		
		if (!validProductId(product_id)) throw new EntityNotFoundException();
		
		if (!validBrandId(brand_id)) throw new EntityNotFoundException();
		
		Price price = validPrices(date).stream()
				.sorted(Comparator.comparingInt(Price::getPriority).reversed())
				.findFirst()
				.get();
			
		return price;
	}
	
	public List<Price> findAll(){		
		return pricesPort
				.getPrices()
				.stream()
				.collect(Collectors.toList());
	}
	
}
