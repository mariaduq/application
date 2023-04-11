package com.example.demo.application.ports;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.model.Price;

@Component
public interface PricesPort {
	
	public List<Price> getPrices();
}
