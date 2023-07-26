package com.example.demo.domain.port;

import java.util.List;

import com.example.demo.domain.model.Price;

public interface PricesPort {
	
	List<Price> getPrices();

	List<Price> getProductPrices(Long productId);
}
