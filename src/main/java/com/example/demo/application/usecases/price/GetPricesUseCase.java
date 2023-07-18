package com.example.demo.application.usecases.price;

import java.time.LocalDateTime;
import java.util.Comparator;

import com.example.demo.domain.port.PricesPort;
import com.example.demo.domain.model.Price;

import jakarta.persistence.EntityNotFoundException;

public class GetPricesUseCase {
	
	private PricesPort pricesPort;
	
	public GetPricesUseCase(PricesPort pricesPort){
		this.pricesPort=pricesPort;
	}
	
	public Price getCorrectPrice(LocalDateTime date, long productId) throws Exception {
	
		return pricesPort.getPrices().stream()
				.filter(price -> price.getProductId() == productId)
				.filter(price -> price.getStartDate().isBefore(date))
				.filter(price -> price.getEndDate().isAfter(date))
				.sorted(Comparator.comparingInt(Price::getPriority).reversed())
				.findFirst()
				.orElseThrow(() -> new Exception("No tenemos ningún precio para los datos introducidos. Compruébalos."));
	}
}
