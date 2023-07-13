package com.example.demo.application.ports;

import java.util.List;

import com.example.demo.domain.Price;

public interface PricesPort {
	
	public List<Price> getPrices();

	public List<Price> getProductPrices(Long productId);
}
